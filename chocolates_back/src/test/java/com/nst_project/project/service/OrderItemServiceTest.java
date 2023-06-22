/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.dtos.OrderItemDto;
import com.nst_project.project.dtos.OrderItemDto;
import com.nst_project.project.exception.OrderItemException;
import com.nst_project.project.mapper.OrderItemMapper;
import com.nst_project.project.mapper.OrderItemMapper;
import com.nst_project.project.model.Category;
import com.nst_project.project.model.Chocolate;
import com.nst_project.project.model.Customer;
import com.nst_project.project.model.Order;
import com.nst_project.project.model.OrderItem;
import com.nst_project.project.model.OrderItem;
import com.nst_project.project.repository.OrderItemRepository;
import com.nst_project.project.repository.OrderItemRepository;
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
public class OrderItemServiceTest {

    @Autowired
    private OrderItemService orderItemService;

    @MockBean
    private OrderItemRepository orderItemRepository;

    @MockBean
    private OrderItemMapper OrderItemMapper;

   
    @Test
    public void findAllSuccessTest() {

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(1, new Order(), new Chocolate(), 1));
        orderItems.add(new OrderItem(2, new Order(), new Chocolate(), 2));
        when(OrderItemMapper.toDto(any(OrderItem.class))).thenReturn(new OrderItemDto());
        when(orderItemRepository.findAll()).thenReturn(orderItems);

        List<OrderItemDto> OrderItemDtos = orderItemService.findAll();

        assertNotNull(OrderItemDtos);
        assertEquals(2, OrderItemDtos.size());
        verify(OrderItemMapper, times(2)).toDto(any(OrderItem.class));

    }

    @Test
    public void saveOrderItemsTest() {
        // Mocking
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderItemID(1);
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemID(2);
        
        when(OrderItemMapper.toEntity(any(OrderItemDto.class))).thenReturn(orderItem);
        when(OrderItemMapper.toDto(any(OrderItem.class))).thenReturn(orderItemDto);
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        // Test
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        orderItemDtos.add(orderItemDto);
        List<OrderItemDto> result=orderItemService.saveOrderItems(orderItemDtos);

        // Verify
        assertEquals(orderItemDtos.size(), result.size());
        assertEquals(orderItemDto.getOrderItemID(), result.get(0).getOrderItemID());
    }
   
}
