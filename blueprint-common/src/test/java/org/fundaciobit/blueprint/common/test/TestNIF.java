package org.fundaciobit.blueprint.common.test;

import org.fundaciobit.blueprint.common.constraint.NIF;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class TestNIF {

    @NIF
    private String nif;

    @Test
    public void testValidNIF() {
        this.nif = "00000000T";
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Set<ConstraintViolation<TestNIF>> validate = validatorFactory.getValidator().validate(this);
        Assert.assertTrue(validate.isEmpty());
    }

    @Test
    public void testInvalidNIF() {
        this.nif = "00000000R";
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Set<ConstraintViolation<TestNIF>> validate = validatorFactory.getValidator().validate(this);
        Assert.assertFalse(validate.isEmpty());
    }


}
