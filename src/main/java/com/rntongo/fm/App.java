/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm;

import com.rntongo.fm.controller.FmController;
import com.rntongo.fm.dao.FmAuditDao;
import com.rntongo.fm.dao.FmAuditDaoFileImpl;
import com.rntongo.fm.dao.FmOrderDao;
import com.rntongo.fm.dao.FmOrderDaoFileImpl;
import com.rntongo.fm.dao.FmProductDao;
import com.rntongo.fm.dao.FmProductDaoFileImpl;
import com.rntongo.fm.dao.FmTaxDao;
import com.rntongo.fm.dao.FmTaxDaoFileImpl;
import com.rntongo.fm.service.FmServiceLayer;
import com.rntongo.fm.service.FmServiceLayerImpl;
import com.rntongo.fm.view.FmView;
import com.rntongo.fm.view.UserIO;
import com.rntongo.fm.view.UserIOConsoleImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author rogersentongo
 */
public class App {
    
    public static void main(String[] args) {
        
//        UserIO myIO = new UserIOConsoleImpl();
//        FmView myView = new FmView(myIO);
//        FmOrderDao myOrderDao= new FmOrderDaoFileImpl();
//        FmTaxDao myTaxDao = new FmTaxDaoFileImpl();
//        FmProductDao myProductDao = new FmProductDaoFileImpl();
//        FmAuditDao myAuditDao = new FmAuditDaoFileImpl();
//        FmServiceLayer myService = new FmServiceLayerImpl(myOrderDao, myTaxDao, myProductDao, myAuditDao);
//        FmController controller = new FmController(myService, myView);
            
            AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
            appContext.scan("com.rntongo.fm");
            appContext.refresh();
            
            FmController controller = appContext.getBean("fmController", FmController.class);
            

            controller.run();
        
        
        
    }
    
}
