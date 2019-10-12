package org.fundaciobit.blueprint.ejb.jpa;

import javax.persistence.PersistenceException;

/**
 * Excepció per indicar la violació d'una clau única a la capa de persistència.
 * TODO: Seria millor que fos application exception enlloc de system exception? d'aquesta
 * manera arribaria al client, enlloc d'arribar dins una EJBException.
 * @author areus
 */
public class UniqueConstraintException extends PersistenceException {

	private static final long serialVersionUID = -7950645901253019396L;

	public UniqueConstraintException(String message) {
        super(message);
    }
}
