package Controllers.owner;


import biz.podoliako.carwash.services.ClientService;
import biz.podoliako.carwash.services.impl.ClientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import biz.podoliako.carwash.models.entity.Client;
import utils.TestUtils;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:controllers/clientControllerTest-context.xml"})
@ActiveProfiles(profiles = "test")
@WebAppConfiguration
public class ClientControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addClient_GET_Test() throws Exception {

        mockMvc.perform(get("/owner/client/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/add.jsp"))
                .andExpect(model().attribute("client", is(new Client())));

    }

    @Test
    public void addClient_POST_ValidationOfParamLengthTest() throws Exception {
        String name = TestUtils.createStringWithLength(100).intern();
        String phoneNumber = TestUtils.createStringWithLength(100).intern();
        Integer priceMultiplicator = new Integer(90);

        mockMvc.perform(post("/owner/client/add")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("name", name)
                    .param("phoneNumber", phoneNumber)
                    .param("priceMultiplicator", priceMultiplicator.toString())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/add.jsp"))
                .andExpect(model().attributeHasFieldErrors("client", "name"))
                .andExpect(model().attributeHasFieldErrors("client", "phoneNumber"))
                .andExpect(model().attribute("client", hasProperty("name", is(name))))
                .andExpect(model().attribute("client", hasProperty("phoneNumber", is(phoneNumber))))
                .andExpect(model().attribute("client", hasProperty("priceMultiplicator", is(priceMultiplicator))));

    }

    @Test
    public void addClient_POST_ValidationNameCanNotBeEmpty_Test() throws Exception {
        String name = "";
        String phoneNumber = "";
        Integer priceMultiplicator = new Integer(100);

        mockMvc.perform(post("/owner/client/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("phoneNumber", phoneNumber)
                        .param("priceMultiplicator", priceMultiplicator.toString())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/add.jsp"))
                .andExpect(model().attributeHasFieldErrors("client", "name"))
                .andExpect(model().attribute("client", hasProperty("name", is(name))))
                .andExpect(model().attribute("client", hasProperty("phoneNumber", is(phoneNumber))))
                .andExpect(model().attribute("client", hasProperty("priceMultiplicator", is(priceMultiplicator))));

    }

    @Test
    public void addClient_Post_formIsValid_ClientHaveToBeSaved_Test() throws Exception {
        String name = "Ivanov";
        String phoneNumber = "";
        Boolean isPayByCash = true;
        Integer priceMultiplicator = new Integer(100);

        mockMvc.perform(post("/owner/client/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", name)
                .param("phoneNumber", phoneNumber)
                .param("isPayByCash", isPayByCash.toString())
                .param("priceMultiplicator", priceMultiplicator.toString()))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:owner/client/all"))
        .andExpect(redirectedUrl("owner/client/all"))
        .andExpect(flash().attribute("globalMsg", "Клиент + " + name + " создан успешно (#"+ 0 + ")"));

        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);

        verify(clientService, times(1)).saveClient(clientArgumentCaptor.capture());
    }

}
