package сontrollers.owner;

import biz.podoliako.carwash.controllers.owner.CarBrandController;
import biz.podoliako.carwash.models.entity.CarBrand;
import biz.podoliako.carwash.services.CarBrandService;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import utils.TestUtils;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@TestPropertySource("classpath:test-local.properties")
@ActiveProfiles(profiles = "test")
@WebAppConfiguration
public class CarBrandControllerTest {
    private MockMvc mockMvc;

    @Autowired
    CarBrandService carBrandService;

    @InjectMocks
    CarBrandController carBrandController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addCarBrand_shouldForwardToCarBrandForm_Test() throws Exception {
        mockMvc.perform(get("/owner/carbrand/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/carbrand/add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/carbrand/add.jsp"))
                .andExpect(model().attributeExists("carBrand"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addCarBrand_shouldSaveBecauseThereAreNotErrors_Test() throws Exception {
        CarBrand carBrand = createCarBrand();

        mockMvc.perform(post("/owner/carbrand/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", carBrand.getName())
                    )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/owner/carbrand/all"))
                .andExpect(redirectedUrl("/owner/carbrand/all"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attributeExists("globalMsg"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addCarBrand_shouldReturnToFormDueToErrors_Test() throws Exception {
        CarBrand carBrand = createCarBrand();
        carBrand.setName(TestUtils.createStringWithLength(35));

        mockMvc.perform(post("/owner/carbrand/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", carBrand.getName())
                    )
                .andExpect(status().isOk())
                .andExpect(view().name("owner/carbrand/add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/carbrand/add.jsp"))
                .andExpect(model().attributeHasFieldErrors("carBrand", "name"))
                .andExpect(model().attributeExists("carBrand"));
    }



    private CarBrand createCarBrand() {
        CarBrand carBrand = new CarBrand();
                 carBrand.setName("Test");
                 carBrand.setDateOfCreation(new Date());

        return carBrand;
    }


}
