/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.controller;

import com.nst_project.project.dtos.CategoryDto;
import com.nst_project.project.exception.CategoryException;
import com.nst_project.project.model.Category;
import com.nst_project.project.repository.CategoryRepository;
import com.nst_project.project.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nodas
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public @ResponseBody
    ResponseEntity<Object> add(@RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto entity = categoryService.save(categoryDto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (CategoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/categories")
    List<CategoryDto> getAllCategories() {
        return categoryService.findAll();
    }
     @GetMapping("/category/{id}")
    ResponseEntity<Object> getCategoryById(@PathVariable Integer id) {
        try {
            CategoryDto entity=categoryService.findById(id);
        return  ResponseEntity.status(HttpStatus.OK).body(entity);
         } catch (CategoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
  @PutMapping("/category")
    public @ResponseBody
    ResponseEntity<Object> update(@RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto entity = categoryService.update(categoryDto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (CategoryException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("category/{id}")
    public @ResponseBody ResponseEntity<Object> delete(@PathVariable Integer id) {
        try{
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Chocolate has been deleted");
        }catch(CategoryException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @PostMapping("/category")
//    Category newCategory(@RequestBody Category newCategory) {
//        return categoryRepository.save(newCategory);
//    }
//
//  
//
//    @PutMapping("/category/{id}")
//    Category updateCategory(@PathVariable Long id, @RequestBody Category newCategory) {
//        return categoryRepository.findById(id).map(category -> {
//            category.setName(newCategory.getName());
//            category.setDescription(newCategory.getDescription());
//            return categoryRepository.save(category);
//        }
//        ).orElseThrow(() -> new CategoryException(id));
//    }
//
//    @DeleteMapping("/category/{id}")
//    String deleteCategory(@PathVariable Long id){
//        if(!categoryRepository.existsById(id))
//            throw new CategoryException(id);
//         categoryRepository.deleteById(id);
//         return "Category with id "+id+" has been deleted";
//    }
}
