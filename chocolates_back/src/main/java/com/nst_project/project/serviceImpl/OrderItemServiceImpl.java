/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.serviceImpl;

import com.nst_project.project.dtos.OrderItemDto;
import com.nst_project.project.mapper.OrderItemMapper;
import com.nst_project.project.model.OrderItem;
import com.nst_project.project.repository.OrderItemRepository;

import java.util.ArrayList;
import java.util.List;
import com.nst_project.project.service.OrderItemService;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author nodas
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public List<OrderItemDto> findByOrderId(Integer id) {
         List<OrderItem> orderItems=orderItemRepository.findByOrderId(id);
        return orderItems.stream().map(orderItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderItemDto> saveOrderItems(List<OrderItemDto> orderItemDtos) {
        List<OrderItemDto> newList = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtos) {
            OrderItemDto oi = orderItemMapper.toDto(orderItemRepository.save(orderItemMapper.toEntity(orderItemDto)));
            newList.add(oi);
        }
        return newList;

    }

    @Override
    public List<OrderItemDto> findAll() {
          List<OrderItem> orderItems=orderItemRepository.findAll();
        return orderItems.stream().map(orderItemMapper::toDto).collect(Collectors.toList());
       
    }

}
