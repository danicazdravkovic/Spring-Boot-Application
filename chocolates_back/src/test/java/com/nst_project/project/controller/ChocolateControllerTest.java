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
import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.exception.CategoryException;
import com.nst_project.project.exception.ChocolateException;
import com.nst_project.project.exception.CustomerException;
import com.nst_project.project.service.CategoryService;
import com.nst_project.project.service.ChocolateService;
import com.nst_project.project.service.CustomerService;
import java.util.ArrayList;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
@ContextConfiguration(classes = ChocolateController.class)
@WebMvcTest(ChocolateController.class)

public class ChocolateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChocolateService chocolateService;

//    @MockBean
//    private CategoryService categoryService;
    @Autowired
    private ObjectMapper objectMapper;

    private ChocolateDto chocolateDto;

    @BeforeEach
    public void setUp() {
        CategoryDto categoryDto = new CategoryDto(1, "category 1", "category 1 desc");
        chocolateDto = new ChocolateDto(1, "chocolate", "description", 11, "url", 10, categoryDto);
    }

    @Test
    public void addSuccess() throws Exception {
        given(this.chocolateService.save(any(ChocolateDto.class))).willReturn(chocolateDto);

//        when(chocolateService.save(chocolateDto)).thenReturn(chocolateDto);

        // when and then
        mockMvc.perform(post("/chocolate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chocolateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chocolateID", equalTo(chocolateDto.getChocolateID())))
                .andExpect(jsonPath("$.name", equalTo(chocolateDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(chocolateDto.getDescription())))
                .andExpect(jsonPath("$.price", equalTo(chocolateDto.getPrice())))
                .andExpect(jsonPath("$.pictureUrl", equalTo(chocolateDto.getPictureUrl())))
                .andExpect(jsonPath("$.discount", equalTo(chocolateDto.getDiscount())))
                .andExpect(jsonPath("$.categoryDto.categoryID", equalTo(chocolateDto.getCategoryDto().getCategoryID())))
                .andExpect(jsonPath("$.categoryDto.name", equalTo(chocolateDto.getCategoryDto().getName())))
                .andExpect(jsonPath("$.categoryDto.description", equalTo(chocolateDto.getCategoryDto().getDescription())))
                ;

    }

    @Test
    public void getAllChocolatesSuccess() throws Exception {
//        CategoryDto categoryDto1 = new CategoryDto(1, "Category1", "category1");
//        CategoryDto categoryDto2 = new CategoryDto(2, "Category2", "category2");
//        ChocolateDto choco1=new ChocolateDto(1, "n1", "d1", 0, "url1", 0, categoryDto1);
//        ChocolateDto choco2=new ChocolateDto(1, "n2", "d2", 0, "url2", 0, categoryDto2);
//
//        List<ChocolateDto> chocolates = Arrays.asList(choco1, choco2);
//
//        when(chocolateService.findAll()).thenReturn(chocolates);
//
//        MvcResult result = mockMvc.perform(get("/chocolates")).andExpect(status().isOk()).andReturn();//vracamo sve category
//        List<ChocolateDto> returnedChocos = objectMapper.readValue(result.getResponse().getContentAsString(),
//                new TypeReference<List<ChocolateDto>>() {
//        });
//
//        assertTrue(returnedChocos.contains(choco1));
// Create a list of ChocolateDto objects to return from the service
        List<ChocolateDto> chocolates = new ArrayList<>();
        chocolates.add(new ChocolateDto(1, "Milk chocolate", "Smooth and creamy milk chocolate", 1.99, "https://example.com/milk_chocolate.jpg", 0.2, new CategoryDto()));
        chocolates.add(new ChocolateDto(2, "Dark chocolate", "Rich and intense dark chocolate", 2.49, "https://example.com/dark_chocolate.jpg", 0.1, new CategoryDto()));

        // Mock the service to return the list of chocolates
        when(chocolateService.findAll()).thenReturn(chocolates);

        // Perform the GET request
        mockMvc.perform(get("/chocolates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].chocolateID", is(1)))
                .andExpect(jsonPath("$[0].name", is("Milk chocolate")))
                .andExpect(jsonPath("$[0].description", is("Smooth and creamy milk chocolate")))
                .andExpect(jsonPath("$[0].price", is(1.99)))
                .andExpect(jsonPath("$[0].pictureUrl", is("https://example.com/milk_chocolate.jpg")))
                .andExpect(jsonPath("$[0].discount", is(0.2)))
                .andExpect(jsonPath("$[0].categoryDto").exists())
                .andExpect(jsonPath("$[1].chocolateID", is(2)))
                .andExpect(jsonPath("$[1].name", is("Dark chocolate")))
                .andExpect(jsonPath("$[1].description", is("Rich and intense dark chocolate")))
                .andExpect(jsonPath("$[1].price", is(2.49)))
                .andExpect(jsonPath("$[1].pictureUrl", is("https://example.com/dark_chocolate.jpg")))
                .andExpect(jsonPath("$[1].discount", is(0.1)))
                .andExpect(jsonPath("$[1].categoryDto").exists());

        // Verify that the service method was called once
        verify(chocolateService, times(1)).findAll();
//        assertTrue(returnedChocos.contains(choco2));

    }
//

    @Test
    public void getChocolateByIdFail() throws Exception {
        when(chocolateService.findById(chocolateDto.getChocolateID())).thenThrow(ChocolateException.class);
        mockMvc.perform(get("/chocolate/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chocolateDto.getChocolateID()))).andExpect(status().isBadRequest());
    }
//

    @Test
    public void getChocolateByIdSuccess() throws Exception {
        when(chocolateService.findById(chocolateDto.getChocolateID())).thenReturn(chocolateDto);
        mockMvc.perform(get("/chocolate/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chocolateDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.chocolateID", equalTo(chocolateDto.getChocolateID())))
                .andExpect(jsonPath("$.name", equalTo(chocolateDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(chocolateDto.getDescription())))
                .andExpect(jsonPath("$.price", equalTo(chocolateDto.getPrice())))
                .andExpect(jsonPath("$.pictureUrl", equalTo(chocolateDto.getPictureUrl())))
                .andExpect(jsonPath("$.discount", equalTo(chocolateDto.getDiscount())))
                .andExpect(jsonPath("$.categoryDto").exists());
    }
//

    @Test
    public void updateFail() throws Exception {
//        ChocolateDto ch=new ChocolateDto(1, "n", "d", 0, "u", 0, new CategoryDto());
        when(chocolateService.update(any(ChocolateDto.class))).thenThrow(ChocolateException.class);

//        when(chocolateService.update(chocolateDto)).thenThrow(ChocolateException.class);
        mockMvc.perform(put("/chocolate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chocolateDto))).andExpect(status().isNotFound())
                ;
    }
//
//

    @Test
    public void updateSuccess() throws Exception {
//        when(chocolateService.update(chocolateDto)).thenReturn(chocolateDto);
        given(this.chocolateService.update(any(ChocolateDto.class))).willReturn(chocolateDto);

        mockMvc.perform(put("/chocolate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chocolateDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chocolateID", equalTo(chocolateDto.getChocolateID())))
                .andExpect(jsonPath("$.name", equalTo(chocolateDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(chocolateDto.getDescription())))
                .andExpect(jsonPath("$.price", equalTo(chocolateDto.getPrice())))
                .andExpect(jsonPath("$.pictureUrl", equalTo(chocolateDto.getPictureUrl())))
                .andExpect(jsonPath("$.discount", equalTo(chocolateDto.getDiscount())))
                .andExpect(jsonPath("$.categoryDto").exists());
    }

    @Test
    public void deleteFail() throws Exception {
        doThrow(new ChocolateException("Chocolate can't been deleted")).when(chocolateService).delete(chocolateDto.getChocolateID());

        mockMvc.perform(delete("/chocolate/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chocolateDto)))
                .andExpect(status().isBadRequest());
    }
//
//
    @Test
    public void deleteSuccess() throws Exception {

        //u service impl delete ne vraca nista pa treba doNothing
        doNothing().when(chocolateService).delete(chocolateDto.getChocolateID());
        mockMvc.perform(delete("/chocolate/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chocolateDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string("Chocolate has been deleted"));

    }
}
