package biz.podoliako.carwash.services.impl;


import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.models.entity.Client;
import biz.podoliako.carwash.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("ClientService")
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientDao clientDao;

    public ClientServiceImpl(){

    }

    @Transactional
    public Long saveClient(Client client){
        client.setName(prepareClientName(client.getName()));
        client =  clientDao.saveClient(client);
        return client.getId();
    }

    private String prepareClientName(String name) {
        return name.trim().toLowerCase();
    }

    @Transactional(readOnly = true)
    public Client getClientByName(String name) {
        name = prepareClientName(name);
        return clientDao.getClientByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientDao.getAllClients();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClientsOrdered() {
        return clientDao.getAllClientsOrdered();
    }

    @Override
    @Transactional(readOnly = true)
    public Client validateId(String idStr) {
        Long id = new Long(idStr);
        Client client = clientDao.find(id);
        if (client == null) {
            throw new IllegalArgumentException("Пользователя с id " + id + " не существует");
        }else {
            return client;
        }

    }

    @Override
    @Transactional
    public void remove(Client client) {
        clientDao.remove(client);
    }

    @Override
    public Client find(Long id) {
        return clientDao.find(id);
    }

    @Override
    public Client update(Client client) {
        client.setName(prepareClientName(client.getName()));
        return  clientDao.update(client);
    }
}
