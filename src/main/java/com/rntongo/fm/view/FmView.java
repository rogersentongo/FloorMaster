/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.view;

import com.rntongo.fm.dao.FmPersistenceException;
import com.rntongo.fm.dto.Order;
import com.rntongo.fm.dto.Product;
import com.rntongo.fm.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author rogersentongo
 */
@Component
public class FmView {
    
    @Autowired
    private UserIO io;
    private int currOrderNum=0;
    
    @Autowired
    public FmView(UserIO io)
    {
        this.io = io;
    }
    
    
    //Print the menu
    public int printMenuAndGetSelection(){
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an order");
        io.print("3. Edit an order");
        io.print("4. Remove an order");
        io.print("5. Exit");
        
        int userChoice = 0;
        
        try{
            userChoice = io.readInt("Please select from the above choices.", 1,5);;
            
        }catch(NumberFormatException e)
        {
            System.err.println("Please input correct format, enter or empty is not it");
        }
        
        return userChoice;
     
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }

    public void displayOrderBanner() {
        io.print("We're now going to display the order");
    }

    public void displayOrderList(List<Order> orderList) {
        
        for(Order ord: orderList)
        {
            

            io.print("****************************************************");
            //From the other section of the code
            io.print("Order Number: "+String.valueOf(ord.getOrderNumber()));
            io.print("Customer Name: "+ord.getCustomerName());
            io.print("State: "+ord.getState());
            io.print("Area: "+ord.getArea().toString());
            io.print("Tax Rate: "+ord.getTaxRate().toString());
            io.print("Product Type: "+ord.getProductType());
            io.print("Cost per square foot: "+ord.getCostPerSquareFoot().setScale(2, RoundingMode.CEILING).toString());
            io.print("Labor Cost per squar foot: "+ord.getLaborCostPerSquareFoot().setScale(2, RoundingMode.CEILING).toString());
            io.print("Labor Cost: "+ord.getLaborCost().setScale(2, RoundingMode.CEILING).toString());
            io.print("Material Cost: "+ord.getMaterialCost().setScale(2, RoundingMode.CEILING).toString());
            io.print("Tax: "+ord.getTax().setScale(2, RoundingMode.CEILING).toString());
            io.print("Total: "+ord.getTotal().setScale(2, RoundingMode.CEILING).toString());
            
            io.print("****************************************************");
            
        }
    }

    public void displayAddOrderBanner() {
        io.print("Adding orders");
    }

    

    public void displayCreateOrderSuccessBanner() {
        io.print("Thank you, your order has been added");
    }

    public Order getInfoCreateNewOrder(List<Product> productList, List<Tax>taxList) {
        //Initialize Order object
        Order orderObj = new Order();
        boolean wrong = true;
        orderObj.setDate(getOrderDateAddOrder());
        
        while(wrong)
        {
            String nameChoice = getNameFromUser();
            if(nameChoice.trim().isEmpty() || nameChoice ==null)
            {
                displayErrorMessage("ENTER A VALUE!!"); 
                continue;
                
            }
            
            orderObj.setCustomerName(nameChoice);
            
        }
        
        
        String stateAbbrev=null;
        displayStatesAndTaxes(taxList);
        wrong =true;
        while(wrong)
        {
            stateAbbrev = getStateAbbrev();
            for(Tax t: taxList)
            {
                if(t.getStateAbbrev().equalsIgnoreCase(stateAbbrev))
                {
                    wrong = false;
                }
            }
            
        }
        orderObj.setState(stateAbbrev);
        
        orderObj.setArea(getAreaAddOrder());
        
        
        orderObj.setTaxRate(getTaxRateAddOrder(orderObj.getState(), taxList));
        
        
        orderObj.setProductType(getProductTypeAddOrder(productList));
        orderObj.setCostPerSquareFoot(getCostPSFAddOrder(productList, orderObj.getProductType()));
        orderObj.setLaborCostPerSquareFoot(getLaborCostPSFAddOrder(productList, orderObj.getProductType()));
        
        //Calc our order costs
        orderObj.calcLaborCost();
        orderObj.calcMaterialCost();
        orderObj.calcTax();
        orderObj.calcTotal();
       
        
        
        
        
        return orderObj;
    }

    public void displayEditOrderBanner() {
        io.print("Now editing orders, please enter date and order num that exists");
    }

    private void displayProducts(List<Product> productList) {
        io.print("Please take a look at the products and choose which one you'd like");
        for(Product p: productList)
        {
            displayProduct(p);
        }
        
    }
    
