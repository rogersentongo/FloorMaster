/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.service;

import com.rntongo.fm.dao.FmAuditDao;
import com.rntongo.fm.dao.FmOrderDao;
import com.rntongo.fm.dao.FmPersistenceException;
import com.rntongo.fm.dao.FmProductDao;
import com.rntongo.fm.dao.FmTaxDao;
import com.rntongo.fm.dto.Order;
import com.rntongo.fm.dto.Product;
import com.rntongo.fm.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author rogersentongo
 */
@Component
public class FmServiceLayerImpl implements FmServiceLayer {
    
   
    
    FmOrderDao orderDao;
    FmTaxDao taxDao;
    FmProductDao productDao;
    FmAuditDao auditDao;

    
    
    
    
    @Autowired
    public FmServiceLayerImpl(FmOrderDao orderDao, FmTaxDao taxDao, FmProductDao productDao, FmAuditDao auditDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
        this.auditDao = auditDao;
    }
    
    
    
    @Override
    public Order getOrder(LocalDate date, int orderNum) throws FmPersistenceException {
        //Set the date for this order
        orderDao.setOrderDate(date);
        //Return the order based on the orderNum
        return orderDao.getOrder(orderNum);
    }

    @Override
    public List<Order> getAllOrders(LocalDate date) throws FmPersistenceException {
        orderDao.setOrderDate(date);
        return orderDao.getAllOrders(date);
    }

    @Override
    public Order createOrder(Order anOrder) throws FmPersistenceException {
        orderDao.setOrderDate(anOrder.getDate());
        return orderDao.addOrder(anOrder);
    }
    
    @Override
    public Order addEditedOrder(Order anOrder) throws FmPersistenceException
    {
        orderDao.setOrderDate(anOrder.getDate());
        return orderDao.addEditedOrder(anOrder);
    }
    
    

    @Override
    public Product getProduct(String productType) throws FmPersistenceException{
        return productDao.getProduct(productType);
    }

    @Override
    public List<Product> getAllProducts() throws FmPersistenceException {
        return productDao.getAllProducts();
    }

    @Override
    public Tax getTax(String stateAbbrev) throws FmPersistenceException {
        return taxDao.getTax(stateAbbrev);
    }

    @Override
    public Boolean validateOrder(LocalDate date, int orderNum) throws FmPersistenceException {
        orderDao.setOrderDate(date);
        orderDao.getOrder(orderNum);
        
        return true;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNum) throws FmPersistenceException {
        orderDao.setOrderDate(date);
        return orderDao.removeOrder(date, orderNum);
    }

    @Override
    public List<Tax> getAllTaxes() throws FmPersistenceException {
        return taxDao.getAllTaxes();
    }

    

    
    
    
    
    
    
    
}
