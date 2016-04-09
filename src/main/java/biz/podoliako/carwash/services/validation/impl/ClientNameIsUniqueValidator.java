package biz.podoliako.carwash.services.validation.impl;


import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.models.entity.Client;
import biz.podoliako.carwash.services.ClientService;
import biz.podoliako.carwash.services.validation.ClientNameUnique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ClientNameIsUniqueValidator implements ConstraintValidator<ClientNameUnique, String> {

    @Autowired
    ClientService clientService;

    @Override
    public void initialize(ClientNameUnique clientNameUnique) {

    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        Client result = clientService.getClientByName(name);

        if ( result == null) {
            return true;
        } else {
            return false;
        }

    }
}