    private void displayProduct(Product aProduct)
    {
        io.print("**********************");
        io.print(aProduct.getProductType());
        io.print(aProduct.getCostPerSquareFoot().setScale(2, RoundingMode.CEILING).toString());
        io.print(aProduct.getLaborCostPerSquareFoot().setScale(2, RoundingMode.CEILING).toString());
        io.print("**********************");
    }

    public boolean billAndConfirmToUser(Order currOrder) {
        

        
        
        //Display our order costs
        io.print("The labor cost for your order is $"+ currOrder.getLaborCost().setScale(2, RoundingMode.CEILING));
        io.print("The material cost for your order is $"+currOrder.getMaterialCost().setScale(2, RoundingMode.CEILING));
        io.print("The tax for your order is $"+currOrder.getTax().setScale(2, RoundingMode.CEILING));
        io.print("The total cost of your order is $"+currOrder.getTotal().setScale(2, RoundingMode.CEILING));
        
        char choice = (io.readString("Do you want to confirm this order? Y/N")).charAt(0);
        
        boolean choiceBool;
        if(choice == 'Y')
        {
            choiceBool = true;
        } else{
            choiceBool = false;
        }
        
        return choiceBool;
    }

    public void displayNotAddedBanner() {
        io.print("Thank you for your choice, the order wasn't added");
    }
    
    private String getNameFromUser(){
        while(true){
            String customerName = io.readString("Please enter the customer name");
            if(customerName == ""){
                io.print("Customer Name Must Not Be Blank");
                continue;
            }
            
            return customerName;
        }
    }

    public void displayExitMessage() {
        io.print("Thank you for using our services!");
    }

    private LocalDate getOrderDateAddOrder() {
        //Get today's date
        LocalDate todayDate = LocalDate.now();
        LocalDate orderDate = null;
        boolean hasErrors = false;
        do{
            
            do{
            
                try{
                    String strDate = io.readString("Please enter order date(MMDDYYYY). Should be numbers and after today");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
                    orderDate = LocalDate.parse(strDate, formatter);
                    hasErrors = false;
                } catch(DateTimeParseException | NullPointerException e)
                {
                    hasErrors = true;
                    io.print("Please enter correct date");
                }

            }while(hasErrors);
            
        } while(orderDate.isBefore(todayDate));
        
        
        
        
        
        
        return orderDate;
    }

    private String getStateAbbrev() {
        
        String stateAbb = io.readString("Please enter the abbreviation for the state:(Look at the options above)");
        return stateAbb;
    }

    private BigDecimal getAreaAddOrder() {
        
//        area.compareTo(areaMin) == -1 || area.compareTo(BigDecimal.ZERO)<0
        
        int area = 0;
        boolean hasErrors= true;
        do{
            
            try{
            
                area = io.readInt("Please enter area(Must  be greater than 99): ");
                hasErrors = false;
            } catch(NumberFormatException e)
            {
                hasErrors = true;
                io.print("ENTER NUMBERS!!");
                
            }
            
            if(area<100)
                hasErrors = true;
            
            
        }while(hasErrors);
        
        
        
        
        return new BigDecimal(String.valueOf(area));
    }

    private BigDecimal getTaxRateAddOrder(String stateAbb, List<Tax> taxList) {
        
        BigDecimal taxRate = null;
        //Loop through the tax list
        for(Tax t: taxList)
        {
            if(t.getStateAbbrev().equalsIgnoreCase(stateAbb))
            {
                taxRate = t.getTaxRate();
            }
        }
        
        return taxRate;
    }

    private String getProductTypeAddOrder(List<Product> productList) {
        displayProducts(productList);
        
        
        boolean wrong = true;
        String prodType = null;
        while(wrong)
        {
          prodType = io.readString("Please enter your product type choice(Must exist): ");
          
          for(Product p: productList)
          {
              if(prodType.equalsIgnoreCase(p.getProductType()))
              {
                  wrong = false;
              }
          }
        }
        
        
        return prodType;
    }

    private BigDecimal getCostPSFAddOrder(List<Product> productList, String productType) {
        BigDecimal costPerSquareFoot=null;
        //Loop through to find the product with matching product type
        for(Product p: productList)
        {
            if(p.getProductType().equalsIgnoreCase(productType))
            {
                costPerSquareFoot = p.getCostPerSquareFoot();
                
            }
        }
        
        return costPerSquareFoot;
    }

