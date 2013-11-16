package main;

import static Model.OfyService.ofy;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Jsonifiable;
import Model.User;

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

import Model.DirectedUserToUserEdge;

public class ConnectServlet extends JsonRestServlet {

	  @Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
	    TokenData accessToken = null;
	    try {
	      accessToken = Jsonifiable.fromJson(req.getReader(), TokenData.class);
	    } catch (IOException e) {
	      sendError(resp, 400, "Unable to read auth result from request body");
	    }
	    GoogleCredential credential = new GoogleCredential.Builder()
	        .setJsonFactory(JSON_FACTORY).setTransport(TRANSPORT)
	        .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build();
	    
	    try {
	      if (accessToken.code != null) {
	    	  
	        GoogleTokenResponse tokenFromExchange = exchangeCode(accessToken);
	        credential.setFromTokenResponse(tokenFromExchange);
	      } else {
	    	if (accessToken.access_token == null) {
	          sendError(resp, 400, "Se perdio el token en la solicitud.");
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
	      sendError(resp, 500, "Fallo en la actualizacion del codigo");
	    } catch (TokenDataException e) {
	      sendError(resp, 500,
	          "Fallo al leer el token. " + e.getMessage());
	    } catch (IOException e) {
	      sendError(resp, 500, e.getMessage());
	    } catch (GoogleApiException e) {
	      sendError(resp, 500, "Failed to query the Google+ API: " + e.getMessage());
	    }
	  }

	  private GoogleTokenResponse exchangeCode(TokenData accessToken)
	      throws TokenDataException {
	    try {
	      GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
	          TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, accessToken.code,
	          "postmessage").execute();
	      return tokenResponse;
	    } catch (IOException e) {
	      throw new TokenDataException(e.getMessage());
	    }
	  }

	  
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

	    if (!issuedTo.matches() || !localId.matches() 
	    		|| !issuedTo.group(1).equals(localId.group(1))) {

	      throw new TokenVerificationException(
	          "Token's client ID does not match app's.");
	    }
	    
	    return tokenInfo.getUserId();
	  }

	  private User saveTokenForUser(String tokenGoogleUserId,
	      GoogleCredential credential) throws GoogleApiException {
	    User user = ofy().load().type(User.class)
	        .filter("googleUserId", tokenGoogleUserId).first().get();
	    if (user == null) {
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
	    user.setGoogleAccessToken(credential.getAccessToken());
	    if (credential.getRefreshToken() != null) {
	      user.setGoogleRefreshToken(credential.getRefreshToken());
	    }
	    user.setGoogleExpiresAt(credential.getExpirationTimeMilliseconds());
	    user.setGoogleExpiresIn(credential.getExpiresInSeconds());
	    ofy().save().entity(user).now();
	    return user;
	  }

	  private void generateFriends(User user, GoogleCredential credential)
	      throws IOException {
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

	  class TokenDataException extends Exception {
	    public TokenDataException(String message) {
	      super(message);
	    }
	  }

	  class TokenVerificationException extends Exception {
	    public TokenVerificationException(String message) {
	      super(message);
	    }
	  }

	  class GoogleApiException extends Exception {
	    public GoogleApiException(String message) {
	      super(message);
	    }
	  }

	  public static class TokenData extends Jsonifiable {

	    public String access_token;

	    public String refresh_token;

	    public String code;

	    public String id_token;

	    public Long expires_at;

	    public Long expires_in;
	  }
}