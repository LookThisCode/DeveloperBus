package com.alow.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alow.dao.AlowDao;
import com.alow.dao.DaoFactory;
import com.alow.dao.GrupoDao;
import com.alow.dao.PessoaDao;
import com.alow.dto.AlowDto;
import com.alow.model.Alow;
import com.alow.model.Grupo;
import com.alow.model.Pessoa;
import com.alow.service.AlowService;
import com.alow.service.crud.CrudService;
import com.alow.service.crud.impl.AlowCrudServiceImpl;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class AlowServiceImpl implements AlowService {
	private CrudService<Alow,Long> crud = new AlowCrudServiceImpl();
	private AlowDao dao = DaoFactory.getAlowDao();
	private GrupoDao grupoDao = DaoFactory.getGrupoDao();
	private PessoaDao pessoaDao = DaoFactory.getPessoaDao();
	
	@Override
	public CrudService<Alow, Long> getCrud() {
		return crud;
	}

	@Override
	public List<AlowDto> getAll() throws EntityNotFoundException {
		List<Alow> ents = dao.getAll();
		return adapter(ents);
	}
	
	@Override
	public List<AlowDto> getAlowsDaPessoa(Long pessoaId) throws EntityNotFoundException {
		List<Alow> ents = dao.getAlowsDaPessoa(pessoaId);
		return adapter(ents);
	}
	
	private List<AlowDto> adapter(List<Alow> ents) throws EntityNotFoundException{
		AlowDto dto;
		Grupo grupo;
		Pessoa pessoa;
		List<AlowDto> lista = new ArrayList<>();
		for(Alow ent:ents){
			grupo = grupoDao.get(ent.getGrupoId());
			pessoa = pessoaDao.get(ent.getPessoaId());
			dto = new AlowDto(grupo, pessoa, ent);
			lista.add(dto);
		}
		return lista;
	}

}
