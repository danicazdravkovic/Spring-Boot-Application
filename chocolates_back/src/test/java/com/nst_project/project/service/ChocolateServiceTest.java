/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.exception.ChocolateException;
import com.nst_project.project.mapper.ChocolateMapper;
import com.nst_project.project.mapper.ChocolateMapper;
import com.nst_project.project.model.Category;
import com.nst_project.project.model.Chocolate;
import com.nst_project.project.model.Chocolate;
import com.nst_project.project.repository.ChocolateRepository;
import com.nst_project.project.repository.ChocolateRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author nodas
 */
@SpringBootTest
public class ChocolateServiceTest {

    @Autowired
    private ChocolateService ChocolateService;

    @MockBean
    private ChocolateRepository ChocolateRepository;

    @MockBean
    private ChocolateMapper ChocolateMapper;

    @Test
    public void saveSuccessTest() {

        ChocolateDto ChocolateDto = new ChocolateDto(1, "name", "desc", 0, "url", 0, new CategoryDto());

        // create a Chocolate entity to return from the repository
        Chocolate ChocolateEntity = new Chocolate(1, "name", "desc", "url", 0, 0, new Category());

        // configure the mocks to return the expected values
        when(ChocolateMapper.toEntity(ChocolateDto)).thenReturn(ChocolateEntity);
        when(ChocolateRepository.save(ChocolateEntity)).thenReturn(ChocolateEntity);
        when(ChocolateMapper.toDto(ChocolateEntity)).thenReturn(ChocolateDto);

        ChocolateDto savedCat = ChocolateService.save(ChocolateDto);

        // verify the expected values
        assertEquals(savedCat, ChocolateDto);
        verify(ChocolateMapper).toEntity(ChocolateDto);
        verify(ChocolateRepository).save(ChocolateEntity);
        verify(ChocolateMapper).toDto(ChocolateEntity);
    }

    @Test
    public void findAllSuccessTest() {

        List<Chocolate> categories = new ArrayList<>();
        categories.add(new Chocolate(1, "name", "desc", "url", 0, 0, new Category()));
        categories.add(new Chocolate(2, "name", "desc", "url", 0, 0, new Category()));
        when(ChocolateMapper.toDto(any(Chocolate.class))).thenReturn(new ChocolateDto());
        when(ChocolateRepository.findAll()).thenReturn(categories);

        List<ChocolateDto> ChocolateDtos = ChocolateService.findAll();

        assertNotNull(ChocolateDtos);
        assertEquals(2, ChocolateDtos.size());
        verify(ChocolateMapper, times(2)).toDto(any(Chocolate.class));

    }

    @Test
    public void findbyIdSuccessTest() {

        Chocolate Chocolate = new Chocolate();
        Chocolate.setChocolateID(1);
        when(ChocolateRepository.findById(1)).thenReturn(Optional.of(Chocolate));

        ChocolateDto ChocolateDto = new ChocolateDto();
        ChocolateDto.setChocolateID(1);
        when(ChocolateMapper.toDto(Chocolate)).thenReturn(ChocolateDto);

        // Act
        ChocolateDto result = ChocolateService.findById(1);

        // Assert
        assertEquals(result, ChocolateDto);

    }

    @Test
    public void findbyIdFailTest() {

        // Arrange
        Integer ChocolateId = 1;
        when(ChocolateRepository.findById(ChocolateId)).thenThrow(new ChocolateException("Error finding Chocolate"));

        // Act & Assert
        assertThrows(ChocolateException.class, () -> ChocolateService.findById(ChocolateId));

    }

    @Test
    public void updateChocolateSuccessTest() throws ChocolateException {
        ChocolateDto ChocolateDto = new ChocolateDto(1, "name", "desc", 0,"url", 0, new CategoryDto());

        // create a Chocolate entity to return from the repository
        Chocolate updatedChocolate = new Chocolate();
        updatedChocolate.setChocolateID(1);
        updatedChocolate.setName("cus");
        updatedChocolate.setDescription("cus");

        when(ChocolateRepository.findById(ChocolateDto.getChocolateID())).thenReturn(Optional.of(updatedChocolate));
        when(ChocolateRepository.save(any(Chocolate.class))).thenReturn(updatedChocolate);
        when(ChocolateMapper.toEntity(ChocolateDto)).thenReturn(updatedChocolate);
        when(ChocolateMapper.toDto(updatedChocolate)).thenReturn(ChocolateDto);

        // Act
        ChocolateDto result = ChocolateService.update(ChocolateDto);

        // Assert
        assertEquals(result, ChocolateDto);
    }

    @Test
    public void deleteSuccessTest() throws ChocolateException {
        // Arrange
        Chocolate Chocolate = new Chocolate();
        Chocolate.setChocolateID(1);
        when(ChocolateRepository.findById(1)).thenReturn(Optional.of(Chocolate));

        // Act
        ChocolateService.delete(1);

        // Assert
        verify(ChocolateRepository, times(1)).deleteById(1);
    }

}
