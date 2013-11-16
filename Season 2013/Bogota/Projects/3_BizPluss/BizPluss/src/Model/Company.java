package Model;

import static Model.OfyService.ofy;

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
import Model.User;
import Model.DirectedUserToUserEdge;


public class Company extends Jsonifiable {

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
 
  public String description;
  
  public String tagline;
 
  public String tipo;
 
  public String braggingRights;
  
  public int numberPlus;
  
  public int numberCircles;

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

public String getTagline() {
	return tagline;
}

public void setTagline(String tagline) {
	this.tagline = tagline;
}

public String getTipo() {
	return tipo;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
}

public String getBraggingRights() {
	return braggingRights;
}

public void setBraggingRights(String braggingRights) {
	this.braggingRights = braggingRights;
}

public int getNumberPlus() {
	return numberPlus;
}

public void setNumberPlus(int numberPlus) {
	this.numberPlus = numberPlus;
}

public int getNumberCircles() {
	return numberCircles;
}

public void setNumberCircles(int numberCircles) {
	this.numberCircles = numberCircles;
}

public List<Key<User>> getPeopleCircle() {
  List<DirectedUserToUserEdge> edges = ofy().load()
      .type(DirectedUserToUserEdge.class)
      .filter("peopleAtCircleID", getId())
      .list();
  List<Key<User>> circleKeys = new ArrayList<Key<User>>();
  for (DirectedUserToUserEdge edge : edges) {
	  circleKeys.add(key(edge.getFriendUserId()));
  }
  return circleKeys;
}

public List<Key<User>> getPeoplePlus() {
  List<DirectedUserToUserEdge> edges = ofy().load()
      .type(DirectedUserToUserEdge.class)
      .filter("peoplePlus", getId())
      .list();
  List<Key<User>> plusKeys = new ArrayList<Key<User>>();
  for (DirectedUserToUserEdge edge : edges) {
	  plusKeys.add(key(edge.getFriendUserId()));
  }
  return plusKeys;
}

}
  
  
  