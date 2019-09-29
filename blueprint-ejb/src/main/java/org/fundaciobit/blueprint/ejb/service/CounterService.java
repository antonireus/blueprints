package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.jpa.Counter;

import javax.ejb.Local;

/**
 * Interfície de l'EJB de gestió de comptadors.
 * La idea és que les implementacions funcionin correctament amb concurrència.
 * La implementació singleton empra la gestió de concurrència del contenidor. L'altra
 * empra un lock a nivell de JPA/base de dades.
 * @author Antoni
 * @see CounterServiceBean
 * @see CounterServiceSingletonBean
 */
@Local
public interface CounterService extends DAO<String, Counter> {

    /**
     * Incrementa el comptador amb la clau indicada en una unitat.
     * @param key clau del comptador
     * @return el nou valor del comptador
     */
    int incCounter(String key);
}
