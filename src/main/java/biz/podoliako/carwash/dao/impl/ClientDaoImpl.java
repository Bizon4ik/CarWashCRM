package biz.podoliako.carwash.dao.impl;


import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.models.entity.Client;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        }

    }

    @Override
    public List<Client> getAllClients() {
        return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }
}
