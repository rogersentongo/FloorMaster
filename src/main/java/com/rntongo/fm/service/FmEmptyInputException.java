/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.service;

/**
 *
 * @author rogersentongo
 */
public class FmEmptyInputException extends Exception {
    
    FmEmptyInputException(String message)
    {
        super(message);
    }
    
    FmEmptyInputException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
}
