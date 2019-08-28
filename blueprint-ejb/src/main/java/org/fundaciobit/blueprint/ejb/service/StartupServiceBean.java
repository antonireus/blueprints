package org.fundaciobit.blueprint.ejb.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * EJB únic que s'executa a la inicialització.
 * Es poden establir dependències d'altres serveis amb l'annotació @DependsOn
 * Veure 4.8.1 de l'spec EJB 3.2
 */
@Singleton
@Startup
public class StartupServiceBean {

   @Inject
   private Logger logger;

   @PostConstruct
   protected void init() {
      //TODO aquí es podria comprovar la taula de base de dades de configuració, i comprovar
      // que tots els paràmetres hi són, o crear valors per defecte pels que no hi siguin
      logger.info("startup!");
   }

   @PreDestroy
   protected void destroy() {
      logger.info("shutdown!");
   }
}
