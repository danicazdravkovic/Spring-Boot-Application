/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.dtos;

import java.util.Objects;

/**
 *
 * @author nodas
 */
public class CustomerDto {

    private Integer customerID;
    private String name;
    private String username;
    private String password;

    public CustomerDto() {
    }

    public CustomerDto(Integer customerID, String name, String username, String password) {
        this.customerID = customerID;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID, name, username, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(customerID, that.customerID) && Objects.equals(name, that.name) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public String toString() {
        return "CategoryDto{"
                + "customerID=" + customerID
                + ", name='" + name + '\''
                + ", username='" + username + '\''
                + ", password='" + password + '\''
                + '}';
    }

}
