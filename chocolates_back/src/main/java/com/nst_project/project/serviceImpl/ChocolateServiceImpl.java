/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.serviceImpl;

import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.exception.ChocolateException;
import com.nst_project.project.mapper.ChocolateMapper;
import com.nst_project.project.model.Chocolate;
import com.nst_project.project.repository.ChocolateRepository;
import com.nst_project.project.service.ChocolateService;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author nodas
 */
@Service
public class ChocolateServiceImpl implements ChocolateService {

    private final ChocolateRepository chocolateRepository;
    private final ChocolateMapper chocolateMapper;

    public ChocolateServiceImpl(ChocolateRepository chocolateRepository, ChocolateMapper chocolateMapper) {
        this.chocolateRepository = chocolateRepository;
        this.chocolateMapper = chocolateMapper;
    }
//
//    public ChocolateServiceImpl() {
//        this.chocolateRepository = null;
//        this.chocolateMapper = null;
//    }

//    @Override
//    public List<ChocolateDto> findAllByOrederByName() {
//        List<ChocolateDto> chocolates=chocolateRepository.findAllByOrederByName();
//        return chocolates.stream().map(chocolateMapper::toDto).collect(Collectors.toList());
//    }
//    
    @Override
    public ChocolateDto save(ChocolateDto chocolateDto) throws ChocolateException {
//        if(chocolateRepository.findById(chocolateDto.getChocolateID()).isPresent()){
//            throw new ChocolateException("That chocolate already exists");
//        }
//CategoryDto category=chocolateDto.getCategoryDto();
        return chocolateMapper.toDto(chocolateRepository.save(chocolateMapper.toEntity(chocolateDto)));
    }

    @Override
    public List<ChocolateDto> findAll() {
        List<Chocolate> chocolates = chocolateRepository.findAll();
        return chocolates.stream().map(chocolateMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ChocolateDto findById(Integer id) throws ChocolateException {
        Chocolate c = chocolateRepository.findById(id).orElseThrow(() -> new ChocolateException("Not found chocolate with id " + id));
        return chocolateMapper.toDto(c);
    }

    @Override
    public ChocolateDto update(ChocolateDto chocolateDto) throws ChocolateException {
    if (chocolateRepository.findById(chocolateDto.getChocolateID()).isPresent())
//            return chocolateRepository.save(chocolateDto);
                return chocolateMapper.toDto(chocolateRepository.save(chocolateMapper.toEntity(chocolateDto)));
        throw new ChocolateException("That chocolate does not exist!");
   
    }

    @Override
    public void delete(Integer id) {
        try{
        chocolateRepository.deleteById(id);
        }catch (Exception e){
            throw new ChocolateException("Chocolate can't been deleted because some orders contain this chocolate");
        }
    }

}
