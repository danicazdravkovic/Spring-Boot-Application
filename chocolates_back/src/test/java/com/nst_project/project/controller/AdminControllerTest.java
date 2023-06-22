/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst_project.project.controller.AdminController;
import com.nst_project.project.dtos.AdminDto;
import com.nst_project.project.exception.AdminException;
import com.nst_project.project.model.Admin;
import com.nst_project.project.service.AdminService;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *
 * @author nodas
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AdminController.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    private AdminDto adminDto;

    @BeforeEach
    public void setUp() {
        adminDto = new AdminDto();
        adminDto.setAdminID(1);
        adminDto.setName("Admin 1");
        adminDto.setUsername("admin 1");
        adminDto.setPassword("admin 1");
    }

    @Test
    public void adminLoginFail() throws Exception {
        //bilo koja instanca AdminDto-a
        when(adminService.login(any(AdminDto.class))).thenThrow(AdminException.class);

        mockMvc.perform(post("/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void adminLoginSuccess() throws Exception {
//        https://stackoverflow.com/questions/72098049/junit-restcontrollertest-for-putmapping-mockhttpservletresponse-body-is-null
//        BDDMockito.given(adminService.login(adminDto));
//BDDMockito.given(employeeService.updateEmployee(eq(employeeId), any()))
        given(this.adminService.login(any(AdminDto.class))).willReturn(adminDto);

//        when(adminService.login(adminDto)).thenReturn(adminDto);

        mockMvc.perform(post("/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.adminID", equalTo(adminDto.getAdminID())))
                .andExpect(jsonPath("$.name", equalTo(adminDto.getName())))
                .andExpect(jsonPath("$.username", equalTo(adminDto.getUsername())))
                .andExpect(jsonPath("$.password", equalTo(adminDto.getPassword())));
    }
}
