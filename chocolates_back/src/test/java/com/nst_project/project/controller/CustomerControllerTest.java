/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst_project.project.controller.CustomerController;
import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.exception.CustomerException;
import com.nst_project.project.service.CustomerService;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.doNothing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author nodas
 */
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerController.class)
@WebMvcTest(CustomerController.class)

public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDto customerDto;

    @BeforeEach
    public void setUp() {
        customerDto = new CustomerDto();
        customerDto.setCustomerID(1);
        customerDto.setName("Customer 1");
        customerDto.setUsername("customer 1");
        customerDto.setPassword("customer 1");
    }

    @Test
    public void addSuccess() throws Exception {

        // kada se pozove fja save vraca se customerDto koji se uneo
        when(customerService.save(customerDto)).thenReturn(customerDto);

        // when
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", equalTo(customerDto.getName())))
                .andExpect(jsonPath("$.username", equalTo(customerDto.getUsername())))
                .andExpect(jsonPath("$.password", equalTo(customerDto.getPassword())));
    }

    @Test
    public void getAllCustomersSuccess() throws Exception {
        CustomerDto customerDto1 = new CustomerDto(1, "Customer1", "customer1", "customer1");
        CustomerDto customerDto2 = new CustomerDto(2, "Customer2", "customer2", "customer2");
        List<CustomerDto> customers = Arrays.asList(customerDto1, customerDto2);

        when(customerService.findAll()).thenReturn(customers);

        MvcResult result = mockMvc.perform(get("/customers")).andExpect(status().isOk()).andReturn();//vracamo sve customere
        List<CustomerDto> returnedCustomers = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<CustomerDto>>() {
        });

        //proveriti da li lista sadrzi oba customera
        assertTrue(returnedCustomers.contains(customerDto1));
        assertTrue(returnedCustomers.contains(customerDto2));

    }

    @Test
    public void getCustomerByIdFail() throws Exception {
        when(customerService.findById(customerDto.getCustomerID())).thenThrow(CustomerException.class);
        mockMvc.perform(get("/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto.getCustomerID()))).andExpect(status().isBadRequest());
    }

    @Test
    public void getCustomerByIdSuccess() throws Exception {
        when(customerService.findById(customerDto.getCustomerID())).thenReturn(customerDto);
        mockMvc.perform(get("/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.customerID", equalTo(customerDto.getCustomerID())))
                .andExpect(jsonPath("$.name", equalTo(customerDto.getName())))
                .andExpect(jsonPath("$.username", equalTo(customerDto.getUsername())))
                .andExpect(jsonPath("$.password", equalTo(customerDto.getPassword())));
    }

    @Test
    public void customerLoginFail() throws Exception {
        when(customerService.login(customerDto)).thenThrow(CustomerException.class);
        mockMvc.perform(post("/customer/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto))).andExpect(status().isBadRequest());
    }

    @Test
    public void customerLoginSuccess() throws Exception {
        when(customerService.login(customerDto)).thenReturn(customerDto);

        mockMvc.perform(post("/customer/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.customerID", equalTo(customerDto.getCustomerID())))
                .andExpect(jsonPath("$.name", equalTo(customerDto.getName())))
                .andExpect(jsonPath("$.username", equalTo(customerDto.getUsername())))
                .andExpect(jsonPath("$.password", equalTo(customerDto.getPassword())));
    }

    @Test
    public void updateFail() throws Exception {
        when(customerService.update(customerDto)).thenThrow(CustomerException.class);
        mockMvc.perform(put("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto))).andExpect(status().isNotFound());
    }

    @Test
    public void updateSuccess() throws Exception {
        when(customerService.update(customerDto)).thenReturn(customerDto);

        mockMvc.perform(put("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.customerID", equalTo(customerDto.getCustomerID())))
                .andExpect(jsonPath("$.name", equalTo(customerDto.getName())))
                .andExpect(jsonPath("$.username", equalTo(customerDto.getUsername())))
                .andExpect(jsonPath("$.password", equalTo(customerDto.getPassword())));
    }

    @Test
    public void deleteFail() throws Exception {
        doThrow(new CustomerException("Unable to delete customer")).when(customerService).delete(customerDto.getCustomerID());
        mockMvc.perform(delete("/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto))).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteSuccess() throws Exception {
       
        //u service impl delete ne vraca nista pa treba doNothing
        doNothing().when(customerService).delete(customerDto.getCustomerID());
        mockMvc.perform(delete("/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string("Customer's account has been deleted"));

    }
}
