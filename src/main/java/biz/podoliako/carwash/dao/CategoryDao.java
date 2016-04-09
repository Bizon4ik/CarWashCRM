package biz.podoliako.carwash.dao;

import biz.podoliako.carwash.models.entity.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao extends DAO<Category> {
    public final static String CATEGORY_TABLE_NAME = "category";

    public Category selectCategory(Long categoryId);

    public List<Category> selectAllCategories(Integer ownerId) throws SQLException;

    Category findByName(String name);
}
