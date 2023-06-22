/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.mapper;

import com.nst_project.project.dtos.AdminDto;
import com.nst_project.project.model.Admin;
import org.springframework.stereotype.Component;

/**
 *
 * @author nodas
 */
@Component
public class AdminMapper {
    public Admin toEntity(AdminDto adminDto) {
        return new Admin(adminDto.getAdminID(), adminDto.getName(), adminDto.getUsername(), adminDto.getPassword());
    }

    //entity->dto
    public AdminDto toDto(Admin admin) {
        return new AdminDto(admin.getAdminID(), admin.getName(), admin.getUsername(), admin.getPassword());
    }
}
