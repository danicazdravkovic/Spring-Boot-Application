/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nst_project.project.service;

import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.exception.CustomerException;
import java.util.List;

/**
 *
 * @author nodas
 */
public interface CustomerService {

    public CustomerDto save(CustomerDto customerDto);

    public List<CustomerDto> findAll();

    public CustomerDto findById(Integer id) throws CustomerException;

    public CustomerDto login(CustomerDto customerDto) throws CustomerException;

    public CustomerDto update(CustomerDto customerDto) throws CustomerException;

    public void delete(Integer id)throws CustomerException;
    
}
