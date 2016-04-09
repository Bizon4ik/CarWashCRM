package biz.podoliako.carwash.services.validation.impl;

import biz.podoliako.carwash.dao.CategoryDao;
import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.services.CategoryService;
import biz.podoliako.carwash.services.validation.CategoryNameUnique;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryNameIsUniqueValidator implements ConstraintValidator<CategoryNameUnique, String> {
    @Autowired
    CategoryService categoryService;

    @Override
    public void initialize(CategoryNameUnique categoryNameUnique) {

    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        try {
            categoryService.findByName(name);
            return false;
        }catch (NoResultException e){
            return true;
        }

    }
}
