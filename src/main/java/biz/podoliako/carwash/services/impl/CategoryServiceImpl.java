package biz.podoliako.carwash.services.impl;

import biz.podoliako.carwash.dao.CategoryDao;
import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Component("CategoryModel")
public class CategoryServiceImpl implements CategoryService {

   @Autowired
    CategoryDao categoryDao;


    @Override
    @Transactional
    public Category persist(Category category) {
        category = prepareCategoryForPersist(category);
        return categoryDao.persist(category);
    }

    private Category prepareCategoryForPersist(Category category) {
        category.setName(category.getName().trim().toLowerCase());
        category.setDescription(category.getDescription().trim().toLowerCase());
        category.setDateOfCreation(new Date());

        return category;
    }

    @Override
    public List<Category> selectAllCategory(Integer ownerId) throws SQLException {
        return null;
    }

    @Override
    public void deleteCategory(String id) throws SQLException {
        /* doNothing */

    }

    @Override
    @Transactional(readOnly = true)
    public Category findByName(String name) {
        name = name.trim().toLowerCase();
        return categoryDao.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category validateId(String idStr) {
        Long id = new Long(idStr);
        Category category = categoryDao.find(id);
        if (category == null) {
            throw new IllegalArgumentException("Категория с id " + id + " не существует");
        }else {
            return category;
        }
    }

    @Override
    @Transactional
    public Category markDeleted(Category category) {
        category.setDateOfDelete(new Date());
        return categoryDao.update(category);
    }

    @Override
    @Transactional
    public Category update(Category category) {
        category = prepareCategoryForPersist(category);
        return  categoryDao.update(category);
    }

}
