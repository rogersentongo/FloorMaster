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
public class FmNoOrderExistsException extends Exception {

    public FmNoOrderExistsException(String message) {
        super(message);
    }

    public FmNoOrderExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    
}
