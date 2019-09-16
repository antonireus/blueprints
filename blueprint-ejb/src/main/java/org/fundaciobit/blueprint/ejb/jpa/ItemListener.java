package org.fundaciobit.blueprint.ejb.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.logging.Logger;

public class ItemListener {

   private static final Logger log = Logger.getLogger(ItemListener.class.getName());

   @PersistenceContext
   protected EntityManager entityManager;

   @PrePersist
   public void prePersist(Item item) {
      log.info("prePersist");
      // Sempre guardam el NIF amb la lletra majuscula
      item.setNif(item.getNif().toUpperCase());

      // TODO no funciona amb EclipseLink que crida el mètode després de fer l'INSERT
      /*
      List<Item> result = entityManager.createNamedQuery("findByNIF", Item.class)
            .setParameter("nif", item.getNif())
            .getResultList();

      if (!result.isEmpty()) {
         String msg = "prePersist: " + item.getNif() + " ja existeix, ID=" + result.get(0).getId();
         log.warning(msg);
         throw new PersistenceException(msg);
      }*/
   }

   @PreUpdate
   public void preUpdate(Item item) {
      log.info("preUpdate");
      // Sempre guardam el NIF amb la lletra majuscula
      item.setNif(item.getNif().toUpperCase());

      // TODO Comprovar que el NIF que es va guardar no està associat amb un altre id
   }

}
