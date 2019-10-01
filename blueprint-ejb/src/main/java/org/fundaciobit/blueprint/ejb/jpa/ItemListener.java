package org.fundaciobit.blueprint.ejb.jpa;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;

/**
 * Reb els esdeveniments de JPA per l'entitat {@link Item}
 *
 * @author Antoni
 */
public class ItemListener {

    private static final Logger LOG = Logger.getLogger(ItemListener.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Es crida abans de fer la creació de Item.
     *
     * @param item Item que és apunt de ser creat.
     */
    @PrePersist
    public void prePersist(Item item) {
        LOG.info("prePersist");
        // Fixam automàticament la data de creació
        item.setCreation(new Date());
        // Sempre guardam el NIF amb la lletra majuscula
        item.setNif(item.getNif().toUpperCase());

        /*
        Comprovam que el NIF no existeix ja.
        Amb Hibernate, l'item encara no té ID i no està associat al context, no hi ha problema.
        Amb EclipseLink, ja s'ha assignat un ID (cridant a la sequència), i en el moment que 
        executam una consulta farà un flush que persistirà l'entitat, provocant la violació de la 
        clau única que és el que voldríem evitar.
        Cal mirar com desactivar aquest flush automàtic.
        
        També cal tenir en compte, fer una query al mateix persistence context dins els mètodes
        d'un listener està, en general, desaconsellat per l'especificació (darrer incís punt 3.5.2)
        */
        
        /*
        LOG.info("Comprovar clau única NIF...");

        List<Item> result = entityManager.createNamedQuery("findByNIF", Item.class)
                .setParameter("nif", item.getNif())
                .getResultList();
                
        if (!result.isEmpty()) {
            String msg = "prePersist: " + item.getNif() + " ja existeix, ID=" + result.get(0).getId();
            LOG.warning(msg);
            throw new UniqueConstraintException(msg);
        }
        */
    }

    /**
     * Es crida abans de l'actualització d'un Item.
     *
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
