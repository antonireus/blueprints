package org.fundaciobit.web.faces;

import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.jpa.Persona;
import org.fundaciobit.blueprint.ejb.service.PersonaService;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * Bean emprat a JSF per gestionar l'alta i l'actualització de Persona.
 * Empram ViewScoped perquè amb RequestScope no funcionaria l'AJAX.
 */
@Named("editPersona")
@ViewScoped
public class EditPersonaManager extends AbstractEditManager<Long, Persona> {

    @EJB
    private PersonaService personaService;

    @Override
    protected DAO<Long, Persona> getService() {
        return personaService;
    }
}
