package biz.podoliako.carwash.services;


import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.models.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("ClientService")
public class ClientService {

    private DaoFactory daoFactory;

    @Autowired
    public ClientService (DaoFactory daoFactory){
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
