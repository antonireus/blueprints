package org.fundaciobit.web.faces.item;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Bean emprat a JSF per gestionar l'alta i l'actualització de Items.
 * Empram ViewScoped perquè amb RequestScope no funcionaria l'AJAX.
 */
@Named("editItem")
@ViewScoped
public class EditItemManager implements Serializable {

    private static final Logger LOG = Logger.getLogger(EditItemManager.class.getName());

    // RECURSOS

    @Inject
    private FacesContext context;

    /**
     * Objecte emprat per mantenir missatges (o altres objectes) entre peticions.
     */
    @Inject
    private Flash flash;

    @EJB
    private ItemService itemService;

    // PROPIETATS

    /**
     * Instància de l'Item que guardarà l'estat.
     */
    private Item item;

    public Item getItem() {
        return item;
    }

    public boolean isCreate() {
        return item.getId() == null;
    }

    // MÈTODES

    @PostConstruct
    private void init() {
        LOG.info("init");
        item = new Item();
    }

    /**
     * Cridat com acció al principi de la vista per precarregar l'Item amb el paràmetre rebut com id.
     */
    public void loadItem() {
        LOG.info("loadItem()");
        if (item.getId() != null) {
            item = itemService.findById(item.getId());
        }
    }

    /**
     * Cridat pel botó de crear o actualitzar.
     * @return id de navegació
     */
    public String createOrUpdateItem() {
        LOG.info("createOrUpdateItem() isCreate=" + isCreate());
        if (isCreate()) {
            itemService.create(item);
            context.addMessage(null, new FacesMessage("Creació correcte"));
        } else {
            itemService.update(item);
            context.addMessage(null, new FacesMessage("Modificació correcte"));
        }
        // Els missatges no aguanten una redirecció ja que no es la mateixa petició
        // amb l'objecte flash podem assegurar que es guardin fins la visualització
        flash.setKeepMessages(true);
        // Redireccionam cap al llistat d'items
        return "listItem?faces-redirect=true";
    }
}
