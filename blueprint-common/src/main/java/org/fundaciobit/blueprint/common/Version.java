package org.fundaciobit.blueprint.common;

import javax.annotation.PostConstruct;
import javax.inject.Named;

@Named
public class Version {

    private String number;

    @PostConstruct
    private void init() {
        number = "0.9";
    }

    public String getNumber() {
        return number;
    }
}
