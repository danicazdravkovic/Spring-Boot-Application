/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nst_project.project.service;

import com.nst_project.project.dtos.OrderItemDto;
import java.util.List;

/**
 *
 * @author nodas
 */
public interface OrderItemService {

    public List<OrderItemDto> saveOrderItems(List<OrderItemDto> orderItemDtos);

    public List<OrderItemDto> findByOrderId(Integer id);

    public List<OrderItemDto> findAll();
    
}
