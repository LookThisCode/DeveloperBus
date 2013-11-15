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


public class Company extends Jsonifiable {
	

 /**
   * @param id ID of User for which to get a Key.
   * @return Key representation of given User's ID.
   */
  public static Key<Company> key(long id) {
    return Key.create(Company.class, id);
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
   * Description: about me
   */
  public String description;
  
  public String tagline;
  
  /*
   * Indica el tipo de pagina
   *    person: representa una persona real.
		page: representa una página.
   */
  public String tipo;
  
  /*
   * Meritos indicados por la compañia
   * braggingRights
   */
  public int braggingRights;
  
  
  /*
   * plusOneCount
   * Numero de +1
   */
  public int numberPlus;
  
  /*
   * plusOneCount
   * Numero de seguidores
   */
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

public int getBraggingRights() {
	return braggingRights;
}

public void setBraggingRights(int braggingRights) {
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
  
  
  

}
  
  
  