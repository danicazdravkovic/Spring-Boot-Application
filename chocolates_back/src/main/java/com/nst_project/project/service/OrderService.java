/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.service;

import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.dtos.OrderDto;
import com.nst_project.project.exception.OrderException;
import java.util.List;

/**
 *
 * @author nodas
 */
public interface OrderService {

    public OrderDto save(OrderDto orderDto) throws OrderException;

    public List<OrderDto> findAll();

    public OrderDto findById(Integer id);

    public void delete(Integer id);

    public List<OrderDto> findAll(Integer customerID);

    public OrderDto update(OrderDto orderDto)throws OrderException;

    
}
