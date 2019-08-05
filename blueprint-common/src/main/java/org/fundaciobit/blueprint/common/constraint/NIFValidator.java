package org.fundaciobit.blueprint.common.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validador de NIF
 */
public class NIFValidator implements ConstraintValidator<NIF, String> {

    /** Lletres del NIF */
    static final char[] LLETRES = {
            'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D',
            'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L',
            'C', 'K', 'E'};

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        /* Sempre cal retornar true. D'això s'encarrega el validaor de NotNull */
        if (value == null) {
            return true;
        }
        /* Els NIFs són 8 números i una lletra */
        if (value.length() != 9) {
            return false;
        }
        /* Validam que els 8 primers són 8 números */
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        int numero = Integer.valueOf(value.substring(0, 8));
        return Character.toUpperCase(value.charAt(8)) == LLETRES[ numero % 23];
    }

    @Override
    public void initialize(NIF constraintAnnotation) {

    }
}
