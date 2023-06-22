/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.serviceImpl;

import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.dtos.OrderDto;
import com.nst_project.project.exception.OrderException;
import com.nst_project.project.mapper.OrderMapper;
import com.nst_project.project.model.Order;
import com.nst_project.project.repository.OrderRepository;
import com.nst_project.project.service.OrderService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author nodas
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDto save(OrderDto orderDto) throws OrderException {
        orderDto.setDate(new Date());
        return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDto)));
    }

    @Override
    public List<OrderDto> findAll() {
      List<Order> orders=orderRepository.findAll();
        return orders.stream().map(orderMapper::toDto).collect(Collectors.toList());
       
    }

    @Override
    public OrderDto findById(Integer id) {
   Order o= orderRepository.findById(id).orElseThrow(
                () -> new OrderException("Not found order with id "+id));
        return orderMapper.toDto(o);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.delete(orderMapper.toEntity(findById(id)));
    }

    @Override
    public List<OrderDto> findAll(Integer customerID) {
     List<Order> orders=orderRepository.findByCustomerid(customerID);
        return orders.stream().map(orderMapper::toDto).collect(Collectors.toList());
       
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
         if (orderRepository.findById(orderDto.getOrderID()).isPresent())
//            return chocolateRepository.save(chocolateDto);
                return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDto)));
        throw new OrderException("That order does not exist!");
    }



    
}
