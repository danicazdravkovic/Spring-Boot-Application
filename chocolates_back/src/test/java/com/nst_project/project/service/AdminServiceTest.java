/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst_project.project.dtos.AdminDto;
import com.nst_project.project.dtos.CustomerDto;
import com.nst_project.project.exception.AdminException;
import com.nst_project.project.exception.CustomerException;
import com.nst_project.project.mapper.AdminMapper;
import com.nst_project.project.mapper.CustomerMapper;
import com.nst_project.project.model.Admin;
import com.nst_project.project.model.Customer;
import com.nst_project.project.repository.AdminRepository;
import com.nst_project.project.repository.CustomerRepository;
import com.nst_project.project.service.AdminService;
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
//import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author nodas
 */

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private AdminMapper adminMapper;

   
    @Test
    public void testLoginSuccess() throws CustomerException {
        // mock customer and list of customers
        AdminDto adminDto = new AdminDto(1, "name", "username", "password");
        Admin admin = new Admin(1, "name", "username", "password");
        List<Admin> admins = Collections.singletonList(admin);

        // mock customerRepository
        when(adminRepository.findByUsernameAndPassword("username", "password")).thenReturn(admins);

        // mock customerMapper
        when(adminMapper.toEntity(adminDto)).thenReturn(admin);
        when(adminMapper.toDto(admin)).thenReturn(adminDto);

        // test
        AdminDto result = adminService.login(adminDto);

        // verify
        verify(adminRepository, times(1)).findByUsernameAndPassword("username", "password");
        verify(adminMapper, times(1)).toEntity(adminDto);
        verify(adminMapper, times(1)).toDto(admin);
        assertThat(result, equalTo(adminDto));
    }
    
    @Test
    public void testLoginFail() throws CustomerException {
          // Arrange
        AdminDto adminDto = new AdminDto(1, "name", "username", "password");
        Admin admin = new Admin(1, "name", "username", "password");

        

        when(adminMapper.toEntity(adminDto)).thenReturn(admin);
        when(adminRepository.findByUsernameAndPassword("username", "wrong_password"))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(AdminException.class, () -> adminService.login(adminDto));
    }
    
    
}
