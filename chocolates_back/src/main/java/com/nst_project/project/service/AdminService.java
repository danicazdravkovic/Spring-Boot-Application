/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nst_project.project.service;

import com.nst_project.project.dtos.AdminDto;
import com.nst_project.project.exception.AdminException;

/**
 *
 * @author nodas
 */
public interface AdminService {

    public AdminDto login(AdminDto adminDto) throws AdminException;
    
}
