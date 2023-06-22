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
import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.dtos.OrderDto;
import com.nst_project.project.dtos.OrderItemDto;
import com.nst_project.project.exception.CategoryException;
import com.nst_project.project.exception.CustomerException;
import com.nst_project.project.exception.OrderException;
import com.nst_project.project.exception.OrderItemException;
import com.nst_project.project.service.CategoryService;
import com.nst_project.project.service.CustomerService;
import com.nst_project.project.service.OrderItemService;
import com.nst_project.project.service.OrderService;
import java.util.ArrayList;
import java.util.Date;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author nodas
 */
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderItemController.class)
@WebMvcTest(OrderItemController.class)

public class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderItemDto orderItemDto;

    @BeforeEach
    public void setUp() {
        orderItemDto = new OrderItemDto(new OrderDto(), 1, new ChocolateDto(), 1);
    }

    @Test
    public void addSuccess() throws Exception {

        // kada se pozove fja save vraca se customerDto koji se uneo
        OrderDto orderDto = new OrderDto(1, new Date(), "s", new CustomerDto());
        List<OrderItemDto> orderItems = new ArrayList<>();
        OrderItemDto orderItemDto1 = new OrderItemDto(orderDto, 1, new ChocolateDto(), 1);

        OrderItemDto orderItemDto2 = new OrderItemDto(orderDto, 2, new ChocolateDto(), 2);
        orderItems.add(orderItemDto1);
        orderItems.add(orderItemDto2);

        when(orderItemService.saveOrderItems(orderItems)).thenReturn(orderItems);
        given(this.orderItemService.saveOrderItems(Mockito.<OrderItemDto>anyList())).willReturn(orderItems);

        // when
        mockMvc.perform(post("/orderItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderItems)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderDto").exists())
                .andExpect(jsonPath("$[0].orderItemID", equalTo(orderItemDto1.getOrderItemID())))
                .andExpect(jsonPath("$[0].chocolateDto").exists())
                .andExpect(jsonPath("$[0].quantity",is(1)))
                .andExpect(jsonPath("$[1].orderDto").exists())
                .andExpect(jsonPath("$[1].orderItemID", equalTo(orderItemDto2.getOrderItemID())))
                .andExpect(jsonPath("$[1].chocolateDto").exists())
                .andExpect(jsonPath("$[1].quantity",is(2)))
                
                ;
    }

    @Test
    public void getAllOrderItemsSuccess() throws Exception {
        List<OrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(new OrderItemDto(new OrderDto(), 1, new ChocolateDto(), 1));
        orderItems.add(new OrderItemDto(new OrderDto(), 2, new ChocolateDto(), 2));

        // Mock the service to return the list of chocolates
        when(orderItemService.findAll()).thenReturn(orderItems);

        // Perform the GET request
        mockMvc.perform(get("/orderItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].orderDto").exists())
                .andExpect(jsonPath("$[0].orderItemID", is(1)))
                .andExpect(jsonPath("$[0].chocolateDto").exists())
                .andExpect(jsonPath("$[0].quantity", is(1)))
                .andExpect(jsonPath("$[0].orderDto").exists())
                .andExpect(jsonPath("$[0].orderItemID", is(1)))
                .andExpect(jsonPath("$[0].chocolateDto").exists())
                .andExpect(jsonPath("$[0].quantity", is(1)));

        // Verify that the service method was called once
        verify(orderItemService, times(1)).findAll();
//        assertTrue(returnedChocos.contains(choco2));

    }
//
//

    @Test
    public void getOrderItemsByOrderIdSuccess() throws Exception {
        List<OrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(new OrderItemDto(new OrderDto(1, new Date(), "s", new CustomerDto()), 1, new ChocolateDto(), 1));
        orderItems.add(new OrderItemDto(new OrderDto(1, new Date(), "s2", new CustomerDto()), 2, new ChocolateDto(), 2));
        when(orderItemService.findByOrderId(orderItemDto.getOrderItemID())).thenReturn(orderItems);
        mockMvc.perform(get("/orderItems/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderItemDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].orderDto.orderID", is(1)))
                .andExpect(jsonPath("$[0].orderDto.orderID").exists())
                .andExpect(jsonPath("$[0].orderDto.status", is("s")))
                .andExpect(jsonPath("$[0].orderDto.customerDto").exists())
                .andExpect(jsonPath("$[1].orderDto.orderID", is(1)))
                .andExpect(jsonPath("$[1].orderDto.orderID").exists())
                .andExpect(jsonPath("$[1].orderDto.status", is("s2")))
                .andExpect(jsonPath("$[1].orderDto.customerDto").exists())
                ;

        // Verify that the service method was called once
//        verify(orderItemService, times(1)).findAll();
//        assertTrue(returnedChocos.contains(choco2));
    }
//
//

//    @Test
//    public void getOrderByIdSuccess() throws Exception {
//        when(orderItemService.findByOrderId(orderItemDto.getOrderItemID())).thenReturn(orderItemDto);
//        mockMvc.perform(get("/order/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(orderItemDto)))
//                // then
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.orderID", equalTo(orderItemDto.getOrderID())))
//                .andExpect(jsonPath("$.date").exists())
//                .andExpect(jsonPath("$.status", equalTo(orderItemDto.getStatus())))
//                .andExpect(jsonPath("$.customerDto").exists());
//    }
////
//
}
