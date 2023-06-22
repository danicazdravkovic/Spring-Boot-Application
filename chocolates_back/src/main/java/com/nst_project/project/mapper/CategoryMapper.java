/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.mapper;

import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.model.Category;
import org.springframework.stereotype.Component;

/**
 *
 * @author nodas
 */
@Component
public class CategoryMapper {
    
    //dto->entity
    public Category toEntity(CategoryDto categoryDto){
        return new Category(categoryDto.getCategoryID(), categoryDto.getName(), categoryDto.getDescription());
    }
    
    //entity->dto
    
    public CategoryDto toDto(Category category){
        return new CategoryDto(category.getCategoryID(), category.getName(), category.getDescription());
    }
}
