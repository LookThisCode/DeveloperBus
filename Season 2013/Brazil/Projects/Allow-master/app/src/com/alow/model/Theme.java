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

package com.alow.model;

import static com.alow.dao.ofy.OfyService.ofy;

import java.util.Calendar;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.alow.model.plus.Jsonifiable;
import com.google.gson.annotations.Expose;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Represents a Theme for Photos.
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
@Entity
@Cache
@EqualsAndHashCode(of="id", callSuper=false)
public class Theme extends Jsonifiable {

  @Expose
  public static String kind = "photohunt#theme";

  /**
   * @param id ID of Theme for which to get a Key.
   * @return Key representation of given Theme's ID.
   */
  public static Key<Theme> key(long id) {
    return Key.create(Theme.class, id);
  }

  /**
   * Primary identifier of this Theme.
   */
  @Id
  @Getter
  @Expose
  public Long id;

  /**
   * Display name of this Theme.
   */
  @Getter
  @Setter
  @Expose
  public String displayName;

  /**
   * Date that this Theme was created.
   */
  @Index
  @Getter
  @Setter
  @Expose
  public Date created;

  /**
   * Date that this Theme should start.
   */
  @Index
  @Getter
  @Setter
  @Expose
  public Date start;

  /**
   * ID of Photo to display as a preview of this Theme.
   */
  @Getter
  @Setter
  @Expose
  public Long previewPhotoId;

  /**
   * @return Current Theme for PhotoHunt today.  A current theme is the theme
   *         for the day.  There cannot be two themes on the same day.
   */
  public static Theme getCurrentTheme() {
    Calendar start = Calendar.getInstance();
    start.set(Calendar.HOUR, 0);
    start.set(Calendar.MINUTE, 0);
    start.set(Calendar.SECOND, 0);
    Calendar end = Calendar.getInstance();
    end.set(Calendar.HOUR, 23);
    end.set(Calendar.MINUTE, 59);
    end.set(Calendar.SECOND, 59);
    return ofy().load().type(Theme.class)
        .filter("start >", start.getTime())
        .filter("start <", end.getTime())
        .order("-start").first().get();
  }
}
