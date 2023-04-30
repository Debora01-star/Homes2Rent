package com.Homes2Rent.Homes2Rent.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.Homes2Rent.Homes2Rent.dto.CustomerInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Customer;
import com.Homes2Rent.Homes2Rent.repository.CustomerRepository;
import com.Homes2Rent.Homes2Rent.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerController.class, CustomerService.class})
@ExtendWith(SpringExtension.class)
class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerRepository customerRepository;

    /**
     * Method under test: {@link CustomerController#createCustomer(CustomerInputDto)}
     */
    @Test
    void testCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setId(123L);
        customer.setLastname("Doe");
        customer.setStreetname("Streetname");
        customer.setTown("Oxford");
        customer.setZipcode("21654");
        when(customerRepository.existsByEmail((String) any())).thenReturn(true);
        when(customerRepository.save((Customer) any())).thenReturn(customer);

        CustomerInputDto customerInputDto = new CustomerInputDto();
        customerInputDto.setEmail("jane.doe@example.org");
        customerInputDto.setFirstname("Jane");
        customerInputDto.setId(123L);
        customerInputDto.setLastname("Doe");
        customerInputDto.setStreetname("Streetname");
        customerInputDto.setTown("Oxford");
        customerInputDto.setZipcode("21654");
        String content = (new ObjectMapper()).writeValueAsString(customerInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Cannot find user customer email already exists"));
    }

    /**
     * Method under test: {@link CustomerController#createCustomer(CustomerInputDto)}
     */
    @Test
    void testCreateCustomer2() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setId(123L);
        customer.setLastname("Doe");
        customer.setStreetname("Streetname");
        customer.setTown("Oxford");
        customer.setZipcode("21654");
        when(customerRepository.existsByEmail((String) any())).thenReturn(false);
        when(customerRepository.save((Customer) any())).thenReturn(customer);

        CustomerInputDto customerInputDto = new CustomerInputDto();
        customerInputDto.setEmail("jane.doe@example.org");
        customerInputDto.setFirstname("Jane");
        customerInputDto.setId(123L);
        customerInputDto.setLastname("Doe");
        customerInputDto.setStreetname("Streetname");
        customerInputDto.setTown("Oxford");
        customerInputDto.setZipcode("21654");
        String content = (new ObjectMapper()).writeValueAsString(customerInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"email\":\"jane.doe@example.org\",\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"streetname\":\"Streetname"
                                        + "\",\"town\":\"Oxford\",\"zipcode\":\"21654\"}"));
    }

    /**
     * Method under test: {@link CustomerController#deleteCustomer(Long)}
     */
    @Test
    void testDeleteCustomer() throws Exception {
        doNothing().when(customerRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customers/{id}", 123L);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Customer deleted"));
    }

    /**
     * Method under test: {@link CustomerController#deleteCustomer(Long)}
     */
    @Test
    void testDeleteCustomer2() throws Exception {
        doThrow(new RecordNotFoundException("?")).when(customerRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customers/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("No customer found"));
    }

    /**
     * Method under test: {@link CustomerController#getCustomers()}
     */
    @Test
    void testGetCustomers() throws Exception {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#getCustomers()}
     */
    @Test
    void testGetCustomers2() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setId(123L);
        customer.setLastname("Doe");
        customer.setStreetname("?");
        customer.setTown("Oxford");
        customer.setZipcode("21654");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findAll()).thenReturn(customerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":null,\"email\":\"jane.doe@example.org\",\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"streetname\":\"?\",\"town"
                                        + "\":\"Oxford\",\"zipcode\":\"21654\"}]"));
    }

    /**
     * Method under test: {@link CustomerController#getOneCustomer(Long)}
     */
    @Test
    void testGetOneCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setId(123L);
        customer.setLastname("Doe");
        customer.setStreetname("Streetname");
        customer.setTown("Oxford");
        customer.setZipcode("21654");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/{id}", 123L);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"email\":\"jane.doe@example.org\",\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"streetname\":\"Streetname"
                                        + "\",\"town\":\"Oxford\",\"zipcode\":\"21654\"}"));
    }

    /**
     * Method under test: {@link CustomerController#getOneCustomer(Long)}
     */
    @Test
    void testGetOneCustomer2() throws Exception {
        when(customerRepository.findById((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("No user found"));
    }

    /**
     * Method under test: {@link CustomerController#getOneCustomer(Long)}
     */
    @Test
    void testGetOneCustomer3() throws Exception {
        when(customerRepository.findById((Long) any())).thenThrow(new RecordNotFoundException("?"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("No user found"));
    }

    /**
     * Method under test: {@link CustomerController#updateCustomer(Long, CustomerInputDto)}
     */
    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setId(123L);
        customer.setLastname("Doe");
        customer.setStreetname("Streetname");
        customer.setTown("Oxford");
        customer.setZipcode("21654");
        Optional<Customer> ofResult = Optional.of(customer);

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setFirstname("Jane");
        customer1.setId(123L);
        customer1.setLastname("Doe");
        customer1.setStreetname("Streetname");
        customer1.setTown("Oxford");
        customer1.setZipcode("21654");

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setFirstname("Jane");
        customer2.setId(123L);
        customer2.setLastname("Doe");
        customer2.setStreetname("Streetname");
        customer2.setTown("Oxford");
        customer2.setZipcode("21654");
        when(customerRepository.findCustomerByEmail((String) any())).thenReturn(customer1);
        when(customerRepository.save((Customer) any())).thenReturn(customer2);
        when(customerRepository.findById((Long) any())).thenReturn(ofResult);

        CustomerInputDto customerInputDto = new CustomerInputDto();
        customerInputDto.setEmail("jane.doe@example.org");
        customerInputDto.setFirstname("Jane");
        customerInputDto.setId(123L);
        customerInputDto.setLastname("Doe");
        customerInputDto.setStreetname("Streetname");
        customerInputDto.setTown("Oxford");
        customerInputDto.setZipcode("21654");
        String content = (new ObjectMapper()).writeValueAsString(customerInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customers/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"email\":\"jane.doe@example.org\",\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"streetname\":\"Streetname"
                                        + "\",\"town\":\"Oxford\",\"zipcode\":\"21654\"}"));
    }

    /**
     * Method under test: {@link CustomerController#updateCustomer(Long, CustomerInputDto)}
     */
    @Test
    void testUpdateCustomer2() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setId(123L);
        customer.setLastname("Doe");
        customer.setStreetname("Streetname");
        customer.setTown("Oxford");
        customer.setZipcode("21654");
        Optional<Customer> ofResult = Optional.of(customer);

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setFirstname("Jane");
        customer1.setId(1L);
        customer1.setLastname("Doe");
        customer1.setStreetname("Streetname");
        customer1.setTown("Oxford");
        customer1.setZipcode("21654");

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setFirstname("Jane");
        customer2.setId(123L);
        customer2.setLastname("Doe");
        customer2.setStreetname("Streetname");
        customer2.setTown("Oxford");
        customer2.setZipcode("21654");
        when(customerRepository.findCustomerByEmail((String) any())).thenReturn(customer1);
        when(customerRepository.save((Customer) any())).thenReturn(customer2);
        when(customerRepository.findById((Long) any())).thenReturn(ofResult);

        CustomerInputDto customerInputDto = new CustomerInputDto();
        customerInputDto.setEmail("jane.doe@example.org");
        customerInputDto.setFirstname("Jane");
        customerInputDto.setId(123L);
        customerInputDto.setLastname("Doe");
        customerInputDto.setStreetname("Streetname");
        customerInputDto.setTown("Oxford");
        customerInputDto.setZipcode("21654");
        String content = (new ObjectMapper()).writeValueAsString(customerInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customers/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Cannot find user Email already exists"));
    }

    /**
     * Method under test: {@link CustomerController#updateCustomer(Long, CustomerInputDto)}
     */
    @Test
    void testUpdateCustomer3() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setId(123L);
        customer.setLastname("Doe");
        customer.setStreetname("Streetname");
        customer.setTown("Oxford");
        customer.setZipcode("21654");

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setFirstname("Jane");
        customer1.setId(123L);
        customer1.setLastname("Doe");
        customer1.setStreetname("Streetname");
        customer1.setTown("Oxford");
        customer1.setZipcode("21654");
        when(customerRepository.findCustomerByEmail((String) any())).thenReturn(customer);
        when(customerRepository.save((Customer) any())).thenReturn(customer1);
        when(customerRepository.findById((Long) any())).thenReturn(Optional.empty());

        CustomerInputDto customerInputDto = new CustomerInputDto();
        customerInputDto.setEmail("jane.doe@example.org");
        customerInputDto.setFirstname("Jane");
        customerInputDto.setId(123L);
        customerInputDto.setLastname("Doe");
        customerInputDto.setStreetname("Streetname");
        customerInputDto.setTown("Oxford");
        customerInputDto.setZipcode("21654");
        String content = (new ObjectMapper()).writeValueAsString(customerInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customers/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

