package com.alow.dao.ofy;

import java.util.List;

import com.alow.dao.AlowDao;
import com.alow.model.Alow;

public class AlowOfyDao extends ObjectifyGenericDao<Alow,Long> implements AlowDao{
	/*private EstadoDao estadoDao = DaoFactory.getEstadoDao();
	private CidadeDao cidadeDao = DaoFactory.getCidadeDao();*/
	
	public AlowOfyDao(){
		super(Alow.class);
	}

	@Override
	public List<Alow> getAlowsDaPessoa(Long pessoaId) {
		return listByProperty("pessoaId", pessoaId);
	}

	/*@Override
	public ArrayList<BateBola> getBateBolasDoUsuario(Long usuarioId) {
		ArrayList<BateBola> ents = arrayListByProperty("usuarioId", usuarioId, "nome");
		for(BateBola ent:ents){
			preencheBateBola(ent);
		}
		return ents;
	}
	
	@Override
	public ArrayList<BateBola> getBateBolasDoUsuarioPorNome(BateBola ent) {
		Objectify ofy = ObjectifyService.begin();
		ArrayList<BateBola> ents = new ArrayList<BateBola>();
		Query<BateBola> q = ofy.query(BateBola.class)
			.filter("nomeUC", ent.getNome().trim().toUpperCase())
			.filter("usuarioId", ent.getUsuarioId());
		for (BateBola p : q) {
			preencheBateBola(p);
			ents.add(p);
		}
		return ents;
	}
	
	@Override
	public ArrayList<BateBola> pesquisar(BateBolaPesquisaDTO ent, String sortField) {
		System.out.println(">>entrou no search peladas...");
		if(ent.getData() != null){
			if(ent.getDataTexto() == null){
				//se a pesquisa for originada pelo servico rest, entao ja tera a dataTexto setada
				ent.setDataTexto(DateUtil.convertDateToString(ent.getData()));	
			}
		}
		
		
		Objectify ofy = ObjectifyService.begin();
		ArrayList<BateBola> ents = new ArrayList<BateBola>();
		
		Query<BateBola> query = ofy.query(BateBola.class);
		 if(ent.getNome() != null && !ent.getNome().equals("")){
			 System.out.println("Qual server: "+ ent.getNome());
			 query.filter("nomeUC", ent.getNome().toUpperCase());
		 }
		 
		 if(ent.getDataTexto() != null){
			 System.out.println("Quando server: "+ ent.getData());
			 query.filter("dataTexto",ent.getDataTexto());
		 }
		 
		 if(ent.getCidadeId() != null && ent.getCidadeId() != 0l){
			 System.out.println("Onde server cidade: "+ ent.getCidadeId());
			 query.filter("cidadeId",ent.getCidadeId());
		 }
		 
		 if(ent.getEstadoId() != null && ent.getEstadoId() != 0l){
			 System.out.println("Onde server estado: "+ ent.getEstadoId());
			 query.filter("estadoId",ent.getEstadoId());
		 }
		 
		 if(ent.getLocal() != null && !ent.getLocal().equals("")){
			 System.out.println("Onde Local server: "+ ent.getLocal());
			 query.filter("localUC",ent.getLocal().toUpperCase());
		 }
		 
		 if(ent.getTemVagas() != null){
			 System.out.println("Detalhe Tem Vagas server: "+ ent.getTemVagas());
			 query.filter("temVagas",ent.getTemVagas());
		 }
		 
		 if(ent.getGratuito() != null){
			 System.out.println("Detalhe Gratuito server: "+ ent.getGratuito());
			 query.filter("gratuito",ent.getGratuito());
		 }
		 
		 if(sortField != null && !sortField.equals("")){
			 query.order(sortField);
		 }
		 
		 for (BateBola p : query) {
				System.out.println(">>> PELADA SERVER: " + p.getNome());
				preencheBateBola(p);
				ents.add(p);
		 }		
		return ents;
	}
	
	private void preencheBateBola(BateBola ent){
		if(ent != null){
			preencheEstado(ent);
			preencheCidade(ent);
		}
	}
	private void preencheEstado(BateBola ent){
		try {
			if(ent.getEstadoId() != null){
				Estado est = estadoDao.get(ent.getEstadoId());
				ent.setEstadoNome(est.getNome());
				ent.setEstadoSigla(est.getSigla());
			}
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			ent.setEstadoNome("");
			ent.setEstadoSigla("");
		}
	}
	private void preencheCidade(BateBola ent){
		try {
			if(ent.getCidadeId() != null){
				ent.setCidadeNome(cidadeDao.get(ent.getCidadeId()).getNome());
			}
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			ent.setCidadeNome("");
		}
	}*/
	
}
