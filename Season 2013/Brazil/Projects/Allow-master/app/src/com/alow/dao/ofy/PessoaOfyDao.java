package com.alow.dao.ofy;

import com.alow.dao.PessoaDao;
import com.alow.model.Pessoa;

public class PessoaOfyDao extends ObjectifyGenericDao<Pessoa,Long> implements PessoaDao{
	/*private EstadoDao estadoDao = DaoFactory.getEstadoDao();
	private CidadeDao cidadeDao = DaoFactory.getCidadeDao();*/
	
	public PessoaOfyDao(){
		super(Pessoa.class);
	}

	@Override
	public Pessoa getPessoaByGoogleUserId(String googleUserId) {
		return getByProperty("googleUserId", googleUserId);
	}
	
}
