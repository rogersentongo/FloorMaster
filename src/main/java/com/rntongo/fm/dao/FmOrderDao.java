/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.dao;

import com.rntongo.fm.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author rogersentongo
 */
public interface FmOrderDao {
    Order getOrder(int orderNum) throws FmPersistenceException;
    List<Order> getAllOrders() throws FmPersistenceException;
    List<Order> getAllOrders(LocalDate date) throws FmPersistenceException;
    Order removeOrder(LocalDate date,int orderNum) throws FmPersistenceException;
    Order addOrder(Order obj) throws FmPersistenceException;
    void setOrderDate(LocalDate date);

    public Order addEditedOrder(Order obj) throws FmPersistenceException;
    
    
    
    
    
    
}
