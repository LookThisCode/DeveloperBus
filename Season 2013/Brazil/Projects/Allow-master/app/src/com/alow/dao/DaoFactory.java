package com.alow.dao;

import com.alow.dao.ofy.AlowOfyDao;
import com.alow.dao.ofy.GrupoOfyDao;
import com.alow.dao.ofy.PessoaOfyDao;


/**
 * Fabrica de Daos
 * @author Inacio
 *
 */
public class DaoFactory {
	private static PessoaDao pessoaDao = new PessoaOfyDao();
	private static GrupoDao grupoDao = new GrupoOfyDao();
	private static AlowDao alowDao = new AlowOfyDao();
	
	public static PessoaDao getPessoaDao(){
		if(pessoaDao == null)
			pessoaDao = new PessoaOfyDao();
		return pessoaDao;
	}
	
	public static GrupoDao getGrupoDao(){
		if(grupoDao == null)
			grupoDao = new GrupoOfyDao();
		return grupoDao;
	}
	
	public static AlowDao getAlowDao(){
		if(alowDao == null)
			alowDao = new AlowOfyDao();
		return alowDao;
	}
	
	
}
