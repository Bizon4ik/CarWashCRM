package dao;

import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.models.entity.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@TestPropertySource("classpath:test-local.properties")
@ActiveProfiles(profiles = "test")
public class ClientDaoTest {

    @Autowired
    ClientDao clientDao;

    @Test
    @Transactional
    @Rollback(true)
    public void shouldSaveClientInDBTest(){
        Client client = new Client();
        client.setName("ivan");

        client = clientDao.saveClient(client);

        assertNotNull(client.getId());
        assertEquals("ivan", client.getName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void getAllClientsTest(){
        Client client1 = createClient(1);
        Client client2 = createClient(2);
        clientDao.saveClient(client1);
        clientDao.saveClient(client2);


        List<Client> clientList = clientDao.getAllClients();
        assertEquals(2, clientList.size());

    }

    @Test
    @Transactional
    @Rollback(true)
    public void getAllClientsAndOrderThemTest(){
        Client client1 = createClient(2);
        Client client2 = createClient(1);
        clientDao.saveClient(client1);
        clientDao.saveClient(client2);


        List<Client> clientList = clientDao.getAllClientsOrdered();
        assertEquals(2, clientList.size());
        assertEquals(client2, clientList.get(0));
        assertEquals(client1, clientList.get(1));

    }


    @Test
    @Transactional
    @Rollback(true)
    public void getClientByNameTest(){
        Client client = createClient(3);
        clientDao.saveClient(client);

        Client clientResult = clientDao.getClientByName(client.getName());
        Client clientResultNull = clientDao.getClientByName("qwe");

        assertEquals(client, clientResult);
        assertEquals(null, clientResultNull);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void findClient_Test(){
        Client client = createClient(1);
        client = clientDao.saveClient(client);

        Client findClient = clientDao.find(client.getId());
        System.out.println("user = " + findClient.getCreatedBy());
        Client findNotExistClient = clientDao.find(new Long(0));
        assertEquals(client, findClient);
        assertNull(findNotExistClient);

    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteClient_Test(){
        Client client = createClient(1);
        client = clientDao.saveClient(client);

        clientDao.remove(client);

        Client findClient = clientDao.find(client.getId());
        assertNull(findClient);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateClient_Test(){
        Client client = createClient(1);
        client = clientDao.saveClient(client);

        Client clientUpdate = createClient(2);
        clientUpdate.setId(client.getId());

        Client clientUpdated = clientDao.update(clientUpdate);

        assertEquals(clientUpdate, clientUpdated);
    }




    private Client createClient(Integer i){
        Client client = new Client();
        client.setName(getName(i));
        client.setIsPayByCash(true);
        client.setDateOfCreation(new Date());
        client.setPhoneNumber("123");

        return client;

    }

    private String getName(Integer i){
        List<String> nameList = new ArrayList<>();
        nameList.add("ivan");
        nameList.add("petr");
        nameList.add("vova");
        nameList.add("goga");

        return  nameList.get(i-1);
    }


}
