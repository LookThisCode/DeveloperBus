package br.com.powerup.domain.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class ExerciseDescriptor {

	@Id Long id;
	String name;
	String description;
	String imageUrl;
	String targetBodyPart;
	
	public ExerciseDescriptor() {
		super();
	}
	
	public ExerciseDescriptor(String name, String target,
			String desc, String url) {
		this.name = name;
		this.description = desc;
		this.imageUrl = url;
		this.targetBodyPart = target;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getTargetBodyPart() {
		return targetBodyPart;
	}
	
}