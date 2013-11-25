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

import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.alow.model.plus.Jsonifiable;
import com.alow.servlet.PhotosServlet;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.gson.annotations.Expose;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnLoad;

/**
 * Represents a User's Photo in PhotoHunt.  Contains all of the properties that
 * allow the Photo to be rendered and managed.
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
@Entity
@Cache
@EqualsAndHashCode(of="id", callSuper=false)
public class Photo extends Jsonifiable {
  @Expose
  public static String kind = "photohunt#photo";

  /**
   * ImagesService to use for image operation execution.
   */
  protected static ImagesService images =
      ImagesServiceFactory.getImagesService();

  /**
   * @param id ID of Photo for which to get a Key.
   * @return Key representation of given Photo's ID.
   */
  public static Key<Photo> key(long id) {
    return Key.create(Photo.class, id);
  }

  /**
   * Primary identifier of this Photo.
   */
  @Id
  @Getter
  @Expose
  public Long id;

  /**
   * ID of the User who owns this Photo.
   */
  @Index
  @Getter
  @Setter
  @Expose
  public Long ownerUserId;

  /**
   * Display name of the User who owns this Photo.
   */
  @Getter
  @Setter
  @Expose
  public String ownerDisplayName;

  /**
   * Profile URL of the User who owns this Photo.
   */
  @Getter
  @Setter
  @Expose
  public String ownerProfileUrl;

  /**
   * Profile photo of the User who owns this Photo.
   */
  @Getter
  @Setter
  @Expose
  public String ownerProfilePhoto;

  /**
   * ID of the Theme to which this Photo belongs.
   */
  @Index
  @Getter
  @Setter
  @Expose
  public Long themeId;

  /**
   * Display name of the Theme to which this Photo belongs.
   */
  @Index
  @Getter
  @Setter
  @Expose
  public String themeDisplayName;

  /**
   * Number of votes this Photo has received.
   */
  @Index
  @Getter
  @Setter
  @Expose
  @Ignore
  public int numVotes;

  /**
   * True if the current user has already voted this Photo.
   */
  @Getter
  @Setter
  @Expose
  public boolean voted;

  /**
   * Image blob key for this Photo.
   */
  @Getter
  @Setter
  private String imageBlobKey;

  /**
   * Date this Photo was uploaded to PhotoHunt.
   */
  @Getter
  @Setter
  @Expose
  public Date created;

  /**
   * URL for full-size image of this Photo.
   */
  @Expose
  @Ignore
  public String fullsizeUrl;

  /**
   * URL for thumbnail image of this Photo.
   */
  @Expose
  @Getter
  @Ignore
  public String thumbnailUrl;

  /**
   * URL for vote call to action on this photo.
   */
  @Expose
  @Getter
  @Ignore
  public String voteCtaUrl;

  /**
   * URL for interactive posts and deep linking to this photo.
   */
  @Expose
  @Getter
  @Ignore
  public String photoContentUrl;

  /**
   * Default size of thumbnails.
   */
  @Expose
  @Ignore
  public static final int DEFAULT_THUMBNAIL_SIZE = 400;

  /**
   * Setup image URLs (fullsizeUrl and thumbnailUrl) after this Photo has been
   * loaded.
   */
  @OnLoad
  protected void setupImageUrls() {
    fullsizeUrl = getImageUrl();
    thumbnailUrl = getImageUrl(DEFAULT_THUMBNAIL_SIZE);
  }

  /**
   * @return URL for full-size image of this photo.
   */
  public String getImageUrl() {
    return getImageUrl(-1);
  }

  /**
   * @param size Size of image for URL to return.
   * @return URL for images for this Photo of given size.
   */
  public String getImageUrl(int size) {
    ServingUrlOptions options = ServingUrlOptions.Builder
        .withBlobKey(new BlobKey(imageBlobKey))
        .secureUrl(true);
    if (size > -1) {
      options.imageSize(size);
    }
    return images.getServingUrl(options);
  }

  /**
   * Setup voteNum after this Photo has been loaded.
   */
  @OnLoad
  protected void setupVoteNum() {
    List<Vote> votes = ofy().load().type(Vote.class)
        .filter("photoId", id)
        .list();
    numVotes = votes.size();
  }

  /**
   * Setup voteCtaUrl after this Photo has been loaded.
   */
  @OnLoad
  protected void setupVoteCtaUrl() {
    voteCtaUrl = PhotosServlet.BASE_URL + "/index.html?photoId=" + id +
        "&action=VOTE";
  }

  /**
   * Setup photoContentUrl after this Photo has been loaded.
   */
  @OnLoad
  protected void photoDeepLinkUrl() {
    photoContentUrl = PhotosServlet.BASE_URL + "/photo.html?photoId=" + id;
  }
}
