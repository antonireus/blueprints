package org.fundaciobit.blueprint.ejb.test;

import org.fundaciobit.blueprint.ejb.jpa.Persona;
import org.fundaciobit.blueprint.ejb.jpa.Persona_;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.Set;
import java.util.stream.Collectors;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPersonaJPA {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("testBlueprintPU");
        em = emf.createEntityManager();
    }

    @Test
    public void testMetamodel() {
        final EntityType<Persona> entity = emf.getMetamodel().entity(Persona.class);

        final Set<String> fields = entity.getSingularAttributes().stream()
                .filter(attr -> attr.getJavaType().equals(String.class))
                .map(Attribute::getName)
                .collect(Collectors.toSet());
        Assert.assertTrue(fields.contains(Persona_.NIF));
        Assert.assertTrue(fields.contains(Persona_.NOM));
        Assert.assertTrue(fields.contains(Persona_.PRIMER_COGNOM));
        Assert.assertTrue(fields.contains(Persona_.SEGON_COGNOM));
        Assert.assertEquals(4, fields.size());
    }

    @AfterClass
    public static void tearDown(){
        em.clear();
        em.close();
        emf.close();
    }
}
