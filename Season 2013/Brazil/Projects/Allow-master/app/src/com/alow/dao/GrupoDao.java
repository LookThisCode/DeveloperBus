package com.alow.dao;

import java.util.List;

import com.alow.model.Grupo;

public interface GrupoDao extends GenericDao<Grupo,Long>{
	
	public List<Grupo> getGruposDaPessoa(Long grupoId);
	
}
