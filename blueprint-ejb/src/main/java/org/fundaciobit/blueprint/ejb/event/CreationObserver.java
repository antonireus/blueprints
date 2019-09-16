package org.fundaciobit.blueprint.ejb.event;

import javax.enterprise.event.Observes;
import java.util.logging.Logger;

public class CreationObserver {

    private static final Logger log = Logger.getLogger(CreationObserver.class.getName());

    public void afterCreation(@Observes @CreatedEvent Object object) {
        log.info("There was a creation of: " + object.getClass().getSimpleName());
    }
}