    private BigDecimal getLaborCostPSFAddOrder(List<Product> productList, String productType) {
        
        BigDecimal laborCostPerSquareFoot=null;
        //Loop through to find the product with matching product type
        for(Product p: productList)
        {
            if(p.getProductType().equalsIgnoreCase(productType))
            {
                laborCostPerSquareFoot = p.getLaborCostPerSquareFoot();
            }
        }
        
        return laborCostPerSquareFoot;
    }

    public LocalDate getInfoDisplayOrders() {
        LocalDate dateToDisplay = getDateToDisplayOrders();
        
        return dateToDisplay;
    }

    private LocalDate getDateToDisplayOrders() {
        boolean hasErrors = false;
        LocalDate dateForOrder = null;
        
        do{
            try{
            String strDate =io.readString("Please enter the  date for the orders you want "
                    + "to display(MMddyyyy e.g 10102021)");
        
            DateTimeFormatter formattter = DateTimeFormatter.ofPattern("MMddyyyy");
            dateForOrder = LocalDate.parse(strDate, formattter);
        
            hasErrors = false;
            }catch(DateTimeParseException | NullPointerException e)
            {
                hasErrors=true;
                io.print("Please enter a valid date!!");
            }
        
            
            
        }while(hasErrors);
        
        
        return dateForOrder;
        
    }

    public void displayRemoveOrderBanner() {
        io.print("We are going to be removing an order of your choice, "
                + "please enter correct date and order number details");
    }

    public int getRemovalOrderNum() {
        
        int orderNumToEdit=0;
        boolean hasErrors = false;
        
        do{
            
            
            try{
                orderNumToEdit = io.readInt("Enter order number for order to be removed");
                hasErrors = false;
            }catch(NumberFormatException | NullPointerException e){
                hasErrors = true;
               io.print("Please enter a number"); 
            } catch(Exception e)
            {
                hasErrors = true;
                io.print("Something else happened! Please input OrderNum that exists");
            }
            
        }while(hasErrors);
        
        
        
        
        return orderNumToEdit;
    }

    public LocalDate getRemovalDate() {
        boolean hasErrors = false;
        LocalDate dateForOrder = null;
        
        do{
            try{
            String strDate =io.readString("Please enter the  date for the order you want to remove(MMddyyyy e.g 10102021)");
        
            DateTimeFormatter formattter = DateTimeFormatter.ofPattern("MMddyyyy");
            dateForOrder = LocalDate.parse(strDate, formattter);
        
            hasErrors = false;
            }catch(DateTimeParseException | NullPointerException e)
            {
                hasErrors=true;
                io.print("Please enter a valid date!!");
            }
        
            
            
        }while(hasErrors);
        
        
        return dateForOrder;
    }

    public void displaySuccessRemovedBanner(Order removeOrder) {
        
        io.print("***************************");
        io.print("Success the following order was removed");
        printOrder(removeOrder);
    }

    private void printOrder(Order ord) {
        
        io.print("****************************************************");
            //From the other section of the code
            io.print("Order Number: "+String.valueOf(ord.getOrderNumber()));
            io.print("Customer Name: "+ord.getCustomerName());
            io.print("State: "+ord.getState());
            io.print("Area: "+ord.getArea().toString());
            io.print("Tax Rate: "+ord.getTaxRate().toString());
            io.print("Product Type: "+ord.getProductType());
            io.print("Cost per square foot: "+ord.getCostPerSquareFoot().setScale(2, RoundingMode.CEILING).toString());
            io.print("Labor Cost per squar foot: "+ord.getLaborCostPerSquareFoot().setScale(2, RoundingMode.CEILING).toString());
            io.print("Labor Cost: "+ord.getLaborCost().setScale(2, RoundingMode.CEILING).toString());
            io.print("Material Cost: "+ord.getMaterialCost().setScale(2, RoundingMode.CEILING).toString());
            io.print("Tax: "+ord.getTax().setScale(2, RoundingMode.CEILING).toString());
            io.print("Total: "+ord.getTotal().setScale(2, RoundingMode.CEILING).toString());
            
            io.print("****************************************************");
        
    }

    public LocalDate getEditOrderDate() {
        
        boolean hasErrors = false;
        LocalDate dateForOrder = null;
        
        do{
            try{
            String strDate =io.readString("Please enter the  date for the order you want to edit(MMddyyyy e.g 10102021)");
        
            DateTimeFormatter formattter = DateTimeFormatter.ofPattern("MMddyyyy");
            dateForOrder = LocalDate.parse(strDate, formattter);
        
            hasErrors = false;
            }catch(DateTimeParseException | NullPointerException e)
            {
                hasErrors=true;
                io.print("Please enter a valid date!!");
            }
        
            
            
        }while(hasErrors);
        
        
        return dateForOrder;
        
    }

