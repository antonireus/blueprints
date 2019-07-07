package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.jpa.Item;

import javax.ejb.Local;

@Local
public interface ItemService extends DAO<Long, Item> {

}
