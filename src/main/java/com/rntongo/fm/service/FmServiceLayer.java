/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.service;

import com.rntongo.fm.dao.FmPersistenceException;
import com.rntongo.fm.dto.Order;
import com.rntongo.fm.dto.Product;
import com.rntongo.fm.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author rogersentongo
 */
public interface FmServiceLayer {
    
    Order getOrder(LocalDate date, int orderNum) throws FmPersistenceException;
    List<Order> getAllOrders(LocalDate date) throws FmPersistenceException;
    Order createOrder(Order anOrder) throws FmPersistenceException;
    Order addEditedOrder(Order anOrder) throws FmPersistenceException;
    Product getProduct(String productType) throws FmPersistenceException;
    List<Product> getAllProducts() throws FmPersistenceException;
    Tax getTax(String stateAbbrev) throws FmPersistenceException;
    List<Tax> getAllTaxes() throws FmPersistenceException;
    Boolean validateOrder(LocalDate date, int orderNum) throws FmPersistenceException;
    Order removeOrder(LocalDate date, int orderNum) throws FmPersistenceException;

    

    
    
    
    
}
