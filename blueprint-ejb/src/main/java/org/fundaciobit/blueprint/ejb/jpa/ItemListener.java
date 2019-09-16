package org.fundaciobit.blueprint.ejb.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.logging.Logger;

/**
 * Reb els esdeveniments de JPA per l'entitat {@link Item}
 * @author Antoni
 */
public class ItemListener {

   private static final Logger LOG = Logger.getLogger(ItemListener.class.getName());

   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Es crida abans de fer la creació de Item.
    * @param item Item que és apunt de ser creat.
    */
   @PrePersist
   public void prePersist(Item item) {
      LOG.info("prePersist");
      // Sempre guardam el NIF amb la lletra majuscula
      item.setNif(item.getNif().toUpperCase());

      // TODO no funciona amb EclipseLink que crida el mètode després de fer l'INSERT
      /*
      List<Item> result = entityManager.createNamedQuery("findByNIF", Item.class)
            .setParameter("nif", item.getNif())
            .getResultList();

      if (!result.isEmpty()) {
         String msg = "prePersist: " + item.getNif() + " ja existeix, ID=" + result.get(0).getId();
         LOG.warning(msg);
         throw new PersistenceException(msg);
      }*/
   }

   /**
    * Es crida abans de l'actualització d'un Item.
    * @param item Item que és apunt de ser creat.
    */
   @PreUpdate
   public void preUpdate(Item item) {
      LOG.info("preUpdate");
      // Sempre guardam el NIF amb la lletra majuscula
      item.setNif(item.getNif().toUpperCase());

      // TODO Comprovar que el NIF que es va guardar no està associat amb un altre id
   }

}
