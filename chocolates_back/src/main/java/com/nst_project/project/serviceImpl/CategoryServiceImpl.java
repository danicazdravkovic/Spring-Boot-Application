/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.serviceImpl;

import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.exception.CategoryException;
import com.nst_project.project.mapper.CategoryMapper;
import com.nst_project.project.model.Category;
import com.nst_project.project.repository.CategoryRepository;
import com.nst_project.project.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

/**
 *
 * @author nodas
 */
//@EnableJpaRepositories
@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

//    public CategoryServiceImpl() {
//        this.categoryRepository = null;
//        this.categoryMapper = null;
//    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) throws CategoryException {
//        if(categoryRepository.findById(categoryDto.getCategoryID()).isPresent()){
//            throw new CategoryException("Category already exists");
//        }
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)));
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categoryList=categoryRepository.findAll();
        return categoryList.stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Integer id) throws CategoryException {
        Category c= categoryRepository.findById(id).orElseThrow(() -> new CategoryException("Not found category with id "+id));
        return categoryMapper.toDto(c);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) throws CategoryException {
         if (categoryRepository.findById(categoryDto.getCategoryID()).isPresent())
//            return chocolateRepository.save(chocolateDto);
                return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)));
        throw new CategoryException("That category does not exist!");
   
    }

    @Override
    public void delete(Integer id) throws CategoryException {
         try{
        categoryRepository.deleteById(id);
        }catch (Exception e){
            throw new CategoryException("Category can't been deleted because some chocolates contain this category");
        }
    }
    
     
}
