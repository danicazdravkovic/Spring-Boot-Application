/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.controller;

import com.nst_project.project.dtos.OrderDto;
import com.nst_project.project.exception.OrderException;
import com.nst_project.project.service.OrderService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nodas
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public @ResponseBody
    ResponseEntity<Object> add(@RequestBody OrderDto orderDto) {
        try {
            OrderDto entity = orderService.save(orderDto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (OrderException e) {

            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/orders")
    List<OrderDto> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/order/{id}")
    ResponseEntity<Object> getOrderById(@PathVariable Integer id) {
        try {
            OrderDto entity = orderService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (OrderException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //orders for specific customer
    @GetMapping("/orders/{id}")
    List<OrderDto> getOrdersByCustomerId(@PathVariable Integer id) {
        return orderService.findAll(id);
    }
  

    @DeleteMapping("/order/{id}")
    @ResponseBody
    ResponseEntity<String> deleteOrder(@PathVariable Integer id) {
        orderService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @PutMapping("/order")
    public @ResponseBody
    ResponseEntity<Object> update(@RequestBody OrderDto orderDto) {
        try {
            OrderDto entity = orderService.update(orderDto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (OrderException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
