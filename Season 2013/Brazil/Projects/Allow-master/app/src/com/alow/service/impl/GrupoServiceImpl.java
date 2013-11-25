package com.alow.service.impl;

import java.util.List;

import com.alow.dao.DaoFactory;
import com.alow.dao.GrupoDao;
import com.alow.model.Grupo;
import com.alow.service.GrupoService;
import com.alow.service.crud.CrudService;
import com.alow.service.crud.impl.GrupoCrudServiceImpl;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class GrupoServiceImpl implements GrupoService{
	private CrudService<Grupo,Long> crud = new GrupoCrudServiceImpl();
	
	@Override
	public CrudService<Grupo, Long> getCrud() {
		return crud;
	}
	
	private GrupoDao dao = DaoFactory.getGrupoDao();
	
	@Override
	public List<Grupo> getGruposDaPessoa(Long grupoId) throws EntityNotFoundException {
		return dao.getGruposDaPessoa(grupoId);
	}


}
