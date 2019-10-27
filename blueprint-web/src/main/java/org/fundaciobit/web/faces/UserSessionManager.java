package org.fundaciobit.web.faces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Controlador per gestionar les opcions de sessió de l'usuari.
 * Veure apartat 2.5.2.1 de l'especificació per saber com gestiona JSF els locales, però bàsicament tenir
 * en compte que per una banda hi ha el locale per defece i per l'altre la llista de suportats, i cal juntar-los.
 */
@Named("user")
@SessionScoped
public class UserSessionManager implements Serializable {

    private static final Logger LOG = Logger.getLogger(UserSessionManager.class.getName());

    @Inject
    private FacesContext context;

    private List<Locale> availableLocales;

    public List<Locale> getAvailableLocales() {
        return availableLocales;
    }

    /**
     * Idioma seleccionat.
     * TODO: Seria millor treballar directament amb Locale? Només s'hauria de solucioanr el problema
     * de la conversió.
     */
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @PostConstruct
    private void init() {
        LOG.info("init");
        availableLocales = new ArrayList<>();
        // TODO: liberty/MyFaces ja afegeix el default-locale dins els supported-locales.
        availableLocales.add(context.getApplication().getDefaultLocale());
        context.getApplication().getSupportedLocales().forEachRemaining(availableLocales::add);

        // Per defecte inialitzam el locale de l'usuari amb el locale que haurà autodectat el view d'acord amb
        // punt 2.5.2.1 de l'especificació
        language = context.getViewRoot().getLocale().getLanguage();
    }
}
