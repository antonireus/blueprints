package org.fundaciobit.blueprint.ejb.jpa;

import javax.persistence.PersistenceException;

/**
 * Excepció per indicar la violació d'una clau única a la capa de persistència.
 * @author areus
 */
public class UniqueConstraintException extends PersistenceException {

    public UniqueConstraintException(String message) {
        super(message);
    }
}
