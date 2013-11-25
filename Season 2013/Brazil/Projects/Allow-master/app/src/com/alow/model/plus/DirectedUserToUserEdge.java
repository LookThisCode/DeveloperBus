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

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Directed edge between two Users, used to construct a directed social graph.
 * Data to build these edges can be pulled from various social sources, like
 * the Google+ People feed, or your existing data set.
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
@Entity
@Cache
public class DirectedUserToUserEdge {
  /**
   * @param id ID of a DirectedUserToUserEdge for which to create Key.
   * @return Key based on given ID.
   */
  public static Key<DirectedUserToUserEdge> key(long id) {
    return Key.create(DirectedUserToUserEdge.class, id);
  }

  /**
   * Primary identifier of this edge.
   */
  @Id
  @Getter
  Long id;

  /**
   * User who owns this relationship.  In Google+, this would be the user who
   * has added targetUserId to their circles.
   */
  @Getter
  @Setter
  @Index
  Long ownerUserId;

  /**
   * Friend of the relationship owner.  This person does not necessarily have
   * the owner in their own social graph.
   */
  @Getter
  @Setter
  @Index
  Long friendUserId;
}
