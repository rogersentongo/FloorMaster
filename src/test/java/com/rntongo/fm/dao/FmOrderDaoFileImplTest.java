/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.dao;

import java.io.File;
import com.rntongo.fm.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author rogersentongo
 */
public class FmOrderDaoFileImplTest {
    
    private FmOrderDao orderDao;
    private String strDate = "10102023";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
    private LocalDate testDate = LocalDate.parse(strDate, formatter);
    
    public FmOrderDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        orderDao = new FmOrderDaoFileImpl(testDate);
        
               
    }
    
    @AfterEach
    public void tearDown() {
        //We delete the testDate
        File currFile = new File("Orders_10102023.txt");
        
        currFile.delete();
    }

    @Test
    public void testAddGetOrder() throws FmPersistenceException {
        
        Order testOrder = new Order(1);
        
        testOrder.setDate(testDate);
        testOrder.setArea(new BigDecimal("1000"));
        testOrder.setProductType("Laminate");
        testOrder.setState("NY");
        testOrder.setTaxRate(new BigDecimal("2.25"));
        testOrder.setCustomerName("Willbert");
        testOrder.setCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.calcLaborCost();
        testOrder.calcMaterialCost();
        testOrder.calcTax();
        testOrder.calcTotal();
        
        //Add order to Dao
        orderDao.addOrder(testOrder);
        
        //Get order
        Order addedOrder = orderDao.getOrder(1);
        
        //Assert
        assertEquals(testOrder.getOrderNumber(), addedOrder.getOrderNumber());
        
        //Check that the data is equal
        assertTrue(testOrder.equals(addedOrder));
    }
    
    @Test
    public void testAddEditedOrder() throws FmPersistenceException{
       
        //Create order to edit
        Order testOrder = new Order(1);
        
        testOrder.setDate(testDate);
        testOrder.setArea(new BigDecimal("1000"));
        testOrder.setProductType("Laminate");
        testOrder.setState("NY");
        testOrder.setTaxRate(new BigDecimal("2.25"));
        testOrder.setCustomerName("Kalyango");
        testOrder.setCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.calcLaborCost();
        testOrder.calcMaterialCost();
        testOrder.calcTax();
        testOrder.calcTotal();
        
        //Add order to Dao
        Order addedOrder = orderDao.addOrder(testOrder);
        
        //Get order to edit
        Order orderToEdit = orderDao.getOrder(1);
        
        //Edit the order
//        orderToEdit.setDate(testDate);
        orderToEdit.setArea(new BigDecimal("2000"));
        orderToEdit.setProductType("Tile");
        orderToEdit.setState("KY");
        orderToEdit.setTaxRate(new BigDecimal("3.25"));
        orderToEdit.setCustomerName("Willbert");
        orderToEdit.setCostPerSquareFoot(new BigDecimal("3.25"));
        orderToEdit.setLaborCostPerSquareFoot(new BigDecimal("3.25"));
        orderToEdit.calcLaborCost();
        orderToEdit.calcMaterialCost();
        orderToEdit.calcTax();
        orderToEdit.calcTotal();
        
        Order editedOrder = orderDao.addEditedOrder(orderToEdit);
        
        assertTrue(!addedOrder.equals(editedOrder), 
                "The edited order should be different from the added Order");
        
        
    }
    
    @Test
    public void testRemoveOrder() throws FmPersistenceException{
        
        //Add order to remove
        Order testOrder = new Order(1);
        
        testOrder.setDate(testDate);
        testOrder.setArea(new BigDecimal("1000"));
        testOrder.setProductType("Laminate");
        testOrder.setState("NY");
        testOrder.setTaxRate(new BigDecimal("2.25"));
        testOrder.setCustomerName("Kalyango");
        testOrder.setCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.calcLaborCost();
        testOrder.calcMaterialCost();
        testOrder.calcTax();
        testOrder.calcTotal();
        
        //Add order to Dao
        Order addedOrder = orderDao.addOrder(testOrder);
        
        Order removedOrder = orderDao.removeOrder(testDate, 1);
        removedOrder.setDate(testDate);
        
        //Check that they're equal
        assertTrue(addedOrder.equals(removedOrder),
                "Removed order should equal added order");
        
        //Specific check that the order doesn't exist anymore
        assertFalse(orderDao.getAllOrders().contains(removedOrder),
                "All orders shouldn't contain Kalyango's order");
        
    }
    
    @Test 
    public void testAddGetAllOrders() throws Exception
    {
        //Create our first order
        Order testOrder = new Order(1);
        
        testOrder.setDate(testDate);
        testOrder.setArea(new BigDecimal("1000"));
        testOrder.setProductType("Laminate");
        testOrder.setState("NY");
        testOrder.setTaxRate(new BigDecimal("2.25"));
        testOrder.setCustomerName("Kalyango");
        testOrder.setCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.calcLaborCost();
        testOrder.calcMaterialCost();
        testOrder.calcTax();
        testOrder.calcTotal();
        
        //Create our second order
        Order testOrder2 = new Order(1);
        
        testOrder2.setDate(testDate);
        testOrder2.setArea(new BigDecimal("20000"));
        testOrder2.setProductType("Tile");
        testOrder2.setState("KY");
        testOrder2.setTaxRate(new BigDecimal("2.25"));
        testOrder2.setCustomerName("Karugaba");
        testOrder2.setCostPerSquareFoot(new BigDecimal("3.25"));
        testOrder2.setLaborCostPerSquareFoot(new BigDecimal("3.25"));
        testOrder2.calcLaborCost();
        testOrder2.calcMaterialCost();
        testOrder2.calcTax();
        testOrder2.calcTotal();
        
        
        //Add both to our DAO
        orderDao.addOrder(testOrder);
        orderDao.addOrder(testOrder2);
        
        
        //Retreive the list of all orders in the dao
        List<Order> allOrders = orderDao.getAllOrders();
        
        //check that they are not null
        assertNotNull(allOrders, "List of orders should't be null");
        //check that they're size is 2
        assertEquals(2, allOrders.size(), "List of orders shouldn't be null");
        
        
        //check that the dao contains each one of them
        assertTrue(allOrders.contains(testOrder),
                "Order list should contain Kalyango");
        assertTrue(allOrders.contains(testOrder2),
                "Order list should contain Karugaba");
        
    }
    
}
