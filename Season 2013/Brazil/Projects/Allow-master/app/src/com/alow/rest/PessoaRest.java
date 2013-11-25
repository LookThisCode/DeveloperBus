package com.alow.rest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.alow.dto.AlowDto;
import com.alow.exception.NotLoggedInException;
import com.alow.model.Grupo;
import com.alow.model.Pessoa;
import com.alow.service.AlowService;
import com.alow.service.GrupoService;
import com.alow.service.PessoaService;
import com.alow.service.impl.AlowServiceImpl;
import com.alow.service.impl.GrupoServiceImpl;
import com.alow.service.impl.PessoaServiceImpl;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@Path("/v1/pessoas")
public class PessoaRest {
	private PessoaService service = new PessoaServiceImpl();
	
	@GET
	@Produces("application/json")
	public String getAll() throws NotLoggedInException {
		List<Pessoa> ents = service.getCrud().getAll();
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<Pessoa>>() {}.getType();
		return gson.toJson(ents,listType);
	}
	
	@GET
	@Path("/{id}/alows")
	@Produces("application/json")
	public String getAlowsDaPessoa(@PathParam("id") Long id) throws EntityNotFoundException {
		AlowService service = new AlowServiceImpl();
		List<AlowDto> ents = service.getAlowsDaPessoa(id);
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<AlowDto>>() {}.getType();
		return gson.toJson(ents,listType);
	}
	
	@GET
	@Path("/{id}/grupos")
	@Produces("application/json")
	public String getGruposDaPessoa(@PathParam("id") Long id) throws EntityNotFoundException {
		GrupoService service = new GrupoServiceImpl();
		List<Grupo> ents = service.getGruposDaPessoa(id);
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<Grupo>>() {}.getType();
		return gson.toJson(ents,listType);
	}
	
}
