/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nst_project.project.repository;

import com.nst_project.project.model.Chocolate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nodas
 */
@Repository
public interface ChocolateRepository extends JpaRepository<Chocolate, Integer> {
//    List<ChocolateDto> findAllByOrederByName();
//    Chocolate save(Chocolate newChocolate);
}
