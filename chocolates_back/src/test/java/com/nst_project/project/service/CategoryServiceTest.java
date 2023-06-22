/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.exception.CategoryException;
import com.nst_project.project.mapper.CategoryMapper;
import com.nst_project.project.mapper.CategoryMapper;
import com.nst_project.project.model.Category;
import com.nst_project.project.model.Category;
import com.nst_project.project.repository.CategoryRepository;
import com.nst_project.project.repository.CategoryRepository;
import com.nst_project.project.service.CategoryService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author nodas
 */
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private CategoryMapper categoryMapper;

    @Test
    public void saveSuccessTest() {

        CategoryDto categoryDto = new CategoryDto(1, "name", "desc");

        // create a Category entity to return from the repository
        Category categoryEntity = new Category(1, "name", "desc");

        // configure the mocks to return the expected values
        when(categoryMapper.toEntity(categoryDto)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryMapper.toDto(categoryEntity)).thenReturn(categoryDto);

        CategoryDto savedCat = categoryService.save(categoryDto);

        // verify the expected values
        assertEquals(savedCat, categoryDto);
        verify(categoryMapper).toEntity(categoryDto);
        verify(categoryRepository).save(categoryEntity);
        verify(categoryMapper).toDto(categoryEntity);
    }

    @Test
    public void findAllSuccessTest() {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "c1", "c1"));
        categories.add(new Category(1, "c2", "c2"));
        when(categoryMapper.toDto(any(Category.class))).thenReturn(new CategoryDto());
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDto> CategoryDtos = categoryService.findAll();

        assertNotNull(CategoryDtos);
        assertEquals(2, CategoryDtos.size());
        verify(categoryMapper, times(2)).toDto(any(Category.class));

    }

    @Test
    public void findbyIdSuccessTest() {

        Category Category = new Category();
        Category.setCategoryID(1);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(Category));

        CategoryDto CategoryDto = new CategoryDto();
        CategoryDto.setCategoryID(1);
        when(categoryMapper.toDto(Category)).thenReturn(CategoryDto);

        // Act
        CategoryDto result = categoryService.findById(1);

        // Assert
        assertEquals(result, CategoryDto);

    }

    @Test
    public void findbyIdFailTest() {

        // Arrange
        Integer CategoryId = 1;
        when(categoryRepository.findById(CategoryId)).thenThrow(new CategoryException("Error finding Category"));

        // Act & Assert
        assertThrows(CategoryException.class, () -> categoryService.findById(CategoryId));

    }

    @Test
    public void updateCategorySuccessTest() throws CategoryException {
        CategoryDto CategoryDto = new CategoryDto(1, "name", "desc");

        // create a Category entity to return from the repository
        Category updatedCategory = new Category();
        updatedCategory.setCategoryID(1);
        updatedCategory.setName("cus");
        updatedCategory.setDescription("cus");

        when(categoryRepository.findById(CategoryDto.getCategoryID())).thenReturn(Optional.of(updatedCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);
        when(categoryMapper.toEntity(CategoryDto)).thenReturn(updatedCategory);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(CategoryDto);

        // Act
        CategoryDto result = categoryService.update(CategoryDto);

        // Assert
        assertEquals(result, CategoryDto);
    }

    @Test
    public void deleteSuccessTest() throws CategoryException {
        // Arrange
        Category Category = new Category();
        Category.setCategoryID(1);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(Category));

        // Act
        categoryService.delete(1);

        // Assert
        verify(categoryRepository, times(1)).deleteById(1);
    }

    

}
