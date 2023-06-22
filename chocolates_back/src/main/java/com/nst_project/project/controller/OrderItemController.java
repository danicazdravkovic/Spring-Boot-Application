/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.controller;

import com.nst_project.project.dtos.OrderItemDto;
import com.nst_project.project.exception.OrderItemException;
import com.nst_project.project.service.OrderItemService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nodas
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

     @PostMapping("/orderItems")
    public @ResponseBody
    ResponseEntity<Object> add(@RequestBody List<OrderItemDto> orderItemDtos) {
        try {
            List<OrderItemDto> entity = orderItemService.saveOrderItems(orderItemDtos);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (OrderItemException e) {

            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }
    
     @GetMapping("/orderItems/{id}")
    ResponseEntity<Object> getOrderItemsByOrderId(@PathVariable Integer id) {
        try {
            
            List<OrderItemDto> entity = orderItemService.findByOrderId(id);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (OrderItemException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
  @GetMapping("/orderItems")
    List<OrderItemDto> getAllOrderItems() {
        return orderItemService.findAll();
    }

}
