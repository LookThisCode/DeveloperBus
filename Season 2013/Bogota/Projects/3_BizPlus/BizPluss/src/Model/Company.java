package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class Company extends Jsonifiable {
	
	private Long id;
	
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
	  
	  
	  public String description;
	
}