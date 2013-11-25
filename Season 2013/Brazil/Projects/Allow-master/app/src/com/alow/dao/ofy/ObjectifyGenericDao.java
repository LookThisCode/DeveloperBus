package com.alow.dao.ofy;

import static com.alow.dao.ofy.OfyService.ofy;

import java.lang.reflect.Modifier;
import java.util.List;

import com.alow.dao.GenericDao;
import com.google.appengine.api.datastore.EntityNotFoundException;


public class ObjectifyGenericDao<T,ID> implements GenericDao<T,ID>{

	static final int BAD_MODIFIERS = Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;

	protected Class<T> clazz;

	/**
	 * We've got to get the associated domain class somehow
	 *
	 * @param clazz
	 */
	protected ObjectifyGenericDao(Class<T> clazz)
	{
		this.clazz = clazz;
	}
	
	@Override
	public T save(T entity){
		ofy().save().entity(entity).now();
		return entity;
	}

	@Override
	public void delete(T entity)
	{
		ofy().delete().entity(entity);
	}
	

	/*public void deleteKey(Key<T> entityKey)
	{
		ofy().delete(entityKey);
	}

	public void deleteAll(Iterable<T> entities)
	{
		ofy().delete(entities);
	}

	public void deleteKeys(Iterable<Key<T>> keys)
	{
		ofy().delete(keys);
	}*/

	public T get(ID id) throws EntityNotFoundException
	{
		Long lLong = null;
		if(id != null){
			String sLong = id.toString();
			lLong = new Long(sLong);
		}
		return ofy().load().type(this.clazz).id(lLong.longValue()).now();
	}

	/*public T get(Key<T> key) throws EntityNotFoundException
	{
		return ofy().load().key(key);
	}*/
	
	public List<T> getAll() {
		return getAll(null);
	}
	
	@Override
	public List<T> getAll(String sortField) {
		List<T> ents = ofy().load().type(this.clazz).list();
		if(sortField != null && !sortField.equals("")){
			ents = ofy().load().type(this.clazz).order(sortField).list();
		}else{
			ents = ofy().load().type(this.clazz).list();
		}
		return ents;
	}

	/**
	 * Convenience method to get all objects matching a single property
	 *
	 * @param propName
	 * @param propValue
	 * @return T matching Object
	 */
	public T getByProperty(String propName, Object propValue)
	{
		return ofy().load().type(clazz).filter(propName, propValue).first().now();
	}

	public List<T> listByProperty(String propName, Object propValue)
	{
		return ofy().load().type(clazz).filter(propName, propValue).list();
	}
	public List<T> listByProperty(String propName, Object propValue, String sortField)
	{
		
	
		if(sortField != null && !sortField.equals("")){
			return ofy().load().type(clazz).filter(propName, propValue).order(sortField).list();
		}else{
			return ofy().load().type(clazz).filter(propName, propValue).list();
		}
	}

	/*public List<Key<T>> listKeysByProperty(String propName, Object propValue)
	{
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return asKeyList(q.fetchKeys());
	}

	public T getByExample(T exampleObj)
	{
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		Iterable<T> iterableResults = queryByExample.fetch();
		Iterator<T> i = iterableResults.iterator();
		T obj = i.next();
		if (i.hasNext())
			throw new RuntimeException("Too many results");
		return obj;
	}

	public List<T> listByExample(T exampleObj)
	{
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		return asList(queryByExample.fetch());
	}
*/
	/*private List<T> asList(Iterable<T> iterable)
	{
		ArrayList<T> list = new ArrayList<T>();
		for (T t : iterable)
		{
			list.add(t);
		}
		return list;
	}
	private ArrayList<T> asArrayList(Iterable<T> iterable)
	{
		ArrayList<T> list = new ArrayList<T>();
		for (T t : iterable)
		{
			list.add(t);
		}
		return list;
	}

	private List<Key<T>> asKeyList(Iterable<Key<T>> iterableKeys)
	{
		ArrayList<Key<T>> keys = new ArrayList<Key<T>>();
		for (Key<T> key : iterableKeys)
		{
			keys.add(key);
		}
		return keys;
	}*/

	/*private Query<T> buildQueryByExample(T exampleObj)
	{
		Query<T> q = ofy().query(clazz);

		// Add all non-null properties to query filter
		for (Field field : clazz.getDeclaredFields())
		{
			// Ignore transient, embedded, array, and collection properties
			if (field.isAnnotationPresent(Transient.class)
				|| (field.isAnnotationPresent(Embedded.class))
				|| (field.getType().isArray())
				|| (Collection.class.isAssignableFrom(field.getType()))
				|| ((field.getModifiers() & BAD_MODIFIERS) != 0))
				continue;

			field.setAccessible(true);

			Object value;
			try
			{
				value = field.get(exampleObj);
			}
			catch (IllegalArgumentException e)
			{
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
			if (value != null)
			{
				q.filter(field.getName(), value);
			}
		}

		return q;
	}*/
}
