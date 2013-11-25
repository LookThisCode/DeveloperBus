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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alow.model.Message;
import com.alow.model.Photo;
import com.alow.model.Vote;
import com.alow.model.plus.DirectedUserToUserEdge;
import com.alow.model.plus.User;
import com.google.api.client.http.GenericUrl;


/**
 * Provides an API to disconnect users from Photohunt. This servlet provides
 * the /api/disconnect endpoint, and exposes the following operations:
 *
 *   POST /api/disconnect
 *
 * @author silvano@google.com (Silvano Luciani)
 */
public class DisconnectServlet extends JsonRestServlet {
  /**
   * Exposed as `POST /api/disconnect`.
   *
   * As required by the Google+ Platform Terms of Service, this end-point:
   *
   *   1. Deletes all data retrieved from Google that is stored in our app.
   *   2. Revokes all of the user's tokens issued to this app.
   *
   * Takes no request payload, and disconnects the user currently identified
   * by their session.
   *
   * Returns the following JSON response representing the User that was
   * connected:
   *
   *   "Successfully disconnected."
   *
   * Issues the following errors along with corresponding HTTP response codes:
   * 401: "Unauthorized request".  No user was connected to disconnect.
   * 500: "Failed to revoke token for given user: "
   *      + error from failed connection to revoke end-point.
   *
   * @see javax.servlet.http.HttpServlet#doPost(
   *     javax.servlet.http.HttpServletRequest,
   *     javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    try {
      checkAuthorization(req);
      Long userId = Long.parseLong(req.getSession()
          .getAttribute(CURRENT_USER_SESSION_KEY).toString());
      List<DirectedUserToUserEdge> edges = ofy().load()
          .type(DirectedUserToUserEdge.class)
          .filter("ownerUserId", userId).list();
      ofy().delete().entities(edges);
      List<Vote> userVotes = ofy().load().type(Vote.class)
          .filter("ownerUserId", userId).list();
      ofy().delete().entities(userVotes);
      List<Photo> userPhotos = ofy().load().type(Photo.class)
          .filter("ownerUserId", userId).list();
      ofy().delete().entities(userPhotos);
      User user = ofy().load().type(User.class).id(userId).get();
      ofy().delete().entity(user);

      revokeToken(user.getGoogleAccessToken());

      req.getSession().removeAttribute(CURRENT_USER_SESSION_KEY);
      sendResponse(req, resp, new Message("Successfully disconnected."),
          "photohunt#message");
    } catch (UserNotAuthorizedException e) {
      sendError(resp, 401,
          "Unauthorized request");
    } catch (IOException e) {
      sendError(resp, 500,
          "Failed to revoke token for given user: " + e.getMessage());
    }
  }

  /**
   * Revoke the given access token, and consequently any other access tokens
   * and refresh tokens issued for this user to this app.
   *
   * Essentially this operation disconnects a user from the app, but keeps
   * their app activities alive in Google.  The same user can later come back
   * to the app, sign-in, re-consent, and resume using the app.
   * @throws IOException Network error occured while making request.
   */
  protected static void revokeToken(String accessToken) throws IOException {
    TRANSPORT.createRequestFactory().buildGetRequest(new GenericUrl(
        String.format(
            "https://accounts.google.com/o/oauth2/revoke?token=%s",
            accessToken))).execute();
  }
}
