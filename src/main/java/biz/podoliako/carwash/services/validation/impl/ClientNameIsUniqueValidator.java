package biz.podoliako.carwash.services.validation.impl;


import biz.podoliako.carwash.dao.ClientDao;
import biz.podoliako.carwash.services.validation.ClientNameUnique;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClientNameIsUniqueValidator implements ConstraintValidator<ClientNameUnique, String> {

    @Autowired
    ClientDao clientDao;

    @Override
    public void initialize(ClientNameUnique clientNameUnique) {

    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (clientDao.getClientByName(name.toLowerCase().trim()) == null) {
            return true;
        } else {
            return false;
        }

    }
}
