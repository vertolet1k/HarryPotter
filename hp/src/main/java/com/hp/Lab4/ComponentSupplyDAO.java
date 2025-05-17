/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;
/**
 *
 * @author vika
 */
public class ComponentSupplyDAO {
    public void addSupply(String type, String name, int qty, LocalDate date) throws SQLException {
        String sql = "INSERT INTO component_supplies (componentType, name, quantity, supplyDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, type);
            ps.setString(2, name);
            ps.setInt(3, qty);
            ps.setString(4, date.toString());
            ps.executeUpdate();
        }
    }

    public List<ComponentSupply> getAllSupplies() throws SQLException {
        List<ComponentSupply> list = new ArrayList<>();
        String sql = "SELECT * FROM component_supplies";
        try (Statement st = DBManager.getConnection().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ComponentSupply(
                    rs.getInt("id"),
                    rs.getString("componentType"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    LocalDate.parse(rs.getString("supplyDate"))
                ));
            }
        }
        return list;
    }
} 