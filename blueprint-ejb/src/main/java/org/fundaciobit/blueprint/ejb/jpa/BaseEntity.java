package org.fundaciobit.blueprint.ejb.jpa;

import java.io.Serializable;

public abstract class BaseEntity<K> implements Serializable {

    public abstract K getId();
}
