package org.fundaciobit.blueprint.ejb.jpa;

import org.fundaciobit.blueprint.common.constraint.NIF;

import javax.persistence.*;
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

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getNif() {
      return nif;
   }

   public void setNif(String nif) {
      this.nif = nif;
   }

   public Date getCreation() {
      return creation;
   }

   public void setCreation(Date creation) {
      this.creation = creation;
   }

   public Map<String, String> getDescription() {
      return description;
   }

   public void setDescription(Map<String, String> description) {
      this.description = description;
   }
}
