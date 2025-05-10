/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

import javax.persistence.*;
/**
 *
 * @author vika
 */
@Entity
@Table(name = "components")
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @Column(name = "quantity_in_stock")
    private int quantityInStock;

    public Component() {
    }

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
    public String getType() { 
        return type; 
    }
    public void setType(String type) { 
        this.type = type; 
    }
    public int getQuantityInStock() { 
        return quantityInStock; 
    }
    public void setQuantityInStock(int quantityInStock) { 
        this.quantityInStock = quantityInStock; 
    }
} 