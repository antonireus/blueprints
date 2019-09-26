package org.fundaciobit.web.servlet;

import org.fundaciobit.blueprint.ejb.jpa.Counter;
import org.fundaciobit.blueprint.ejb.service.CounterService;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name="counterServlet", urlPatterns="/counter", loadOnStartup = 10)
public class CounterServlet extends HttpServlet {

	private static final long serialVersionUID = 4433545994404713280L;

    private static final Logger log = Logger.getLogger(CounterServlet.class.getName());

    @EJB(beanName = "CounterServiceSingletonBean")
    private CounterService counterService;

    private  static final String COUNTER_ATTRIBUTE = "COUNTER_KEY";

    class CounterHolder {
        int value;
    }

    @Override
    public void init(ServletConfig config) {
        log.info("init");
        ServletContext context = config.getServletContext();
        context.setAttribute(COUNTER_ATTRIBUTE, new CounterHolder());

        Counter counter = counterService.findById(COUNTER_ATTRIBUTE);
        if (counter == null) {
            counter = new Counter();
            counter.setId(COUNTER_ATTRIBUTE);
            counterService.create(counter);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("doGet");

        ServletContext context = request.getServletContext();

        int contextCounter;
        int persistenceCounter;

        final CounterHolder counter = (CounterHolder) context.getAttribute(COUNTER_ATTRIBUTE);
        synchronized (counter) {
            contextCounter = ++counter.value;
        }

        persistenceCounter = counterService.incCounter(COUNTER_ATTRIBUTE);

        log.info("ContextCounter: " + contextCounter +
                ", PersistenceCounter: " + persistenceCounter);

        ServletOutputStream os = response.getOutputStream();
        os.println("<html><head><title>Counter</title></head><body>");
        os.println("<h1>Context counter: " + contextCounter + "</h1>");
        os.println("<h1>Persistence counter: " + persistenceCounter + "</h1>");
        os.println("</body></html>");
    }
}
