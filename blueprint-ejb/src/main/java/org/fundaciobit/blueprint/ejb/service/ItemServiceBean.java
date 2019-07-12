package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.ejb.dao.AbstractJpaDAO;
import org.fundaciobit.blueprint.ejb.jpa.Item;

import javax.ejb.Stateless;
import java.time.OffsetDateTime;

@Stateless
public class ItemServiceBean extends AbstractJpaDAO<Long, Item> implements ItemService {

   @Override
   public Item create(Item entity) {
      entity.setCreation(OffsetDateTime.now());
      return super.create(entity);
   }
}
