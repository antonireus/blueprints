package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.ejb.dao.AbstractJpaDAO;
import org.fundaciobit.blueprint.ejb.jpa.Counter;

import javax.ejb.Stateless;
import javax.persistence.LockModeType;

@Stateless
public class CounterServiceBean extends AbstractJpaDAO<String, Counter> implements CounterService {

    @Override
    public int incCounter(String key) {
        /*entityManager.createNamedQuery("updateCounter")
                .setParameter("key", key)
                .executeUpdate();
        return findById(key).getValue();*/
        Counter counter = entityManager.find(Counter.class, key, LockModeType.PESSIMISTIC_WRITE);
        return counter.incValue();
    }
}
