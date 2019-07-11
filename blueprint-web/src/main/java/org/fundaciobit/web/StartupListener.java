package org.fundaciobit.web;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

/**
 * Permet fer tasques d'inicialització/finalització al contenidor web.
 */
@WebListener
public class StartupListener implements ServletContextListener {

   @Inject
   private Logger logger;

   @Override
   public void contextInitialized(ServletContextEvent sce) {
      logger.info("Context initialized!");
   }

   @Override
   public void contextDestroyed(ServletContextEvent sce) {
      logger.info("Context destroyed!");
   }
}
