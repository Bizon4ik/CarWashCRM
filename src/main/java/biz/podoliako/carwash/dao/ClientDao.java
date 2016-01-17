package biz.podoliako.carwash.dao;


import biz.podoliako.carwash.models.entity.Client;

public interface ClientDao {

    public Client saveClient(Client client);

    Client getClientByName(String s);
}
