package guru.springframework.controllers;

import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    // inject an instance of customercontroller configured with our mock customerservice
    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testListCustomer() throws Exception{
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerService.listAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/list"))
                .andExpect(model().attribute("customers", hasSize(2)));
    }

    @Test
    public void testShowCustomer() throws Exception {
        Integer id = 1;

        when(customerService.getCustomerById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/show"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testNewCustomer() throws Exception{

        verifyNoInteractions(customerService);

        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Integer id = 1;

        when(customerService.getCustomerById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Integer id = 1;

        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"));

        verify(customerService, times(1)).deleteCustomer(id);
    }

    @Test
    public void testSaveOrUIpdateCustomer() throws Exception {
        Integer id = 1;
        String firstName = "John";
        String lastName = "Doe";
        String email = "johndoe@email.com";
        String phoneNumber = "0219898";

        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);

        when(customerService.saveOrUpdateCustomer(Matchers.<Customer>any())).thenReturn(customer);

        mockMvc.perform(post("/customer")
                .param("id", "1")
                .param("firstName", "John")
                .param("LastName", "Doe")
                .param("email", "johndoe@email.com")
                .param("phoneNumber", "0219898"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/show/1"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andExpect(model().attribute("customer", hasProperty("id", is(id))))
                .andExpect(model().attribute("customer", hasProperty("firstName", is(firstName))))
                .andExpect(model().attribute("customer", hasProperty("lastName", is(lastName))))
                .andExpect(model().attribute("customer", hasProperty("email", is(email))))
                .andExpect(model().attribute("customer", hasProperty("phoneNumber", is(phoneNumber))));

        // The object that is created from the FORM POST parameters and went through the Spring bindings
        ArgumentCaptor<Customer> boundCustomer = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).saveOrUpdateCustomer(boundCustomer.capture());

        //check if bindings happened correctly
        assertEquals(id, boundCustomer.getValue().getId());
        assertEquals(firstName, boundCustomer.getValue().getFirstName());
        assertEquals(lastName, boundCustomer.getValue().getLastName());
        assertEquals(email, boundCustomer.getValue().getEmail());
        assertEquals(phoneNumber, boundCustomer.getValue().getPhoneNumber());
    }
}
