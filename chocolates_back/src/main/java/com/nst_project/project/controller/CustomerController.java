/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.controller;

import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.exception.CustomerException;
import com.nst_project.project.service.CustomerService;
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
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public @ResponseBody
    ResponseEntity<Object> add(@RequestBody CustomerDto customerDto) {
        try {
            CustomerDto entity = customerService.save(customerDto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/customers")
    List<CustomerDto> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/customer/{id}")
    ResponseEntity<Object> getCustomerById(@PathVariable Integer id) {
        try {
            CustomerDto entity = customerService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/customer/login")
    ResponseEntity<Object> customerLogin(@RequestBody CustomerDto customerDto) {
        try {
            CustomerDto entity = customerService.login(customerDto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/customer")
    public @ResponseBody
    ResponseEntity<Object> update(@RequestBody CustomerDto customerDto) {
        try {
            CustomerDto entity = customerService.update(customerDto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("customer/{id}")
    public @ResponseBody
    ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            customerService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Customer's account has been deleted");
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
