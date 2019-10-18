package org.fundaciobit.web.faces.item;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;
import org.fundaciobit.web.faces.PaginationHelper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Bean emprat a JSF per gestonar el llistat d'Items.
 */
@Named
@ViewScoped
public class ListItemController implements Serializable {

    private static final Logger LOG = Logger.getLogger(ListItemController.class.getName());

    @EJB
    private ItemService itemService;

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    private PaginationHelper pagination = new PaginationHelper(5);

    public PaginationHelper getPagination() {
        return pagination;
    }

    @PostConstruct
    private void init() {
        LOG.info("init");
        loadList();
    }

    private void loadList() {
        pagination.setCount((int) itemService.countAll());
        items = itemService.findAll(pagination.getPageFirstItem(), pagination.getPageSize());
    }

    public void next() {
        if (pagination.isHasNextPage()) {
            pagination.nextPage();
            loadList();
        }
    }

    public void previous() {
        if (pagination.isHasPreviousPage()) {
            pagination.previousPage();
            loadList();
        }
    }
}
