
package Model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import lombok.Getter;
import lombok.Setter;

public class DirectedUserToUserEdge {
  
  public static Key<DirectedUserToUserEdge> key(long id) {
    return Key.create(DirectedUserToUserEdge.class, id);
  }

  Long id;

  
  Long ownerUserId;

  Long friendUserId;
  
  Long peopleAtCircleID;
  
  Long peoplePlusId;

public Long getPeopleAtCircleID() {
	return peopleAtCircleID;
}

public void setPeopleAtCircleID(Long peopleAtCircleID) {
	this.peopleAtCircleID = peopleAtCircleID;
}

public Long getPeoplePlusId() {
	return peoplePlusId;
}

public void setPeoplePlusId(Long peoplePlus) {
	this.peoplePlusId = peoplePlus;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getOwnerUserId() {
	return ownerUserId;
}

public void setOwnerUserId(Long ownerUserId) {
	this.ownerUserId = ownerUserId;
}

public Long getFriendUserId() {
	return friendUserId;
}

public void setFriendUserId(Long friendUserId) {
	this.friendUserId = friendUserId;
}
  
  
}
