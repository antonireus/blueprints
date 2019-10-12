package org.fundaciobit.web.servlet;

import org.fundaciobit.blueprint.ejb.service.MailService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "mailServlet", urlPatterns = "/mail", loadOnStartup = 2)
public class MailServlet extends HttpServlet {

	private static final long serialVersionUID = -3256837260198852283L;

	private static final Logger log = Logger.getLogger(MailServlet.class.getName());

    @EJB
    private MailService mailService;

    @Override
    public void init() {
        log.info("init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet");
        request.getRequestDispatcher("/mail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("doPost");

        String subject = request.getParameter("subject");
        String email = request.getParameter("email");
        String content = request.getParameter("content");

        mailService.sendEmail(subject, email, content);

        response.sendRedirect(request.getContextPath() + "/mail");
    }
}
