/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;
/**
 *
 * @author vika
 */
public class InventorySystem {
    public static void main(String[] args) {
        try {
            DBManager.initDB();
        } catch (Exception e) {
            System.out.println("Ошибка инициализации БД: " + e.getMessage());
            return;
        }
        InventoryManager manager = new InventoryManager();
        javax.swing.SwingUtilities.invokeLater(() -> {
            InventoryGUI gui = new InventoryGUI(manager);
            gui.setVisible(true);
        });
    }
} 