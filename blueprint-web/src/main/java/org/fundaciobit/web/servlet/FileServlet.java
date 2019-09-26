package org.fundaciobit.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Location (8.1.5) pot ser un directori absolut, o un directori relatiu. Si és relatiu o serà respecte
 * el directori temporal proporcinat pel contenidor a cada mòdul web: javax.servlet.context.tempdir (4.8.1).
 * Els fitxers es guardaran temporalment a disc a partir de 16k (16384 bytes)
 */
@WebServlet(name = "fileServlet", urlPatterns = "/file", loadOnStartup = 1)
@MultipartConfig(location = "fileServlet", fileSizeThreshold = 16384)
public class FileServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(FileServlet.class.getName());

    private File uploadDir;

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.info("init");

        File contextTempDir = (File) config.getServletContext().getAttribute("javax.servlet.context.tempdir");
        if (contextTempDir == null) {
            throw new UnavailableException("Atribut 'javax.servlet.context.tempdir' és null");
        }

        log.info("contexTempDir: " + contextTempDir.getAbsolutePath());

        if (!contextTempDir.exists() || !contextTempDir.isDirectory()) {
            throw new UnavailableException(contextTempDir.getAbsolutePath() + " no existeix o no és un directori");
        }

        // No hi ha manera d'agafar la cadena que s'ha ficat a location? Sembla que no, hem de repetir el literal.
        // El directori s'ha de crear, perquè no es crea automàticament.
        uploadDir = new File(contextTempDir, "fileServlet");
        if (uploadDir.exists()) {
            log.info(uploadDir.getAbsolutePath() + " ja existeix.");
        } else if (!uploadDir.mkdir()) {
            throw new UnavailableException("No s'ha pogut crear el directori " + uploadDir.getAbsolutePath());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<File> files = Arrays.asList(uploadDir.listFiles());
        request.setAttribute("files", files);
        request.getRequestDispatcher("/files.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.info("doPost");

        Part filePart = request.getPart("file");

        log.info("Name: " + filePart.getName());
        log.info("SubmittedFileName: " + filePart.getSubmittedFileName());
        log.info("ContentType: " + filePart.getContentType());
        log.info("Size: " + filePart.getSize());

        if (filePart.getSize() > 0) {
            filePart.write(UUID.randomUUID().toString());
            filePart.delete();
        }

        response.sendRedirect(request.getContextPath() + "/file");
    }
}
