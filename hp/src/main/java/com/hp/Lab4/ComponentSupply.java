/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;

import java.time.LocalDate;
/**
 *
 * @author vika
 */
public class ComponentSupply implements java.io.Serializable {
    private int id;
    private String componentType; // wood/core
    private String name;
    private int quantity;
    private LocalDate supplyDate;

    public ComponentSupply(int id, String componentType, String name, int quantity, LocalDate supplyDate) {
        this.id = id;
        this.componentType = componentType;
        this.name = name;
        this.quantity = quantity;
        this.supplyDate = supplyDate;
    }

    public int getId() { 
        return id; 
    }
    public String getComponentType() { 
        return componentType; 
    }
    public String getName() { 
        return name; 
    }
    public int getQuantity() { 
        return quantity; 
    }
    public LocalDate getSupplyDate() { 
        return supplyDate; 
    }
} 