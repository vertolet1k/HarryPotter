/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.harrypotter.dao;

import com.harrypotter.HibernateUtil;
import com.harrypotter.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
/**
 *
 * @author vika
 */
public class CustomerDAO {
    public void save(Customer customer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(customer);
        tx.commit();
        session.close();
    }

    public void update(Customer customer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(customer);
        tx.commit();
        session.close();
    }

    public void delete(Customer customer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(customer);
        tx.commit();
        session.close();
    }

    public Customer findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Customer customer = session.get(Customer.class, id);
        session.close();
        return customer;
    }

    public List<Customer> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Customer> list = session.createQuery("from Customer", Customer.class).list();
        session.close();
        return list;
    }
} 