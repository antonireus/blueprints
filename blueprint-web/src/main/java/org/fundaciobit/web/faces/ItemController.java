package org.fundaciobit.web.faces;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import java.util.List;

@Named
public class ItemController {

    @EJB
    private ItemService itemService;

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    @PostConstruct
    private void init() {
        items = itemService.findAll();
    }
}
