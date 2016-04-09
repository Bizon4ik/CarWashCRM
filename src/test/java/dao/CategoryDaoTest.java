package dao;

import biz.podoliako.carwash.dao.CategoryDao;
import biz.podoliako.carwash.models.entity.Category;
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
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@TestPropertySource("classpath:test-local.properties")
@ActiveProfiles(profiles = "test")
public class CategoryDaoTest {

    @Autowired
    CategoryDao categoryDao;

    @Test
    @Transactional
    @Rollback(true)
    public void shouldPersistCategory_Test(){
        Category category = createCategory();
        category = categoryDao.persist(category);
        assertNotNull(category.getId());
        assertThat(category.getId(), greaterThan(new Long(0)));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void shouldGetAllPersistedCategories_Test(){
        Category category1 = createCategory("Test1");
            categoryDao.persist(category1);
        Category category2 = createCategory("Test2");
            categoryDao.persist(category2);
        Category category3 = createCategory("Test3");
            categoryDao.persist(category3);

        List<Category> categoryList = categoryDao.findAll();

        assertEquals(3, categoryList.size());
        assertEquals(category1, categoryList.get(0));
        assertEquals(category2, categoryList.get(1));
        assertEquals(category3, categoryList.get(2));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void shouldFindCategoryById_Test(){
        Category category = createCategory();
        category = categoryDao.persist(category);

        Category result = categoryDao.find(category.getId());

        assertEquals(category, result);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void shouldRemoveCategory_Test(){
        Category category = createCategory();
        category = categoryDao.persist(category);

        categoryDao.remove(category);

        assertNull(categoryDao.find(category.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldUpdateCategory_Test(){
        Category category = createCategory();
            category = categoryDao.persist(category);
        category.setDateOfDelete(new Date());
        category.setName("test");
        category.setDescription("des");

        categoryDao.update(category);

        Category result = categoryDao.find(category.getId());
        assertNotNull(result.getDateOfDelete());
        assertEquals(category, result);
    }

    private Category createCategory(){
        Category category = new Category();
        category.setName("TestCategory");
        category.setDateOfCreation(new Date());
        category.setCreatedBy(null);
        return category;
    }

    private Category createCategory(String name){
        Category category = new Category();
        category.setName(name);
        category.setDateOfCreation(new Date());
        category.setCreatedBy(null);
        return category;
    }
}
