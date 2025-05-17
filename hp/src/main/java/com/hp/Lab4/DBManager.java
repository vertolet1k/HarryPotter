/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;

import java.sql.*;
/**
 *
 * @author vika
 */
public class DBManager {
    private static final String DB_URL = "jdbc:sqlite:inventory.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    public static void initDB() throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS wands (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    woodType TEXT NOT NULL,
                    coreType TEXT NOT NULL,
                    owner TEXT,
                    isSold INTEGER NOT NULL
                );
            """);
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS customers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    wandId INTEGER,
                    FOREIGN KEY (wandId) REFERENCES wands(id)
                );
            """);
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS component_supplies (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    componentType TEXT NOT NULL,
                    name TEXT NOT NULL,
                    quantity INTEGER NOT NULL,
                    supplyDate TEXT NOT NULL
                );
            """);
        }
    }
} 