package biz.podoliako.carwash.services;


import biz.podoliako.carwash.models.entity.Client;

public interface ClientService {
    public Long saveClient(Client client);


    public Client getClientByName(String name);
}
