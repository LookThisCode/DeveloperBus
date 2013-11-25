package com.alow.service.crud;

import java.util.List;

import com.alow.dao.GenericDao;
import com.alow.exception.DaoException;
import com.alow.exception.EntityExistsException;
import com.alow.exception.HasDependenciesException;
import com.alow.service.crud.util.CrudUtil;
import com.google.appengine.api.datastore.EntityNotFoundException;

public interface CrudService<ENT,ID> {
	public List<ENT> getAll();
	public Boolean delete(ENT ent) throws HasDependenciesException, DaoException;
	public ENT save(ENT ent) throws DaoException, EntityExistsException;
	
	/**
	 * Checa as dependencias da entidade
	 * @param ent Entidade para verifica��o
	 * @return retorno diferente de vazio => existe, retorno igual vazio => n�o existe
	 * 			O retorno e a mensagem
	 */
	public String hasDependencies(ENT ent);
	public String exists(ENT ent);
	
	public abstract GenericDao<ENT,ID> getDao();
	public abstract String isValid(ENT ent);
	public abstract ID getId(ENT ent);
	public abstract List<CrudUtil<ENT>> getCrudUtilExistList(ENT ent);
	public abstract Boolean deleteById(ID id) throws EntityNotFoundException, HasDependenciesException, DaoException;
}
