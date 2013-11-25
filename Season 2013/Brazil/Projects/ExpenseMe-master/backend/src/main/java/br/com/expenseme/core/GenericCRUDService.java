package br.com.expenseme.core;

import com.google.appengine.api.datastore.Key;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Generic Service implementation that provides helper methods for using the
 * entity repository This implementation can be extended or for a simple
 * utilization, can be used by injection with will be produced by
 *
 * @param <T> entity class
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
public class GenericCRUDService<T> {

    private static final int NO_LIMIT = 0;
    private EntityManager em;
    private final Class<T> entityClass;

    /**
     * Creates a GenericService according with EntityManager and entity class
     * parameters
     *
     * @param entityClass
     * @param em
     */
    public GenericCRUDService(Class<T> entityClass, EntityManager em) {
        this.entityClass = entityClass;
        this.em = em;
    }

    /**
     * Insert new parameterized entity
     *
     * @param entity
     * @see EntityManager#persist(java.lang.Object)
     */
    public void persist(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    /**
     * Delete the parameterized entity
     *
     * @param entity
     * @see EntityManager#remove(java.lang.Object)
     */
    public void remove(T entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    /**
     * Updates a entity changes in database.
     *
     * @param entity
     * @see EntityManager#merge(java.lang.Object)
     */
    public void merge(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    /**
     * Return all entities in database. SELECT * FROM "EntityTable"
     *
     * @return result list of query
     */
    public Collection<T> findAll() {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(entityClass);
        query.from(entityClass);
        return (Collection<T>) em.createQuery(query).getResultList();
    }

    /**
     * Find the entity with parameterized id
     *
     * @param id of sought entity
     * @return sought entity
     */
    public T findById(Key id) {
        return em.find(entityClass, id);
    }

    /**
     * Query database utilizing the parameterized named query
     *
     * @param namedQuery
     * @param parametersValues optional parameters values specified by named
     * query
     * @return result list of executed query
     */
    public List<T> findByNamedQuery(String namedQuery, Object... parametersValues) {
        return createTypedQuery(namedQuery, NO_LIMIT, parametersValues).getResultList();
    }

    /**
     * Query database utilizing the parameterized named query, limiting the
     * results by the resultLimit parameter
     *
     * @param namedQuery
     * @param resultLimit max number of results
     * @param parametersValues optional parameters values specified by named
     * query
     * @return
     */
    public List<T> findByNamedQueryWithLimitedResult(String namedQuery, int resultLimit, Object... parametersValues) {
        return createTypedQuery(namedQuery, resultLimit, parametersValues).getResultList();
    }

    /**
     * Helper method for creating a named typed query with capacity to limit the
     * number of result rows
     *
     * @param namedQuery id of namedQuery
     * @param resultLimit limit the number of result rows if the value is
     * greater than 0
     * @param parameters optional values of named query parameters
     * @return TypedQuery
     * @see TypedQuery
     */
    private TypedQuery<T> createTypedQuery(String namedQuery, int resultLimit, Object... parameters) {
        TypedQuery<T> query = em.createNamedQuery(namedQuery, entityClass);
        int count = 1;
        for (Object value : parameters) {
            query.setParameter(count, value);
            count++;
        }
        if (resultLimit > NO_LIMIT) {
            query.setMaxResults(resultLimit);
        }
        return query;
    }

    /**
     * Return entity manager
     *
     * @return entityManager instance
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * Sets the Entity Manager This method is used for entity manager injection.
     *
     * @param em EntityManager
     */
    @Inject
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
