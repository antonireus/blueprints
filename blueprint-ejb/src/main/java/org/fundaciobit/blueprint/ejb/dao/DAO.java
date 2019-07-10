package org.fundaciobit.blueprint.ejb.dao;

import java.util.List;

public interface DAO<K, E> {

    E create(E entity);

    E update(E entity);

    void delete(E entity);

    void deleteById(K id);

    E findById(K id);

    List<E> findAll();
}
