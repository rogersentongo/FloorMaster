/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.dao;

import com.rntongo.fm.dto.Tax;
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
public class FmTaxDaoFileImpl implements FmTaxDao {
    
    private final String DELIMITER = ",";
    private final String TAX_FILE;
    private Map<String, Tax> taxDetails;

    public FmTaxDaoFileImpl() {
        TAX_FILE = "Taxes.txt";
    }

    public FmTaxDaoFileImpl(String TAX_FILE) {
        this.TAX_FILE = TAX_FILE;
    }
    
    private void loadTax() throws FmPersistenceException
    {
        Scanner scanner;
        taxDetails = new HashMap<String, Tax>();
        
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
            
        }catch(IOException e)
        {
            throw new FmPersistenceException("Could not load taxes", e);
        }
        
        String currLine;
        Tax taxInfo;
        scanner.nextLine();
        
        while(scanner.hasNextLine())
        {
            currLine = scanner.nextLine();
            taxInfo = unmarshallTax(currLine);
            taxDetails.put(taxInfo.getStateAbbrev(), taxInfo);   
        }
        scanner.close();
        
        
    }
    
    
    
    public Tax unmarshallTax(String taxInfo)
    {
        //Get string and tokenize it
        String [] taxAsToken = taxInfo.split(DELIMITER);
        
        //initialize new tax obj with first value
        
        Tax taxObj = new Tax(taxAsToken[0]);
        taxObj.setStateName(taxAsToken[1]);
        taxObj.setTaxRate(new BigDecimal(taxAsToken[2]));
        
        return taxObj;
        
    }

    @Override
    public Tax getTax(String stateAbbrev) throws FmPersistenceException {
        loadTax();
        
        return taxDetails.get(stateAbbrev);
    }

    @Override
    public List<Tax> getAllTaxes() throws FmPersistenceException {
        loadTax();
        
        return new ArrayList<>(taxDetails.values());
    }
    
    
    
}
