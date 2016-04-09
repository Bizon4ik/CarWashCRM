package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.CategoryDao;
import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component("CategoryDao")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager em;

    private Connection conn = null;
    private ConnectionDB connectionDB;

    @Autowired
    public CategoryDaoImpl(ConnectionDB connectDB) throws SQLException, NamingException {
       this.conn = connectDB.getConnection();
        this.connectionDB = connectDB;
    }

    @Override
    public Category selectCategory(Long categoryId) {
        Connection connection = null;
        PreparedStatement ps = null;
        Category category = new Category();
        try {
            connection = connectionDB.getConnection();

            String query = "SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE id = ?";

            ps = connection.prepareStatement(query);
            ps.setLong(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setDateOfCreation(rs.getTimestamp("date_of_creation"));

            }

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectCategory (CategoryDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectCategory (CategoryDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectCategory (CategoryDaoImpl) " + e);
            }
        }

        return category;
    }

    @Override
    public List<Category> selectAllCategories(Integer ownerId) throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        String query = "SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE owner_id = " + ownerId + " ORDER BY name";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            Category category = new Category();

            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
            category.setDescription(rs.getString("description"));
            category.setDateOfCreation(rs.getTimestamp("date_of_creation"));

            categoryList.add(category);
        }
        return categoryList;
    }

    @Override
    public Category findByName(String name) {
        try {
            Query q =  em.createQuery("SELECT c FROM Category as c WHERE c.name=:name", Category.class);
            q.setParameter("name", name);
            return (Category) q.getSingleResult();
        }catch (NonUniqueResultException e){
            throw new NonUniqueResultException("В таблице Categories несколько пользователей с именем " + name);
        }
    }

    @Override
    public Category persist(Category category) {
        em.persist(category);
        return category;
    }

    @Override
    public Category find(Long id) {
        return em.find(Category.class, id);
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("SELECT c FROM  Category c WHERE c.dateOfDelete=NULL", Category.class)
                                                                                                    .getResultList();
    }

    @Override
    public List<Category> findAllIncludingDeleted() {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    @Override
    public Category update(Category category) {
        Category currentCategory = find(category.getId());
                 currentCategory.setName(category.getName());
                 currentCategory.setDescription(category.getDescription());
                 currentCategory.setCreatedBy(category.getCreatedBy());
                 currentCategory.setDateOfCreation(category.getDateOfCreation());
                 currentCategory.setDateOfDelete(category.getDateOfDelete());

        return  currentCategory;
    }

    @Override
    public void remove(Category category) {
        em.remove(em.contains(category) ? category : em.merge(category));

    }


}
