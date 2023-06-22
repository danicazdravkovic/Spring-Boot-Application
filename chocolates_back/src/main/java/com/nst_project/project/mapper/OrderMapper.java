/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.mapper;

import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.dtos.OrderDto;
import com.nst_project.project.model.Customer;
import com.nst_project.project.model.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author nodas
 */
@Component
public class OrderMapper {

    CustomerMapper customerMapper = new CustomerMapper();

    public Order toEntity(OrderDto orderDto) {
        Customer customer = customerMapper.toEntity(orderDto.getCustomerDto());

        return new Order(orderDto.getOrderID(), orderDto.getDate(),orderDto.getStatus(), customer);
    }

    //entity->dto
    public OrderDto toDto(Order order) {
        CustomerDto customerDto = customerMapper.toDto(order.getCustomer());
        return new OrderDto(order.getOrderID(), order.getDate(), order.getStatus(),customerDto);
    }

}
