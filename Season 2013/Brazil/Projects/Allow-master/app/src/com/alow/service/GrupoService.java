package com.alow.service;

import java.util.List;

import com.alow.model.Grupo;
import com.alow.service.crud.CrudService;
import com.google.appengine.api.datastore.EntityNotFoundException;

public interface GrupoService {
	
	public abstract CrudService<Grupo,Long> getCrud();
	public abstract List<Grupo> getGruposDaPessoa(Long pessoaId) throws EntityNotFoundException;

}
