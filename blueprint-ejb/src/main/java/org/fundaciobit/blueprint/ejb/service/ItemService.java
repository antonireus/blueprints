package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.common.constraint.NIF;
import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.jpa.Item;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ItemService extends DAO<Long, Item> {

    List<Item> findByNIFs(List<@NIF String> nifs);

    Item findByNIF(@NIF String nif);
}
