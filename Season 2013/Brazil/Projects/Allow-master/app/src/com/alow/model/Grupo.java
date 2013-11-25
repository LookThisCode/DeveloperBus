package com.alow.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Grupo {
	
	@Id
	private Long id;
	private String nome;
	@Index
	private Long gestorId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getGestorId() {
		return gestorId;
	}
	public void setGestorId(Long gestorId) {
		this.gestorId = gestorId;
	}

}
