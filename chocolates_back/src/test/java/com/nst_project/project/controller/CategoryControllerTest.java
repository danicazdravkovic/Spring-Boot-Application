/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst_project.project.controller.CustomerController;
import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.exception.CategoryException;
import com.nst_project.project.exception.CustomerException;
import com.nst_project.project.service.CategoryService;
import com.nst_project.project.service.CustomerService;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.doNothing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author nodas
 */
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CategoryController.class)
@WebMvcTest(CategoryController.class)

public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDto categoryDto;

    @BeforeEach
    public void setUp() {
        categoryDto = new CategoryDto();
        categoryDto.setCategoryID(1);
        categoryDto.setName("Category 1");
        categoryDto.setDescription("Category 1");
    }

    @Test
    public void addSuccess() throws Exception {

        // kada se pozove fja save vraca se customerDto koji se uneo
        when(categoryService.save(categoryDto)).thenReturn(categoryDto);

        // when
        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", equalTo(categoryDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(categoryDto.getDescription())))
               ;
    }

    @Test
    public void getAllCategoriesSuccess() throws Exception {
        CategoryDto categoryDto1 = new CategoryDto(1, "Category1", "category1");
                CategoryDto categoryDto2 = new CategoryDto(2, "Category2", "category2");
        List<CategoryDto> categories = Arrays.asList(categoryDto1  , categoryDto2);

        when(categoryService.findAll()).thenReturn(categories);

        MvcResult result = mockMvc.perform(get("/categories")).andExpect(status().isOk()).andReturn();//vracamo sve category
        List<CategoryDto> returnedCategories = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<CategoryDto>>() {
        });

        assertTrue(returnedCategories.contains(categoryDto1));
        assertTrue(returnedCategories.contains(categoryDto2));

    }

    @Test
    public void getCategoryByIdFail() throws Exception {
        when(categoryService.findById(categoryDto.getCategoryID())).thenThrow(CategoryException.class);
        mockMvc.perform(get("/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto.getCategoryID()))).andExpect(status().isBadRequest());
    }

    @Test
    public void getCategoryByIdSuccess() throws Exception {
        when(categoryService.findById(categoryDto.getCategoryID())).thenReturn(categoryDto);
        mockMvc.perform(get("/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.categoryID", equalTo(categoryDto.getCategoryID())))
                .andExpect(jsonPath("$.name", equalTo(categoryDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(categoryDto.getDescription())));
    }

    
    @Test
    public void updateFail() throws Exception {
        when(categoryService.update(categoryDto)).thenThrow(CategoryException.class);
        mockMvc.perform(put("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto))).andExpect(status().isNotFound());
    }
//
    @Test
    public void updateSuccess() throws Exception {
        when(categoryService.update(categoryDto)).thenReturn(categoryDto);

        mockMvc.perform(put("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.categoryID", equalTo(categoryDto.getCategoryID())))
                .andExpect(jsonPath("$.name", equalTo(categoryDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(categoryDto.getDescription())));
    }

    @Test
    public void deleteFail() throws Exception {
        doThrow(new CategoryException("Category can't been deleted")).when(categoryService).delete(categoryDto.getCategoryID());
        
        mockMvc.perform(delete("/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isBadRequest());
    }
//
    @Test
    public void deleteSuccess() throws Exception {
       
        //u service impl delete ne vraca nista pa treba doNothing
        doNothing().when(categoryService).delete(categoryDto.getCategoryID());
        mockMvc.perform(delete("/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string("Chocolate has been deleted"));

    }
}
