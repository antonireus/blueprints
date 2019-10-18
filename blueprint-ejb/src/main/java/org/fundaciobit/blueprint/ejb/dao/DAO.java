package org.fundaciobit.blueprint.ejb.dao;

import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

/**
 * Defineix les operacions dels Data Access Object per una entitat.
 * @author Antoni
 * @param <K> Tipus de la clau primària de l'entitat.
 * @param <E> Tipus de l'entitat.
 */
public interface DAO<K, E> {

    /**
     * Crea una nova entitat.
     * @param entity Entitat a guardar.
     * @return l'entitat de guardar-la
     */
    E create(E entity);

    /**
     * Actualitza una entitat.
     * @param entity Entitat a actualitzar.
     * @return l'entitat actualitzada.
     */
    E update(E entity);

    /**
     * Esborra una entitat.
     * @param entity l'entitat a esborrar.
     */
    void delete(E entity);

    /**
     * Esborra una entitat pel seu identificador.
     * @param id Identificador de l'entitat a esborrar.
     */
    void deleteById(K id);

    /**
     * Carrega una entitat pel seu identificador.
     * @param id Identificador de l'entitat a carregar.
     * @return l'entitat que es correspon a l'identificador o null si no existeix.
     */
    E findById(K id);

    /**
     * Carrega totes les entitats.
     * @return llista de totes les entitats.
     */
    List<E> findAll();

    /**
     * Carrega un subconjunt de les entitats.
     * @param firstResult índex del primer resultat.
     * @param size nombre màxim de resultats.
     * @return llista de totes les entitats.
     */
    List<E> findAll(@PositiveOrZero int firstResult, @Positive int size);

    /**
     * Retorna el nombre d'entitats.
     * @return Nombre d'entitats.
     */
    long countAll();

    List<E> findFiltered(@PositiveOrZero int firstResult, @Positive int size,
                         Map<SingularAttribute<E, ?>, Object> filter);
}
