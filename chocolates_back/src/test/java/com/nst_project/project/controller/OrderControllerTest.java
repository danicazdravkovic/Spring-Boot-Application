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
import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.dtos.OrderDto;
import com.nst_project.project.exception.CategoryException;
import com.nst_project.project.exception.CustomerException;
import com.nst_project.project.exception.OrderException;
import com.nst_project.project.service.CategoryService;
import com.nst_project.project.service.CustomerService;
import com.nst_project.project.service.OrderService;
import java.util.ArrayList;
import java.util.Date;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
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
@ContextConfiguration(classes = OrderController.class)
@WebMvcTest(OrderController.class)

public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDto orderDto;
    private Date date;

    @BeforeEach
    public void setUp() {
        date = new Date();
        orderDto = new OrderDto(1,date, "status", new CustomerDto());
    }

    @Test
    public void addSuccess() throws Exception {

        // kada se pozove fja save vraca se customerDto koji se uneo
        given(this.orderService.save(any(OrderDto.class))).willReturn(orderDto);

//        when(orderService.save(orderDto)).thenReturn(orderDto);
        // when
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID", equalTo(orderDto.getOrderID())))
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.status", equalTo(orderDto.getStatus())))
                .andExpect(jsonPath("$.customerDto").exists())
               ;
    }

    @Test
    public void getAllOordersSuccess() throws Exception {
        List<OrderDto> orders = new ArrayList<>();
        orders.add(new OrderDto(1, date, "s", new CustomerDto()));
        orders.add(new OrderDto(2, date, "s2", new CustomerDto()));

        // Mock the service to return the list of chocolates
        when(orderService.findAll()).thenReturn(orders);

        // Perform the GET request
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].orderID", is(1)))
                .andExpect(jsonPath("$[0].date").exists())
                .andExpect(jsonPath("$[0].status", is("s")))
                .andExpect(jsonPath("$[0].customerDto").exists())
                .andExpect(jsonPath("$[1].orderID", is(2)))
                .andExpect(jsonPath("$[1].date").exists())
                .andExpect(jsonPath("$[1].status", is("s2")))
                .andExpect(jsonPath("$[1].customerDto").exists());

        // Verify that the service method was called once
        verify(orderService, times(1)).findAll();
//        assertTrue(returnedChocos.contains(choco2));

    }
//
//

    @Test
    public void getOrderByIdFail() throws Exception {
        when(orderService.findById(orderDto.getOrderID())).thenThrow(OrderException.class);
        mockMvc.perform(get("/order/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto.getOrderID()))).andExpect(status().isBadRequest());
    }
//
//

    @Test
    public void getOrderByIdSuccess() throws Exception {
        when(orderService.findById(orderDto.getOrderID())).thenReturn(orderDto);
        mockMvc.perform(get("/order/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.orderID", equalTo(orderDto.getOrderID())))
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.status", equalTo(orderDto.getStatus())))
                .andExpect(jsonPath("$.customerDto").exists());
    }
////
//

    @Test
    public void updateFail() throws Exception {
//        ChocolateDto ch=new ChocolateDto(1, "n", "d", 0, "u", 0, new CategoryDto());
//        when(orderService.update(orderDto)).thenThrow(OrderException.class);
        when(orderService.update(any(OrderDto.class))).thenThrow(OrderException.class);

        mockMvc.perform(put("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto))).andExpect(status().isNotFound())
              ;
    }
////
////
//

    @Test
    public void updateSuccess() throws Exception {
//        when(orderService.update(orderDto)).thenReturn(orderDto);
        given(this.orderService.update(any(OrderDto.class))).willReturn(orderDto);

        mockMvc.perform(put("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID", equalTo(orderDto.getOrderID())))
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.status", equalTo(orderDto.getStatus())))
                .andExpect(jsonPath("$.customerDto").exists());
    }
//

    @Test
    public void deleteSuccess() throws Exception {

        //u service impl delete ne vraca nista pa treba doNothing
        doNothing().when(orderService).delete(orderDto.getOrderID());
        mockMvc.perform(delete("/order/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

    }
}
