package com.alow.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class GrupoPessoa {
	@Id
	private String id;

	private Long grupoId;
	private Long pessoaId;

	public GrupoPessoa(Long grupoId, Long pessoaId) {
		this.id = grupoId.toString() + "-" + pessoaId.toString();
		this.grupoId = grupoId;
		this.pessoaId = pessoaId;
	}

	public String getId() {
		return id;
	}

	public Long getGrupoId() {
		return grupoId;
	}

	public Long getPessoaId() {
		return pessoaId;
	}

}
