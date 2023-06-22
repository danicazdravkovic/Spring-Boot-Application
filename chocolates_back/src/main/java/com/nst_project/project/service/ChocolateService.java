/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.service;

import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.exception.ChocolateException;
import com.nst_project.project.model.Chocolate;
import java.util.List;

/**
 *
 * @author nodas
 */
public interface ChocolateService {

     ChocolateDto save(ChocolateDto chocolateDto) throws ChocolateException;
//    List<ChocolateDto> findAllByOrederByName();

    public List<ChocolateDto> findAll();

    public ChocolateDto findById(Integer id) throws ChocolateException;

    public ChocolateDto update(ChocolateDto chocolateDto) throws ChocolateException;

    public void delete(Integer id) throws ChocolateException;
}
