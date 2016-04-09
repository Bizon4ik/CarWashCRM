package biz.podoliako.carwash.services;


import biz.podoliako.carwash.models.entity.Client;

import java.util.List;

public interface ClientService {
    public Long saveClient(Client client);

    public Client getClientByName(String name);

    public List<Client> getAllClients();

    public List<Client> getAllClientsOrdered();

    Client validateId(String id);

    void remove(Client client);

    Client find(Long id);

    Client update(Client client);
}
