package services;

import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.models.entity.Client;
import biz.podoliako.carwash.services.ClientService;
import biz.podoliako.carwash.services.impl.ClientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services/ClientService/ClientServiceTest-config.xml"})
@ActiveProfiles(profiles = "test")
public class ClientServiceTest {

    @Autowired
    ClientService clientService;

    @InjectMocks
    @Autowired
    ClientDao clientDaoMock;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getClientByNameTest(){
        String name = "ivan";

        when(clientDaoMock.getClientByName(name)).thenReturn(null);
        Client client = clientService.getClientByName(name);

        assertNull(client);
    }


}
