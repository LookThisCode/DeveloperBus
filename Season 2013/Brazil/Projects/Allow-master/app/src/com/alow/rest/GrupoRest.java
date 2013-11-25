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

import com.alow.exception.DaoException;
import com.alow.exception.EntityExistsException;
import com.alow.exception.HasDependenciesException;
import com.alow.exception.NotLoggedInException;
import com.alow.model.Grupo;
import com.alow.service.GrupoService;
import com.alow.service.impl.GrupoServiceImpl;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@Path("/v1/grupos")
public class GrupoRest {
	
	private GrupoService service = new GrupoServiceImpl();
	
	@GET
	@Produces("application/json")
	public String getAll() throws NotLoggedInException {
		List<Grupo> ents = service.getCrud().getAll();
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
		Type listType = new TypeToken<ArrayList<Grupo>>() {}.getType();
		return gson.toJson(ents,listType);
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String incluir(Grupo ent) throws DaoException, EntityExistsException {
		Grupo entidadePersistida = service.getCrud().save(ent);
		Gson gson = new GsonBuilder().create();
		return gson.toJson(entidadePersistida);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public String atualizar(Grupo ent) throws DaoException, EntityExistsException {
		Grupo entidadeAtualizada = service.getCrud().save(ent);
		Gson gson = new GsonBuilder().create();
		return gson.toJson(entidadeAtualizada);
	}
	
	@DELETE
	@Path("/{id}")
	public void remover(@PathParam("id") Long id ) throws EntityNotFoundException, HasDependenciesException, DaoException{
		service.getCrud().deleteById(id);
	}
}
