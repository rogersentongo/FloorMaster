/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

/**
 *
 * @author rogersentongo
 */
@Component
public class FmAuditDaoFileImpl implements FmAuditDao {
    
    private static final String AUDIT_FILE = "audit.txt";

    @Override
    public void writeAuditEntry(String entry) throws FmPersistenceException {
        
        PrintWriter out;
        
        try{
            
            out = new PrintWriter(new FileWriter(AUDIT_FILE));
            
            
        } catch(IOException ex){
            
            throw new FmPersistenceException("Cannot access file", ex);
            
        }
        
        LocalDateTime timeStamp = LocalDateTime.now();
        out.println(timeStamp+" : "+entry);
        out.flush();
    }
    
}
