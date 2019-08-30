package org.fundaciobit.blueprint.ejb.service;

import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * EJB que simula una tasca que s'executa cada cert temps.
 * La podria posar en marxa un altre EJB Startup o es pot programar
 * amb annotacions com en aquest cas.
 * Si volguessim una sola classe sense por a cridades concurrents i compartint
 * estat entre cridades el fariem @Singleton enlloc de @Stateless
 */
@Stateless
public class ScheduledServiceBean {

   @Inject
   private Logger logger;

   /**
    * El mètode s'executa cada minut normalment, cada 2 minuts en cap de setmana.
    * Persistent a false indica que si el servidor s'atura no fa falta
    * recuperar les cridades planificades que s'han perdut.
    */
   @Schedules({
         @Schedule(minute = "*/1", hour = "*", dayOfWeek = "Mon-Fri", persistent = false, info = "entresetmana"),
         @Schedule(minute = "*/2", hour = "*", dayOfWeek = "Sat-Sun", persistent = false, info = "capdesetmana")
   })
   protected void scheduledTask(Timer timer) {
      logger.info("scheduledTask: " + timer.getInfo());
   }
}
