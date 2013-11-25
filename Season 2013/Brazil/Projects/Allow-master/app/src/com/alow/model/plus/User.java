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

package com.alow.model.plus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import static com.alow.dao.ofy.OfyService.ofy;

@Entity
@Cache
@EqualsAndHashCode(of="id", callSuper=false)
public class User extends Jsonifiable {
	
  @Expose
  public static String kind = "photohunt#user";

  /**
   * @param id ID of User for which to get a Key.
   * @return Key representation of given User's ID.
   */
  public static Key<User> key(long id) {
    return Key.create(User.class, id);
  }

  /**
   * Primary identifier of this User.  Specific to PhotoHunt.
   */
  @Id
  @Getter
  @Expose
  public Long id;

  /**
   * Primary email address of this User.
   */
  @Getter
  @Setter
  @Index
  @Expose
  public String email;

  /**
   * UUID identifier of this User within Google products.
   */
  @Getter
  @Setter
  @Index
  @Expose
  public String googleUserId;

  /**
   * Display name that this User has chosen for Google products.
   */
  @Getter
  @Setter
  @Index
  @Expose
  public String googleDisplayName;

  /**
   * Public Google+ profile URL for this User.
   */
  @Getter
  @Setter
  @Expose
  public String googlePublicProfileUrl;

  /**
   * Public Google+ profile image for this User.
   */
  @Getter
  @Setter
  @Expose
  public String googlePublicProfilePhotoUrl;

  /**
   * Access token used to access Google APIs on this User's behalf.
   */
  @Getter
  @Setter
  @Index
  public String googleAccessToken;

  /**
   * Refresh token used to refresh this User's googleAccessToken.
   */
  @Getter
  @Setter
  public String googleRefreshToken;

  /**
   * Validity of this User's googleAccessToken in seconds.
   */
  @Getter
  @Setter
  public Long googleExpiresIn;

  /**
   * Expiration time in milliseconds since Epoch for this User's
   * googleAccessToken.
   * Exposed for mobile clients, to help determine if they should request a new
   * token.
   */
  @Getter
  @Setter
  @Expose
  public Long googleExpiresAt;

  /**
   * @return List of Key<User> representing keys of friends.
   */
  public List<Key<User>> getFriendKeys() {
    List<DirectedUserToUserEdge> edges = ofy().load()
        .type(DirectedUserToUserEdge.class)
        .filter("ownerUserId", getId())
        .list();
    List<Key<User>> friendKeys = new ArrayList<Key<User>>();
    for (DirectedUserToUserEdge edge : edges) {
      friendKeys.add(key(edge.getFriendUserId()));
    }
    return friendKeys;
  }

  /**
   * @return List of Longs representing IDs of friends.
   */
  public List<Long> getFriendIds() {
    List<DirectedUserToUserEdge> edges = ofy().load()
        .type(DirectedUserToUserEdge.class)
        .filter("ownerUserId", getId())
        .list();
    List<Long> friendIds = new ArrayList<Long>();
    for (DirectedUserToUserEdge edge : edges) {
      friendIds.add(edge.getFriendUserId());
    }
    return friendIds;
  }

  /**
   * @return Collection of Users that this User has listed as their friend(s).
   */
  public Collection<User> getFriends() {
    return ofy().load().keys(getFriendKeys()).values();
  }
}
