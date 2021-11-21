/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.controller;

import com.rntongo.fm.dao.FmPersistenceException;
import com.rntongo.fm.dto.Order;
import com.rntongo.fm.service.FmServiceLayer;
import com.rntongo.fm.view.FmView;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author rogersentongo
 */
@Component
public class FmController {
    
    private FmServiceLayer service;
    private FmView view;
    
    @Autowired
    public FmController(FmServiceLayer service, FmView view)
    {
        this.service = service;
        this.view = view;
    }
    
    
    public void run()
    {
        int menuSelection =0;
        boolean keepGoing = true;
           
            
            while(keepGoing)
            {
                try{
                    
                    menuSelection = getMenuSelection();
                
                switch(menuSelection)
                {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        createOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
                    
                    

                } catch (FmPersistenceException e)
                {

                    view.displayErrorMessage(e.getMessage());
                }

                
                
                    
            }
            exitMessage(); 
            
        
        
        
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    

    private void displayOrders() throws FmPersistenceException {
        view.displayOrderBanner();
        LocalDate dateToDisplay= view.getInfoDisplayOrders();
        List<Order> orderList = service.getAllOrders(dateToDisplay);
        view.displayOrderList(orderList);
    }

    private void createOrder() throws FmPersistenceException {
        //We display that we're adding an order
        view.displayAddOrderBanner();
        
        //Create the order from the view
        Order currOrder = view.getInfoCreateNewOrder(service.getAllProducts(), service.getAllTaxes());
        
        //Display order totals and prompt user if they want to add order
        boolean confirm = view.billAndConfirmToUser(currOrder);
        
        
        //Add the order using the service layer
        if(confirm)
        {
            service.createOrder(currOrder);
            //Display that we successfully created the order
            view.displayCreateOrderSuccessBanner();
        } else{
            view.displayNotAddedBanner();
        }
        
//        if(!confirm){
//            view.displayNotAddedBanner();
//            return;
//        }
//        
//        service.createOrder(currOrder);
//        //Display that we successfully created the order
//        view.displayCreateOrderSuccessBanner();
        
    }

    private void editOrder() throws FmPersistenceException {
        view.displayEditOrderBanner();
        
        //ask view for order to be edited
        boolean notExists = true;
        LocalDate editOrderDate = null;
        int editOrderNum = 0;
        Order editableOrder = null;
        Order editedOrder = null;
        do{
            editOrderDate = view.getEditOrderDate();
            editOrderNum = view.getEditOrderNum();
            
            
            try{
                
                editableOrder = service.getOrder(editOrderDate, editOrderNum);
                //Pass the order to the view to return editedOrder
                editedOrder = view.editOrder(editableOrder, editOrderDate, 
                        editOrderNum,service.getAllTaxes(), service.getAllProducts());
                notExists = false;
            } catch(FmPersistenceException | NullPointerException | NumberFormatException e)
            {
                notExists = true;
                view.displayErrorMessage("INSERT DATE & ORDER THAT EXIST!!");
            }
            
        }while(notExists);
            
        
        
        
        
        
        
        
        
        
        boolean confirm = view.billAndConfirmToUser(editedOrder);
        
        
        
        //Add the order using the service layer
        if(confirm)
        {
            service.addEditedOrder(editedOrder);
            //Display that we successfully created the order
            view.displayCreateOrderSuccessBanner();
        } else{
            view.displayNotAddedBanner();
        }
                
        
    }

    private void removeOrder() throws FmPersistenceException {
       
        view.displayRemoveOrderBanner();
        
        //ask view for order to be edited
        boolean notExists = true;
        LocalDate removeOrderDate = null;
        int removeOrderNum = 0;
        do{
            removeOrderDate = view.getRemovalDate();
            removeOrderNum = view.getRemovalOrderNum();
            
            
            try{
                
                Order checkOrder = service.getOrder(removeOrderDate, removeOrderNum);
                Order removeOrder = service.removeOrder(removeOrderDate, removeOrderNum);
                view.displaySuccessRemovedBanner(removeOrder);
                notExists = false;
            } catch(FmPersistenceException | NullPointerException e)
            {
                notExists = true;
                view.displayErrorMessage("INSERT DATE & ORDER THAT EXIST!!");
            }
            
        }while(notExists);
        
    }

    private void unknownCommand() {
        view.displayErrorMessage("Unknown Command selected in the menu");
    }
    
    private void exitMessage() {
        view.displayExitMessage();
    }
    
}
