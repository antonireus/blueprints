package org.fundaciobit.blueprint.ejb.dao;

import org.fundaciobit.blueprint.common.interceptor.Logged;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Logged
public abstract class AbstractJpaDAO<K, E> implements DAO<K, E> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<E> entityClass;

    protected AbstractJpaDAO() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public E create(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public E update(E entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(K id) {
        delete(findById(id));
    }

    @Override
    public E findById(K id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<E> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        Root<E> entity = query.from(entityClass);
        TypedQuery<E> typedQuery = entityManager.createQuery(query.select(entity));
        return typedQuery.getResultList();
    }
}
