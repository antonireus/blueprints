package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.ejb.dao.AbstractJpaDAO;
import org.fundaciobit.blueprint.ejb.jpa.Counter;

import javax.ejb.Stateless;
import javax.persistence.LockModeType;

/**
 * Feim servir un lock a nivell de JPA/base de dades per incrementar el contador evitant concurrència.
 */
@Stateless
public class CounterServiceBean extends AbstractJpaDAO<String, Counter> implements CounterService {

    @Override
    public int incCounter(String key) {
        Counter counter = entityManager.find(Counter.class, key, LockModeType.PESSIMISTIC_WRITE);
        return counter.incValue();
    }
}
