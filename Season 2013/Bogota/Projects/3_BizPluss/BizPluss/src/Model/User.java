package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.annotations.Expose;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import Model.OfyService;
import Model.DirectedUserToUserEdge;
import static Model.OfyService.ofy;

public class User extends Jsonifiable {
	

  public static Key<User> key(long id) {
    return Key.create(User.class, id);
  }

  
  public Long id;

  
  public String email;

  public String googleUserId;

  
  public String googleDisplayName;

  public String googlePublicProfileUrl;

  public String googlePublicProfilePhotoUrl;

  public String googleAccessToken;

  public String googleRefreshToken;

  public Long googleExpiresIn;

  public Long googleExpiresAt;
 

  
  /**
   * Description: about me
   */
  public String description;
  
  
  /*
   * lista de nombres de organizaciones
   */
  public ArrayList<String> nombres_organizaciones;
  
  
  
public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getGoogleUserId() {
	return googleUserId;
}

public void setGoogleUserId(String googleUserId) {
	this.googleUserId = googleUserId;
}

public String getGoogleDisplayName() {
	return googleDisplayName;
}

public void setGoogleDisplayName(String googleDisplayName) {
	this.googleDisplayName = googleDisplayName;
}

public String getGooglePublicProfileUrl() {
	return googlePublicProfileUrl;
}

public void setGooglePublicProfileUrl(String googlePublicProfileUrl) {
	this.googlePublicProfileUrl = googlePublicProfileUrl;
}

public String getGooglePublicProfilePhotoUrl() {
	return googlePublicProfilePhotoUrl;
}

public void setGooglePublicProfilePhotoUrl(String googlePublicProfilePhotoUrl) {
	this.googlePublicProfilePhotoUrl = googlePublicProfilePhotoUrl;
}

public String getGoogleAccessToken() {
	return googleAccessToken;
}

public void setGoogleAccessToken(String googleAccessToken) {
	this.googleAccessToken = googleAccessToken;
}

public String getGoogleRefreshToken() {
	return googleRefreshToken;
}

public void setGoogleRefreshToken(String googleRefreshToken) {
	this.googleRefreshToken = googleRefreshToken;
}

public Long getGoogleExpiresIn() {
	return googleExpiresIn;
}

public void setGoogleExpiresIn(Long googleExpiresIn) {
	this.googleExpiresIn = googleExpiresIn;
}

public Long getGoogleExpiresAt() {
	return googleExpiresAt;
}

public void setGoogleExpiresAt(Long googleExpiresAt) {
	this.googleExpiresAt = googleExpiresAt;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public ArrayList<String> getNombres_organizaciones() {
	return nombres_organizaciones;
}

public void setNombres_organizaciones(ArrayList<String> nombres_organizaciones) {
	this.nombres_organizaciones = nombres_organizaciones;
}

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

  public Collection<User> getFriends() {
    return ofy().load().keys(getFriendKeys()).values();
  }
}