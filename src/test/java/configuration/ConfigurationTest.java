package configuration;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@WebAppConfiguration
public class ConfigurationTest {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void IsSessionFactoryExist(){
        assertNotEquals(null, sessionFactory);
    }

}
