package org.fundaciobit.web.servlet;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Inject
    private Logger logger;

    @EJB
    private ItemService itemService;

    @PostConstruct
    public void init() {
        logger.info("INIT");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        if (name != null) {
            logger.info("Creant nou item: " + name);
            Item item = new Item();
            item.setName(name);
            itemService.create(item);
        }

        List<Item> items = itemService.findAll();

        ServletOutputStream os = response.getOutputStream();
        os.println("<html><head><title>Items</title></head><body>");
        if (items.isEmpty()) {
            os.println("Buid. Afegeix el paràmetre 'name' per crear un item.");
        } else {
            os.println("<ul>");
            for (Item item : items) {
                os.println("<li>" + item.getId() + ", " + item.getName() + "</li>");
            }
            os.println("</ul>");
        }

        os.println("</body></html>");
    }
}
