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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Llista els fitxers dins el directori de treball, i permet pujar-hi fitxers amb un formulari que defineixi
 * un camp de tipus file i nom file.
 *
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

        // D'altra banda, hauríem de tenir en compte File.isAbsolut per saber si uploadDir és una ruta absoluta
        // o és relativa a tempDir.
        uploadDir = new File(contextTempDir, "fileServlet");
        if (uploadDir.exists()) {
            log.info(uploadDir.getAbsolutePath() + " ja existeix.");
        } else if (!uploadDir.mkdir()) {
            throw new UnavailableException("No s'ha pogut crear el directori " + uploadDir.getAbsolutePath());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.info("doGet");

        File[] fileArray = uploadDir.listFiles();
        List<File> files = fileArray != null ? Arrays.asList(fileArray) : new ArrayList<>();

        request.setAttribute("files", files);
        request.getRequestDispatcher("/files.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.info("doPost");

        Part part = request.getPart("file");

        log.info("Name: " + part.getName());
        log.info("SubmittedFileName: " + part.getSubmittedFileName());
        log.info("ContentType: " + part.getContentType());
        log.info("Size: " + part.getSize());

        if (part.getSize() > 0) {
            String uuid = UUID.randomUUID().toString();
            /*
            En teoria bastaria fer part.write(uuid); però en jboss dona un error.
            Així que copiam de l'inputStream.
             */

            // uploadDir és un directori temporal. Això en producció no serveix.
            File savedFile = new File(uploadDir, uuid);
            try (InputStream is = part.getInputStream()) {//try-with-resources tancarà automàticament l'InputStream
                Files.copy(is, savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            // Borram els possibles fitxers temporals que s'hagin generat.
            part.delete();
        }

        response.sendRedirect(request.getContextPath() + "/file");
    }
}
