package org.fundaciobit.blueprint.ejb.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entitat per representar un contador: una clau i un nombre.
 */
@Entity
@Table(name = "BLP_COUNTER", indexes =
   @Index(name = "BLP_COUNTER_PK_I", columnList = "ID", unique = true)
)
@NamedQuery(
        name="updateCounter",
        query="UPDATE Counter c SET c.counterValue = c.counterValue + 1 WHERE c.id = :id"
)
public class Counter implements Serializable {

   private static final long serialVersionUID = 1L;

   public Counter() {
   }

   @Id
   @Column(name="ID", nullable = false, length = 100)
   private String id;

   @Column(name="COUNTERVALUE", nullable = false)
   private int counterValue;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public int getCounterValue() {
      return counterValue;
   }

   public void setCounterValue(int counterValue) {
      this.counterValue = counterValue;
   }

   /**
    * Incrementa el comptador amb una unitat
    * @return el nou valor del comptador
    */
   public int incValue() {
      return ++counterValue;
   }
}
