/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.dtos.OrderDto;
import com.nst_project.project.dtos.OrderDto;
import com.nst_project.project.exception.OrderException;
import com.nst_project.project.mapper.OrderMapper;
import com.nst_project.project.mapper.OrderMapper;
import com.nst_project.project.model.Category;
import com.nst_project.project.model.Customer;
import com.nst_project.project.model.Order;
import com.nst_project.project.model.Order;
import com.nst_project.project.repository.OrderRepository;
import com.nst_project.project.repository.OrderRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author nodas
 */
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService OrderService;

    @MockBean
    private OrderRepository OrderRepository;

    @MockBean
    private OrderMapper OrderMapper;

    @Test
    public void saveSuccessTest() {

        OrderDto OrderDto = new OrderDto(1, new Date(), "s", new CustomerDto());

        // create a Order entity to return from the repository
        Order OrderEntity = new Order(1, new Date(), "s", new Customer());

        // configure the mocks to return the expected values
        when(OrderMapper.toEntity(OrderDto)).thenReturn(OrderEntity);
        when(OrderRepository.save(OrderEntity)).thenReturn(OrderEntity);
        when(OrderMapper.toDto(OrderEntity)).thenReturn(OrderDto);

        OrderDto savedCat = OrderService.save(OrderDto);

        // verify the expected values
        assertEquals(savedCat, OrderDto);
        verify(OrderMapper).toEntity(OrderDto);
        verify(OrderRepository).save(OrderEntity);
        verify(OrderMapper).toDto(OrderEntity);
    }

    @Test
    public void findAllSuccessTest() {

        List<Order> categories = new ArrayList<>();
        categories.add(new Order(1, new Date(), "s", new Customer()));
        categories.add(new Order(1, new Date(), "s", new Customer()));
        when(OrderMapper.toDto(any(Order.class))).thenReturn(new OrderDto());
        when(OrderRepository.findAll()).thenReturn(categories);

        List<OrderDto> OrderDtos = OrderService.findAll();

        assertNotNull(OrderDtos);
        assertEquals(2, OrderDtos.size());
        verify(OrderMapper, times(2)).toDto(any(Order.class));

    }

    @Test
    public void findbyIdSuccessTest() {

        Order Order = new Order();
        Order.setOrderID(1);
        when(OrderRepository.findById(1)).thenReturn(Optional.of(Order));

        OrderDto OrderDto = new OrderDto();
        OrderDto.setOrderID(1);
        when(OrderMapper.toDto(Order)).thenReturn(OrderDto);

        // Act
        OrderDto result = OrderService.findById(1);

        // Assert
        assertEquals(result, OrderDto);

    }

    @Test
    public void findbyIdFailTest() {

        // Arrange
        Integer OrderId = 1;
        when(OrderRepository.findById(OrderId)).thenThrow(new OrderException("Error finding Order"));

        // Act & Assert
        assertThrows(OrderException.class, () -> OrderService.findById(OrderId));

    }

    @Test
    public void updateOrderSuccessTest() throws OrderException {
        OrderDto OrderDto = new OrderDto(1, new Date(), "s", new CustomerDto());

        // create a Order entity to return from the repository
        Order updatedOrder = new Order();
        updatedOrder.setOrderID(1);
        updatedOrder.setStatus("cus");
//        updatedOrder.setDescription("cus");

        when(OrderRepository.findById(OrderDto.getOrderID())).thenReturn(Optional.of(updatedOrder));
        when(OrderRepository.save(any(Order.class))).thenReturn(updatedOrder);
        when(OrderMapper.toEntity(OrderDto)).thenReturn(updatedOrder);
        when(OrderMapper.toDto(updatedOrder)).thenReturn(OrderDto);

        // Act
        OrderDto result = OrderService.update(OrderDto);

        // Assert
        assertEquals(result, OrderDto);
    }

    @Test
    public void deleteSuccessTest() throws OrderException {
        // Arrange
        Order Order = new Order();
        Order.setOrderID(1);
        when(OrderRepository.findById(1)).thenReturn(Optional.of(Order));

        // Act
        OrderService.delete(1);

        // Assert
//        verify(OrderRepository, times(1)).deleteById(1);
    }


}
