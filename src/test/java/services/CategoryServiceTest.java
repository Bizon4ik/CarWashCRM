package services;

import biz.podoliako.carwash.dao.CategoryDao;
import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.services.CategoryService;
import biz.podoliako.carwash.services.impl.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CategoryServiceTest {

    @Configuration
    static class CategoryServiceTestContextConfiguration {
        @Bean
        public CategoryService clientService() {
            return new CategoryServiceImpl();
        }
        @Bean
        public CategoryDao clientDao() {
            return Mockito.mock(CategoryDao.class);
        }
    }

    @Autowired
    CategoryDao categoryDaoMock;

    @Autowired
    CategoryService categoryService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void CategoryHastoBeMarkedAsDeleted_Test(){
        Category category = new Category();
        category.setName("test");
        category.setDescription("description");
        category.setDateOfCreation(new Date());

        ArgumentCaptor<Category> argument = ArgumentCaptor.forClass(Category.class);

        categoryService.markDeleted(category);

        verify(categoryDaoMock).update(argument.capture());
        assertNotNull(argument.getValue().getDateOfDelete());
    }

    @Test(expected = NumberFormatException.class)
    public void validateId_shouldThrowException_if_id_not_Long_Test(){
        String id = "abc";
        categoryService.validateId(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateId_shouldThrowException_if_not_Category_with_Id(){
        Long id = new Long(100);

        Category category = new Category();
        category.setId(id);
        category.setName("test");
        when(categoryDaoMock.find(id)).thenReturn(null);

        categoryService.validateId(id.toString());
    }

    @Test
    public void validateId_shouldReturnCategory_when_id_valid_test(){
        Long id = new Long(100);

        Category category = new Category();
        category.setId(id);
        category.setName("Test");
        when(categoryDaoMock.find(id)).thenReturn(category);
        Category result = categoryService.validateId(id.toString());

        assertEquals(category, result);
    }

}
