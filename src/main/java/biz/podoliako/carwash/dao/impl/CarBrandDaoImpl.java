package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.CarBrandDao;
import biz.podoliako.carwash.models.entity.CarBrand;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CarBrandDaoImpl implements CarBrandDao{

    private ConnectionDB connectionDB;

    @PersistenceContext
    private EntityManager em;

    @Override
    public CarBrand findByName(String name) throws SQLException {
        try {
            Query q =  em.createQuery("SELECT c FROM CarBrand as c WHERE c.name=:name", CarBrand.class);
            q.setParameter("name", name);
            return (CarBrand) q.getSingleResult();
        }catch (NonUniqueResultException e){
            throw new NonUniqueResultException("В таблице Categories несколько пользователей с именем " + name);
        }
    }

    @Override
    public List<CarBrand> selectAllCarBrands(Integer ownerId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        List<CarBrand> carBrands = new ArrayList<>();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT * FROM " + CAR_BRAND_TABLE + " WHERE owner_id = ? AND date_of_delete IS NULL ORDER BY name";

            ps = connection.prepareStatement(query);
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CarBrand carBrand = new CarBrand();
                carBrand.setId(rs.getLong("id"));
                carBrand.setName(rs.getString("name"));
                carBrand.setDateOfCreation(rs.getTimestamp("date_of_creation"));
                carBrand.setDateOfDelete(null);

                carBrands.add(carBrand);
            }
            return carBrands;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectAllCarBrands (CarBrandDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectAllCarBrands (CarBrandDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectAllCarBrands (CarBrandDaoImpl) " + e);
            }
        }

    }

    @Override
    public CarBrand selectBrandById(Integer carBrandId) {
        Connection connection = null;
        PreparedStatement ps = null;
        CarBrand carBrand = new CarBrand();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                    " id, name, created_by, date_of_creation, date_of_delete, owner_id" +
                    " FROM " + CAR_BRAND_TABLE  +
                    " WHERE id =?"  ;

            ps = connection.prepareStatement(query);
            ps.setInt(1, carBrandId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                carBrand.setId(rs.getLong("id"));
                carBrand.setName(rs.getString("name"));
                carBrand.setDateOfCreation(rs.getTimestamp("date_of_creation"));
                carBrand.setDateOfDelete(rs.getTimestamp("date_of_delete"));
            }
            return carBrand;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectBrandById (CarBrandDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectBrandById (CarBrandDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectBrandById (CarBrandDaoImpl) " + e);
            }
        }

    }

    @Override
    public CarBrand persist(CarBrand carBrand) {
        em.persist(carBrand);
        return carBrand;
    }

    @Override
    public CarBrand find(Long id) {
        return null;
    }

    @Override
    public List<CarBrand> findAll() {
        return null;
    }

    @Override
    public List<CarBrand> findAllIncludingDeleted() {
        return null;
    }

    @Override
    public CarBrand update(CarBrand entity) {
        return null;
    }

    @Override
    public void remove(CarBrand entity) {

    }
}
