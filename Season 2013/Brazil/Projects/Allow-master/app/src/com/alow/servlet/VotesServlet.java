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

import com.alow.model.Photo;
import com.alow.model.Vote;
import com.alow.model.plus.User;
import com.alow.servlet.PhotosServlet.MomentWritingException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.Plus.Moments.Insert;
import com.google.api.services.plus.model.ItemScope;
import com.google.api.services.plus.model.Moment;

/**
 * Provides an API for working with Votes.  This servlet provides the
 * /api/votes end-point, and exposes the following operations:
 *
 *   PUT /api/votes
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
public class VotesServlet extends JsonRestServlet {
  /**
   * Exposed as `PUT /api/votes`.
   *
   * Takes a request payload that is a JSON object containing the Photo ID
   * for which the currently logged in user is voting.
   *
   * {
   *   "photoId":0
   * }
   *
   * Returns the following JSON response representing the Photo for which the
   * User voted.
   *
   * {
   *   "id":0,
   *   "ownerUserId":0,
   *   "ownerDisplayName":"",
   *   "ownerProfileUrl":"",
   *   "ownerProfilePhoto":"",
   *   "themeId":0,
   *   "themeDisplayName":"",
   *   "numVotes":1,
   *   "voted":true,
   *   "created":0,
   *   "fullsizeUrl":"",
   *   "thumbnailUrl":"",
   *   "voteCtaUrl":"",
   *   "photoContentUrl":""
   * }
   *
   * Issues the following errors along with corresponding HTTP response codes:
   * 401: "Unauthorized request".  No user was connected to disconnect.
   * 401: "Access token expired" (there is a logged in user, but it doesn’t
   *      have a refresh token and his access token is expiring in less than
   *      100 secs, get a new token and retry)
   * 500: "Error writing app activity: " + error from client library
   *
   * @see javax.servlet.http.HttpServlet#doPut(
   *     javax.servlet.http.HttpServletRequest,
   *     javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
    try {
      checkAuthorization(req);
      Vote vote = Vote.fromJson(req.getReader(), Vote.class);
      Long currentUserId = Long.parseLong(req.getSession()
          .getAttribute(CURRENT_USER_SESSION_KEY).toString());
      User author = ofy().load().type(User.class).id(currentUserId).get();
      GoogleCredential credential = this.getCredentialFromLoggedInUser(req);
      vote.setOwnerUserId(currentUserId);
      List<Vote> voteExist = ofy().load().type(Vote.class)
          .filter("ownerUserId", currentUserId)
          .filter("photoId", vote.getPhotoId()).list();
      Photo photo = ofy().load().type(Photo.class).id(vote.getPhotoId()).get();
      photo.setVoted(true);
      if (voteExist.size() < 1) {
        photo.setNumVotes(photo.getNumVotes() + 1);
        ofy().save().entity(vote).now();
        ofy().clear();
        addVoteToGooglePlusAppActivity(author, photo, credential);
      }
      sendResponse(req, resp, photo);
    } catch (IOException e) {
      sendError(resp, 400, "Unable to read vote from request body");
    } catch (UserNotAuthorizedException e) {
      sendError(resp, 401, "Unauthorized request");
    } catch (GoogleTokenExpirationException e) {
      sendError(resp, 401, "Access token expired");
    } catch (MomentWritingException e) {
      sendError(resp, 500, "Error writing app activity: " + e.getMessage());
    }
  }

  /**
   * Add to the User's Google+ app activity for this app that they voted on
   * the given Photo.
   * @param author User voting.
   * @param photo Photo on which they're voting.
   * @param credential Credential used to authorize the request to Google.
   * @throws MomentWritingException Failed to write app activity to Google.
   */
  private void addVoteToGooglePlusAppActivity(User author, Photo photo,
      GoogleCredential credential) throws MomentWritingException {
    ItemScope target = new ItemScope().setUrl(photo.getPhotoContentUrl());
    ItemScope result = new ItemScope().setType("http://schema.org/Review")
        .setName("A vote for a PhotoHunt photo")
        .setUrl(photo.getPhotoContentUrl()).setText("Voted!");
    Moment content = new Moment()
        .setType("http://schemas.google.com/ReviewActivity").setTarget(target)
        .setResult(result);
    Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential).build();
    try {
      Insert request = plus.moments().insert(author.googleUserId, "vault",
          content);
      Moment moment = request.execute();
    } catch (IOException e) {
      throw new MomentWritingException(e.getMessage());
    }
  }
}
