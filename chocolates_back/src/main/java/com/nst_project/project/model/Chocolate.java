/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author nodas
 */
@Entity
public class Chocolate {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chocolateID;
    private String name;
    private String description;
    @Column(length = 1500)
    private String pictureUrl;
    private double discount; 
    private double price;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="categoryID")
    private Category category;

    public Chocolate() {
    }

    public Chocolate(Integer chocolateID, String name, String description, String pictureUrl, double discount, double price, Category category) {
        this.chocolateID = chocolateID;
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.discount = discount;
        this.price = price;
        this.category = category;
    }

    public Integer getChocolateID() {
        return chocolateID;
    }

    public void setChocolateID(Integer chocolateID) {
        this.chocolateID = chocolateID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

  

    
 

 
}
