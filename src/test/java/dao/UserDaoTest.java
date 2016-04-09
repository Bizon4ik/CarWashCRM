package dao;


import biz.podoliako.carwash.dao.UserDao;
import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.models.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@TestPropertySource("classpath:test-local.properties")
@ActiveProfiles(profiles = "test")
public class UserDaoTest {

    @Autowired
    private  UserDao userDao;


    @Test
    @Transactional
    @Rollback(true)
    public void find_User_test()  {
        User user = getUser();
        user = userDao.persist(user);

        User userTest = userDao.find(user.getId());
        System.out.println(user);

        assertEquals(user, userTest);
    }



    @Test
    @Transactional
    @Rollback(true)
    public void persist_User_test(){
        User user = getUser();
        User userTest = userDao.persist(user);

        assertEquals(user, userTest);
    }

    private User getUser(){
        User user = new User();
        user.setName("test");
        user.setRole(Role.owner);
        user.setCreatedBy(-1000);

        return user;
    }


}
