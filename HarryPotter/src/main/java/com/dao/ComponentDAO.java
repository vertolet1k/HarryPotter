/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.main.HibernateUtil;
import com.entity.Component;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
/**
 *
 * @author vika
 */
public class ComponentDAO {
    public void save(Component component) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(component);
        tx.commit();
        session.close();
    }

    public void update(Component component) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(component);
        tx.commit();
        session.close();
    }

    public void delete(Component component) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(component);
        tx.commit();
        session.close();
    }

    public Component findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Component component = session.get(Component.class, id);
        session.close();
        return component;
    }

    public List<Component> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Component> list = session.createQuery("from Component", Component.class).list();
        session.close();
        return list;
    }
} 