/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nst_project.project.exception;

/**
 *
 * @author nodas
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        
        super("Could not find the user with id "+ id);
    }
    
}
