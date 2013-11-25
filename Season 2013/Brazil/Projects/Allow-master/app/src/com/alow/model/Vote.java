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
 * Represents a single vote by a single User on a single Photo.
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
@Entity
@Cache
@EqualsAndHashCode(of="id", callSuper=false)
public class Vote extends Jsonifiable {
	
  @Expose
  public static String kind = "photohunt#vote";

  /**
   * @param id ID of Vote for which to get a Key.
   * @return Key representation of given Vote's ID.
   */
  public static Key<Vote> key(long id) {
    return Key.create(Vote.class, id);
  }

  /**
   * Primary identifier of this Vote.
   */
  @Id
  @Getter
  @Expose
  public Long id;

  /**
   * ID of User who owns this Vote.
   */
  @Index
  @Getter
  @Setter
  @Expose
  public Long ownerUserId;

  /**
   * ID of the Photo to which this Vote was made.
   */
  @Index
  @Getter
  @Setter
  @Expose
  public Long photoId;
}
