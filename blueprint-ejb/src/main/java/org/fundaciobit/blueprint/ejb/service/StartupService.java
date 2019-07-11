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
public class StartupService {

   @Inject
   private Logger logger;

   @PostConstruct
   public void init() {
      logger.info("startup!");
   }

   @PreDestroy
   public void destroy() {
      logger.info("shutdown!");
   }
}
