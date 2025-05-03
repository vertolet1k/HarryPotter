/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.harrypotter;

import com.harrypotter.dao.*;
import com.harrypotter.entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author vika
 */
public class GUI extends JFrame {
    public GUI() {
        setTitle("Магазин Оливандера");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Компоненты", createComponentPanel());
        tabbedPane.addTab("Покупатели", createCustomerPanel());
        tabbedPane.addTab("Палочки", createWandPanel());
        tabbedPane.addTab("Поставки", createSupplyPanel());

        JButton clearBtn = new JButton("Очистить все данные");
        clearBtn.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить все данные из базы?", "Подтвердите очистку", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                new WandDAO().findAll().forEach(w -> new WandDAO().delete(w));
                new SupplyDAO().findAll().forEach(s -> new SupplyDAO().delete(s));
                new CustomerDAO().findAll().forEach(c -> new CustomerDAO().delete(c));
                new ComponentDAO().findAll().forEach(c -> new ComponentDAO().delete(c));
                getContentPane().removeAll();
                JTabbedPane newTabs = new JTabbedPane();
                newTabs.addTab("Компоненты", createComponentPanel());
                newTabs.addTab("Покупатели", createCustomerPanel());
                newTabs.addTab("Палочки", createWandPanel());
                newTabs.addTab("Поставки", createSupplyPanel());
                getContentPane().setLayout(new BorderLayout());
                getContentPane().add(newTabs, BorderLayout.CENTER);
                getContentPane().add(clearBtn, BorderLayout.SOUTH);
                revalidate();
                repaint();
            }
        });
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(clearBtn, BorderLayout.SOUTH);
    }

    private void refreshComponentTable(DefaultTableModel model, List<com.harrypotter.entity.Component> list) {
        model.setRowCount(0);
        for (com.harrypotter.entity.Component c : list) {
            model.addRow(new Object[]{c.getId(), c.getName(), c.getType(), c.getQuantityInStock()});
        }
    }

    private JPanel createComponentPanel() {
        ComponentDAO dao = new ComponentDAO();
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        String[] columns = {"ID", "Название", "Тип", "В наличии"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        refreshComponentTable(model, dao.findAll());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel();
        JTextField nameField = new JTextField(10);
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"wood", "core"});
        JTextField qtyField = new JTextField(5);
        JButton addBtn = new JButton("Добавить");
        form.add(new JLabel("Название:"));
        form.add(nameField);
        form.add(new JLabel("Тип:"));
        form.add(typeBox);
        form.add(new JLabel("Количество:"));
        form.add(qtyField);
        form.add(addBtn);
        panel.add(form, BorderLayout.SOUTH);

        addBtn.addActionListener((ActionEvent e) -> {
            try {
                com.harrypotter.entity.Component c = new com.harrypotter.entity.Component();
                c.setName(nameField.getText());
                c.setType((String) typeBox.getSelectedItem());
                c.setQuantityInStock(Integer.parseInt(qtyField.getText()));
                dao.save(c);
                refreshComponentTable(model, dao.findAll());
                nameField.setText("");
                qtyField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private void refreshCustomerTable(DefaultTableModel model, List<Customer> list) {
        model.setRowCount(0);
        for (Customer c : list) {
            model.addRow(new Object[]{c.getId(), c.getName(), c.getContactInfo()});
        }
    }

    private JPanel createCustomerPanel() {
        CustomerDAO dao = new CustomerDAO();
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        String[] columns = {"ID", "Имя", "Контакт"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        refreshCustomerTable(model, dao.findAll());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel();
        JTextField nameField = new JTextField(10);
        JTextField contactField = new JTextField(10);
        JButton addBtn = new JButton("Добавить");
        form.add(new JLabel("Имя:"));
        form.add(nameField);
        form.add(new JLabel("Контакт:"));
        form.add(contactField);
        form.add(addBtn);
        panel.add(form, BorderLayout.SOUTH);

        addBtn.addActionListener((ActionEvent e) -> {
            try {
                Customer c = new Customer();
                c.setName(nameField.getText());
                c.setContactInfo(contactField.getText());
                dao.save(c);
                refreshCustomerTable(model, dao.findAll());
                nameField.setText("");
                contactField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private void refreshWandTable(DefaultTableModel model, List<Wand> list) {
        model.setRowCount(0);
        for (Wand w : list) {
            model.addRow(new Object[]{
                    w.getId(),
                    w.getWood() != null ? w.getWood().getName() : "",
                    w.getCore() != null ? w.getCore().getName() : "",
                    w.getDateCreated() != null ? w.getDateCreated().toString() : "",
                    w.isSold(),
                    w.getCustomer() != null ? w.getCustomer().getName() : ""
            });
        }
    }

    private JPanel createWandPanel() {
        WandDAO wandDAO = new WandDAO();
        ComponentDAO componentDAO = new ComponentDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        String[] columns = {"ID", "Древесина", "Сердцевина", "Дата", "Продана", "Покупатель"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        refreshWandTable(model, wandDAO.findAll());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel();
        JComboBox<com.harrypotter.entity.Component> woodBox = new JComboBox<>(componentDAO.findAll().stream().filter(c -> "wood".equals(c.getType())).toArray(com.harrypotter.entity.Component[]::new));
        JComboBox<com.harrypotter.entity.Component> coreBox = new JComboBox<>(componentDAO.findAll().stream().filter(c -> "core".equals(c.getType())).toArray(com.harrypotter.entity.Component[]::new));
        JComboBox<Wand> wandBox = new JComboBox<>(wandDAO.findAll().stream().filter(w -> !w.isSold()).toArray(Wand[]::new));
        wandBox.setSelectedIndex(-1);
        JComboBox<Customer> customerBox = new JComboBox<>(customerDAO.findAll().toArray(Customer[]::new));
        customerBox.setSelectedIndex(-1);
        JButton addBtn = new JButton("Создать палочку");
        form.add(new JLabel("Древесина:"));
        form.add(woodBox);
        form.add(new JLabel("Сердцевина:"));
        form.add(coreBox);
        form.add(addBtn);
        panel.add(form, BorderLayout.SOUTH);

        woodBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String text = value instanceof com.harrypotter.entity.Component ? ((com.harrypotter.entity.Component) value).getName() : "";
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });
        coreBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String text = value instanceof com.harrypotter.entity.Component ? ((com.harrypotter.entity.Component) value).getName() : "";
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });

        addBtn.addActionListener((ActionEvent e) -> {
            try {
                Wand w = new Wand();
                w.setWood((com.harrypotter.entity.Component) woodBox.getSelectedItem());
                w.setCore((com.harrypotter.entity.Component) coreBox.getSelectedItem());
                w.setDateCreated(LocalDate.now());
                w.setSold(false);
                wandDAO.save(w);
                refreshWandTable(model, wandDAO.findAll());
                // Обновить woodBox, coreBox, wandBox и customerBox после добавления новой палочки
                updateWandBox(woodBox, coreBox, componentDAO);
                updateWandBox(wandBox, wandDAO);
                wandBox.setSelectedIndex(-1);
                woodBox.setSelectedIndex(-1);
                coreBox.setSelectedIndex(-1);
                customerBox.setSelectedIndex(-1);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel sellForm = new JPanel();
        sellForm.add(new JLabel("Палочка:"));
        sellForm.add(wandBox);
        sellForm.add(new JLabel("Покупатель:"));
        sellForm.add(customerBox);
        JButton sellBtn = new JButton("Продать");
        sellForm.add(sellBtn);
        panel.add(sellForm, BorderLayout.NORTH);

        customerBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String text = value instanceof Customer ? ((Customer) value).getName() : "";
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });

        sellBtn.addActionListener((ActionEvent e) -> {
            try {
                Wand w = (Wand) wandBox.getSelectedItem();
                Customer c = (Customer) customerBox.getSelectedItem();
                if (w != null && c != null) {
                    w.setSold(true);
                    w.setCustomer(c);
                    wandDAO.update(w);
                    refreshWandTable(model, wandDAO.findAll());
                    updateWandBox(wandBox, wandDAO);
                    wandBox.setSelectedIndex(-1);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private void updateWandBox(JComboBox<com.harrypotter.entity.Component> woodBox, JComboBox<com.harrypotter.entity.Component> coreBox, ComponentDAO componentDAO) {
        woodBox.setModel(new DefaultComboBoxModel<>(componentDAO.findAll().stream().filter(c -> "wood".equals(c.getType())).toArray(com.harrypotter.entity.Component[]::new)));
        coreBox.setModel(new DefaultComboBoxModel<>(componentDAO.findAll().stream().filter(c -> "core".equals(c.getType())).toArray(com.harrypotter.entity.Component[]::new)));
        woodBox.setSelectedIndex(-1);
        coreBox.setSelectedIndex(-1);
    }

    private void updateWandBox(JComboBox<Wand> wandBox, WandDAO wandDAO) {
        wandBox.setModel(new DefaultComboBoxModel<>(wandDAO.findAll().stream().filter(wa -> !wa.isSold()).toArray(Wand[]::new)));
        wandBox.setSelectedIndex(-1);
    }

    private void refreshSupplyTable(DefaultTableModel model, List<Supply> list) {
        model.setRowCount(0);
        for (Supply s : list) {
            model.addRow(new Object[]{
                    s.getId(),
                    s.getComponent() != null ? s.getComponent().getName() : "",
                    s.getDate() != null ? s.getDate().toString() : "",
                    s.getQuantity()
            });
        }
    }

    private JPanel createSupplyPanel() {
        SupplyDAO supplyDAO = new SupplyDAO();
        ComponentDAO componentDAO = new ComponentDAO();
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        String[] columns = {"ID", "Компонент", "Дата", "Количество"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        refreshSupplyTable(model, supplyDAO.findAll());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel();
        JComboBox<com.harrypotter.entity.Component> compBox = new JComboBox<>(componentDAO.findAll().toArray(com.harrypotter.entity.Component[]::new));
        compBox.setSelectedIndex(-1);
        JTextField qtyField = new JTextField(5);
        JButton addBtn = new JButton("Добавить поставку");
        form.add(new JLabel("Компонент:"));
        form.add(compBox);
        form.add(new JLabel("Количество:"));
        form.add(qtyField);
        form.add(addBtn);
        panel.add(form, BorderLayout.SOUTH);

        compBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String text = value instanceof com.harrypotter.entity.Component ? ((com.harrypotter.entity.Component) value).getName() : "";
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });

        addBtn.addActionListener((ActionEvent e) -> {
            try {
                Supply s = new Supply();
                s.setComponent((com.harrypotter.entity.Component) compBox.getSelectedItem());
                s.setDate(LocalDate.now());
                s.setQuantity(Integer.parseInt(qtyField.getText()));
                supplyDAO.save(s);
                refreshSupplyTable(model, supplyDAO.findAll());
                compBox.setModel(new DefaultComboBoxModel<>(componentDAO.findAll().toArray(com.harrypotter.entity.Component[]::new)));
                compBox.setSelectedIndex(-1);
                qtyField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
} 