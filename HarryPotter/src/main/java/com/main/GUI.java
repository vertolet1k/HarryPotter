/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main;

import com.dao.SupplyDAO;
import com.dao.WandDAO;
import com.entity.Customer;
import com.dao.*;
import com.entity.*;

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

    private void refreshComponentTable(DefaultTableModel model, List<com.entity.Component> list) {
    model.setRowCount(0);
    for (com.entity.Component c : list) {
        if (c != null) {
            model.addRow(new Object[]{c.getId(), c.getName(), c.getType(), c.getQuantityInStock()});
        }
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
                String name = nameField.getText().trim();
                String type = (String) typeBox.getSelectedItem();
                String qtyStr = qtyField.getText().trim();
                if (name.isEmpty() || type == null || qtyStr.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Заполните все поля!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int qty = Integer.parseInt(qtyStr);
                com.entity.Component c = new com.entity.Component();
                c.setName(name);
                c.setType(type);
                c.setQuantityInStock(qty);
                dao.save(c);
                refreshComponentTable(model, dao.findAll());
                nameField.setText("");
                qtyField.setText("");
                // Обновить ComboBox в других панелях (палочки, поставки)
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private void refreshCustomerTable(DefaultTableModel model, List<Customer> list) {
        model.setRowCount(0);
        for (Customer c : list) {
            if (c != null) {
                model.addRow(new Object[]{c.getId(), c.getName(), c.getContactInfo()});
            }
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
                String name = nameField.getText().trim();
                String contact = contactField.getText().trim();
                if (name.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Заполните все поля!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Customer c = new Customer();
                c.setName(name);
                c.setContactInfo(contact);
                dao.save(c);
                refreshCustomerTable(model, dao.findAll());
                nameField.setText("");
                contactField.setText("");
                // Обновить ComboBox покупателей в createWandPanel
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private void refreshWandTable(DefaultTableModel model, List<Wand> list) {
        model.setRowCount(0);
        for (Wand w : list) {
            if (w != null) {
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
        JComboBox<com.entity.Component> woodBox = new JComboBox<>();
        JComboBox<com.entity.Component> coreBox = new JComboBox<>();
        updateWandBox(woodBox, coreBox, componentDAO);
        JComboBox<Wand> wandBox = new JComboBox<>();
        updateWandBox(wandBox, wandDAO);
        wandBox.setSelectedIndex(-1);
        JComboBox<Customer> customerBox = new JComboBox<>();
        updateCustomerBox(customerBox, customerDAO);
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
                String text = value instanceof com.entity.Component ? ((com.entity.Component) value).getName() : "";
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });
        coreBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String text = value instanceof com.entity.Component ? ((com.entity.Component) value).getName() : "";
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });

        addBtn.addActionListener((ActionEvent e) -> {
            try {
                com.entity.Component wood = (com.entity.Component) woodBox.getSelectedItem();
                com.entity.Component core = (com.entity.Component) coreBox.getSelectedItem();
                if (wood == null || core == null) {
                    JOptionPane.showMessageDialog(panel, "Выберите древесину и сердцевину!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Wand w = new Wand();
                w.setWood(wood);
                w.setCore(core);
                w.setDateCreated(LocalDate.now());
                w.setSold(false);
                wandDAO.save(w);
                refreshWandTable(model, wandDAO.findAll());
                updateWandBox(woodBox, coreBox, componentDAO);
                updateWandBox(wandBox, wandDAO);
                updateCustomerBox(customerBox, customerDAO);
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

    private void updateWandBox(JComboBox<com.entity.Component> woodBox, JComboBox<com.entity.Component> coreBox, ComponentDAO componentDAO) {
        woodBox.setModel(new DefaultComboBoxModel<>(componentDAO.findAll().stream().filter(c -> c != null && "wood".equals(c.getType())).toArray(com.entity.Component[]::new)));
        coreBox.setModel(new DefaultComboBoxModel<>(componentDAO.findAll().stream().filter(c -> c != null && "core".equals(c.getType())).toArray(com.entity.Component[]::new)));
        woodBox.setSelectedIndex(-1);
        coreBox.setSelectedIndex(-1);
    }

    private void updateWandBox(JComboBox<Wand> wandBox, WandDAO wandDAO) {
        wandBox.setModel(new DefaultComboBoxModel<>(wandDAO.findAll().stream().filter(wa -> wa != null && !wa.isSold()).toArray(Wand[]::new)));
        wandBox.setSelectedIndex(-1);
    }

    private void updateCustomerBox(JComboBox<Customer> customerBox, CustomerDAO customerDAO) {
        customerBox.setModel(new DefaultComboBoxModel<>(customerDAO.findAll().stream().filter(c -> c != null).toArray(Customer[]::new)));
        customerBox.setSelectedIndex(-1);
    }

    private void refreshSupplyTable(DefaultTableModel model, List<Supply> list) {
        model.setRowCount(0);
        for (Supply s : list) {
            if (s != null) {
                model.addRow(new Object[]{
                        s.getId(),
                        s.getComponent() != null ? s.getComponent().getName() : "",
                        s.getDate() != null ? s.getDate().toString() : "",
                        s.getQuantity()
                });
            }
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
        JComboBox<com.entity.Component> compBox = new JComboBox<>();
        compBox.setModel(new DefaultComboBoxModel<>(componentDAO.findAll().stream().filter(c -> c != null).toArray(com.entity.Component[]::new)));
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
                String text = value instanceof com.entity.Component ? ((com.entity.Component) value).getName() : "";
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });

        addBtn.addActionListener((ActionEvent e) -> {
            try {
                com.entity.Component comp = (com.entity.Component) compBox.getSelectedItem();
                String qtyStr = qtyField.getText().trim();
                if (comp == null || qtyStr.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Выберите компонент и введите количество!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int qty = Integer.parseInt(qtyStr);
                Supply s = new Supply();
                s.setComponent(comp);
                s.setDate(LocalDate.now());
                s.setQuantity(qty);
                supplyDAO.save(s);
                refreshSupplyTable(model, supplyDAO.findAll());
                compBox.setModel(new DefaultComboBoxModel<>(componentDAO.findAll().stream().filter(c -> c != null).toArray(com.entity.Component[]::new)));
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