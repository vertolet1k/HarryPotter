/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;

import java.io.*;
/**
 *
 * @author vika
 */
public class InventorySystem {
    public static void main(String[] args) {
        InventoryManager manager = null;
        try {
            manager = DataPersistence.load("inventory.dat");
            System.out.println("Данные успешно загружены.");
        } catch (Exception e) {
            manager = new InventoryManager();
            System.out.println("Начата новая сессия.");
        }
        InventoryManager finalManager = manager;
        javax.swing.SwingUtilities.invokeLater(() -> {
            InventoryGUI gui = new InventoryGUI(finalManager);
            gui.setVisible(true);
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                DataPersistence.save(finalManager, "inventory.dat");
                System.out.println("Данные сохранены.");
            } catch (IOException e) {
                System.out.println("Ошибка сохранения данных: " + e.getMessage());
            }
        }));
    }
} 