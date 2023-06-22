/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.mapper;

import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.model.Customer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

/**
 *
 * @author nodas
 */
//@EnableJpaRepositories
@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDto customerDto) {
        return new Customer(customerDto.getCustomerID(), customerDto.getName(), customerDto.getUsername(), customerDto.getPassword());
    }

    //entity->dto
    public CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.getCustomerID(), customer.getName(), customer.getUsername(), customer.getPassword());
    }

}
