/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;
/**
 *
 * @author vika
 */
public class Customer implements java.io.Serializable {
    private int id;
    private String name;
    private int wandId;

    public Customer(int id, String name, int wandId) {
        this.id = id;
        this.name = name;
        this.wandId = wandId;
    }

    public int getId() { 
        return id; 
    }
    public String getName() { 
        return name; 
    }
    public int getWandId() { 
        return wandId; 
    }
} 
