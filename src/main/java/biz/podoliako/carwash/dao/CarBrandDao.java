package biz.podoliako.carwash.dao;

import biz.podoliako.carwash.models.entity.CarBrand;

import java.sql.SQLException;
import java.util.List;

public interface CarBrandDao extends DAO<CarBrand> {
    public static String CAR_BRAND_TABLE = "car_brand";

    CarBrand findByName(String name) throws SQLException;

    List<CarBrand> selectAllCarBrands(Integer ownerId) throws SQLException;

    CarBrand selectBrandById(Integer carBrandId);
}
