package org.fundaciobit.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

/**
 * Permet fer tasques d'inicialització/finalització al contenidor web.
 */
@WebListener
public class StartupListener implements ServletContextListener {

   private static final Logger log = Logger.getLogger(StartupListener.class.getName());

   @Override
   public void contextInitialized(ServletContextEvent sce) {
      log.info("Context initialized!");
   }

   @Override
   public void contextDestroyed(ServletContextEvent sce) {
      log.info("Context destroyed!");
   }
}
