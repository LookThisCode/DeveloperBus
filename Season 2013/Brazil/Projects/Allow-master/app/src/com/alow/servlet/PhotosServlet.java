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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alow.model.Message;
import com.alow.model.Photo;
import com.alow.model.Theme;
import com.alow.model.Vote;
import com.alow.model.plus.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.Plus.Moments.Insert;
import com.google.api.services.plus.model.ItemScope;
import com.google.api.services.plus.model.Moment;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.cmd.Query;

/**
 * Provides an API for working with Photos. This servlet provides the
 * /api/photos endpoint, and exposes the following operations:
 *
 *   GET /api/photos
 *   GET /api/photos?photoId=1234
 *   GET /api/photos?themeId=1234
 *   GET /api/photos?userId=me
 *   GET /api/photos?themeId=1234&userId=me
 *   GET /api/photos?themeId=1234&userId=me&friends=true
 *   POST /api/photos
 *   DELETE /api/photos?photoId=1234
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
public class PhotosServlet extends JsonRestServlet {

  /**
   * Base URL of the /api/photos end-point.  Initialized for every GET request.
   */
  public static String BASE_URL;
  
  /**
   * Logger for this servlet.
   */
  private static final Logger log = Logger.getLogger(PhotosServlet.class.getName());

  /**
   * BlobstoreService from which to fetch image information after an Image
   * upload.
   */
  private final BlobstoreService blobstoreService =
      BlobstoreServiceFactory.getBlobstoreService();

  /**
   * Exposed as `GET /api/photos`.
   *
   * Accepts the following request parameters.
   *
   * 'photoId': id of the requested photo. Will return a single Photo.
   * 'themeId': id of a theme. Will return the collection of photos for the
   *            specified theme.
   * 'userId': id of the owner of the photo. Will return the collection of
   *           photos for that user. The keyword ‘me’ can be used and will be
   *           converted to the logged in user. Requires auth.
   * 'friends': value evaluated to boolean, if true will filter only photos
   *            from friends of the logged in user. Requires auth.
   *
   * Returns the following JSON response representing a list of Photos.
   *
   * [
   *   {
   *     "id":0,
   *     "ownerUserId":0,
   *     "ownerDisplayName":"",
   *     "ownerProfileUrl":"",
   *     "ownerProfilePhoto":"",
   *     "themeId":0,
   *     "themeDisplayName":"",
   *     "numVotes":0,
   *     "voted":false, // Whether or not the current user has voted on this.
   *     "created":0,
   *     "fullsizeUrl":"",
   *     "thumbnailUrl":"",
   *     "voteCtaUrl":"", // URL for Vote interactive post button.
   *     "photoContentUrl":"" // URL for Google crawler to hit to get info.
   *   },
   *   ...
   * ]
   *
   * Issues the following errors along with corresponding HTTP response codes:
   * 401: "Unauthorized request" (if certain parameters are present in the
   *      request)
   *
   * @see javax.servlet.http.HttpServlet#doGet(
   *     javax.servlet.http.HttpServletRequest,
   *     javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      BASE_URL = getBaseUrlFromRequest(req);
      String photoId = req.getParameter("photoId");
      String themeId = req.getParameter("themeId");
      String userIdParam = req.getParameter("userId");
      Long userId;
      Long currentUserId = null;
      if(req.getSession().getAttribute(CURRENT_USER_SESSION_KEY) != null) {
        currentUserId = Long.parseLong(req.getSession()
            .getAttribute(CURRENT_USER_SESSION_KEY).toString());
      }
      boolean showFriends = Boolean.parseBoolean(req.getParameter("friends"));
      Query<Photo> q = ofy().load().type(Photo.class);
      if (photoId != null) {
        // Get the photo with the given ID and return it.
        Photo photo = q.filter("id", Long.parseLong(photoId)).first().get();
        sendResponse(req, resp, photo);
      } else {
        if (userIdParam != null) {
          // If the key word me is used, retrieve the current user from the session
          // The user needs to be authenticated to use 'me'
          if (userIdParam.equals("me")) {
            checkAuthorization(req);
            userId = currentUserId;
          } else {
            userId = Long.parseLong(userIdParam);
          }
          if (showFriends) {
            checkAuthorization(req);
            // Get all photos for the user's friends.
            User user = ofy().load().type(User.class)
                .filterKey(User.key(userId)).first().get();
            List<Long> friendIds = user.getFriendIds();
            if (friendIds.size() > 30) {
              friendIds = friendIds.subList(0, 30);
            }
            if (friendIds.size() > 0) {
              q = q.filter("ownerUserId in", friendIds);
            } else {
              List<Photo> emptyList = new ArrayList<Photo>();
              // If there are no friends for the user, return an empty list
              sendResponse(req, resp, emptyList,
                  "photohunt#photos");
              return;
            }
          } else {
            // Get all photos for the user.
            q = q.filter("ownerUserId", userId);
          }
        }
        if (themeId != null) {
          // Limit photos to just those for the given theme.
          q = q.filter("themeId", Long.parseLong(themeId));
        }
        List<Photo> photos = q.list();

        if (currentUserId != null) {
          // Build Hash map of voted photos
          List<Vote> userVotes = ofy().load().type(Vote.class)
              .filter("ownerUserId", currentUserId)
              .list();
          HashMap<Long, Boolean> userVotesTable = new HashMap<Long, Boolean>(userVotes.size());
          Iterator<Vote> votesIterator = userVotes.iterator();
          while (votesIterator.hasNext()) {
            userVotesTable.put(votesIterator.next().getPhotoId(), true);
          }
          // Check if user voted for each photo
          Iterator<Photo> photosIterator = photos.iterator();
          while (photosIterator.hasNext()) {
            Photo current = photosIterator.next();
            current.setVoted(userVotesTable.containsKey(current.getId()));
          }
        }
        sendResponse(req, resp, photos, "photohunt#photos");
      }
    } catch (UserNotAuthorizedException e) {
      sendError(resp, 401,
          "Unauthorized request");
    }
  }

  /**
   * Exposed as `POST /api/photos`.
   *
   * Takes the following payload in the request body.  Payload represents a
   * Photo that should be created.
   * {
   *   "id":0,
   *   "ownerUserId":0,
   *   "ownerDisplayName":"",
   *   "ownerProfileUrl":"",
   *   "ownerProfilePhoto":"",
   *   "themeId":0,
   *   "themeDisplayName":"",
   *   "numVotes":0,
   *   "voted":false, // Whether or not the current user has voted on this.
   *   "created":0,
   *   "fullsizeUrl":"",
   *   "thumbnailUrl":"",
   *   "voteCtaUrl":"", // URL for Vote interactive post button.
   *   "photoContentUrl":"" // URL for Google crawler to hit to get info.
   * }
   *
   * Returns the following JSON response representing the created Photo.
   * {
   *   "id":0,
   *   "ownerUserId":0,
   *   "ownerDisplayName":"",
   *   "ownerProfileUrl":"",
   *   "ownerProfilePhoto":"",
   *   "themeId":0,
   *   "themeDisplayName":"",
   *   "numVotes":0,
   *   "voted":false, // Whether or not the current user has voted on this.
   *   "created":0,
   *   "fullsizeUrl":"",
   *   "thumbnailUrl":"",
   *   "voteCtaUrl":"", // URL for Vote interactive post button.
   *   "photoContentUrl":"" // URL for Google crawler to hit to get info.
   * }
   *
   * Issues the following errors along with corresponding HTTP response codes:
   * 400: "Bad Request" if the request is missing image data.
   * 401: "Unauthorized request" (if certain parameters are present in the
   *      request)
   * 401: "Access token expired" (there is a logged in user, but he doesn't
   *      have a refresh token and his access token is expiring in less than
   *      100 seconds, get a new token and retry)
   * 500: "Error while writing app activity: " + error from client library.
   *
   * @see javax.servlet.http.HttpServlet#doPost(
   *     javax.servlet.http.HttpServletRequest,
   *     javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    try {
      checkAuthorization(req);
      List<BlobKey> blobKeys = blobstoreService.getUploads(req).get("image");
      BlobKey imageKey = null;
      
      if (blobKeys != null) {
    	  imageKey = blobKeys.iterator().next();
      }
      
      if (imageKey == null) {
        sendError(resp, 400, "Missing image data.");
        return;
      }
      
      Long currentUserId = (Long) req.getSession().getAttribute(
          CURRENT_USER_SESSION_KEY);
      User author = ofy().load().type(User.class).id(currentUserId).get();
      GoogleCredential credential = this.getCredentialFromLoggedInUser(req);
      Photo photo = new Photo();
      photo.setOwnerUserId(author.getId());
      photo.setOwnerDisplayName(author.getGoogleDisplayName());
      photo.setOwnerProfilePhoto(author.getGooglePublicProfilePhotoUrl());
      photo.setOwnerProfileUrl(author.getGooglePublicProfileUrl());
      photo.setThemeId(Theme.getCurrentTheme().getId());
      photo.setThemeDisplayName(Theme.getCurrentTheme().getDisplayName());
      photo.setCreated(Calendar.getInstance().getTime());
      photo.setNumVotes(0);
      photo.setImageBlobKey(imageKey.getKeyString());
      ofy().save().entity(photo).now();
      ofy().clear();
      photo = ofy().load().type(Photo.class).id(photo.getId()).get();
      try {
        addPhotoToGooglePlusHistory(author, photo, credential);
      } catch (MomentWritingException e) {
        log.severe("Error while writing app activity: " + e.getMessage());
      }
      sendResponse(req, resp, photo);
    } catch (UserNotAuthorizedException e) {
      sendError(resp, 401, "Unauthorized request");
    } catch (GoogleTokenExpirationException e) {
      sendError(resp, 401, "Access token expired");
    }
  }

  /**
   * Exposed as `DELETE /api/photos`.
   *
   * Accepts the following request parameters.
   *
   * 'photoId': id of the photo to delete.
   *
   * Returns the following JSON response representing success.
   * "Photo successfully deleted."
   *
   * Issues the following errors along with corresponding HTTP response codes:
   * 401: "Unauthorized request" (if certain parameters are present in the
   *      request)
   * 404: "Photo with given ID does not exist."
   *
   * @see javax.servlet.http.HttpServlet#doDelete(
   *     javax.servlet.http.HttpServletRequest,
   *     javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    final String doesNotExist = "Photo with given ID does not exist.";
    try {
      checkAuthorization(req);
      Long photoId = Long.parseLong(req.getParameter("photoId"));
      Photo photo = ofy().load().key(Photo.key(photoId)).safeGet();
      Long userId = Long.parseLong(req.getSession()
          .getAttribute(CURRENT_USER_SESSION_KEY).toString());
      if (!userId.equals(photo.getOwnerUserId())) {
        throw new NotFoundException();
      }
      ofy().delete().entity(photo).now();
      List<Vote> photoVotes = ofy().load().type(Vote.class)
          .filter("photoId", photoId).list();
      ofy().delete().entities(photoVotes);
      sendResponse(req, resp, new Message("Photo successfully deleted"),
          "photohunt#message");
    } catch (NotFoundException nfe) {
      sendError(resp, 404, doesNotExist);
    } catch (NumberFormatException e) {
      sendError(resp, 404, doesNotExist);
    } catch (UserNotAuthorizedException e) {
      sendError(resp, 401,
          "Unauthorized request");
    }
  }

  /**
   * Creates an app activity in Google indicating that the given User has
   * uploaded the given Photo.
   * @param author Creator of Photo.
   * @param photo Photo itself.
   * @param credential Credential with which to authorize request to Google.
   * @throws MomentWritingException Failed to write app activity.
   */
  private void addPhotoToGooglePlusHistory(User author, Photo photo,
      GoogleCredential credential) throws MomentWritingException{
    ItemScope target = new ItemScope().setUrl(photo.getPhotoContentUrl());
    Moment content = new Moment().setType(
        "http://schemas.google.com/AddActivity").setTarget(target);
    Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential).build();
    try {
      Insert request = plus.moments().insert(author.googleUserId,
          "vault", content);
      Moment moment = request.execute();
    } catch (IOException e) {
      throw new MomentWritingException(e.getMessage());
    }
  }

  /**
   * @param req Request from which to fetch base URL.
   * @return Base URL from the given request.
   */
  private String getBaseUrlFromRequest(HttpServletRequest req) {
    return req.getScheme() + "://" + req.getServerName()
        + ((req.getServerPort() != 80) ? (":" + req.getServerPort()) : "");
  }

  /**
   * Thrown when writing app activity to Google fails.
   */
  public static class MomentWritingException extends Exception {
    public MomentWritingException(String message) {
      super(message);
    }
  }
}
