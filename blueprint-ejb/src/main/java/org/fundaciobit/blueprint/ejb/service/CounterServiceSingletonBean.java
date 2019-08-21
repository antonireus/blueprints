package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.ejb.dao.AbstractJpaDAO;
import org.fundaciobit.blueprint.ejb.jpa.Counter;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Lock(LockType.WRITE)
public class CounterServiceSingletonBean extends AbstractJpaDAO<String, Counter> implements CounterService {

    @Override
    public int incCounter(String key) {
        Counter counter = entityManager.find(Counter.class, key);
        return counter.incValue();
    }
}
