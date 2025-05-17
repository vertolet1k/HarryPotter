/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;

import java.sql.*;
import java.util.*;
/**
 *
 * @author vika
 */
public class CustomerDAO {
    public void addCustomer(String name, int wandId) throws SQLException {
        String sql = "INSERT INTO customers (name, wandId) VALUES (?, ?)";
        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, wandId);
            ps.executeUpdate();
        }
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Statement st = DBManager.getConnection().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("wandId")
                ));
            }
        }
        return list;
    }
} 