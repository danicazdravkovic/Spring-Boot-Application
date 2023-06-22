/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nst_project.project.service;

import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.exception.CategoryException;
import com.nst_project.project.model.Category;
import java.util.List;

/**
 *
 * @author nodas
 */
public interface CategoryService {

    public CategoryDto save(CategoryDto categoryDto) throws CategoryException;

    public List<CategoryDto> findAll();

    public CategoryDto findById(Integer id) throws CategoryException;

    public CategoryDto update(CategoryDto categoryDto)throws CategoryException;

    public void delete(Integer id)throws CategoryException;
    
}
