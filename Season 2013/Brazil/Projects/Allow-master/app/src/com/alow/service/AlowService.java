package com.alow.service;

import java.util.List;

import com.alow.dto.AlowDto;
import com.alow.model.Alow;
import com.alow.service.crud.CrudService;
import com.google.appengine.api.datastore.EntityNotFoundException;

public interface AlowService {
	public abstract CrudService<Alow,Long> getCrud();
	
	public abstract List<AlowDto> getAll() throws EntityNotFoundException;
	public abstract List<AlowDto> getAlowsDaPessoa(Long pessoaId) throws EntityNotFoundException;
	
}
