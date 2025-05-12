/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;

import java.util.*;
import java.time.LocalDate;
/**
 *
 * @author vika
 */
public class InventoryManager  {
    private List<Wand> wands = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<ComponentSupply> supplies = new ArrayList<>();
    private int wandCounter = 1;
    private int customerCounter = 1;
    private int supplyCounter = 1;

    public boolean addWand(String wood, String core) {
        if (getAvailableComponent("wood", wood) <= 0 || getAvailableComponent("core", core) <= 0) {
            return false;
        }
        wands.add(new Wand(wandCounter++, wood, core));
        return true;
    }

    public boolean sellWand(int wandId, String customerName) {
        for (Wand wand : wands) {
            if (wand.getId() == wandId && !wand.isSold()) {
                wand.sellTo(customerName);
                customers.add(new Customer(customerCounter++, customerName, wandId));
                return true;
            }
        }
        return false;
    }

    public void addSupply(String type, String name, int qty, LocalDate date) {
        supplies.add(new ComponentSupply(supplyCounter++, type, name, qty, date));
    }

    public List<Wand> getWands() { 
        return wands; 
    }
    public List<Customer> getCustomers() { 
        return customers; 
    }
    public List<ComponentSupply> getSupplies() { 
        return supplies;
    }

    public int getAvailableComponent(String type, String name) {
        int supplied = 0;
        for (ComponentSupply s : supplies) {
            if (s.getComponentType().equalsIgnoreCase(type) && s.getName().equalsIgnoreCase(name)) {
                supplied += s.getQuantity();
            }
        }
        int used = 0;
        if (type.equalsIgnoreCase("wood")) {
            for (Wand w : wands) {
                if (w.getWoodType().equalsIgnoreCase(name)) used++;
            }
        } else if (type.equalsIgnoreCase("core")) {
            for (Wand w : wands) {
                if (w.getCoreType().equalsIgnoreCase(name)) used++;
            }
        }
        return supplied - used;
    }

    public void clearAll() {
        wands.clear();
        customers.clear();
        supplies.clear();
        wandCounter = 1;
        customerCounter = 1;
        supplyCounter = 1;
    }

    public List<Component> getComponentsList() {
        Map<String, Component> map = new LinkedHashMap<>();
        // Считаем поставки
        for (ComponentSupply s : supplies) {
            String key = s.getComponentType().toLowerCase() + ":" + s.getName().toLowerCase();
            map.putIfAbsent(key, new Component(s.getComponentType(), s.getName(), 0));
            map.get(key).setQuantity(map.get(key).getQuantity() + s.getQuantity());
        }
        // Считаем расход на палочки
        for (Wand w : wands) {
            String woodKey = "wood:" + w.getWoodType().toLowerCase();
            String coreKey = "core:" + w.getCoreType().toLowerCase();
            if (map.containsKey(woodKey)) {
                map.get(woodKey).setQuantity(map.get(woodKey).getQuantity() - 1);
            }
            if (map.containsKey(coreKey)) {
                map.get(coreKey).setQuantity(map.get(coreKey).getQuantity() - 1);
            }
        }
        return new ArrayList<>(map.values());
    }
} 