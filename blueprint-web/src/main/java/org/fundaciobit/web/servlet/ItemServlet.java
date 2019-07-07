package org.fundaciobit.web.servlet;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @EJB
    private ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        if (name != null) {
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
