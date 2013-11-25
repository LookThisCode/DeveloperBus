package com.alow.dao;

import java.util.List;

import com.alow.model.Alow;

public interface AlowDao extends GenericDao<Alow,Long>{
	public List<Alow> getAlowsDaPessoa(Long pessoaId);
	/*public ArrayList<BateBola> getBateBolasDoUsuario(Long usuarioId);
	public ArrayList<BateBola> getBateBolasDoUsuarioPorNome(BateBola ent);
	public ArrayList<BateBola> pesquisar(BateBolaPesquisaDTO ent, String sortField);*/
}
