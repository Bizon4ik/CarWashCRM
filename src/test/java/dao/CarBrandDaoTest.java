package dao;

import biz.podoliako.carwash.dao.CarBrandDao;
import biz.podoliako.carwash.models.entity.CarBrand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@TestPropertySource("classpath:test-local.properties")
@ActiveProfiles(profiles = "test")
public class CarBrandDaoTest {

    @Autowired
    CarBrandDao carBrandDao;

    @Test
    @Transactional
    @Rollback(true)
    public void persistCarBrand_Test(){
        CarBrand carBrand = createCarBrand();

        carBrand = carBrandDao.persist(carBrand);

        assertNotNull(carBrand.getId());
    }

    private CarBrand createCarBrand() {
        CarBrand carBrand = new CarBrand();
                carBrand.setName("TestDao");
                carBrand.setDateOfCreation(new Date());
                carBrand.setCreatedBy(null);

        return carBrand;
    }


}
