package services;

import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.models.entity.Client;
import biz.podoliako.carwash.services.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:services/ClientService/ClientServiceTest-config.xml"})
public class ClientServiceTest {

    @Autowired
    ClientService clientService;

    @Autowired
    ClientDao clientDaoMock;

    @Test
    public void getClientByNameTest(){
        String name = "ivan";

        when(clientDaoMock.getClientByName(anyString())).thenReturn(null);
        Client client = clientService.getClientByName(name);

        assertNull(client);
    }


}
