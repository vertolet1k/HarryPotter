/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.main.HibernateUtil;
import com.entity.Wand;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
/**
 *
 * @author vika
 */
public class WandDAO {
    public void save(Wand wand) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(wand);
        tx.commit();
        session.close();
    }

    public void update(Wand wand) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(wand);
        tx.commit();
        session.close();
    }

    public void delete(Wand wand) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(wand);
        tx.commit();
        session.close();
    }

    public Wand findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Wand wand = session.get(Wand.class, id);
        session.close();
        return wand;
    }

    public List<Wand> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Wand> list = session.createQuery("from Wand", Wand.class).list();
        session.close();
        return list;
    }
} 