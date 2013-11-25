package br.com.powerup.domain.model;

import java.util.Date;

import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Workout {

	@Parent
	private com.google.appengine.api.datastore.Key parent;

	@Id Long id;
	private Date startedOn = new Date();

	@SuppressWarnings("unused")
	private Workout() {
		super();
	}

	public Workout(Long trainingId) {
		this.parent = KeyFactory.createKey(Training.class.getSimpleName(), trainingId);
	}

	public Date getStartedOn() {
		return startedOn;
	}
}
