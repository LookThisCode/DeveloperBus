package com.alow.service.crud;

import java.util.ArrayList;
import java.util.List;

import com.alow.dao.GenericDao;
import com.alow.exception.DaoException;
import com.alow.exception.EntityExistsException;
import com.alow.exception.HasDependenciesException;
import com.alow.service.crud.util.CrudUtil;
import com.google.appengine.api.datastore.EntityNotFoundException;

public abstract class CrudServiceAbs<ENT,ID> implements CrudService<ENT,ID> {
	@Override public abstract GenericDao<ENT,ID> getDao();
	@Override public abstract String isValid(ENT ent);
	@Override public abstract ID getId(ENT ent);
	@Override public abstract List<CrudUtil<ENT>> getCrudUtilExistList(ENT ent);
	@Override public abstract String hasDependencies(ENT ent);
	
	@Override
	public List<ENT> getAll() {
		return getDao().getAll();
	}
	
	@Override
	public Boolean deleteById(ID id) throws EntityNotFoundException, HasDependenciesException, DaoException {
		ENT ent = getDao().get(id);
		delete(ent);
		return true;
	}

	@Override
	public Boolean delete(ENT ent) throws HasDependenciesException, DaoException {
		if (ent == null){
			throw new DaoException("Objeto nulo.");
		}
		String retorno = hasDependencies(ent);
		if (retorno != "") {
			throw new HasDependenciesException(retorno);
		}else{
			getDao().delete(ent);
		}
		return true;
	}


	@Override
	public ENT save(ENT ent) throws DaoException, EntityExistsException {
		String retorno = isValid(ent);
		if (retorno != "") {
			throw new DaoException(retorno);
		}else{
			String mensaExists = exists(ent);
			if (!mensaExists.equals("")){
				throw new EntityExistsException(mensaExists);
			}
		}
		getDao().save(ent);
		return ent;
	}
	
	 /** Checa se a entidade j� existe no banco de dados
	 * @param ent Entidade para verifica��o
	 * @return uma mensagem se a entidade j� existe, mensagem vazia se n�o existe
	 */
	@Override
	public String exists(ENT ent){
		String retorno = "";
		List<CrudUtil<ENT>> crudUtilList = getCrudUtilExistList(ent);
		if(crudUtilList != null && crudUtilList.size() > 0){
			for(CrudUtil<ENT> crudExist:crudUtilList){
				ArrayList<ENT> ents = crudExist.getLista();
				boolean existe = false;
				if (ents.size() >= 1){
					if (getId(ent) != null){
						if(ents.size() == 1){
							System.out.println("Ents(0): "+getId(ents.get(0)));
							System.out.println("Ent: "+getId(ent));
							if(!getId(ents.get(0)).equals(getId(ent))){
								existe = true;
							}
						}else{
							existe = true;
						}
					}else{
						existe = true;
					}
				}
				if (existe){
					retorno = crudExist.getMensagem();
					break;
				}
			}
		}
		return retorno;
	}

}
