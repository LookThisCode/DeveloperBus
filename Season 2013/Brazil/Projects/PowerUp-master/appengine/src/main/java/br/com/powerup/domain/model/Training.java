package br.com.powerup.domain.model;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Training {

	@Parent
	private com.google.appengine.api.datastore.Key parent;
	
	@Id private Long id;
	private Date createdOn = new Date();
	
	public Long getId() {
		return id;
	}

	private List<Exercise> exercicios = Lists.newArrayList();
	
	@SuppressWarnings("unused")
	private Training() {
		super();
	}
	
	public Training(User user) {
		user(user);
	}

	public List<Exercise> getExercicios() {
		return exercicios;
	}
	
	public void adicionaExercicio(Exercise exercicio) {
		exercicio.setOrder(exercicios.size());
		exercicios.add(exercicio);
	}

	public String key() {
		return parent == null ?
				com.googlecode.objectify.Key.create(this.getClass(), id).getString()
				: com.googlecode.objectify.Key.create(com.googlecode.objectify.Key.create(parent),
						this.getClass(), id).getString();
	}

	public void user(User user) {
		this.parent = KeyFactory.createKey("User", user.getUserId());
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int totalExercises() {
		return getExercicios().size();
	}
	
}