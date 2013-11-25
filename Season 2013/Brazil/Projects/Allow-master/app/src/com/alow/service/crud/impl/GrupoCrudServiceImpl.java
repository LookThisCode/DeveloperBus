package com.alow.service.crud.impl;

import java.util.ArrayList;
import java.util.List;

import com.alow.dao.DaoFactory;
import com.alow.dao.GrupoDao;
import com.alow.model.Grupo;
import com.alow.service.crud.CrudServiceAbs;
import com.alow.service.crud.util.CrudUtil;
import com.alow.validacao.GrupoVerifier;

public class GrupoCrudServiceImpl extends CrudServiceAbs<Grupo,Long>{
	
	@Override
	public GrupoDao getDao() {
		return DaoFactory.getGrupoDao();
	}

	@Override
	public String isValid(Grupo ent) {
		return GrupoVerifier.isValid(ent);
	}
	
	@Override
	public List<CrudUtil<Grupo>> getCrudUtilExistList(Grupo ent){
		List<CrudUtil<Grupo>> retorno = new ArrayList<CrudUtil<Grupo>>();
		/*CrudUtil<Grupo> crudUtilNome = new CrudUtil<Grupo>();
		crudUtilNome.setLista(getDao().getBateBolasDoUsuarioPorNome(ent));
		crudUtilNome.setMensagem("Já existe um Bate-Bola com o nome "+ent.getNome());
		retorno.add(crudUtilNome);*/
		return retorno;
	}

	@Override
	public String hasDependencies(Grupo ent){
		String retorno = "";
		/*ArrayList<ElementoConfig> ents = getElementosConfigDoElemento(ent.getId());
		if(ents != null && ents.size() > 0){
			retorno = "Háï¿½ ao menos uma configuraï¿½ï¿½o para esse Dispositivo";
		}*/
		return retorno;
	}

	@Override
	public Long getId(Grupo ent) {
		if(ent != null){
			return ent.getId();
		}
		return null;
	}
	
	/*@Override
	public Grupo save(Grupo ent) throws DaoException,	EntityExistsException {
		//ent.setDataTexto(DateUtil.convertDateToString(ent.getData()));
		//ent.setHoraTexto(DateUtil.convertHourToString(ent.getHora()));
		return super.save(ent);
	}*/
}
