package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.common.constraint.NIF;
import org.fundaciobit.blueprint.ejb.dao.DAO;
import org.fundaciobit.blueprint.ejb.jpa.Persona;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import java.util.List;

@Local
public interface PersonaService extends DAO<Long, Persona> {

    List<Persona> findByNIFs(List<@NIF String> nifs);

    Persona findByNIF(@NotNull @NIF String nif);
}
