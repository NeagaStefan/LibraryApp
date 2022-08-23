package com.example.libraryapp.Controller;

import com.example.libraryapp.Entity.Customer;
import com.example.libraryapp.Entity.CustomerDto;
import com.example.libraryapp.Service.BookService;
import com.example.libraryapp.Service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CustomerControllerTest {
    @Autowired
    private MockMvc mvc;



    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerService customerService;
    private CustomerDto customer;
    @MockBean
    private BookService bookService;

    @BeforeEach
    void setUp() {
        customer = new CustomerDto(2L, "Stefan", "Neaga", "neagastefan99@yahoo.com", "None");
    }
    @Test
    void fetchAllCustomers() throws Exception{
        Mockito.when(customerService.fetchAllCustomers()).thenReturn(new ArrayList<>(List.of(customer)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }


    @Test
    void fetchCustomerById() throws Exception{
        Mockito.when(customerService.fetchCustomerById(ArgumentMatchers.any())).thenReturn(customer);
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/customers/").param("id", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CustomerDto response = new ObjectMapper().readValue(mvcResultAsString,CustomerDto.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Stefan", response.getFirstName());
        Assertions.assertEquals("Neaga", response.getLastName());
    }

    @Test
    void fetchCustomerByLastName() throws Exception {
        Mockito.when(customerService.fetchCustomerByLastName(ArgumentMatchers.anyString())).thenReturn(new ArrayList<>(List.of(customer)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/customers/name").param("lastName", "Neaga")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());

    }

    @Test
    void fetchCustomerByEmail() throws Exception{
        Mockito.when(customerService.fetchCustomerByEmail(ArgumentMatchers.anyString())).thenReturn(customer);
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/customers/email").param("email", "neagastefan99@yahoo.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CustomerDto response = new ObjectMapper().readValue(mvcResultAsString, CustomerDto.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Stefan", response.getFirstName());
        Assertions.assertEquals("Neaga", response.getLastName());
    }

    @Test
    void saveACustomer() throws Exception {
        //given

        Mockito.when(customerService.saveACustomer(Mockito.any(CustomerDto.class))).thenReturn(new Customer(3L,"Stefan","Neaga","neagastefan79@yahoo.com","None"));
        //When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(customer)
                        .getBytes(StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        Assertions.assertNotNull(result);


    }

    @Test
    void updateACostumer() throws Exception {
        //given

        Mockito.when(customerService.saveACustomer(Mockito.any(CustomerDto.class))).thenReturn(new Customer(3L,"Stefan","Neaga","neagastefan79@yahoo.com","None"));
        //When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/customers/").param("id","3")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(customer)
                                .getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        Assertions.assertNotNull(result);


    }

    @Test
    void updateBookTaken() throws  Exception {
        Mockito.doNothing().when(customerService).updateBookTaken(ArgumentMatchers.any(),ArgumentMatchers.anyString());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/customers/")
                .param("id","2").param("title","Odiseea")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void updateBookReturned() throws  Exception{
        Mockito.doNothing().when(customerService).updateReturned(ArgumentMatchers.any(),ArgumentMatchers.anyString());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/customers/")
                        .param("id","2").param("title","Odiseea")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteById() throws Exception {
        Mockito.doNothing().when(customerService).deleteById(ArgumentMatchers.any());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/customers")
                        .param("id","2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
