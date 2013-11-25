package com.alow.dao;

import com.alow.model.Pessoa;

public interface PessoaDao extends GenericDao<Pessoa,Long>{
	
	public Pessoa getPessoaByGoogleUserId(String googleUserId);
	
}
