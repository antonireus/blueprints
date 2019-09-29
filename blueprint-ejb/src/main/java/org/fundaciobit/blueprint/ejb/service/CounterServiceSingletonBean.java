package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.ejb.dao.AbstractJpaDAO;
import org.fundaciobit.blueprint.ejb.jpa.Counter;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 * Feim servir un singleton per incrementar el contador evitant concurrència. A diferència de la solució amb un
 * lock de JPA/base de dades, aquesta solució només es podria emprar si no hi ha altres aplicacions o components
 * atacant la mateixa taula de base de dades.
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class CounterServiceSingletonBean extends AbstractJpaDAO<String, Counter> implements CounterService {

    @Lock(LockType.WRITE)
    @Override
    public int incCounter(String key) {
        Counter counter = entityManager.find(Counter.class, key);
        return counter.incValue();
    }
}
