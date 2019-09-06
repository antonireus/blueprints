package org.fundaciobit.web.servlet;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
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
            throws ServletException, IOException {

        logger.info("doGet");
        request.setAttribute("items", itemService.findAll());

        request.getRequestDispatcher("/item/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.info("doPost");

        Item item = new Item();
        item.setName(request.getParameter("name"));
        item.setNif(request.getParameter("nif"));
        logger.info("itemService.create: " + item.getName() + ", " + item.getNif());

        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        if (constraintViolations.isEmpty()) {
            itemService.create(item);
        } else {
            request.getSession().setAttribute("item_constraintViolations", constraintViolations);
        }

        response.sendRedirect(request.getContextPath() + "/item");
    }
}
