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
public class ChocolateDto {

    private Integer chocolateID;
    private String name;
    private String description;
    private double price;
    private String pictureUrl;
    private double discount;
    private CategoryDto categoryDto;

    public ChocolateDto() {
    }

    public ChocolateDto(Integer chocolateID, String name, String description, double price, String pictureUrl, double discount, CategoryDto categoryDto) {
        this.chocolateID = chocolateID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.discount = discount;
        this.categoryDto = categoryDto;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

   
}
