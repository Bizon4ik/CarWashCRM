package Controllers.owner;

import biz.podoliako.carwash.controllers.owner.CategoryController;
import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.services.CategoryService;
import biz.podoliako.carwash.services.utils.GeneralUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import utils.TestUtils;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@TestPropertySource("classpath:test-local.properties")
@ActiveProfiles(profiles = "test")
@WebAppConfiguration
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void  addCategory_GET_Test() throws Exception {
        mockMvc.perform(get("/owner/category/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/category/add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/category/add.jsp"))
                .andExpect(model().attribute("category", is(new Category())));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addCategory_Post_shouldSaveCategory_no_errors_Test() throws Exception {
        Category category = createCategory();

        mockMvc.perform(post("/owner/category/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", category.getName())
                        .param("description", category.getDescription())
                       )
                        .andExpect(status().isFound())
                        .andExpect(view().name("redirect:/owner/category/all"))
                        .andExpect(redirectedUrl("/owner/category/all"))
                        .andExpect(model().attributeHasNoErrors())
                        .andExpect(flash().attributeExists("globalMsg"));
    }

    @Test
    public void addCategory_Post_shouldReturnToFormBecauseNameAndDescriptionToLong_Test() throws Exception {
        Category category = createCategory();
        category.setName(TestUtils.createStringWithLength(150));
        category.setDescription(TestUtils.createStringWithLength(250));

        mockMvc.perform(post("/owner/category/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", category.getName())
                        .param("description", category.getDescription())
        )
                        .andExpect(status().isOk())
                        .andExpect(view().name("owner/category/add"))
                        .andExpect(forwardedUrl("/WEB-INF/pages/owner/category/add.jsp"))
                        .andExpect(model().attributeHasFieldErrors("category", "name", "description"))
                        .andExpect(model().attributeExists("category"));
    }

    @Test
    public void addCategory_Post_shouldReturnToFormBecauseNameIsEmpty_Test() throws Exception {
        Category category = createCategory();
        category.setName("  ");

        mockMvc.perform(post("/owner/category/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", category.getName())
                        .param("description", category.getDescription())
                       )
                        .andExpect(status().isOk())
                        .andExpect(view().name("owner/category/add"))
                        .andExpect(forwardedUrl("/WEB-INF/pages/owner/category/add.jsp"))
                        .andExpect(model().attributeHasFieldErrors("category", "name"))
                        .andExpect(model().attributeExists("category"));
    }


    @Test
    @Transactional
    @Rollback(true)
    public void showAllCategory_Test() throws Exception {
        mockMvc.perform(get("/owner/category/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/category/all"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/category/all.jsp"))
                .andExpect(model().attributeExists("categoryList"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteCategory_CategoryHaveToBeMarkedAsDeleted_Test() throws Exception {
        Category category = createCategory();
        category = categoryService.persist(category);

        mockMvc.perform(get("/owner/category/delete/"+category.getId()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/owner/category/all"))
                .andExpect(redirectedUrl("/owner/category/all"))
                .andExpect(flash().attributeExists("globalMsg"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteCategory_showErrorIdIsNotCorrect_Test() throws Exception {
        String id = "abc";

        mockMvc.perform(get("/owner/category/delete/"+id))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/owner/category/all"))
                .andExpect(redirectedUrl("/owner/category/all"))
                .andExpect(flash().attributeExists("globalError"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteCategory_showErrorIdIsNotExist_Test() throws Exception {
        String id = "0";

        mockMvc.perform(get("/owner/category/delete/"+id))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/owner/category/all"))
                .andExpect(redirectedUrl("/owner/category/all"))
                .andExpect(flash().attributeExists("globalError"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateCategory_NoErrorsShowCategoryForm_Test() throws Exception {
        Category category = createCategory();
        category = categoryService.persist(category);

        mockMvc.perform(get("/owner/category/update/"+category.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/category/add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/category/add.jsp"))
                .andExpect(model().attribute("category", category))
                .andExpect(model().attribute("title", "update"));
    }

    @Test
    public void updateCategory_showErrorIdIsNotCorrect_Test() throws Exception {
        String id = "abc";

        mockMvc.perform(get("/owner/category/update/"+id))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/owner/category/all"))
                .andExpect(redirectedUrl("/owner/category/all"))
                .andExpect(flash().attributeExists("globalError"));
    }

    @Test
    public void updateCategory_showErrorIdIsNotExist_Test() throws Exception {
        String id = "0";

        mockMvc.perform(get("/owner/category/update/"+id))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/owner/category/all"))
                .andExpect(redirectedUrl("/owner/category/all"))
                .andExpect(flash().attributeExists("globalError"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateCategoryPost_NoErrorsTheCategoryhasToBeUpdated_Test() throws Exception {
        Category category = createCategory();
        category = categoryService.persist(category);

        mockMvc.perform(post("/owner/category/update/"+category.getId())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("name", category.getName()+"1")
                    .param("description", category.getDescription()+"1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/owner/category/all"))
                .andExpect(redirectedUrl("/owner/category/all"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(flash().attributeExists("globalMsg"));
    }








    private Category createCategory(){
        Category category = new Category();
        category.setName("TestCategory");
        category.setDescription("Description");
        category.setDateOfCreation(new Date());
        category.setCreatedBy(null);
        return category;
    }

}
