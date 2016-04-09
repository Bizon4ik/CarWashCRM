package Controllers.owner;


import biz.podoliako.carwash.controllers.owner.ClientController;
import biz.podoliako.carwash.services.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import biz.podoliako.carwash.models.entity.Client;
import utils.TestUtils;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@TestPropertySource("classpath:test-local.properties")
@ActiveProfiles(profiles = "test")
@WebAppConfiguration
public class ClientControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    private MockMvc mockMvc;

    @Autowired
    ClientService clientService;

    @InjectMocks
    ClientController clientController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        /*mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();*/

    }

    @Test
    public void addClient_GET_Test() throws Exception {
        mockMvc.perform(get("/owner/clients/add"))
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

        mockMvc.perform(post("/owner/clients/add")
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

        mockMvc.perform(post("/owner/clients/add")
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
    @Transactional
    @Rollback(true)
    public void  addClient_Post_formIsValid_ClientHaveToBeSaved_Test() throws Exception {
        String name = "ivanov";
        String phoneNumber = "";
        Boolean isPayByCash = true;
        Integer priceMultiplicator = new Integer(100);

        mockMvc.perform(post("/owner/clients/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", name)
                .param("phoneNumber", phoneNumber)
                .param("isPayByCash", isPayByCash.toString())
                .param("priceMultiplicator", priceMultiplicator.toString()))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/owner/clients/all"))
        .andExpect(redirectedUrl("/owner/clients/all"))
        .andExpect(flash().attributeExists("globalMsg"));

    }

    @Test
    @Transactional
    @Rollback(true)
    public void  addClient_Post_formIsNOTValid_ClientNameIsExist_shouldReturnErrorMessage_Test() throws Exception {
        String name = "ivanov";
        String phoneNumber = "";
        Boolean isPayByCash = true;
        Integer priceMultiplicator = new Integer(100);

        Client client = new Client();
        client.setName(name);
        client.setPhoneNumber("123");
        client.setIsPayByCash(false);
        client.setPriceMultiplicator(90);

        clientService.saveClient(client);

        mockMvc.perform(post("/owner/clients/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", name)
                .param("phoneNumber", phoneNumber)
                .param("isPayByCash", isPayByCash.toString())
                .param("priceMultiplicator", priceMultiplicator.toString()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("owner/clients/add"))
                    .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/add.jsp"))
                    .andExpect(model().attribute("client", hasProperty("name", is(name))))
                    .andExpect(model().attribute("client", hasProperty("phoneNumber", is(phoneNumber))))
                    .andExpect(model().attribute("client", hasProperty("priceMultiplicator", is(priceMultiplicator))));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void allClientPage_GET_Test() throws Exception {
        mockMvc.perform(get("/owner/clients/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/all"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/all.jsp"))
                .andExpect(model().attributeExists("clientList"));

    }

    @Test
    @Transactional
    @Rollback(true)
    public void  deleteClient_Test() throws Exception {
        Client client = createClient("Ivan");

        Long id = clientService.saveClient(client);

        mockMvc.perform(get("/owner/clients/delete/" + id))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/owner/clients/all"))
                .andExpect(redirectedUrl("/owner/clients/all"))
                .andExpect(flash().attributeExists("globalMsg"));

    }

    @Test
    @Transactional
    @Rollback(true)
    public void  deleteClient_Test_Id_not_corrent() throws Exception {
        mockMvc.perform(get("/owner/clients/delete/abc"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/all"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/all.jsp"))
                .andExpect(model().attribute("globalError", "Не коректный id клиента для удаления"))
                .andExpect(model().attributeExists("clientList"));


    }

    @Test
    @Transactional
    @Rollback(true)
    public void  deleteClient_Test_Id_not_Exist() throws Exception {
        mockMvc.perform(get("/owner/clients/delete/0"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/all"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/all.jsp"))
                .andExpect(model().attribute("globalError", "Вы пытаетесь удалить не существующего клиента"))
                .andExpect(model().attributeExists("clientList"));


    }

    @Test
    @Transactional
    @Rollback(true)
    public void update_Client_showForm_test() throws Exception {
        Client client = createClient("ivan");
        Long id = clientService.saveClient(client);

        mockMvc.perform(get("/owner/clients/update/"+ id))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/add.jsp"))
                .andExpect(model().attribute("client", is(client)));

    }

    @Test
    @Transactional
    @Rollback(true)
    public void  updateClient_Test_Id_not_corrent() throws Exception {
        mockMvc.perform(get("/owner/clients/update/abc"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/all"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/all.jsp"))
                .andExpect(model().attribute("globalError", "Не коректный id клиента для редактирования"))
                .andExpect(model().attributeExists("clientList"));

    }

    @Test
    @Transactional
    @Rollback(true)
    public void  updateClient_Test_Id_not_Exist() throws Exception {
        mockMvc.perform(get("/owner/clients/update/0"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/all"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/all.jsp"))
                .andExpect(model().attribute("globalError", "Вы пытаетесь обновить не существующего клиента"))
                .andExpect(model().attributeExists("clientList"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void  updateClient_Test_Id_not_Exist_Post() throws Exception {
        Client client = createClient("test");

        mockMvc.perform(post("/owner/clients/update/0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", client.getName())
                .param("phoneNumber", client.getPhoneNumber())
                .param("isPayByCash", client.getIsPayByCash().toString())
                .param("priceMultiplicator", client.getPriceMultiplicator().toString()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("owner/clients/all"))
                    .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/all.jsp"))
                    .andExpect(model().attribute("globalError", "Вы пытаетесь обновить не существующего клиента"))
                    .andExpect(model().attributeExists("clientList"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void  updateClient_Test_Id_not_corrent_Post() throws Exception {
        Client client = createClient("test");

        mockMvc.perform(post("/owner/clients/update/abc")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", client.getName())
                .param("phoneNumber", client.getPhoneNumber())
                .param("isPayByCash", client.getIsPayByCash().toString())
                .param("priceMultiplicator", client.getPriceMultiplicator().toString()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("owner/clients/all"))
                    .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/all.jsp"))
                    .andExpect(model().attribute("globalError", "Не коректный id клиента для редактирования"))
                    .andExpect(model().attributeExists("clientList"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateClient_validation_length_of_param_Test() throws Exception {
        Client client = createClient("test");
        Long id = clientService.saveClient(client);

        String name = TestUtils.createStringWithLength(100).intern();
        String phoneNumber = TestUtils.createStringWithLength(100).intern();

        mockMvc.perform(post("/owner/clients/update/"+id.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("phoneNumber", phoneNumber)
                        .param("priceMultiplicator", client.getPriceMultiplicator().toString())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/add.jsp"))
                .andExpect(model().attributeHasFieldErrors("client", "name"))
                .andExpect(model().attributeHasFieldErrors("client", "phoneNumber"))
                .andExpect(model().attribute("client", hasProperty("name", is(name))))
                .andExpect(model().attribute("client", hasProperty("phoneNumber", is(phoneNumber))))
                .andExpect(model().attribute("client", hasProperty("priceMultiplicator", is(client.getPriceMultiplicator()))))
                .andExpect(model().attribute("client", hasProperty("id", is(id))))
                .andExpect(model().attribute("title", "update"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateClient_name_cannot_be_empty_Test() throws Exception {
        Client client = createClient("test");
        Long id = clientService.saveClient(client);

        String name = "";

        mockMvc.perform(post("/owner/clients/update/"+id.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("phoneNumber", client.getPhoneNumber())
                        .param("priceMultiplicator", client.getPriceMultiplicator().toString())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("owner/clients/add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/owner/clients/add.jsp"))
                .andExpect(model().attributeHasFieldErrors("client", "name"))
                .andExpect(model().attribute("client", hasProperty("name", is(name))))
                .andExpect(model().attribute("client", hasProperty("phoneNumber", is(client.getPhoneNumber()))))
                .andExpect(model().attribute("client", hasProperty("priceMultiplicator", is(client.getPriceMultiplicator()))))
                .andExpect(model().attribute("client", hasProperty("id", is(id))))
                .andExpect(model().attribute("title", "update"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateClient_changes_have_to_be_save_Test() throws Exception {
        Client client = createClient("test");
        Long id = clientService.saveClient(client);

        Client clientChanged = createClient("supertest");
        clientChanged.setPhoneNumber("5700876");

        mockMvc.perform(post("/owner/clients/update/" + id.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", clientChanged.getName())
                        .param("phoneNumber", clientChanged.getPhoneNumber())
                        .param("priceMultiplicator", clientChanged.getPriceMultiplicator().toString())
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/owner/clients/all"))
                .andExpect(redirectedUrl("/owner/clients/all"))
                .andExpect(flash().attributeExists("globalMsg"));

        Client updatedClient = clientService.find(id);

        assertEquals(clientChanged, updatedClient);
    }



    private Client createClient(String name){
        Client client = new Client();
        client.setName(name);
        client.setPhoneNumber("123");
        client.setIsPayByCash(false);
        client.setPriceMultiplicator(100);

        return  client;
    }



}
