package com.alow.dao;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

public interface GenericDao<ENT,ID> {
	public ENT save(ENT entity);
	public void delete(ENT entity);
	//public void deleteAll(Iterable<ENT> entities);
	public ENT get(ID id) throws EntityNotFoundException;
	public List<ENT> getAll();
	public List<ENT> getAll(String sortField);
	public ENT getByProperty(String propName, Object propValue);
	//public List<ENT> listByProperty(String propName, Object propValue);
	//public List<ENT> ListByProperty(String propName, Object propValue, String sortField);
}
