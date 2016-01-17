package dao;

import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.models.entity.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:dao/daoTest-context.xml"})
public class ClientDaoTest {

    @Autowired
    ClientDao clientDao;

    @Test
    public void shouldSaveClientInDBTest(){
        Client client = new Client();
        client.setName("ivan");

        client = clientDao.saveClient(client);

        assertNotNull(client.getId());
        assertEquals("ivan", client.getName());
    }


}
