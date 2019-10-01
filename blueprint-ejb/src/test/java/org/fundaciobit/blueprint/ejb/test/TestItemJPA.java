package org.fundaciobit.blueprint.ejb.test;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.junit.*;
import org.junit.runners.MethodSorters;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestItemJPA {

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

    @Test(expected = PersistenceException.class)
    public void testUniqueConstraintViolation() {
        Item item = new Item();
        item.setName("New Test");
        item.setCreation(new Date());
        item.setNif("99999999R");
        item.getDescription().put("ca", "New Test ca");
        item.getDescription().put("es", "New Test es");
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();

        Assert.fail("No hauria de poder gravar perquè el NIF està repetit");
    }

    @AfterClass
    public static void tearDown(){
        em.clear();
        em.close();
        emf.close();
    }
}
