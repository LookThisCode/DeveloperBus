package com.alow.dto;

import com.alow.model.Alow;
import com.alow.model.Grupo;
import com.alow.model.Pessoa;

public class AlowDto {
	private Grupo grupo;
	private Pessoa gestor;
	private Alow alow;
	
	public AlowDto(Grupo grupo, Pessoa gestor, Alow alow) {
		super();
		this.grupo = grupo;
		this.gestor = gestor;
		this.alow = alow;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public Pessoa getGestor() {
		return gestor;
	}
	public void setGestor(Pessoa gestor) {
		this.gestor = gestor;
	}
	public Alow getAlow() {
		return alow;
	}
	public void setAlow(Alow alow) {
		this.alow = alow;
	}

}
