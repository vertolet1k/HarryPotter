/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author vika
 */
public class InventoryManager {
    private final WandDAO wandDAO = new WandDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ComponentSupplyDAO supplyDAO = new ComponentSupplyDAO();

    public boolean addWand(String wood, String core) {
        if (getAvailableComponent("wood", wood) <= 0 || getAvailableComponent("core", core) <= 0) {
            return false;
        }
        try {
            wandDAO.addWand(wood, core);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sellWand(int wandId, String customerName) {
        try {
            boolean sold = wandDAO.sellWand(wandId, customerName);
            if (sold) {
                customerDAO.addCustomer(customerName, wandId);
            }
            return sold;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addSupply(String type, String name, int qty, LocalDate date) {
        try {
            supplyDAO.addSupply(type, name, qty, date);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Wand> getWands() {
        try {
            return wandDAO.getAllWands();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Customer> getCustomers() {
        try {
            return customerDAO.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<ComponentSupply> getSupplies() {
        try {
            return supplyDAO.getAllSupplies();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void clearAll() {
        try {
            DBManager.getConnection().createStatement().executeUpdate("DELETE FROM wands");
            DBManager.getConnection().createStatement().executeUpdate("DELETE FROM customers");
            DBManager.getConnection().createStatement().executeUpdate("DELETE FROM component_supplies");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Получить доступное количество компонента по типу и названию
    public int getAvailableComponent(String type, String name) {
        int supplied = 0;
        try {
            for (ComponentSupply s : getSupplies()) {
                if (s.getComponentType().equalsIgnoreCase(type) && s.getName().equalsIgnoreCase(name)) {
                    supplied += s.getQuantity();
                }
            }
            int used = 0;
            if (type.equalsIgnoreCase("wood")) {
                for (Wand w : getWands()) {
                    if (w.getWoodType().equalsIgnoreCase(name)) used++;
                }
            } else if (type.equalsIgnoreCase("core")) {
                for (Wand w : getWands()) {
                    if (w.getCoreType().equalsIgnoreCase(name)) used++;
                }
            }
            return supplied - used;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Получить список всех компонентов с остатками
    public List<Component> getComponentsList() {
        Map<String, Component> map = new LinkedHashMap<>();
        for (ComponentSupply s : getSupplies()) {
            String key = s.getComponentType().toLowerCase() + ":" + s.getName().toLowerCase();
            map.putIfAbsent(key, new Component(s.getComponentType(), s.getName(), 0));
            map.get(key).setQuantity(map.get(key).getQuantity() + s.getQuantity());
        }
        for (Wand w : getWands()) {
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