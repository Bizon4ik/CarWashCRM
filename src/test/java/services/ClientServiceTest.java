package services;

import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.dao.impl.ClientDaoImpl;
import biz.podoliako.carwash.models.entity.Client;
import biz.podoliako.carwash.services.ClientService;
import biz.podoliako.carwash.services.impl.ClientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ClientServiceTest {

    @Configuration
    static class ClientServiceTestContextConfiguration {
        @Bean
        public ClientService clientService() {
            return new ClientServiceImpl();
        }
        @Bean
        public ClientDao clientDao() {
            return Mockito.mock(ClientDao.class);
        }
    }

    @Autowired
    ClientService clientService;

    @Autowired
    ClientDao clientDaoMock;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NumberFormatException.class)
    public void validateId_shouldThrowException_if_id_not_Long_Test(){
        String id = "abc";
        clientService.validateId(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateId_shouldThrowException_if_not_Client_with_Id(){
        Long id = new Long(100);

        Client client = new Client();
        client.setId(id);
        client.setName("Ivan");
        when(clientDaoMock.find(id)).thenReturn(null);

        clientService.validateId(id.toString());
    }

    @Test
    public void validateId_shouldReturnClient_when_id_valid_test(){
        Long id = new Long(100);

        Client client = new Client();
        client.setId(id);
        client.setName("Ivan");
        when(clientDaoMock.find(id)).thenReturn(client);
        Client result = clientService.validateId(id.toString());

        assertEquals(client, result);
    }


}
