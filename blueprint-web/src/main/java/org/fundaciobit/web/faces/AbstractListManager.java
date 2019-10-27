package org.fundaciobit.web.faces;

import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.jpa.BaseEntity;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Classe abstract pels beans que gestionen llistats d'entitats.
 */
public abstract class AbstractListManager<K, E extends BaseEntity<K>> implements Serializable {

    private static final Logger LOG = Logger.getLogger(AbstractListManager.class.getName());

    /*
     * Guarda la classe del tipus d'entitat.
     */
    //private final Class<E> entityClass;

    // RECURSOS

    // MODEL

    private List<E> entities;

    private final Map<String, String> filter = new HashMap<>();

    private final PaginationHelper pagination = new PaginationHelper(5);

    public List<E> getEntities() {
        return entities;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public PaginationHelper getPagination() {
        return pagination;
    }

    // MÈTODES A IMPLEMENTAR PER LES SUBCLASSES

    protected abstract DAO<K, E> getService();

    // MÈTODES

    @SuppressWarnings("unchecked")
    protected AbstractListManager() {
        /*
        Class<?> clazz = getClass();
        while (!clazz.getSuperclass().equals(AbstractListManager.class)) {
            clazz = clazz.getSuperclass();
        }
        ParameterizedType genericSuperclass = (ParameterizedType) clazz.getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
         */
    }

    @PostConstruct
    private void init() {
        LOG.info("init");
        loadList();
    }

    public void loadList() {
        LOG.info("loadList. filter=" + filter);
        pagination.setCount((int) getService().countFiltered(filter));
        entities = getService().findFiltered(pagination.getPageFirstItem(), pagination.getPageSize(), filter);
    }

    public void next() {
        if (pagination.isHasNextPage()) {
            pagination.nextPage();
            loadList();
        }
    }

    public void previous() {
        if (pagination.isHasPreviousPage()) {
            pagination.previousPage();
            loadList();
        }
    }
}
