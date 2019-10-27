package org.fundaciobit.blueprint.ejb.jpa;

import org.fundaciobit.blueprint.common.constraint.NIF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
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
@SequenceGenerator(name="persona-sequence", sequenceName = "BLP_PERSONA_SEQ", allocationSize = 1)
@Table(name = "BLP_PERSONA",
        indexes = {@Index(name = "BLP_PERSONA_PK_I", columnList = "PERSONAID", unique = true)},
        uniqueConstraints = {@UniqueConstraint(name= "BLP_PERSONA_NIF_UK", columnNames = "NIF")}
)
@NamedQueries({
        @NamedQuery(name = "Persona.findByNIFs",
                query = "SELECT p FROM Persona p WHERE p.nif IN (:nifs)"),
        @NamedQuery(name = "Persona.findByNIF",
                query = "SELECT p FROM Persona p WHERE p.nif LIKE :nif")
})
@XmlRootElement(name = "persona")
@XmlAccessorType(XmlAccessType.FIELD)
public class Persona extends BaseEntity<Long> {

   private static final long serialVersionUID = 1L;

   /**
    * Constructor buid.
    */
   public Persona() {
   }

   // FIELDS

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona-sequence")
   @Column(name="PERSONAID", nullable = false, length = 19)
   @XmlElement(required = true, nillable = true)
   private Long id;

   @NotNull @NIF @Column(name = "NIF", nullable = false, length = 9)
   @XmlElement(required = true)
   private String nif;

   @NotEmpty @Size(max = 50)
   @Column(name = "NOM", nullable = false, length = 50)
   @XmlElement(required = true)
   private String nom;

   @NotEmpty @Size(max = 50)
   @Column(name = "PRIMERCOGNOM", nullable = false, length = 50)
   @XmlElement(required = true)
   private String primerCognom;

   @NotEmpty @Size(max = 50)
   @Column(name = "SEGONCOGNOM", nullable = false, length = 50)
   @XmlElement(required = true)
   private String segonCognom;

   @NotNull @Past
   @Temporal(TemporalType.DATE)
   @Column(name = "DATANAIXEMENT", nullable = false)
   @XmlElement(required = true)
   private Date dataNaixement;

   // GETTERS & SETTERS

   @Override
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getNif() {
      return nif;
   }

   public void setNif(String nif) {
      this.nif = nif;
   }

   public String getNom() {
      return nom;
   }

   public void setNom(String nom) {
      this.nom = nom;
   }

   public String getPrimerCognom() {
      return primerCognom;
   }

   public void setPrimerCognom(String primerCognom) {
      this.primerCognom = primerCognom;
   }

   public String getSegonCognom() {
      return segonCognom;
   }

   public void setSegonCognom(String segonCognom) {
      this.segonCognom = segonCognom;
   }

   public Date getDataNaixement() {
      return dataNaixement;
   }

   public void setDataNaixement(Date dataNaixement) {
      this.dataNaixement = dataNaixement;
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
      Persona persona = (Persona) o;
      return nif.equals(persona.nif);
   }

   @Override
   public int hashCode() {
      return Objects.hash(nif);
   }
}
