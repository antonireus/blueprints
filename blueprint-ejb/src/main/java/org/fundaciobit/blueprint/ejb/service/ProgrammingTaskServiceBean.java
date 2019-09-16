package org.fundaciobit.blueprint.ejb.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import java.util.logging.Logger;

@Startup
@Singleton
@DependsOn("StartupServiceBean")
public class ProgrammingTaskServiceBean {

   private static final Logger log = Logger.getLogger(ProgrammingTaskServiceBean.class.getName());

   @Resource
   private TimerService timerService;

   @PostConstruct
   protected void init() {
      log.info("init programming task!");

      // Cream un timer no persistent que s'executa la primera vegada d'aquí 60 segons, i després cada 60 segons.
      TimerConfig config1 = new TimerConfig("cada 60 segons", false);
      timerService.createIntervalTimer(60_000, 60_000, config1);

      // Cream un timer no persistent que s'executa al segon 17 de cada minut.
      TimerConfig config2 = new TimerConfig("cada minut al segon 17", false);
      ScheduleExpression expression = new ScheduleExpression()
            .second(17)
            .minute("*")
            .hour("*");
      timerService.createCalendarTimer(expression, config2);
   }

   /**
    * Realment no seria necessari perquè els timers no són persistents, però
    * es possible cancel·lar timers si volem.
    */
   @PreDestroy
   protected void destroy() {
      log.info("deprogramming tasks!");
      timerService.getTimers().forEach(Timer::cancel);
   }

   @Timeout
   protected void programmedTask(Timer timer) {
      log.info("programmedTask: " + timer.getInfo());
   }
}