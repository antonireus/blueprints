package org.fundaciobit.blueprint.ejb.event;

import javax.enterprise.event.Observes;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Observador d'esdeveniments de tipus creació.
 * @author Antoni
 */
public class CreationObserver {

    private static final Logger LOG = Logger.getLogger(CreationObserver.class.getName());

    /**
     * Rep els esdeveniments de tipus creació d'entitat.
     * @param object Entitat que ha disparat l'esdeveniment.
     */
    public void afterCreation(@Observes @CreatedEvent Object object) {
        LOG.log(Level.INFO, "There was a creation of: {0}", object.getClass().getSimpleName());
    }
}
