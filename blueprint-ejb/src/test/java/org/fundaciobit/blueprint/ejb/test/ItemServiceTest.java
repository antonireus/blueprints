package org.fundaciobit.blueprint.ejb.test;

import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.Date;

@RunWith(Arquillian.class)
public class ItemServiceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.fundaciobit.blueprint.ejb")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/container-persistence.xml", "META-INF/persistence.xml");
        System.out.println(jar.toString(true));
        return jar;
    }

    @EJB
    private ItemService itemService;

    @Test
    @InSequence(1)
    public void testItemServiceCreate() {
        Item item = new Item();
        item.setName("Test");
        item.setCreation(new Date());
        item.setNif("00000000T");
        item.getDescription().put("ca", "Test ca");
        item.getDescription().put("es", "Test es");
        itemService.create(item);

        Assert.assertNotNull(item.getId());
    }

}
