/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.controller;

import com.nst_project.project.dtos.ChocolateDto;
import com.nst_project.project.exception.CategoryException;
import com.nst_project.project.exception.ChocolateException;
import com.nst_project.project.model.Category;
import com.nst_project.project.model.Chocolate;
import com.nst_project.project.repository.CategoryRepository;
import com.nst_project.project.repository.ChocolateRepository;
import com.nst_project.project.service.ChocolateService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nodas
 */
@RestController
@CrossOrigin("http://localhost:3000")
//@RequestMapping("chocolate")
public class ChocolateController {
//      @Autowired
//    private ChocolateRepository chocolateRepository;

    private final ChocolateService chocolateService;

    public ChocolateController(ChocolateService chocolateService) {
        this.chocolateService = chocolateService;
    }

    @PostMapping("/chocolate")
    public @ResponseBody
    ResponseEntity<Object> add(@RequestBody ChocolateDto chocolateDto) {
        try {
            ChocolateDto entity = chocolateService.save(chocolateDto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (ChocolateException e) {

            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/chocolates")
    List<ChocolateDto> getAllChocolates() {
        return chocolateService.findAll();
    }

    @GetMapping("/chocolate/{id}")
    ResponseEntity<Object> getChocolateById(@PathVariable Integer id) {
        try {
            ChocolateDto entity = chocolateService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (ChocolateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/chocolate")
    public @ResponseBody
    ResponseEntity<Object> update(@RequestBody ChocolateDto chocolateDto) {
        try {
            ChocolateDto entity = chocolateService.update(chocolateDto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (ChocolateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("chocolate/{id}")
    public @ResponseBody ResponseEntity<Object> delete(@PathVariable Integer id) {
        try{
        chocolateService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Chocolate has been deleted");
        }catch(ChocolateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
//    @PostMapping("/chocolate");
//    Chocolate newCategory(@RequestBody Chocolate newChocolate) {
////        System.out.println(newChocolate);
//        return chocolateService.save(newChocolate);
//    }
//
//    @GetMapping("/chocolates")
//    List<Chocolate> getAllCategories() {
//        return chocolateRepository.findAll();
//    }
//
//    @GetMapping("/chocolate/{id}")
//    Chocolate getChocolateById(@PathVariable Long id) {
//        return chocolateRepository.findById(id).orElseThrow(
//                () -> new ChocolateException(id));
//    }
//
//    @PutMapping("/chocolate/{id}")
//    Chocolate updateChocolate(@PathVariable Long id, @RequestBody Chocolate newChocolate) {
//        return chocolateRepository.findById(id).map(chocolate -> {
//            chocolate.setName(newChocolate.getName());
//            chocolate.setDescription(newChocolate.getDescription());
//            chocolate.setPrice(newChocolate.getPrice());
//            chocolate.setCategory(chocolate.getCategory());
//            return chocolateRepository.save(chocolate);
//        }
//        ).orElseThrow(() -> new ChocolateException(id));
//    }
//
//    @DeleteMapping("/chocolate/{id}")
//    String deleteChocolate(@PathVariable Long id){
//        if(!chocolateRepository.existsById(id))
//            throw new ChocolateException(id);
//         chocolateRepository.deleteById(id);
//         return "Category with id "+id+" has been deleted";
//    }
}
