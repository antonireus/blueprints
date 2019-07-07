package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.ejb.dao.AbstractJpaDAO;
import org.fundaciobit.blueprint.ejb.jpa.Item;

import javax.ejb.Stateless;

@Stateless
public class ItemServiceBean extends AbstractJpaDAO<Long, Item> implements ItemService {

}
