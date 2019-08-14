package org.fundaciobit.blueprint.ejb.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 */
@Entity
@Table(name = "BLP_COUNTER", indexes =
   @Index(name = "BLP_COUNTER_PK_I", columnList = "KEY", unique = true)
)
@NamedQuery(
        name="updateCounter",
        query="UPDATE Counter c SET c.value = c.value + 1 WHERE c.key = :key"
)
public class Counter implements Serializable {

   private static final long serialVersionUID = 1L;

   public Counter() {
   }

   @Id
   @Column(name="KEY", nullable = false, length = 100)
   private String key;

   @Column(name="VALUE", nullable = false)
   private int value;

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public int getValue() {
      return value;
   }

   public void setValue(int value) {
      this.value = value;
   }

   public int incValue() {
      return ++value;
   }
}
