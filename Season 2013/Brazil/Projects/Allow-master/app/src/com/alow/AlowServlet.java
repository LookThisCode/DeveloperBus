package com.alow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alow.exception.DaoException;
import com.alow.exception.EntityExistsException;
import com.alow.model.Alow;
import com.alow.model.Pessoa;
import com.alow.service.AlowService;
import com.alow.service.PessoaService;
import com.alow.service.impl.AlowServiceImpl;
import com.alow.service.impl.PessoaServiceImpl;

@SuppressWarnings("serial")
public class AlowServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//process(req,resp);
		PessoaService service = new PessoaServiceImpl();
		List<Pessoa> pessoas = service.getCrud().getAll();
		System.out.println("Tam pessoas: "+pessoas.size());
		for(Pessoa pessoa:pessoas){
			resp.getWriter().println("Pessoa: "+pessoa.getGoogleDisplayName());
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//process(req,resp);
		postAlow(req, resp);
		//processGrupo(req,resp);
		//processGrupo2(req,resp);
	}
	
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		String conta = (String) req.getParameter("conta");
		Pessoa ent = new Pessoa();
		List<String> pushIds = new ArrayList<>();
		pushIds.add("id1");
		pushIds.add("id2");
		pushIds.add("id3");
		ent.setDevices(pushIds);
		PessoaService service = new PessoaServiceImpl();
		try {
			service.getCrud().save(ent);
		} catch (DaoException e) {
			resp.getWriter().println("Erro: "+e.getMessage());
			e.printStackTrace();
		} catch (EntityExistsException e) {
			resp.getWriter().println("Erro: "+e.getMessage());
			e.printStackTrace();
		}
		resp.getWriter().println("Pessoa: "+ent.getId());
	}
	
	/*public void processGrupo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		String conta = (String) req.getParameter("conta");
		Grupo ent = new Grupo();
		ent.setNome(conta);
		Key<Pessoa> gestor = Key.create(Pessoa.class,6333186975989760l);
		ent.setGestor(gestor);
		ofy().save().entity(ent).now();
		//resp.getWriter().println("Grupo: "+ent.getId()+" - Gestor: "+ ofy().load().type(Pessoa.class).id(5910974510923776l).now().getConta());
		resp.getWriter().println("Grupo: "+ent.getId());
	}*/
	
	/*public void processGrupo2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		Grupo grupo = ofy().load().type(Grupo.class).id(4925812092436480l).now();
		Pessoa pessoa = ofy().load().type(Pessoa.class).id(grupo.getGestor().getId()).now();
		resp.getWriter().println("Grupo: "+grupo.getNome()+" - Gestor: "+ pessoa.getConta());
		//resp.getWriter().println("Grupo: "+ent.getId());
	}*/
	
	public void postAlow(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		String texto = (String) req.getParameter("conta");
		Alow ent = new Alow();
		ent.setTexto(texto);
		ent.setGrupoId(4925812092436480l);
		ent.setPessoaId(6333186975989760l);
		
		AlowService service = new AlowServiceImpl();
		try {
			service.getCrud().save(ent);
		} catch (DaoException e) {
			resp.getWriter().println("Erro: "+e.getMessage());
			e.printStackTrace();
		} catch (EntityExistsException e) {
			resp.getWriter().println("Erro: "+e.getMessage());
			e.printStackTrace();
		}
		resp.getWriter().println("Alow: "+ent.getId());
	}
	
}
