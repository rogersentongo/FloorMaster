/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.service;

import com.rntongo.fm.dao.FmAuditDao;
import com.rntongo.fm.dao.FmOrderDao;
import com.rntongo.fm.dao.FmOrderDaoFileImpl;
import com.rntongo.fm.dao.FmPersistenceException;
import com.rntongo.fm.dao.FmProductDao;
import com.rntongo.fm.dao.FmProductDaoFileImpl;
import com.rntongo.fm.dao.FmTaxDao;
import com.rntongo.fm.dao.FmTaxDaoFileImpl;
import com.rntongo.fm.dto.Order;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class FmServiceLayerImplTest {
    
    
    private FmServiceLayer service;
    private String strDate = "10102023";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
    private LocalDate testDate = LocalDate.parse(strDate, formatter);
    private FmOrderDao orderDao = new FmOrderDaoFileImpl(testDate);
    private FmProductDao prodDao = new FmProductDaoFileImpl();
    private FmTaxDao taxDao = new FmTaxDaoFileImpl();
    private FmAuditDao auditDao = new FmAuditDaoStubImpl();
    
    public FmServiceLayerImplTest() {
        FmOrderDao orderDao = new FmOrderDaoFileImpl(testDate);
        FmProductDao prodDao = new FmProductDaoFileImpl();
        FmTaxDao taxDao = new FmTaxDaoFileImpl();
        FmAuditDao auditDao = new FmAuditDaoStubImpl();
        service = new FmServiceLayerImpl(orderDao, taxDao, prodDao, auditDao); 
        
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws FmPersistenceException {
        
        Order testOrder = new Order(3);
        
        testOrder.setCustomerName("Kasibante");
        testOrder.setDate(testDate);
        testOrder.setProductType("Laminate");
        testOrder.setTaxRate(new BigDecimal("3.25"));
        testOrder.setArea(new BigDecimal("325"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("3.25"));
        testOrder.setCostPerSquareFoot(new BigDecimal("3.25"));
        testOrder.calcLaborCost();
        testOrder.calcMaterialCost();
        testOrder.calcTax();
        testOrder.calcTotal();
        
        service.createOrder(testOrder);
        
            
    }
    
    @AfterEach
    public void tearDown() {
        //We delete the testDate
        File currFile = new File("Orders_10102023.txt");
        
        currFile.delete();
    }

    @Test
    public void testCreateValidOrder() {
        
        Order testOrder = new Order(2);
        
        testOrder.setCustomerName("Robert Mueller");
        testOrder.setDate(testDate);
        testOrder.setProductType("Tile");
        testOrder.setTaxRate(new BigDecimal("2.25"));
        testOrder.setArea(new BigDecimal("225"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.setCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.calcLaborCost();
        testOrder.calcMaterialCost();
        testOrder.calcTax();
        testOrder.calcTotal();
        
        try{
            Order retrievedOrder = service.createOrder(testOrder);
            //Get Order By ID and Do an Assert
            //ASSERT
            assertEquals(retrievedOrder.getOrderNumber(), testOrder.getOrderNumber());
        } catch(FmPersistenceException e)
        {
            fail("Student was valid. No Exception should have been thrown.");
        }
        
        
        
        
    }
    
    @Test
    public void testGetAllOrders() throws Exception
    {
        Order testOrder = new Order(2);
        
        testOrder.setDate(testDate);
        testOrder.setCustomerName("Robert Mueller");
        testOrder.setProductType("Tile");
        testOrder.setTaxRate(new BigDecimal("2.25"));
        testOrder.setArea(new BigDecimal("225"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.setCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.calcLaborCost();
        testOrder.calcMaterialCost();
        testOrder.calcTax();
        testOrder.calcTotal();
        
        service.createOrder(testOrder);
        
        assertEquals(2, service.getAllOrders(testOrder.getDate()).size(),
                "Should only have two orders.");
        assertTrue(service.getAllOrders(testOrder.getDate()).contains(testOrder),
                "The orders should contain Mueller's order");
    }
    
    @Test
    public void testGetOrder() throws Exception
    {
        //Create order
        Order testOrder = new Order(2);
        
        testOrder.setDate(testDate);
        testOrder.setCustomerName("Robert Mueller");
        testOrder.setProductType("Tile");
        testOrder.setTaxRate(new BigDecimal("2.25"));
        testOrder.setArea(new BigDecimal("225"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.setCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.calcLaborCost();
        testOrder.calcMaterialCost();
        testOrder.calcTax();
        testOrder.calcTotal();
        
        //Add order 
        service.createOrder(testOrder);
        
        //Our setup order has date testDate and order num 3
        Order retrieved = service.getOrder(testDate, 2);
//        retrieved.setDate(testDate);
        
        assertTrue(retrieved.getCustomerName().equals("Robert Mueller"),
                "Customer name should be Robert Mueller");
        assertTrue(retrieved.getArea().equals(new BigDecimal("225")),
                "Area should be 225");
        assertTrue(retrieved.getProductType().equals("Tile"),
                "Product type should be tile");
        assertTrue(retrieved.equals(testOrder), "The two should be equal");
        
        
    }
    
    @Test
    public void testRemoveOrder() throws FmPersistenceException
    {
        Order testOrder = new Order(2);
        
        testOrder.setCustomerName("Robert Mueller");
        testOrder.setDate(testDate);
        testOrder.setProductType("Tile");
        testOrder.setTaxRate(new BigDecimal("2.25"));
        testOrder.setArea(new BigDecimal("225"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.setCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.calcLaborCost();
        testOrder.calcMaterialCost();
        testOrder.calcTax();
        testOrder.calcTotal();
        
        //Add order using the service
        service.createOrder(testOrder);
        
        //Remove order
        
        Order removed = service.removeOrder(testDate, 2);
        
        assertTrue(testOrder.equals(removed),
         "The orders should be the same");
        
        
    }
    
    @Test
    public void testEditOrder() throws FmPersistenceException
    {
        Order testOrder = new Order(2);
        
        testOrder.setCustomerName("Robert Mueller");
        testOrder.setDate(testDate);
        testOrder.setProductType("Tile");
        testOrder.setTaxRate(new BigDecimal("2.25"));
        testOrder.setArea(new BigDecimal("225"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.setCostPerSquareFoot(new BigDecimal("2.25"));
        testOrder.calcLaborCost();
        testOrder.calcMaterialCost();
        testOrder.calcTax();
        testOrder.calcTotal();
        
        //we add the order 
        
        service.createOrder(testOrder);
        
        //We retrieve the order
        Order toEdit = service.getOrder(testDate, 2);
//        toEdit.setDate(testDate);
        
        //We edit the order
        toEdit.setArea(new BigDecimal("1005678"));
        toEdit.setCustomerName("Willburton");
        
        //We add the order back using the edit function
        service.addEditedOrder(toEdit);
        
        //We retrieve the order again
        Order retrieved = service.getOrder(testDate, 2);
        
        assertFalse(retrieved.equals(testOrder),
                "The two orders shouldn't be the same");
        
        
        
        
        
        
    }
    
}
