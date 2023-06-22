/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.exception.CustomerException;
import com.nst_project.project.mapper.CustomerMapper;
import com.nst_project.project.model.Customer;
import com.nst_project.project.repository.CustomerRepository;
import com.nst_project.project.service.CustomerService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author nodas
 */
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerMapper customerMapper;

    @Test
    public void saveSuccessTest() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerID(1);
        customerDto.setName("cus");
        customerDto.setUsername("cus");
        customerDto.setPassword("pass");

        // create a customer entity to return from the repository
        Customer customerEntity = new Customer();
        customerEntity.setCustomerID(1);
        customerEntity.setName("cus");
        customerEntity.setUsername("cus");
        customerEntity.setPassword("pass");

        // configure the mocks to return the expected values
        when(customerMapper.toEntity(customerDto)).thenReturn(customerEntity);
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.toDto(customerEntity)).thenReturn(customerDto);

        CustomerDto savedCustomerDto = customerService.save(customerDto);

        // verify the expected values
        assertEquals(savedCustomerDto, customerDto);
        verify(customerMapper).toEntity(customerDto);
        verify(customerRepository).save(customerEntity);
        verify(customerMapper).toDto(customerEntity);
    }

    @Test
    public void findAllSuccessTest() {

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "n1", "n1", "n1"));
        customers.add(new Customer(1, "n2", "n2", "n2"));
        when(customerMapper.toDto(any(Customer.class))).thenReturn(new CustomerDto());
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDto> customerDtos = customerService.findAll();

        assertNotNull(customerDtos);
        assertEquals(2, customerDtos.size());
        verify(customerMapper, times(2)).toDto(any(Customer.class));

    }

    @Test
    public void findbyIdSuccessTest() {

        Customer customer = new Customer();
        customer.setCustomerID(1);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerID(1);
        when(customerMapper.toDto(customer)).thenReturn(customerDto);

        // Act
        CustomerDto result = customerService.findById(1);

        // Assert
        assertEquals(result, customerDto);

    }

    @Test
    public void findbyIdFailTest() {

       // Arrange
        Integer customerId = 1;
        when(customerRepository.findById(customerId)).thenThrow(new CustomerException("Error finding customer"));

        // Act & Assert
        assertThrows(CustomerException.class, () -> customerService.findById(customerId));
    
    }

    @Test
    public void testLoginSuccess() throws CustomerException {
        // mock customer and list of customers
        CustomerDto customerDto = new CustomerDto(1, "name", "username", "password");
        Customer customer = new Customer(1, "name", "username", "password");
        List<Customer> customers = Collections.singletonList(customer);

        // mock customerRepository
        when(customerRepository.findByUsernameAndPassword("username", "password")).thenReturn(customers);

        // mock customerMapper
        when(customerMapper.toEntity(customerDto)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(customerDto);

        // test
        CustomerDto result = customerService.login(customerDto);

        // verify
        verify(customerRepository, times(1)).findByUsernameAndPassword("username", "password");
        verify(customerMapper, times(1)).toEntity(customerDto);
        verify(customerMapper, times(1)).toDto(customer);
        assertThat(result, equalTo(customerDto));
    }
    
    @Test
    public void testLoginFail() throws CustomerException {
          // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUsername("username");
        customerDto.setPassword("password");

        Customer customer = new Customer();
        customer.setCustomerID(1);
        customer.setUsername("username");
        customer.setPassword("wrong_password");

        when(customerMapper.toEntity(customerDto)).thenReturn(customer);
        when(customerRepository.findByUsernameAndPassword("username", "wrong_password"))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(CustomerException.class, () -> customerService.login(customerDto));
    }
    
    @Test
public void updateCustomerSuccessTest() throws CustomerException {
 CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerID(1);
        customerDto.setName("cus");
        customerDto.setUsername("cus");
        customerDto.setPassword("pass");

        // create a customer entity to return from the repository
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerID(1);
        updatedCustomer.setName("cus");
        updatedCustomer.setUsername("cus");
        updatedCustomer.setPassword("pass");

    when(customerRepository.findById(customerDto.getCustomerID())).thenReturn(Optional.of(updatedCustomer));
    when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);
    when(customerMapper.toEntity(customerDto)).thenReturn(updatedCustomer);
    when(customerMapper.toDto(updatedCustomer)).thenReturn(customerDto);

    // Act
    CustomerDto result = customerService.update(customerDto);

    // Assert
    assertEquals(result, customerDto);
}

@Test
    public void deleteSuccessTest() throws CustomerException {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerID(1);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // Act
        customerService.delete(1);

        // Assert
        verify(customerRepository, times(1)).deleteById(1);
    }
    

}
