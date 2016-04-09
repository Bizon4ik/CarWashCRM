package biz.podoliako.carwash.services;

import biz.podoliako.carwash.models.entity.Category;



import java.sql.SQLException;
import java.util.List;


public interface CategoryService {

    public Category persist(Category category);

    public List<Category> selectAllCategory(Integer ownerId) throws SQLException;

    public void deleteCategory(String id) throws SQLException;


    Category findByName(String name);

    List<Category> findAll();

    Category validateId(String id);

    Category markDeleted(Category category);

    Category update(Category category);
}
