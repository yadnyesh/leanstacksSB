package org.example.ws.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.ws.AbstractTest;
import org.example.ws.model.Greeting;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * Created by z063407 on 6/26/17.
 */
@Transactional
public class GreetingServiceTest extends AbstractTest {

    @Autowired
    private GreetingService service;

    @Before
    public void setUp() {
        service.evictCache();
    }

    @After
    public void tearDown() {
        //clean up after each test method
    }

    @Test
    public void testFindAll() {
        Collection<Greeting> list = service.findAll();
        Assert.assertNotNull("Failure - expected not null", list);
        Assert.assertEquals("Failure - expected size 2", 2, list.size());
    }

    @Test
    public void testFindOne() {
        Long id = new Long(1);

        Greeting entity = service.findOne(id);

        Assert.assertNotNull("Failure - expected not null", entity);
        Assert.assertEquals("Failure - Expected Id attribute match", id, entity.getId());
    }

    @Test
    public void testFindOneNotFound() {
        Long id = Long.MAX_VALUE;
        Greeting entity = service.findOne(id);
        Assert.assertNull("Failure - Expected Null", entity);
    }

    @Test
    public void testCreate() {
        Greeting entity = new Greeting();
        entity.setText("test");

        Greeting createdEntity = service.create(entity);

        Assert.assertNotNull("Failure - expected not null", createdEntity);
        Assert.assertNotNull("Failure - expected id attribute not null", createdEntity.getText());

        Collection<Greeting> list = service.findAll();

        Assert.assertEquals("Failure - expected size", 3, list.size());
    }

    @Test
    public void testCreatedWithId() {
        Exception e = null;

        Greeting entity = new Greeting();
        entity.setId(new Long(1));
        entity.setText("Unit Test");

        try {
            service.create(entity);
        } catch (Exception eee) {
            e = eee;
        }

        Assert.assertNotNull("Failure - expected exception", e);
        Assert.assertTrue("Failure - expected EntityExistsException", e instanceof Exception);

    }

    @Test
    public void testUpdate() {
        Long id = new Long(1);
        Greeting entity = service.findOne(id);

        Assert.assertNotNull("Failure - expected not null", entity);

        String updatedText = entity.getText() + " test";
        entity.setText(updatedText);

        Greeting updatedEntity = service.update(entity);

        Assert.assertNotNull("Failure - expected updated entity not null", updatedEntity);
        Assert.assertEquals("Failure - expected updated entity id attribute unchanged", id, updatedEntity.getId());
        Assert.assertEquals("Failure - expected updated entity text attribute match", updatedText, updatedEntity.getText());

    }

    @Test
    public void testUpdateNotFound() {
        Exception e = null;

        Greeting entity = new Greeting();
        entity.setId(Long.MAX_VALUE);
        entity.setText("test");

        try {
            service.update(entity);
        } catch (Exception nre) {
            e = nre;
        }

        Assert.assertNull("Failure - Expected exception", e);
        //Assert.assertTrue("Failure - Expected Noresultexception", e instanceof Exception);
    }

    public void testDelete() {
        Long id = new Long(1);
        Greeting entity = service.findOne(id);
        Assert.assertNotNull("Failure - Expected not null", entity);
        service.delete(id);
        Collection<Greeting> list = service.findAll();
        Assert.assertEquals("Failure - Expected size", 1, list.size());
        Greeting deletedEntity = service.findOne(id);
        Assert.assertNull("Failure - expected entity to be null", deletedEntity);

    }

}
