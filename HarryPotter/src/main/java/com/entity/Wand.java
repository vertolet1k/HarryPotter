/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

import javax.persistence.*;
import java.time.LocalDate;
/**
 *
 * @author vika
 */
@Entity
@Table(name = "wands")
public class Wand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "wood_id")
    private Component wood;
    @ManyToOne
    @JoinColumn(name = "core_id")
    private Component core;
    private LocalDate dateCreated;
    private boolean isSold;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Wand() {}

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public Component getWood() { 
        return wood; 
    }
    public void setWood(Component wood) { 
        this.wood = wood; 
    }
    public Component getCore() { 
        return core; 
    }
    public void setCore(Component core) { 
        this.core = core; 
    }
    public LocalDate getDateCreated() { 
        return dateCreated; 
    }
    public void setDateCreated(LocalDate dateCreated) { 
        this.dateCreated = dateCreated; 
    }
    public boolean isSold() { 
        return isSold; 
    }
    public void setSold(boolean sold) { 
        isSold = sold; 
    }
    public Customer getCustomer() { 
        return customer; 
    }
    public void setCustomer(Customer customer) { 
        this.customer = customer; 
    }

    @Override
    public String toString() {
        String wood = getWood() != null ? getWood().getName() : "";
        String core = getCore() != null ? getCore().getName() : "";
        return "ID: " + getId() + ", " + wood + " / " + core;
    }
} 