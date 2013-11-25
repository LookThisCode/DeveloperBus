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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alow.model.plus.User;

/**
 * Provides an API for retrieving the currently logged in user. This servlet
 * provides the /api/users end-point, and exposes the following operations:
 *
 *   GET /api/users
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
public class UsersServlet extends JsonRestServlet {
  /**
   * Exposed as `GET /api/users`.
   *
   * Returns the following JSON response representing the currently logged in
   * User.
   *
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
   * 401: "Unauthorized request".  No user was connected.
   *
   * @see javax.servlet.http.HttpServlet#doGet(
   *     javax.servlet.http.HttpServletRequest,
   *     javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      checkAuthorization(req);
      Long currentUserId = (Long) req.getSession().getAttribute(
          CURRENT_USER_SESSION_KEY);
      User user = ofy().load().type(User.class).id(currentUserId).get();
      sendResponse(req, resp, user);
    } catch (UserNotAuthorizedException e) {
      sendError(resp, 401, "Unauthorized request");
    }
  }
}
