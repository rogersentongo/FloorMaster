/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.dao;

import com.rntongo.fm.dto.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author rogersentongo
 */
@Component
public class FmProductDaoFileImpl implements FmProductDao {
    private final String DELIMITER = ",";
    private final String PRODUCT_FILE;
    private Map<String, Product> products;

    public FmProductDaoFileImpl() {
        PRODUCT_FILE = "Products.txt";
    }
    
    

    public FmProductDaoFileImpl(String PRODUCT_FILE) {
        this.PRODUCT_FILE = PRODUCT_FILE;
    }
    
    
    private Product unmarshallProducts(String productAsText)
    {
        String [] productsAsToken = productAsText.split(DELIMITER);
        
        Product prodObj = new Product(productsAsToken[0]);
        prodObj.setCostPerSquareFoot(new BigDecimal(productsAsToken[1]));
        prodObj.setLaborCostPerSquareFoot(new BigDecimal(productsAsToken[2]));
        
        
        return prodObj;
    }
    
    private void loadProducts() throws FmPersistenceException
    {
        Scanner scanner;
        products = new HashMap<String, Product>();
        
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
            
        }catch(IOException e)
        {
            throw new FmPersistenceException("Cannot load products", e);
        }
        
        String currLine;
        Product aProduct;
        
        scanner.nextLine(); //Need to skip header
        while(scanner.hasNextLine())
        {
            //Insert into our map the product objects
            currLine = scanner.nextLine();
            aProduct = unmarshallProducts(currLine);
            String productType = aProduct.getProductType();
            products.put(productType, aProduct);
        }
        
        scanner.close();
        
        
    }
    
    
    
    @Override
    public Product getProduct(String productType) throws FmPersistenceException {
        loadProducts();
        
        return products.get(productType);
    }

    @Override
    public List<Product> getAllProducts() throws FmPersistenceException {
        loadProducts();
        
        return new ArrayList<>(products.values());
    }
    
}
