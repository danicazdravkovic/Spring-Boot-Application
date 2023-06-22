/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.serviceImpl;

import com.nst_project.project.dtos.AdminDto;
import com.nst_project.project.exception.AdminException;
import com.nst_project.project.mapper.AdminMapper;
import com.nst_project.project.model.Admin;
import com.nst_project.project.repository.AdminRepository;
import com.nst_project.project.service.AdminService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author nodas
 */
@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    public AdminDto login(AdminDto adminDto) throws AdminException { 
        Admin a=adminMapper.toEntity(adminDto);
        List<Admin> admins = adminRepository.findByUsernameAndPassword(a.getUsername(),a.getPassword());
        if(!admins.isEmpty()) 
            return adminMapper.toDto(admins.get(0));       
        throw new AdminException("Incorrect username or password.");
   
    }

}
