package org.fundaciobit.web.faces;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Bean emprat a JSF per gestionar Items.
 */
@Named
@ViewScoped
public class ItemController implements Serializable {

    private static final Logger LOG = Logger.getLogger(ItemController.class.getName());

    @EJB
    private ItemService itemService;

    private Item editItem;
    private List<Item> items;

    @PostConstruct
    private void init() {
        LOG.info("init");
        items = itemService.findAll();
        editItem = new Item();
    }

    public Item getEditItem() {
        return editItem;
    }

    public List<Item> getItems() {
        return items;
    }

    public void saveItem() {
        LOG.info("saveItem");
        itemService.create(editItem);
        init();
    }
}
