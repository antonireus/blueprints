package org.fundaciobit.blueprint.ejb.event;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Logger;

public class CreationObserver {

    @Inject
    private Logger logger;

    public void afterCreation(@Observes @CreatedEvent Object object) {
        logger.info("There was a creation of: " + object.getClass().getSimpleName());
    }
}
