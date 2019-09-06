package org.fundaciobit.blueprint.common;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

/**
 * Bean que proporciona instàncies de log
 * @author areus
 */
public class LogProducer {

    /**
     * Produeix una instància de logger de JUL
     * @param injectionPoint paràmetre automàtic de CDI que permet informació de context.
     * @return instància inicialitzada amb el nom de la classe on es fa la injecció
     */
    @Produces
    public Logger createLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
