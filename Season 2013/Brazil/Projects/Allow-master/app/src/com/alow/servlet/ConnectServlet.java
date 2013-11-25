/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alow.servlet;

import static com.alow.dao.ofy.OfyService.ofy;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alow.exception.DaoException;
import com.alow.exception.EntityExistsException;
import com.alow.model.Pessoa;
import com.alow.model.plus.DirectedUserToUserEdge;
import com.alow.model.plus.Jsonifiable;
import com.alow.model.plus.User;
import com.alow.service.PessoaService;
import com.alow.service.impl.PessoaServiceImpl;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.PeopleFeed;
import com.google.api.services.plus.model.Person;
import com.google.gson.annotations.Expose;

/**
 * Provides an API to connect users to Photohunt.  This servlet provides the
 * /api/connect end-point, and exposes the following operations:
 *
 *   POST /api/connect
 *
 * @author silvano@google.com (Silvano Luciani)
 */
public class ConnectServlet extends JsonRestServlet {

  /**
   * Exposed as `POST /api/connect`.
   *
   * Takes the following payload in the request body.  The payload represents
   * all of the parameters that are required to authorize and connect the user
   * to the app.
   * {
   *   "state":"",
   *   "access_token":"",
   *   "token_type":"",
   *   "expires_in":"",
   *   "code":"",
   *   "id_token":"",
   *   "authuser":"",
   *   "session_state":"",
   *   "prompt":"",
   *   "client_id":"",
   *   "scope":"",
   *   "g_user_cookie_policy":"",
   *   "cookie_policy":"",
   *   "issued_at":"",
   *   "expires_at":"",
   *   "g-oauth-window":""
   * }
   *
   * Returns the following JSON response representing the User that was
   * connected:
   * {
   *   "id":0,
   *   "googleUserId":"",
   *   "googleDisplayName":"",
   *   "googlePublicProfileUrl":"",
   *   "googlePublicProfilePhotoUrl":"",
   *   "googleExpiresAt":0
   * }
   *
   * Issues the following errors along with corresponding HTTP response codes:
   * 401: The error from the Google token verification end point.
   * 500: "Failed to upgrade the authorization code." This can happen during
   *      OAuth v2 code exchange flows.
   * 500: "Failed to read token data from Google."
   *      This response also sends the error from the token verification
   *      response concatenated to the error message.
   * 500: "Failed to query the Google+ API: " 
   *      This error also includes the error from the client library
   *      concatenated to the error response.
   * 500: "IOException occurred." The IOException could happen when any
   *      IO-related errors occur such as network connectivity loss or local
   *      file-related errors.
   *
   * @see javax.servlet.http.HttpServlet#doPost(
   *     javax.servlet.http.HttpServletRequest,
   *     javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    TokenData accessToken = null;
    try {
      // read the token
      accessToken = Jsonifiable.fromJson(req.getReader(), TokenData.class);
    } catch (IOException e) {
      sendError(resp, 400, "Unable to read auth result from request body");
    }
    
    // Create a credential object.
    GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY).setTransport(TRANSPORT)
        .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build();
    
    try {
      if (accessToken.code != null) {
        // exchange the code for a token (Web Frontend)
        GoogleTokenResponse tokenFromExchange = exchangeCode(accessToken);
        credential.setFromTokenResponse(tokenFromExchange);
      } else {
    	if (accessToken.access_token == null) {
          sendError(resp, 400, "Missing access token in request.");
    	}
    	
        // use the token received from the client
        credential.setAccessToken(accessToken.access_token)
            .setRefreshToken(accessToken.refresh_token)
            .setExpiresInSeconds(accessToken.expires_in)
            .setExpirationTimeMilliseconds(accessToken.expires_at);
      }
      // ensure that we consider logged in the user that owns the access token
      String tokenGoogleUserId = verifyToken(credential);
      User user = saveTokenForUser(tokenGoogleUserId, credential);
      // save the user in the session
      HttpSession session = req.getSession();
      session.setAttribute(CURRENT_USER_SESSION_KEY, user.id);
      generateFriends(user, credential);
      sendResponse(req, resp, user);
    } catch (TokenVerificationException e) {
      sendError(resp, 401, e.getMessage());
    } catch (TokenResponseException e) {
      sendError(resp, 500, "Failed to upgrade the authorization code.");
    } catch (TokenDataException e) {
      sendError(resp, 500,
          "Failed to read token data from Google. " + e.getMessage());
    } catch (IOException e) {
      sendError(resp, 500, e.getMessage());
    } catch (GoogleApiException e) {
      sendError(resp, 500, "Failed to query the Google+ API: " + e.getMessage());
    } catch (DaoException e) {
    	sendError(resp, 500, "Failed to access database: " + e.getMessage());
	} catch (EntityExistsException e) {
		sendError(resp, 500, "Entity already exists: " + e.getMessage());
	}
  }

  /**
   * Exchanges the `code` member of the given AccessToken object, and returns
   * the relevant GoogleTokenResponse.
   * @param accessToken Container of authorization code to exchange.
   * @return Token response from Google indicating token information.
   * @throws TokenDataException Failed to exchange code (code invalid).
   */
  private GoogleTokenResponse exchangeCode(TokenData accessToken)
      throws TokenDataException {
    try {
      // Upgrade the authorization code into an access and refresh token.
      GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
          TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, accessToken.code,
          "postmessage").execute();
      return tokenResponse;
    } catch (IOException e) {
      throw new TokenDataException(e.getMessage());
    }
  }

  /**
   * Verify that the token in the given credential is valid.
   * @param credential Credential to verify.
   * @return Google user ID for which token was issued.
   * @throws TokenVerificationException Credential is not valid.
   * @throws IOException Could not verify Credential because of a network
   *                     failure.
   */
  private String verifyToken(GoogleCredential credential)
      throws TokenVerificationException, IOException {
    // Check that the token is valid.
    Oauth2 oauth2 = new Oauth2.Builder(TRANSPORT, JSON_FACTORY, credential)
        .build();
    Tokeninfo tokenInfo = oauth2.tokeninfo()
        .setAccessToken(credential.getAccessToken()).execute();
    // If there was an error in the token info, abort.
    if (tokenInfo.containsKey("error")) {
      throw new TokenVerificationException(tokenInfo.get("error").toString());
    }
    
    if (credential.getExpiresInSeconds() == null) {
    	// Set the expiry time if it hasn't already been set.
    	int expiresIn = tokenInfo.getExpiresIn();
		credential.setExpiresInSeconds((long) expiresIn);
		credential.setExpirationTimeMilliseconds(System.currentTimeMillis() + expiresIn * 1000);
    }
    
    Pattern p = Pattern.compile("(\\d+)([-]?)(.*)$");
	Matcher issuedTo = p.matcher(CLIENT_ID);
	Matcher localId = p.matcher(tokenInfo.getIssuedTo());

    // Make sure the token we got is for our app.
    if (!issuedTo.matches() || !localId.matches() 
    		|| !issuedTo.group(1).equals(localId.group(1))) {

      throw new TokenVerificationException(
          "Token's client ID does not match app's.");
    }
    
    return tokenInfo.getUserId();
  }

  /**
   * Either:
   * 1. Create a user for the given ID and credential
   * 2. or, update the existing user with the existing credential
   *
   * If 2, then ask Google for the user's public profile information to store.
   *
   * @param tokenGoogleUserId Google user ID to update.
   * @param credential Credential to set for the user.
   * @return Updated User.
   * @throws GoogleApiException Could not fetch profile info for user.
 * @throws EntityExistsException 
 * @throws DaoException 
   */
  private User saveTokenForUser(String tokenGoogleUserId, GoogleCredential credential) throws GoogleApiException, DaoException, EntityExistsException {
    User user = ofy().load().type(User.class)
        .filter("googleUserId", tokenGoogleUserId).first().get();
    if (user == null) {
      // Register a new user.  Collect their Google profile info first.
      Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential).build();
      Person profile;
      Plus.People.Get get;
      try {
        get = plus.people().get("me");
        profile = get.execute();
      } catch (IOException e) {
        throw new GoogleApiException(e.getMessage());
      }
      user = new User();
      user.setGoogleUserId(profile.getId());
      user.setGoogleDisplayName(profile.getDisplayName());
      user.setGooglePublicProfileUrl(profile.getUrl());
      user.setGooglePublicProfilePhotoUrl(profile.getImage().getUrl());
    }
    // TODO(silvano): Also fetch and set the email address for the user.
    user.setGoogleAccessToken(credential.getAccessToken());
    if (credential.getRefreshToken() != null) {
      user.setGoogleRefreshToken(credential.getRefreshToken());
    }
    user.setGoogleExpiresAt(credential.getExpirationTimeMilliseconds());
    user.setGoogleExpiresIn(credential.getExpiresInSeconds());
    ofy().save().entity(user).now();
    PessoaService service = new PessoaServiceImpl();
    service.
    generateOrUpdatePessoa(user);
    return user;
  }
  
  public void generateOrUpdatePessoa(User user) throws DaoException, EntityExistsException{
	  System.out.println(">>>> Entrou no generate !!!!!!!!");
	  PessoaService service = new PessoaServiceImpl();
	  Pessoa pessoa = service.getPessoaByGoogleUserId(user.googleUserId);
	  if(pessoa == null){
		  pessoa = new Pessoa(user.googleUserId, user.googleDisplayName, user.googlePublicProfileUrl, user.googlePublicProfilePhotoUrl);
	  }else{
		  pessoa.setGoogleDisplayName(user.googleDisplayName);
		  pessoa.setGooglePublicProfileUrl(user.googlePublicProfileUrl);
		  pessoa.setGooglePublicProfilePhotoUrl(user.googlePublicProfilePhotoUrl);
	  }
	  service.getCrud().save(pessoa);
  }

  /**
   * Query Google for the list of the user's friends that they've shared with
   * our app, and then store those friends for later use.
   * @param user User for which to get friends.
   * @param credential Credential to use to authorize people.list request.
   * @throws IOException Unable to fetch friends because of network error.
   */
  private void generateFriends(User user, GoogleCredential credential)
      throws IOException {
    // TODO(silvano): Refactor this method to not have race condition.
    // TODO(silvano): Refactor this method to use task queue.
    // TODO(silvano): Refactor this method to fetch more than first page.

    // Simple but inefficient way of building the friends list
    Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential).build();
    Plus.People.List get;
    List<DirectedUserToUserEdge> friends = ofy().load()
        .type(DirectedUserToUserEdge.class)
        .filter("ownerUserId", user.getId()).list();
    ofy().delete().entities(friends);

    get = plus.people().list(user.getGoogleUserId(), "visible");
    PeopleFeed feed = get.execute();
    boolean done = false;
    do {
      for (Person googlePlusPerson : feed.getItems()) {
        User friend = ofy().load().type(User.class).filter(
            "googleUserId", googlePlusPerson.getId()).first().get();
        if (friend != null) {
          DirectedUserToUserEdge friendEdge = new DirectedUserToUserEdge();
          friendEdge.setOwnerUserId(user.getId());
          friendEdge.setFriendUserId(friend.getId());
          ofy().save().entity(friendEdge).now();
        }
      }
      done = true;
    } while (!done);
  }

  /**
   * Thrown when token data can't be read from verification end-point.
   */
  class TokenDataException extends Exception {
    public TokenDataException(String message) {
      super(message);
    }
  }

  /**
   * Exception thrown when errors occurs during token verification.
   */
  class TokenVerificationException extends Exception {
    public TokenVerificationException(String message) {
      super(message);
    }
  }

  /**
   * Exception thrown when errors occurs querying the Google + API.
   */
  class GoogleApiException extends Exception {
    public GoogleApiException(String message) {
      super(message);
    }
  }

  /**
   * Simple Jsonifiable to represent token information sent/retrieved from our
   * app and its clients (web, Android, iOS).
   */
  public static class TokenData extends Jsonifiable {
    public static String kind = "photohunt#tokendata";

    /**
     * Google access token used to authorize requests to Google.
     */
    @Expose
    public String access_token;

    /**
     * Google refresh token used to get new access tokens when needed.
     */
    @Expose
    public String refresh_token;

    /**
     * Authorization code used to exchange for an access/refresh token pair.
     */
    @Expose
    public String code;

    /**
     * Identity token for this user.
     */
    @Expose
    public String id_token;

    /**
     * When the access token expires.
     */
    @Expose
    public Long expires_at;

    /**
     * How long until the access token expires.
     */
    @Expose
    public Long expires_in;
  }
}
