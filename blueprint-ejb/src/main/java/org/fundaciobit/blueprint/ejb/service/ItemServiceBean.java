package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.common.constraint.NIF;
import org.fundaciobit.blueprint.ejb.dao.AbstractJpaDAO;
import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.jpa.UniqueConstraintException;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;

@Stateless
public class ItemServiceBean extends AbstractJpaDAO<Long, Item> implements ItemService {

   @Override
   public Item create(Item entity) {
      Item other = findByNIF(entity.getNif());
      if (other != null) {
         throw new UniqueConstraintException(other.getNif());
      }
      return super.create(entity);
   }

   @Override
   public List<Item> findByNIFs(List<@NIF String> nifs) {
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);
      Root<Item> from = query.from(Item.class);
      CriteriaBuilder.In<String> in = criteriaBuilder.in(from.get("nif"));
      nifs.stream().map(String::toUpperCase).forEach(in::value);
      TypedQuery<Item> typedQuery = entityManager.createQuery(query.select(from).where(in));
      return typedQuery.getResultList();
   }

   @Override
   public Item findByNIF(@NotNull @NIF String nif) {
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);
      Root<Item> from = query.from(Item.class);
      Predicate equals = criteriaBuilder.equal(from.get("nif"), nif.toUpperCase());
      TypedQuery<Item> typedQuery = entityManager.createQuery(query.select(from).where(equals));
      List<Item> resultList = typedQuery.getResultList();
      return resultList.isEmpty() ? null : resultList.get(0);
   }
}
