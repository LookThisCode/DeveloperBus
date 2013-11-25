package com.alow.rest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.alow.dto.AlowDto;
import com.alow.exception.DaoException;
import com.alow.exception.EntityExistsException;
import com.alow.exception.HasDependenciesException;
import com.alow.exception.NotLoggedInException;
import com.alow.model.Alow;
import com.alow.service.AlowService;
import com.alow.service.impl.AlowServiceImpl;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@Path("/v1/alows")
public class AlowRest {
	
	private AlowService service = new AlowServiceImpl();
	
	@GET
	@Produces("application/json")
	public String getAll() throws NotLoggedInException, EntityNotFoundException {
		System.out.println("Chegou a requisicao get all alows!!!");
		List<AlowDto> ents = service.getAll();
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<AlowDto>>() {}.getType();
		return gson.toJson(ents,listType);
	}
	
	@POST
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public String incluir(Alow ent) throws DaoException, EntityExistsException {
		System.out.println("POST: "+ent);
		Alow entidadePersistida = service.getCrud().save(ent);
		Gson gson = new GsonBuilder().create();
		return gson.toJson(entidadePersistida);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public String atualizar(Alow ent) throws DaoException, EntityExistsException {
		Alow entidadeAtualizada = service.getCrud().save(ent);
		Gson gson = new GsonBuilder().create();
		return gson.toJson(entidadeAtualizada);
	}
	
	@DELETE
	@Path("/{id}")
	public void remover(@PathParam("id") Long id ) throws EntityNotFoundException, HasDependenciesException, DaoException{
		System.out.println("chegou no delete: "+id);
		service.getCrud().deleteById(id);
	}
	
}
