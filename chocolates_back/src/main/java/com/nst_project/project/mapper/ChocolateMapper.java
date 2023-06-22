/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.mapper;

import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.model.Category;
import com.nst_project.project.model.Chocolate;
import org.springframework.stereotype.Component;

/**
 *
 * @author nodas
 */
@Component
public class ChocolateMapper {

    CategoryMapper cm = new CategoryMapper();

    public Chocolate toEntity(ChocolateDto chocolateDto) {
        Category category = cm.toEntity(chocolateDto.getCategoryDto());
        return new Chocolate(chocolateDto.getChocolateID(), chocolateDto.getName(), chocolateDto.getDescription(),chocolateDto.getPictureUrl(),chocolateDto.getDiscount(), chocolateDto.getPrice(), category);
    }

    //entity->dto
    public ChocolateDto toDto(Chocolate chocolate) {
        CategoryDto categoryDto = cm.toDto(chocolate.getCategory());
        return new ChocolateDto(chocolate.getChocolateID(), chocolate.getName(), chocolate.getDescription(), chocolate.getPrice(), chocolate.getPictureUrl(), chocolate.getDiscount(), categoryDto);
    }

}
