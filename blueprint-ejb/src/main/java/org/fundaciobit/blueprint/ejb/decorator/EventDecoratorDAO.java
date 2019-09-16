package org.fundaciobit.blueprint.ejb.decorator;

import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.event.CreatedEvent;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Decorator que genera un esdeveniment després de crear una entitat.
 * @author Antoni
 * @param <K> Tipus de la clau primària de l'entitat.
 * @param <E> Tipus de l'entitat.
 */
@Decorator
public abstract class EventDecoratorDAO<K, E> implements DAO<K, E> {

    private static final Logger LOG = Logger.getLogger(EventDecoratorDAO.class.getName());

    /**
     * Interfícice sobre la que s'aplicarà el decorator.
     */
    @Inject @Delegate
    private DAO<K, E> delegate;

    /**
     * Disparador d'esdeveniment.
     */
    @Inject @CreatedEvent
    private Event<Object> createdEvent;

    /**
     * Crea una entitat i dispara un esdeveniment.
     * @param entity Entitat que es crea.
     * @return L'entitat que s'ha creat.
     */
    @Override
    public E create(E entity) {
        LOG.info("create");
        final E result = delegate.create(entity);
        LOG.info("fire!");
        createdEvent.fire(result);
        return result;
    }
}