    public int getEditOrderNum() {

            int orderNumToEdit=0;
            boolean hasErrors = false;

            do{


                try{
                    orderNumToEdit = io.readInt("Enter order number for order to be edited");
                    hasErrors = false;
                }catch(NumberFormatException | NullPointerException e){
                    hasErrors = true;
                   io.print("Please enter a number"); 
                } catch(Exception e)
                {
                    hasErrors = true;
                    io.print("Something else happened! Please input OrderNum that exists");
                }

            }while(hasErrors);
        
        
        
        
            return orderNumToEdit;




    }
    
    
 

    public Order editOrder(Order editableOrder, LocalDate editOrderDate, int OrderNum, List<Tax> taxList, List<Product> prodList) {
        boolean hasErrors = true;
        io.print("If you dont want to edit an option, simply press enter");
        String userNameChoice = io.readString("Enter customer name: "+editableOrder.getCustomerName());
        if(userNameChoice==null || userNameChoice.trim().isEmpty())
        {
           
        } else{
            //doesn't matter name could be anything
            editableOrder.setCustomerName(userNameChoice);
        }
        
        
        do{
            String userChoice = io.readString("Enter tax Abbreviation choice or press enter to keep current " +editableOrder.getState());
            
            if(userChoice==null ||userChoice.trim().isEmpty())
            {
                //User pressed enter
                break;
            }
            
            //check if user choice exists in the available list of abbreviations
            for(Tax t: taxList)
            {
                if(t.getStateAbbrev().equalsIgnoreCase(userChoice))
                {
                    editableOrder.setState(t.getStateAbbrev());
                    hasErrors = false;
                    break;
                }
            }
            
//            displayErrorMessage("ENTER TAX ABBREVIATION THAT EXISTS!!");
            
            
            
        }while(hasErrors);
        
        hasErrors =true;
        do{
            
            String userChoice = io.readString("Enter product type choice or press enter to leave it as it is: "+editableOrder.getProductType());
            
            if(userChoice == null || userChoice.trim().isEmpty())
            {
                //User pressed enter
                break;
            }
            
            //We check if it exists in our list of product types
            for(Product p: prodList)
            {
                if(p.getProductType().equalsIgnoreCase(userChoice))
                {
                    editableOrder.setProductType(p.getProductType());
                    hasErrors = false;
                    break;
                }
                
            }  
        }while(hasErrors);
            
        
        
        hasErrors = true;
        do{
            String strChoice = io.readString("Enter an area amount greater than 100 or enter to keep current value: " +editableOrder.getArea());
            
            if(strChoice==null || strChoice.trim().isEmpty())
            {
                //User pressed enter
                break;
            }
            
            //Check if string can be number else continute
            if(isNotNumber(strChoice))
            {
                displayErrorMessage("ENTER A NUMBER!!");
                continue;
                
            }
            
            
            //Check if value is greater than 100
            if(Integer.parseInt(strChoice)<100)
            {
                //User chose value less than 100
                displayErrorMessage("ENTER NUMBER GREATER THAN 99");
                continue;
            } else{
                //user chose correct value
                editableOrder.setArea(new BigDecimal(strChoice));
                hasErrors = false;
            }
            

            
            
            
        }while(hasErrors);
        
        
        
        
        
        
                
        

        
        editableOrder.setDate(editOrderDate);
        editableOrder.setOrderNumber(OrderNum);
        //Recalculate the fields
        editableOrder.calcLaborCost();
        editableOrder.calcMaterialCost();
        editableOrder.calcTax();
        editableOrder.calcTotal();
        
        printOrder(editableOrder);
        
        return editableOrder;
    }

    private void displayStatesAndTaxes(List<Tax> taxList) {
        
        for(Tax t: taxList)
        {
            io.print("**********************");
            io.print("State Abbreviation: "+t.getStateAbbrev());
            io.print("State Name: "+t.getStateName());
            io.print("State Tax Rate: "+t.getTaxRate());
            io.print("**********************");
            
            
        }
    }

    private boolean isNotNumber(String strChoice) {
        try{
            Integer.parseInt(strChoice);
            return false;
        }catch(NumberFormatException e)
        {
            return true;
        }
        
       
    }
    
    
    
    
}
