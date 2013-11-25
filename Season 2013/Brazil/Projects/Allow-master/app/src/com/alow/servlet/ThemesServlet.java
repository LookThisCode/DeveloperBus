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

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alow.model.Theme;

/**
 * Provides an API for working with Themes.  This servlet provides the
 * /api/themes end-point, and exposes the following operations:
 *
 *   GET /api/themes
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
public class ThemesServlet extends JsonRestServlet {
  /**
   * Exposed as `GET /api/themes`.  When requested, if no theme exists for the
   * current day, then a theme with the name of "Beautiful" is created for
   * today.  This leads to multiple themes with the name "Beautiful" if you
   * use the app over multiple days without changing this logic.  This behavior
   * is purposeful so that the app is easier to get up and running.
   *
   * Returns the following JSON response representing a list of Themes.
   *
   * [
   *   {
   *     "id":0,
   *     "displayName":"",
   *     "created":0,
   *     "start":0
   *   },
   *   ...
   * ]
   *
   * @see javax.servlet.http.HttpServlet#doGet(
   *     javax.servlet.http.HttpServletRequest,
   *     javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Collection<Theme> themes = ofy().load().type(Theme.class).order("-created")
        .list();
    Theme currentTheme = Theme.getCurrentTheme();
    if (currentTheme == null) {
      // There is no current theme.  Create a new one.
      Theme defaultTheme = new Theme();
      defaultTheme.setDisplayName("Beautiful");
      Date now = new Date();
      defaultTheme.setCreated(now);
      defaultTheme.setStart(now);
      ofy().save().entity(defaultTheme).now();
      themes.add(defaultTheme);
    }
    sendResponse(req, resp, themes, "photohunt#themes");
  }
}
