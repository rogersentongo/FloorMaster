/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rntongo.fm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author rogersentongo
 */
public class Order {
    
    //Private Member methods
    private LocalDate date;
    private int orderNumber;
    private String customerName;
    private String productType;
    private String state;
    private BigDecimal taxRate;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal Total;
    
    public Order()
    {
        
    }

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.orderNumber;
        hash = 41 * hash + Objects.hashCode(this.customerName);
        hash = 41 * hash + Objects.hashCode(this.productType);
        hash = 41 * hash + Objects.hashCode(this.taxRate);
        hash = 41 * hash + Objects.hashCode(this.area);
        hash = 41 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 41 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSquareFoot, other.laborCostPerSquareFoot)) {
            return false;
        }
        return true;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }
    
    
    
    

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public BigDecimal getArea() {
        return area;
    }
    
    public void calcLaborCost() {
      
             this.laborCost = getArea().multiply(getLaborCostPerSquareFoot());
        
       
        
        
    }

    
    public void calcMaterialCost() {
        this.materialCost = getArea().multiply(getCostPerSquareFoot());
        
        
    }

    
    public void calcTax() {
        
        BigDecimal sumCost = getMaterialCost().add(getLaborCost());
        BigDecimal divisor = new BigDecimal("100");
        BigDecimal taxRateEffective = getTaxRate().divide(divisor);
        System.out.println(taxRateEffective.toString());
        this.tax = sumCost.multiply(taxRateEffective);
    }

    public void calcTotal() {
        
        this.Total= getMaterialCost().add(getLaborCost().add(getTax()));
        
        
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return Total;
    }

    public void setTotal(BigDecimal Total) {
        this.Total = Total;
    }
    
    
    

    
    
    
    
    
}
