package br.com.powerup.domain.model;

import java.util.Date;

import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Worklog {
	
	@Parent
	private com.google.appengine.api.datastore.Key parent;
	
	@Id private Long id;
	private String exerciseId; 
	private Date occurredOn = new Date();

	@SuppressWarnings("unused")
	private Worklog() {
		super();
	}
	
	public Worklog(String exerciseId, Long trainingId) {
		this.exerciseId = exerciseId;
		this.parent = KeyFactory.createKey(Workout.class.getSimpleName(), trainingId);
	}

	public String getExerciseId() {
		return exerciseId;
	}

	public Date getOccurredOn() {
		return occurredOn;
	}
	
	
}