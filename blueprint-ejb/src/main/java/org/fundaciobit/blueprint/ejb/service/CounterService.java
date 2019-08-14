package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.jpa.Counter;

import javax.ejb.Local;

@Local
public interface CounterService extends DAO<String, Counter> {

    int incCounter(String key);
}
