package biz.podoliako.carwash.services.impl;


import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.models.entity.Client;
import biz.podoliako.carwash.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("ClientService")
public class ClientServiceImpl implements ClientService {

    private DaoFactory daoFactory;

    @Autowired
    public ClientServiceImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;

    }

    @Transactional
    public Long saveClient(Client client){
        client.setName(prepareClientName(client.getName()));
        client =  daoFactory.getClientDao().saveClient(client);
        return client.getId();
    }

    private String prepareClientName(String name) {
        return name.trim().toLowerCase();
    }

    @Transactional(readOnly = true)
    public Client getClientByName(String name) {
        name = prepareClientName(name);
        return daoFactory.getClientDao().getClientByName(name);
    }
}
