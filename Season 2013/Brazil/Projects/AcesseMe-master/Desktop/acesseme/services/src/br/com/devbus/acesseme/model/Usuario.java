/**
 * Copyright 2011 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.devbus.acesseme.model;

import java.util.ArrayList;
import java.util.List;

import br.com.devbus.acesseme.datastore.Util;

import com.google.appengine.api.datastore.Entity;

/**
 * This class defines the methods for basic operations of create, update & retrieve
 * 
 * @author 
 *
 */
public class Usuario {

	private static final String USER = "Usuario";
	private static final String EMAIL = "email";
	
  public static Entity createUser(String email, String necessidade, String token) {
    Entity user = getSingleUser(email);
    if (user == null) {
      user = new Entity(USER);
      user.setProperty(EMAIL, email);
      user.setProperty("necessidade", necessidade);
      user.setProperty("token", token);
      Util.persistEntity(user);
    }
    return user;
  }

	/**
	 * get All the items in the list
	 * 
	 * @param kind
	 *          : item kind
	 * @return all the items
	 */
  public static Iterable<Entity> getAllUsers() {
    Iterable<Entity> entities = Util.listEntities(USER, null, null);
    return entities;
  }

  public static Iterable<Entity> getUser(String email) {
    Iterable<Entity> entities = Util.listEntities(USER, EMAIL, email);
    return entities;
  }

  public static Entity getSingleUser(String email) {
    Iterable<Entity> results = Util.listEntities(USER, EMAIL, email);
    List<Entity> entity = new ArrayList<Entity>();
    for(Entity e : results)
      if(e!=null)
        entity.add(e);
      if (!entity.isEmpty()) {
        return (Entity)entity.remove(0);
      }
	  return null;
  }
}
