package biz.podoliako.carwash.dao.impl;


import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.models.entity.Client;
import biz.podoliako.carwash.services.ConnectionDB;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public Client getClientByName(String s) {
        return null;
    }
}
