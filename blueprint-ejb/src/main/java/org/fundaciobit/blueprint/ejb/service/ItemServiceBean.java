package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.common.constraint.NIF;
import org.fundaciobit.blueprint.ejb.dao.AbstractJpaDAO;
import org.fundaciobit.blueprint.ejb.jpa.Item;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Stateless
public class ItemServiceBean extends AbstractJpaDAO<Long, Item> implements ItemService {

   @Override
   public Item create(Item entity) {
      entity.setCreation(new Date());
      return super.create(entity);
   }

   @Override
   public List<Item> findByNIFs(List<@NIF String> nifs) {
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);
      Root<Item> item = query.from(Item.class);
      CriteriaBuilder.In<String> in = criteriaBuilder.in(item.get("nif"));
      nifs.forEach((nif) -> in.value(nif));
      TypedQuery<Item> typedQuery = entityManager.createQuery(query.select(item).where(in));
      return typedQuery.getResultList();
   }
}
