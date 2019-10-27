package org.fundaciobit.blueprint.ejb.service;

import org.fundaciobit.blueprint.common.constraint.NIF;
import org.fundaciobit.blueprint.ejb.dao.AbstractJpaDAO;
import org.fundaciobit.blueprint.ejb.jpa.Persona;
import org.fundaciobit.blueprint.ejb.jpa.UniqueConstraintException;

import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.List;

@Stateless
public class PersonaServiceBean extends AbstractJpaDAO<Long, Persona> implements PersonaService {

   @Override
   public Persona create(Persona entity) {
      Persona other = findByNIF(entity.getNif());
      if (other != null) {
         throw new UniqueConstraintException(other.getNif());
      }
      return super.create(entity);
   }

   @Override
   public List<Persona> findByNIFs(List<@NIF String> nifs) {
      return entityManager.createNamedQuery("Persona.findByNIFs", Persona.class)
                      .setParameter("nifs", nifs)
                      .getResultList();
   }

   @Override
   public Persona findByNIF(@NotNull @NIF String nif) {
      final List<Persona> resultList =
              entityManager.createNamedQuery("Persona.findByNIF", Persona.class)
                      .setParameter("nif", nif)
                      .getResultList();
      return resultList.isEmpty() ? null : resultList.get(0);
   }
}
