package br.com.powerup.domain.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class WorkoutDevice {
	
	private @Id Long id;
	private String name;
	
	@SuppressWarnings("unused")
	private WorkoutDevice() {
		super();
	}

	public WorkoutDevice(String name) {
		super();
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}

}
