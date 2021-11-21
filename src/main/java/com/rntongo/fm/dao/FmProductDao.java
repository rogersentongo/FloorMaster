/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.dao;

import com.rntongo.fm.dto.Product;
import java.util.List;

/**
 *
 * @author rogersentongo
 */
public interface FmProductDao {
    
    Product getProduct(String productType) throws FmPersistenceException;
    List<Product> getAllProducts() throws FmPersistenceException;
    
}
