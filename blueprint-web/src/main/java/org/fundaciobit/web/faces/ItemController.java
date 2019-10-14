package org.fundaciobit.web.faces;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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

    @Inject
    FacesContext context;

    @EJB
    private ItemService itemService;

    private Item editItem;
    private List<Item> items;

    private PaginationHelper pagination = new PaginationHelper(2);

    @PostConstruct
    private void init() {
        LOG.info("init");
        refreshList();
        refreshEdit();
    }

    private void refreshList() {
        pagination.setCount((int) itemService.countAll());
        items = itemService.findAll(pagination.getPageFirstItem(), pagination.getPageSize());
    }

    private void refreshEdit() {
        editItem = new Item();
    }

    public PaginationHelper getPagination() {
        return pagination;
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
        context.addMessage(null, new FacesMessage("Alta correcte"));
        refreshList();
        refreshEdit();
    }

    public void next() {
        if (pagination.isHasNextPage()) {
            pagination.nextPage();
            refreshList();
        }
    }

    public void previous() {
        if (pagination.isHasPreviousPage()) {
            pagination.previousPage();
            refreshList();
        }
    }
}
