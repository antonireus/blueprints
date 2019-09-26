package org.fundaciobit.web.servlet;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

@WebServlet(name = "itemServlet", urlPatterns = "/item", loadOnStartup = 10)
public class ItemServlet extends HttpServlet {

    private static final long serialVersionUID = -7525166929518102623L;

    private static final Logger log = Logger.getLogger(ItemServlet.class.getName());

    @Resource
    private Validator validator;

    @EJB
    private ItemService itemService;

    @Override
    public void init() {
        log.info("init");

        {
            Item item = new Item();
            item.setName("Item 1");
            item.setNif("00000000T");
            item.getDescription().put("ca", "Desc 1 ca");
            item.getDescription().put("es", "Desc 1 es");
            itemService.create(item);
        }

        {
            Item item = new Item();
            item.setName("Item 2");
            item.setNif("00000001R");
            item.getDescription().put("ca", "Desc 2 ca");
            item.getDescription().put("es", "Desc 2 es");
            itemService.create(item);
        }

        {
            Item item = new Item();
            item.setName("Item 3");
            item.setNif("00000002W");
            item.getDescription().put("ca", "Desc 3 ca");
            item.getDescription().put("es", "Desc 3 es");
            itemService.create(item);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet");
        request.setAttribute("items", itemService.findAll());

        request.getRequestDispatcher("/item/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("doPost");

        Item item = new Item();
        item.setName(request.getParameter("name"));
        item.setNif(request.getParameter("nif"));

        item.getDescription().put("ca", request.getParameter("description_ca"));
        item.getDescription().put("es", request.getParameter("description_es"));

        log.info("itemService.create: " + item.getName() + ", " + item.getNif());

        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        if (constraintViolations.isEmpty()) {
            itemService.create(item);
        } else {
            request.getSession().setAttribute("item_constraintViolations", constraintViolations);
        }

        response.sendRedirect(request.getContextPath() + "/item");
    }
}
