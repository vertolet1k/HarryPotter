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
public class WandDAO {
    public void addWand(String wood, String core) throws SQLException {
        String sql = "INSERT INTO wands (woodType, coreType, isSold) VALUES (?, ?, 0)";
        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, wood);
            ps.setString(2, core);
            ps.executeUpdate();
        }
    }

    public List<Wand> getAllWands() throws SQLException {
        List<Wand> list = new ArrayList<>();
        String sql = "SELECT * FROM wands";
        try (Statement st = DBManager.getConnection().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Wand w = new Wand(
                    rs.getInt("id"),
                    rs.getString("woodType"),
                    rs.getString("coreType")
                );
                if (rs.getInt("isSold") == 1) {
                    w.sellTo(rs.getString("owner"));
                }
                list.add(w);
            }
        }
        return list;
    }

    public boolean sellWand(int wandId, String customerName) throws SQLException {
        String sql = "UPDATE wands SET isSold=1, owner=? WHERE id=? AND isSold=0";
        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, customerName);
            ps.setInt(2, wandId);
            return ps.executeUpdate() > 0;
        }
    }
} 