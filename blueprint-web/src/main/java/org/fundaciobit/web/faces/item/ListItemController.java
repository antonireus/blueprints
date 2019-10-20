package org.fundaciobit.web.faces.item;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;
import org.fundaciobit.web.faces.PaginationHelper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Bean emprat a JSF per gestonar el llistat d'Items.
 */
@Named("listItem")
@ViewScoped
public class ListItemController implements Serializable {

    private static final Logger LOG = Logger.getLogger(ListItemController.class.getName());

    // RECURSOS

    @EJB
    private ItemService itemService;

    // PROPIETATS

    private List<Item> items;

    private final Map<String, String> filter = new HashMap<>();

    private final PaginationHelper pagination = new PaginationHelper(5);

    public List<Item> getItems() {
        return items;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public PaginationHelper getPagination() {
        return pagination;
    }

    // MÈTODES

    @PostConstruct
    private void init() {
        LOG.info("init");
        loadList();
    }

    public void loadList() {
        LOG.info("loadList. filter=" + filter);
        pagination.setCount((int) itemService.countFiltered(filter));
        items = itemService.findFiltered(pagination.getPageFirstItem(), pagination.getPageSize(),filter);
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
