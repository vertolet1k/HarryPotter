/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;

import java.io.Serializable;
/**
 *
 * @author vika
 */
public class Component implements Serializable {
    private String type; // wood/core
    private String name;
    private int quantity;

    public Component(String type, String name, int quantity) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
    }

    public String getType() { 
        return type; 
    }
    public String getName() { 
        return name; 
    }
    public int getQuantity() { 
        return quantity; 
    }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
} 