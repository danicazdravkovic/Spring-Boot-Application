/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.dtos;

import com.nst_project.project.model.Chocolate;
import com.nst_project.project.model.Order;

/**
 *
 * @author nodas
 */
public class OrderItemDto {

    private OrderDto orderDto;
    private Integer orderItemID;
    private ChocolateDto chocolateDto;
    private Integer quantity;

    public OrderItemDto() {
    }

    public OrderItemDto(OrderDto order, Integer orderItemID, ChocolateDto chocolate, Integer quantity) {
        this.orderDto = order;
        this.orderItemID = orderItemID;
        this.chocolateDto = chocolate;
        this.quantity = quantity;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public Integer getOrderItemID() {
        return orderItemID;
    }

    public void setOrderItemID(Integer orderItemID) {
        this.orderItemID = orderItemID;
    }

    public ChocolateDto getChocolateDto() {
        return chocolateDto;
    }

    public void setChocolateDto(ChocolateDto chocolateDto) {
        this.chocolateDto = chocolateDto;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    
}
