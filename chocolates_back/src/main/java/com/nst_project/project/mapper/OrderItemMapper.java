/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.mapper;

import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.dtos.OrderDto;
import com.nst_project.project.dtos.OrderItemDto;
import com.nst_project.project.model.Chocolate;
import com.nst_project.project.model.Order;
import com.nst_project.project.model.OrderItem;
import org.springframework.stereotype.Component;

/**
 *
 * @author nodas
 */
@Component
public class OrderItemMapper {

    ChocolateMapper chocolateMapper = new ChocolateMapper();
    OrderMapper orderMapper = new OrderMapper();
    //dto->entity

    public OrderItem toEntity(OrderItemDto orderItemDto) {
        Chocolate chocolate = chocolateMapper.toEntity(orderItemDto.getChocolateDto());
        Order order = orderMapper.toEntity(orderItemDto.getOrderDto());

        return new OrderItem(orderItemDto.getOrderItemID(), order, chocolate, orderItemDto.getQuantity());
    }

    //entity->dto
    public OrderItemDto toDto(OrderItem orderItem) {
        ChocolateDto chocolateDto = chocolateMapper.toDto(orderItem.getChocolate());
        OrderDto orderDto = orderMapper.toDto(orderItem.getOrder());

        return new OrderItemDto(orderDto, orderItem.getOrderItemID(), chocolateDto, orderItem.getQuantity());
    }
}
