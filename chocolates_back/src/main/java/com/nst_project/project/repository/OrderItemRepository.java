/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.repository;

import com.nst_project.project.model.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nodas
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer>{
    @Query(value = "SELECT * FROM ORDER_ITEM o WHERE o.orderid= :orderID", nativeQuery = true)
    public List<OrderItem> findByOrderId(Integer orderID);
}
