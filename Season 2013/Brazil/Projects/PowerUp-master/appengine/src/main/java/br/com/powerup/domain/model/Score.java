package br.com.powerup.domain.model;

import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Score {
	
	@Parent
	private com.google.appengine.api.datastore.Key parent;
	@Id Long id;
	private Long totalPoints = 0l;

	@SuppressWarnings("unused")
	private Score() {
		super();
	}

	public Score(String userId, Long initialScore) {
		this.parent = KeyFactory.createKey("User", userId);
	}
	
	public Score(String userId) {
		this(userId, 0l);
	}

	public void increment( int pointsEarned ) {
		totalPoints += pointsEarned;
	}

	public Long getTotalPoints() {
		return totalPoints;
	}
	
	
	
}