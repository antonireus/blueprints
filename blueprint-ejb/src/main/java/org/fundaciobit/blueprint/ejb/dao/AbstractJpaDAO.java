package org.fundaciobit.blueprint.ejb.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractJpaDAO<K, E> implements DAO<K, E> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<E> entityClass;

    public AbstractJpaDAO() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public E create(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(K entity) {
        entityManager.remove(entity);
    }

    @Override
    public E findById(K id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<E> findAll() {
        return entityManager.createQuery(
                "select e from " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }
}
