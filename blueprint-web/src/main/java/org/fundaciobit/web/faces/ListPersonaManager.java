package org.fundaciobit.web.faces;

import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.jpa.Persona;
import org.fundaciobit.blueprint.ejb.service.PersonaService;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * Bean emprat a JSF per gestionar el llistat de Persones.
 * Empram ViewScoped perquè amb RequestScope no funcionaria l'AJAX.
 */
@Named("listPersona")
@ViewScoped
public class ListPersonaManager extends AbstractListManager<Long, Persona> {

    @EJB
    private PersonaService personaService;

    @Override
    protected DAO<Long, Persona> getService() {
        return personaService;
    }
}
