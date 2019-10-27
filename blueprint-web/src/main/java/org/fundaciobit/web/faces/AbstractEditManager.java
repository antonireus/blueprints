package org.fundaciobit.web.faces;

import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.jpa.BaseEntity;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Logger;

/**
 * Classe abstract pels beans que gestionen edicions d'entitats.
 */
public abstract class AbstractEditManager<K, E extends BaseEntity<K>> implements Serializable {

    private static final Logger LOG = Logger.getLogger(AbstractEditManager.class.getName());

    /**
     * Guarda la classe del tipus d'entitat.
     */
    private final Class<E> entityClass;

    // RECURSOS

    @Inject
    private FacesContext context;

    @Inject
    private Flash flash;

    // MODEL

    private E entity;

    public E getEntity() {
        return entity;
    }

    // MÈTODES A IMPLEMENTAR PER LES SUBCLASSES

    protected abstract DAO<K, E> getService();

    // MÈTODES

    /**
     * Empra com a convenció que serà list<i>Entity</i>, és a dir list + el nom de la classe
     * persistent que gestiona.
     */
    protected String getListAction() {
        return "list" + entityClass.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    protected AbstractEditManager() {
        Class<?> clazz = getClass();
        while (!clazz.getSuperclass().equals(AbstractEditManager.class)) {
            clazz = clazz.getSuperclass();
        }
        ParameterizedType genericSuperclass = (ParameterizedType) clazz.getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @PostConstruct
    private void init() {
        LOG.info("init");
        try {
            entity = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isCreate() {
        return entity.getId() == null;
    }

    /**
     * Cridat com acció al principi de la vista per precarregar l'entitat amb el paràmetre rebut com id.
     */
    public void load() {
        if (entity.getId() != null) {
            entity = getService().findById(entity.getId());
        }
    }

    /**
     * Cridat pel botó de crear o actualitzar.
     * @return id de navegació
     */
    public String createOrUpdate() {
        if (isCreate()) {
            getService().create(getEntity());
            context.addMessage(null, new FacesMessage("Creació correcte"));
        } else {
            getService().update(getEntity());
            context.addMessage(null, new FacesMessage("Modificació correcte"));
        }
        // Els missatges no aguanten una redirecció ja que no es la mateixa petició
        // amb l'objecte flash podem assegurar que es guardin fins la visualització
        flash.setKeepMessages(true);
        // Redireccionam cap al llistat d'items
        return getListAction() + "?faces-redirect=true";
    }
}
