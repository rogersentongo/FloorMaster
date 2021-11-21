/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.view;

import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author rogersentongo
 */
@Component
public class UserIOConsoleImpl implements UserIO {
    
    
    
    final private Scanner user = new Scanner(System.in);
    
    @Override
    public void print(String message){
        
        
        System.out.println(message);
    }
    
    
    @Override
    public String readString(String prompt){
        System.out.println(prompt);
        return user.nextLine();
    }
    
    
    
    
    //reads int from user
    @Override
    public int readInt(String prompt){
        
        System.out.println(prompt);
        return Integer.parseInt(user.nextLine());
    }
    
    
    
    @Override
    public int readInt(String prompt, int min, int max){
        System.out.println(prompt);
        int result=0;
        do {
            
            result = Integer.parseInt(user.nextLine());
            if(result < min || result > max)
                System.out.println("Your number is not within range");
            
        } while( result <min || result >max);
        
        
        
        
        return result;
    }
    
    
    
    
    @Override
    public double readDouble(String prompt){
        
        System.out.println(prompt);
        
        return Double.parseDouble(user.nextLine());
        
        
    }
    
    
    
    @Override
    public double readDouble(String prompt, double min, double max)
    {
        System.out.println(prompt);
        double result=0;
        
        do{
            result = Double.parseDouble(user.nextLine());
            
            if(result <min || result > max)
                System.out.println("Your number is not within range");
            
        }while(result < min || result > max );
        
        return result;
        
    }
    
    
    @Override
    public float readFloat(String prompt){
        
        System.out.println(prompt);
        
        return Float.parseFloat(user.nextLine());
        
    }
    
    
    @Override
    public float readFloat(String prompt, float min, float max){
        
        System.out.println(prompt);
        float result=0;
        
        do{
            result = Float.parseFloat(user.nextLine());
            
            if(result <min || result > max)
                System.out.println("Your number is not within range");
            
        }while(result < min || result > max );
        
        return result;
        
    }
    
    
    
    @Override
    public long readLong(String prompt){
        System.out.println(prompt);
        
        return Long.parseLong(user.nextLine());
    }
    
    @Override
    public long readLong(String prompt, long min, long max){
        
        System.out.println(prompt);
        long result=0;
        
        do{
            result = Long.parseLong(user.nextLine());
            
            if(result <min || result > max)
                System.out.println("Your number is not within range");
            
        }while(result < min || result > max );
        
        return result;
    }
}
