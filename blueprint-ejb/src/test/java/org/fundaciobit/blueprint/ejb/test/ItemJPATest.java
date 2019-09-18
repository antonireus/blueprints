package org.fundaciobit.blueprint.ejb.test;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class ItemJPATest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("testBlueprintPU");
        em = emf.createEntityManager();
    }

    @Test
    public void testCreateUpdateItem() {
        Item item = new Item();
        item.setName("Test");
        item.setCreation(new Date());
        item.setNif("00000000T");
        item.getDescription().put("ca", "Test ca");
        item.getDescription().put("es", "Test es");
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();

        List<Item> list = em.createQuery("select i from Item i", Item.class).getResultList();
        Assert.assertEquals(1, list.size());

        item.setNif("99999999r");
        em.getTransaction().begin();
        em.merge(item);
        em.getTransaction().commit();

        Assert.assertEquals("99999999R", em.find(Item.class, item.getId()).getNif());
    }

    @AfterClass
    public static void tearDown(){
        em.clear();
        em.close();
        emf.close();
    }
}
