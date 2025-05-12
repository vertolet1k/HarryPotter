/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;
/**
 *
 * @author vika
 */
public class Wand implements java.io.Serializable {
    private int id;
    private String woodType;
    private String coreType;
    private String owner; // null, если не продана
    private boolean isSold;

    public Wand(int id, String woodType, String coreType) {
        this.id = id;
        this.woodType = woodType;
        this.coreType = coreType;
        this.owner = null;
        this.isSold = false;
    }

    public int getId() { return id; }
    public String getWoodType() { return woodType; }
    public String getCoreType() { return coreType; }
    public String getOwner() { return owner; }
    public boolean isSold() { return isSold; }

    public void sellTo(String owner) {
        this.owner = owner;
        this.isSold = true;
    }

    public void reset() {
        this.owner = null;
        this.isSold = false;
    }
} 
