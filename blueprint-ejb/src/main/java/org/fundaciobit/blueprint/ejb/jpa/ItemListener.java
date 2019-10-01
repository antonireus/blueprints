package org.fundaciobit.blueprint.ejb.jpa;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Reb els esdeveniments de JPA per l'entitat {@link Item}. Es pot emprar per fixar camps automàtics,
 * o certes validacions. Permet també cridar a altres recursos, com enviar un missatge JMS o cridar EJBs.
 * En general però no està recomant fer querys o accedir al mateix persistence context de l'entitat (veure
 * darrer apartat del punt 3.5.2 de l'especificació JPA 2.2).
 *
 * @author areus
 */
public class ItemListener {

    private static final Logger LOG = Logger.getLogger(ItemListener.class.getName());

    /**
     * Es crida abans de fer la creació de Item.
     * Assegura que la lletra del NIF està en majúscula i fixa la data de creació.
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
    }

    /**
     * Es crida abans de l'actualització d'un Item.
     * Assegura que la lletra del NIF està en majúscula.
     *
     * @param item Item que és apunt de ser creat.
     */
    @PreUpdate
    public void preUpdate(Item item) {
        LOG.info("preUpdate");
        // Sempre guardam el NIF amb la lletra majuscula
        item.setNif(item.getNif().toUpperCase());
    }

}
