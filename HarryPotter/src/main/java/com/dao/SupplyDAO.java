/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.main.HibernateUtil;
import com.entity.Supply;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
/**
 *
 * @author vika
 */
public class SupplyDAO {
    public void save(Supply supply) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(supply);
        tx.commit();
        session.close();
    }

    public void update(Supply supply) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(supply);
        tx.commit();
        session.close();
    }

    public void delete(Supply supply) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(supply);
        tx.commit();
        session.close();
    }

    public Supply findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Supply supply = session.get(Supply.class, id);
        session.close();
        return supply;
    }

    public List<Supply> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Supply> list = session.createQuery("from Supply", Supply.class).list();
        session.close();
        return list;
    }
} 