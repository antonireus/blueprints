package org.fundaciobit.blueprint.common.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validador de NIF. Valida que siguin 8 nombres seguits de la lletra de control correcte.
 */
public class NIFValidator implements ConstraintValidator<NIF, String> {

    /** Lletres del NIF */
    private static final char[] LLETRES = {
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
        /* la posició de la lletra és mòdul 23 de la representció del número */
        int indexLletra = Integer.valueOf(value.substring(0, 8)) % LLETRES.length;
        return Character.toUpperCase(value.charAt(8)) == LLETRES[indexLletra];
    }

    @Override
    public void initialize(NIF constraintAnnotation) {

    }
}
