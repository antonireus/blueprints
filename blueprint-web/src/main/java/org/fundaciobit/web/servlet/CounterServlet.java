package org.fundaciobit.web.servlet;

import org.fundaciobit.blueprint.ejb.jpa.Counter;
import org.fundaciobit.blueprint.ejb.service.CounterService;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Servlet que manté un contador del número de vegades que ha estat cridat.
 */
@WebServlet(name="counterServlet", urlPatterns="/counter", loadOnStartup = 10)
public class CounterServlet extends HttpServlet {

	private static final long serialVersionUID = 4433545994404713280L;

    private static final Logger log = Logger.getLogger(CounterServlet.class.getName());

    /**
     * Servei per incrementar el contador evitant problemes de concurrència.
     * La implementació singleton empra la gestió de concurrència sobre l'EJB.
     * La implemntació normal empra un lock de JPA a nivell de base de dades.
     */
    //@EJB(beanName = "CounterServiceBean")
    @EJB(beanName = "CounterServiceSingletonBean")
    private CounterService counterService;

    private static final String COUNTER_ID = "COUNTER_ID";

    @Override
    public void init(ServletConfig config) {
        log.info("init");

        Counter counter = counterService.findById(COUNTER_ID);
        if (counter == null) {
            log.info("Creant counter " + COUNTER_ID);
            counter = new Counter();
            counter.setId(COUNTER_ID);
            counterService.create(counter);
        } else {
            log.info("Counter value al inici: " + counter.getCounterValue());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("doGet");

        int counterValue = counterService.incCounter(COUNTER_ID);
        log.info("Counter value: " + counterValue);

        ServletOutputStream os = response.getOutputStream();
        os.println("<html><head><title>Counter</title></head><body>");
        os.println("<h1>Counter: " + COUNTER_ID + "</h1>");
        os.println("<p>Counter value: " + counterValue + "</p>");
        os.println("</body></html>");
    }
}
