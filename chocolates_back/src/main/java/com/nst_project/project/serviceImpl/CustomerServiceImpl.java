/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.serviceImpl;

import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.exception.CustomerException;
import com.nst_project.project.mapper.CustomerMapper;
import com.nst_project.project.model.Customer;
import com.nst_project.project.repository.CustomerRepository;
import com.nst_project.project.service.CustomerService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author nodas
 */
@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        return customerMapper.toDto(customerRepository.save(customerMapper.toEntity(customerDto)));
    }

    @Override
    public List<CustomerDto> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream().map(customerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CustomerDto findById(Integer id) throws CustomerException {
        Customer c = customerRepository.findById(id).orElseThrow(
                () -> new CustomerException("Not found customer with id " + id));
        return customerMapper.toDto(c);
    }

    @Override
    public CustomerDto login(CustomerDto customerDto) throws CustomerException {
        Customer c=customerMapper.toEntity(customerDto);
        List<Customer> customers = customerRepository.findByUsernameAndPassword(c.getUsername(),c.getPassword());
        if(!customers.isEmpty()) 
            return customerMapper.toDto(customers.get(0));       
        throw new CustomerException("Incorrect username or password.");
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) throws CustomerException {
         if (customerRepository.findById(customerDto.getCustomerID()).isPresent())
//            return chocolateRepository.save(chocolateDto);
                return customerMapper.toDto(customerRepository.save(customerMapper.toEntity(customerDto)));
        throw new CustomerException("That chocolate does not exist!");
   
    }

    @Override
    public void delete(Integer id) throws CustomerException {
           try{
        customerRepository.deleteById(id);
        }catch (Exception e){
            throw new CustomerException("Chocolate can't been deleted because some orders contain this chocolate");
        }
    }

}
