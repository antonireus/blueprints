package org.fundaciobit.blueprint.ejb.dao;

import java.util.List;

public interface DAO<K, E> {

    E create(E entity);

    void delete(K entity);

    E findById(K id);

    List<E> findAll();
}
