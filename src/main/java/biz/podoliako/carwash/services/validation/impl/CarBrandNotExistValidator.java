package biz.podoliako.carwash.services.validation.impl;


import biz.podoliako.carwash.services.CarBrandService;
import biz.podoliako.carwash.services.validation.CarBrandNotExist_depricated;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarBrandNotExistValidator implements ConstraintValidator<CarBrandNotExist_depricated, String> {

    @Autowired
    CarBrandService carBrandService;

    @Override
    public void initialize(CarBrandNotExist_depricated carBrandNotExist) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
       /* boolean result = false;
        try {
            result =  carBrandService.isCarBrandExist(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        return true;
    }
}
