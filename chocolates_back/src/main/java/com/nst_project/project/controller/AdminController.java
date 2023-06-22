/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.controller;

import com.nst_project.project.dtos.AdminDto;
import com.nst_project.project.exception.AdminException;
import com.nst_project.project.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nodas
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admin/login")
    ResponseEntity<Object> adminLogin(@RequestBody AdminDto adminDto) {
        try {
            AdminDto entity = adminService.login(adminDto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (AdminException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
