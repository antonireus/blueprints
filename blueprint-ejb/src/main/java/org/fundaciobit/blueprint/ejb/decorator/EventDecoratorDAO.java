package org.fundaciobit.blueprint.ejb.decorator;

import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.event.CreatedEvent;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Logger;

@Decorator
public abstract class EventDecoratorDAO<K, E> implements DAO<K, E> {

    @Inject @Delegate
    private DAO<K, E> delegate;

    @Inject
    private Logger logger;

    @Inject @CreatedEvent
    private Event<Object> createdEvent;

    @Override
    public E create(E entity) {
        logger.info("create");
        final E result = delegate.create(entity);
        logger.info("fire!");
        createdEvent.fire(result);
        return result;
    }
}
