package org.fundaciobit.blueprint.ejb.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * Entitat per representar un contador: una clau i un nombre.
 */
@Entity
@Table(name = "BLP_COUNTER", indexes =
   @Index(name = "BLP_COUNTER_PK_I", columnList = "ID", unique = true)
)
public class Counter extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;

   /**
    * Constructor buid.
    */
   public Counter() {
   }

   @Id
   @Column(name="ID", nullable = false, length = 100)
   private String id;

   @Column(name="COUNTERVALUE", nullable = false)
   private int counterValue;

   /**
    * Obté l'identificador del comptador.
    * @return identificador del comptador.
    */
   @Override
   public String getId() {
      return id;
   }

   /**
    * Fixa l'identificador del comptador.
    * @param id Identificador del comptador.
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Obté el valor del comptador.
    * @return valor del comptador.
    */
   public int getCounterValue() {
      return counterValue;
   }

   /**
    * Fixa el valor del comptador
    * @param counterValue valor del comptador.
    */
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
