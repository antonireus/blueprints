package org.fundaciobit.blueprint.common;

import javax.annotation.PostConstruct;
import javax.inject.Named;

/**
 * Bean amb el nom de versió
 * @author areus
 */
@Named
public class Version {

    private String number;

    /**
     * Inicialitza el bean de versió
     */
    @PostConstruct
    private void init() {
        number = "0.9";
    }

    public String getNumber() {
        return number;
    }
}
