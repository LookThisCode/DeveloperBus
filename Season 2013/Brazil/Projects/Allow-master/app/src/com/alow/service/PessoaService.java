package com.alow.service;

import com.alow.exception.DaoException;
import com.alow.exception.EntityExistsException;
import com.alow.model.Pessoa;
import com.alow.model.plus.User;
import com.alow.service.crud.CrudService;

public interface PessoaService {
	
	public abstract CrudService<Pessoa,Long> getCrud();
	public abstract Pessoa getPessoaByGoogleUserId(String googleUserId);
	public abstract Pessoa generateOrUpdatePessoa(User user) throws DaoException, EntityExistsException;
	public abstract Pessoa generateOrUpdatePessoa(String googleUserId, String deviceId, String registrationId) throws DaoException, EntityExistsException;
	
}
