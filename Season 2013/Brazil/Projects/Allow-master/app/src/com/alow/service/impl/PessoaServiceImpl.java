package com.alow.service.impl;

import com.alow.dao.DaoFactory;
import com.alow.dao.PessoaDao;
import com.alow.exception.DaoException;
import com.alow.exception.EntityExistsException;
import com.alow.model.Pessoa;
import com.alow.model.plus.User;
import com.alow.service.PessoaService;
import com.alow.service.crud.CrudService;
import com.alow.service.crud.impl.PessoaCrudServiceImpl;

public class PessoaServiceImpl implements PessoaService {
	private CrudService<Pessoa,Long> crud = new PessoaCrudServiceImpl();
	private PessoaDao dao = DaoFactory.getPessoaDao();
	
	@Override
	public CrudService<Pessoa, Long> getCrud() {
		return crud;
	}

	@Override
	public Pessoa getPessoaByGoogleUserId(String googleUserId) {
		return dao.getPessoaByGoogleUserId(googleUserId);
	}

	@Override
	public Pessoa generateOrUpdatePessoa(User user) throws DaoException, EntityExistsException{
		  Pessoa pessoa = getPessoaByGoogleUserId(user.googleUserId);
		  if(pessoa == null){
			  pessoa = new Pessoa(user.googleUserId, user.googleDisplayName, user.googlePublicProfileUrl, user.googlePublicProfilePhotoUrl);
		  }else{
			  pessoa.setGoogleDisplayName(user.googleDisplayName);
			  pessoa.setGooglePublicProfileUrl(user.googlePublicProfileUrl);
			  pessoa.setGooglePublicProfilePhotoUrl(user.googlePublicProfilePhotoUrl);
		  }
		  return getCrud().save(pessoa);
	  }
	
	@Override
	public Pessoa generateOrUpdatePessoa(String googleUserId, String deviceId, String registrationId) throws DaoException, EntityExistsException{ 
		  Pessoa pessoa = getPessoaByGoogleUserId(googleUserId);
		  if(pessoa == null){
			  pessoa = new Pessoa(googleUserId, registrationId, deviceId);
		  }else{
			  pessoa.addDevice(deviceId);
		  }
		  return getCrud().save(pessoa);
	  }
}
