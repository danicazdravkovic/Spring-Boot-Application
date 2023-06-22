/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nst_project.project.repository;

import com.nst_project.project.model.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nodas
 */
//@EnableJpaRepositories
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

//    public List<Customer> findByUsername(String username);

//    public List<Customer> findByUsername();
    public List<Customer> findByUsernameAndPassword(String username, String password);
    
}
