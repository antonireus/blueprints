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
    * El mètode s'executa cada 10 segons normalment, cada 20 en cap de setmana.
    * Persistent a false indica que si el servidor s'atura no fa falta
    * recuperar les cridades planificades que s'han perdut.
    */
   @Schedules({
         @Schedule(second = "*/10", minute = "*", hour = "*", dayOfWeek = "Mon-Fri", persistent = false),
         @Schedule(second = "*/20", minute = "*", hour = "*", dayOfWeek = "Sat-Sun", persistent = false, info="capdesetmana")
   })
   protected void executeEvery10or20Seconds(Timer timer) {
      if (timer.getInfo() != null) {
         logger.info("20 seconds!");
      } else {
         logger.info("10 seconds!");
      }
   }
}
