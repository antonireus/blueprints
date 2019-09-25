package org.fundaciobit.blueprint.ejb.jpa;

import org.fundaciobit.blueprint.common.constraint.NIF;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Requisits de les classes persistents:
 *   L'etiqueta Entity, constructor buid. Ni la classe ni els seus mètodes
 * o camps poden ser final. Si les hem de passar com a paràmetres detached ha d'implementar serializable
 * (i per tant també és recomanable el serialVersionUID).
 * Els camps persistents han de ser private protected o package-private i només es poden accedir mitjançant els
 * mètodes
 */
@Entity
@EntityListeners(ItemListener.class)
@SequenceGenerator(name="item-sequence", sequenceName = "BLP_ITEM_SEQ", allocationSize = 1)
@Table(name = "BLP_ITEM",
        indexes = {@Index(name = "BLP_ITEM_PK_I", columnList = "ITEMID", unique = true)},
        uniqueConstraints = {@UniqueConstraint(name= "BLP_ITEM_NIF_UK", columnNames = "NIF")}
)
@NamedQuery(
        name="findByNIF",
        query="SELECT i FROM Item i WHERE i.nif LIKE :nif"
)
@NamedEntityGraph(
        name="item_description", attributeNodes = {
                @NamedAttributeNode(value="description")
}
)
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Serializable {

   private static final long serialVersionUID = 1L;

   /**
    * Constructor buid.
    */
   public Item() {
   }

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item-sequence")
   @Column(name="ITEMID", nullable = false, length = 19)
   @XmlElement(required = true, nillable = true)
   private Long id;

   @Column(name = "NAME", nullable = false, length = 50)
   @NotNull @Size(min = 3, max= 50)
   @XmlElement(required = true)
   private String name;

   @Column(name = "NIF", nullable = false, length = 9)
   @NotNull @NIF
   @XmlElement(required = true)
   private String nif;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "CREATION", nullable = false)
   @XmlElement(required = true, nillable = true)
   private Date creation;

   @ElementCollection(fetch = FetchType.EAGER)
   @MapKeyColumn(name="LANG", length = 5)
   @Column(name="DESCRIPTION", nullable = false, length = 200)
   @CollectionTable(name="BLP_ITEM_I18N_DESCRIPTION",
           joinColumns = @JoinColumn(name="ITEM_ITEMID", referencedColumnName = "ITEMID",
                   foreignKey = @ForeignKey(name="BLP_ITEM_I18N_DESCRIPTION_ITEM_FK")),
           uniqueConstraints = @UniqueConstraint(name = "BLP_ITEM_I18N_DESCRIPTION_UK",
                   columnNames = {"ITEM_ITEMID", "LANG"}))
   private Map<String, @NotNull @Size(min = 3, max = 200) String> description = new HashMap<>();

   /**
    * Obté l'identificador del item.
    * @return Identificador del item.
    */
   public Long getId() {
      return id;
   }

   /**
    * Fixa l'identificador del item.
    * @param id Identificador del item.
    */
   public void setId(Long id) {
      this.id = id;
   }

   /**
    * Obté el nom del item.
    * @return Nom del item.
    */
   public String getName() {
      return name;
   }

   /**
    * Fixa el nom del item.
    * @param name Nom del item.
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Obté el NIF del item.
    * @return NIF del item.
    */
   public String getNif() {
      return nif;
   }

   /**
    * Fixa el NIF del item.
    * @param nif NIF del item.
    */
   public void setNif(String nif) {
      this.nif = nif;
   }

   /**
    * Obté la data de creació del item. Amb precisió de timestamp.
    * @return Data de creació del item.
    */
   public Date getCreation() {
      return creation;
   }

   /**
    * Fixa la data de creació del item.
    * @param creation Data de creació del item.
    */
   public void setCreation(Date creation) {
      this.creation = creation;
   }

   /**
    * Obté les descripcions del item, indexades per idioma. La clau del map de valors és el codi de l'idioma.
    * @return descripcions del item indexades per idioma.
    */
   public Map<String, String> getDescription() {
      return description;
   }

   /**
    * Fixa les descripcions del item.
    * @param description Descripcions del item indexades per idioma.
    */
   public void setDescription(Map<String, String> description) {
      this.description = description;
   }

   /*
      La implementació de equals i hashCode s'hauria de fer sempre emprant una clau natural.
      En aquest cas, nif. O en cas que no n'hi hagi amb l'id, però comparant-ho només si no és
      null, i retornant un valor fix al hashCode per evitar que canvii després de cridar persist.
      Veure:
      https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html
      Apartat: 2.5.7. Implementing equals() and hashCode()
    */

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if ( o == null || getClass() != o.getClass() ) {
         return false;
      }
      Item item = (Item) o;
      return nif.equals(item.nif);
   }

   @Override
   public int hashCode() {
      return Objects.hash(nif);
   }
}
