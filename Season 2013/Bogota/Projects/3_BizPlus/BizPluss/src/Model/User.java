package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User extends Jsonifiable {
	

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
  public Long id;

  /**
   * Primary email address of this User.
   */
  public String email;

  /**
   * UUID identifier of this User within Google products.
   */
  public String googleUserId;

  /**
   * Display name that this User has chosen for Google products.
   */
  public String googleDisplayName;

  /**
   * Public Google+ profile URL for this User.
   */
  public String googlePublicProfileUrl;

  /**
   * Public Google+ profile image for this User.
   */
  public String googlePublicProfilePhotoUrl;

  /**
   * Access token used to access Google APIs on this User's behalf.
   */
  public String googleAccessToken;

  /**
   * Refresh token used to refresh this User's googleAccessToken.
   */
  public String googleRefreshToken;

  /**
   * Validity of this User's googleAccessToken in seconds.
   */
  public Long googleExpiresIn;

  /**
   * Expiration time in milliseconds since Epoch for this User's
   * googleAccessToken.
   * Exposed for mobile clients, to help determine if they should request a new
   * token.
   */
  public Long googleExpiresAt;
  
  /**
   * Comunity
   */
  public String community;

  
  /**
   * Description
   */
  public String description;
  
  
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