package biz.podoliako.carwash.dao.impl;


import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.models.entity.Client;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.*;
import java.util.List;

@Repository("ClientDao")
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Transactional
public class ClientDaoImpl implements ClientDao{


    @PersistenceContext
    private EntityManager em;


    public ClientDaoImpl() {

    }

    @Override
    public Client saveClient(Client client) {
        em.persist(client);
        return client;
    }

    @Override
    public Client getClientByName(String name) {
        try {
            Query q =  em.createQuery("SELECT c FROM Client as c WHERE c.name=:name", Client.class);
            q.setParameter("name", name);
            return (Client) q.getSingleResult();
        }catch (NoResultException e){
            return null;
        }catch (NonUniqueResultException e){
            throw new NonUniqueResultException("В таблице Client несколько пользователей с именем " + name);
        }

    }

    @Override
    public List<Client> getAllClients() {
        return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    public List<Client> getAllClientsOrdered(){
        return em.createQuery("SELECT c FROM Client c ORDER BY c.name", Client.class).getResultList();
    }

    @Override
    public void remove(Client client) {
        em.remove(em.contains(client) ? client : em.merge(client));
    }


    @Override
    public Client find(Long id) {
        return em.find(Client.class, id);
    }

    @Override
    public Client update(Client client) {
        Client currentClient = find(client.getId());
        currentClient.setName(client.getName());
        currentClient.setPhoneNumber(client.getPhoneNumber());
        currentClient.setPriceMultiplicator(client.getPriceMultiplicator());

        return currentClient;
    }
}
