package org.fundaciobit.blueprint.common.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Anotació per definir que un camp s'ha de validar com un NIF.
 * @author areus
 */
@Documented
@Constraint(validatedBy = NIFValidator.class)
@Repeatable(NIF.List.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface NIF {

    /**
     * Permet definir l'etiqueta del missatge d'error.
     * @return Etiqueta del missatge d'error
     */
    String message() default "{org.fundaciobit.blueprint.common.constraint.NIF.message}";

    /**
     * Permet definir els grups on s'empra la validació.
     * @return Grups on s'empra la validació
     */
    Class<?>[] groups() default {};

    /**
     * Permet configurar paràmetres addicionals.
     * @return paràmetres addicionals
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Permetre emprar {@link NIF} varies vegades.
     * @see NIF
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        /**
         * Implementació de la llista de constraints de tipus NIF.
         * @return llista de constraints del tipus NIF.
         */
        NIF[] value();
    }
}
