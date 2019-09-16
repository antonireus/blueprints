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

    /**
     * Valida que el camp és un NIF correcte. Valida que sigui un string amb 8
     * nombres i una lletra, i que la lletra es correspongui amb la definició
     * de NIF. Si el valor és null retornarà true (De la validació
     * de null ja s'encarrega la validació @NotNull).
     * @param value Valor a validar.
     * @param context context de validació
     * @return true si és null o és vàlid, false en cas contrari.
     */
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

    /**
     * Inicialitza el validador.
     * @param constraintAnnotation l'anotació. 
     */
    @Override
    public void initialize(NIF constraintAnnotation) {

    }
}
