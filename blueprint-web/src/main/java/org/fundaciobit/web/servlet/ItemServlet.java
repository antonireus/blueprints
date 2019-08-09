package org.fundaciobit.web.servlet;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@WebServlet(name = "itemServlet", urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

	 private static final long serialVersionUID = -7525166929518102623L;

	 @Inject
    private Logger logger;

	@Resource
    private Validator validator;

    @EJB
    private ItemService itemService;

    @Override
    public void init() {
        logger.info("init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        logger.info("doGet");

        String name = request.getParameter("name");
        String nif = request.getParameter("nif");
        if (name != null) {
            Item item = new Item();
            item.setName(name);
            item.setNif(nif);
            logger.info("itemService.create: " + name + ", " + nif);
            Set<ConstraintViolation<Item>> violationSet = validator.validate(item);
            if (violationSet.isEmpty()) {
                try {
                    itemService.create(item);
                } catch (EJBException ejbException) {
                    logger.severe(ejbException.getMessage());
                }
            } else {
                for (ConstraintViolation<Item> violation : violationSet) {
                    logger.warning(violation.getPropertyPath() + ": " + violation.getMessage());
                }
            }
        }

        logger.info("itemService.findAll");
        List<Item> items = itemService.findAll();

        ServletOutputStream os = response.getOutputStream();
        os.println("<html><head><title>Items</title></head><body>");
        if (items.isEmpty()) {
            os.println("Buid. Afegeix el paràmetre 'name' per crear un item.");
        } else {
            os.println("<ul>");
            for (Item item : items) {
                os.println("<li>" + item.getId() + ", " + item.getName() + ", " + item.getCreation() + "</li>");
            }
            os.println("</ul>");
        }

        os.println("</body></html>");
    }
}
