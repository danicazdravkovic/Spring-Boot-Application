/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.dtos;

import java.util.Date;

/**
 *
 * @author nodas
 */
public class OrderDto {
    private Integer orderID;
    private Date date;
    private String status;
    private CustomerDto customerDto;

    public OrderDto() {
    }

    public OrderDto(Integer orderID, Date date, String status, CustomerDto customerDto) {
        this.orderID = orderID;
        this.date = date;
        this.status = status;
        this.customerDto = customerDto;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

  
  
    
}
