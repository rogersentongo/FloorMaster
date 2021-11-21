/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.dao;

import com.rntongo.fm.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class FmOrderDaoFileImpl implements FmOrderDao {
    private String fileName="Orders_";
    
    private String dateExtension=null;
    
    private final String orderHeader = "OrderNumber,CustomerName,State,TaxRate,ProductType,"
            + "Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
            + "MaterialCost,LaborCost,Tax,Total";
    private LocalDate orderDate;
    private final String DELIMITER = ",";
    private Map<Integer, Order> orders;
    
    public FmOrderDaoFileImpl()
    {
        
    }

    public FmOrderDaoFileImpl(LocalDate date) {
        this.orderDate = date;
    }

    @Override
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;  
    } 
    
    
    

    
   
    private void createFileName()
    {
        //We empty the filename and the dateExtension
        this.fileName = "Orders_";
        this. dateExtension = null;
        //convert dateTime object to string
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("MMddyyyy");
//        if(dateExtension == null)
//        {
//            dateExtension = customFormat.format(orderDate);
//            this.fileName = fileName+dateExtension+".txt";
//            
//        }
         dateExtension = customFormat.format(orderDate);
         this.fileName = fileName+dateExtension+".txt";
        
    }
    
    
    
    private void createFile() throws FmPersistenceException
    {
        
        //Create writer object
        PrintWriter out;
        
        try{
            out = new PrintWriter(new FileWriter(fileName));
        } catch(IOException e){
            
            throw new FmPersistenceException("Cannot write order header ", e);
            
        }
        out.flush(); 
        out.close();
    }
    
    public String marshallOrder(Order anOrder)
    {
        String orderAsText = anOrder.getOrderNumber()+DELIMITER;
        orderAsText += anOrder.getCustomerName()+DELIMITER;
        orderAsText += anOrder.getState()+DELIMITER;
        orderAsText += anOrder.getTaxRate()+DELIMITER;
        orderAsText += anOrder.getProductType()+DELIMITER;
        orderAsText += anOrder.getArea()+DELIMITER;
        orderAsText += anOrder.getCostPerSquareFoot()+DELIMITER;
        orderAsText += anOrder.getLaborCostPerSquareFoot()+DELIMITER;
        orderAsText += anOrder.getMaterialCost()+DELIMITER;
        orderAsText += anOrder.getLaborCost()+DELIMITER;
        orderAsText += anOrder.getTax()+DELIMITER;
        orderAsText += anOrder.getTotal();
        
        return orderAsText;
    }
    
    public Order unmarshallOrder(String orderAsText)
    {
        //We get a line from the file and tokenize it 
        String [] orderAsToken = orderAsText.split(DELIMITER);
        
        int orderNumber = Integer.parseInt(orderAsToken[0]);
        
        //We initialize the Order object
        Order currOrder = new Order(orderNumber);
        currOrder.setDate(orderDate);
        currOrder.setCustomerName(orderAsToken[1]);
        currOrder.setState(orderAsToken[2]);
        currOrder.setTaxRate(new BigDecimal(orderAsToken[3]));
        currOrder.setProductType(orderAsToken[4]);
        currOrder.setArea(new BigDecimal(orderAsToken[5]));
        currOrder.setCostPerSquareFoot(new BigDecimal(orderAsToken[6]));
        currOrder.setLaborCostPerSquareFoot(new BigDecimal(orderAsToken[7]));
        currOrder.setMaterialCost(new BigDecimal(orderAsToken[8]));
        currOrder.setLaborCost(new BigDecimal(orderAsToken[9]));
        currOrder.setTax(new BigDecimal(orderAsToken[10]));
        currOrder.setTotal(new BigDecimal(orderAsToken[11]));
        
        return currOrder;
    }
    
    private void loadOrders() throws FmPersistenceException
    {
        //create canner object
        Scanner scanner;
        
        try{
            
            scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
            
        }catch(IOException e){
            
            throw new FmPersistenceException("Could not load the Orders, Order date may be wrong", e);
            
        }
        
        //Create objects to catch the data
        
        String currentLine;
        Order anOrder;
        
        scanner.nextLine();
        while(scanner.hasNextLine())
        {
            currentLine = scanner.nextLine();
            anOrder = unmarshallOrder(currentLine);
            orders.put(anOrder.getOrderNumber(), anOrder);
        }
        
        scanner.close();
        
    }
    
   
    
    private void writeOrders() throws FmPersistenceException
    {
        
        
        //create writer object
        PrintWriter out;
        
        try{
            out = new PrintWriter(fileName);
            
        }catch(IOException e)
        {
            throw new FmPersistenceException( "Could not write orders to file", e);
        }
        
        //We create string to store marshalled order
        String marshalledOrder;
        
        List<Order> allOrders = this.getAllOrders();
        out.println(this.orderHeader);
        //Iterate through and marshall the snack objects
        for(Order currOrder: allOrders)
        {
            marshalledOrder = marshallOrder(currOrder);
            out.println(marshalledOrder);
            out.flush();
        }
        out.close();
        
        
    }
    
    
    @Override
    public Order getOrder(int orderNum) throws FmPersistenceException {
        orders = new HashMap<>();
        
        createFileName();
        
        loadOrders();
        
        return orders.get(orderNum);
    }

    @Override
    public List<Order> getAllOrders()throws FmPersistenceException {
        
        
        return new ArrayList<>(orders.values());
    }
    
    @Override
    public List<Order> getAllOrders(LocalDate date) throws FmPersistenceException {
        orders = new HashMap<>();
        
        setOrderDate(date);
        createFileName();
        
        //we load the orders
        loadOrders();
         
        
        return new ArrayList<>(orders.values());
    }
    

    @Override
    public Order removeOrder(LocalDate date, int orderNum) throws FmPersistenceException {
        
        orders = new HashMap<>();
        //Set date
        setOrderDate(date);
        createFileName();
        Order removedOrder=null;
        
        
        loadOrders();
        removedOrder = orders.remove(orderNum);
        
        
        
        
        writeOrders();
        return removedOrder;
    }

    @Override
    public Order addOrder(Order obj) throws FmPersistenceException {
        //set date
        setOrderDate(obj.getDate());
        
        //Create file name
        createFileName();
        //we need to initialize the Map datastructure
        orders = new HashMap<>();
        
        //Check if filname exists
        if(checkifExists(this.fileName))
        {
            //we ignore the creation of a file and we load orders and add order to file
            //We need to load orders and get the max numbers
            loadOrders();
            int maxNum = 0;
            //we find maxNumber from order objects
            for(Map.Entry<Integer, Order> e: orders.entrySet() )
            {
                int currOrderNum = e.getValue().getOrderNumber();
                if(currOrderNum>maxNum)
                {   
                    maxNum=currOrderNum;
                    
                }
            }
            maxNum++;
            obj.setOrderNumber(maxNum);
            
            orders.put(obj.getOrderNumber(), obj);
            
        } else{
            //we create a file
            createFile();
            
            obj.setOrderNumber(1);
            orders.put(obj.getOrderNumber(), obj);
        }
 
       
        
        writeOrders();
        return obj;
    }
    
    @Override
    public Order addEditedOrder(Order anOrder) throws FmPersistenceException
    {
        orders = new HashMap<>();
        setOrderDate(anOrder.getDate());
        createFileName();
        loadOrders();
        
        //insert the order into the memory
        orders.put(anOrder.getOrderNumber(), anOrder);
        //write to memory
        writeOrders();
        
        return anOrder;
    }

    private boolean checkifExists(String fileName) {
   
        File tempFile = new File(fileName);
        return tempFile.exists();
    }

    
    
    

    
    
    
    
}
