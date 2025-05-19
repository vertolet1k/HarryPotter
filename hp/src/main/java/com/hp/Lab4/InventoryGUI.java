/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hp.Lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
/**
 *
 * @author vika
 */
public class InventoryGUI extends JFrame {
    private InventoryManager manager;

    private static final String[] ALLOWED_CORES = {
        "Сердечная струна Дракона", "Перо Феникса", "Волосы из хвоста единорога", "Волосы вейлы", "Фестральные волосы на хвосте",
        "Усы тролля", "Коралловый", "СтебельДиттани", "Перо из хвоста Громовой птицы", "Кошачья шерсть вампуса",
        "Хребет Белого Речного Монстра", "Грубые волосы", "Рогатый Змеиный рог", "Сердечная струна Snallygaster",
        "Рога джекалопа", "Усы Kneazle", "Волосы келпи", "Рог василиска", "Волосы Курупиры", "Волосы африканской русалки",
        "Сказочное крыло", "Оболочка"
    };

    private static final String[] ALLOWED_WOODS = {
        "Акация", "Английский дуб", "Боярышник", "Бузина", "Бук", "Виноградная лоза", "Вишня", "Вяз", "Граб", "Грецкий орех", 
        "Груша", "Ель", "Ива", "Каштан", "Кедр", "Кипарис", "Красное дерево", "Красный дуб", "Лавр", "Липа", "Лиственница", 
        "Ольха", "Орешник", "Осина", "Остролист", "Пихта", "Рябина", "Сосна", "Терновник", "Тис", "Тополь", "Чёрное (эбеновое) дерево",
        "Чёрный орешник", "Яблоня", "Явор", "Ясень"
    };

    public InventoryGUI(InventoryManager manager) {
        super("Лавка Оливандера");
        this.manager = manager;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        JButton addWandBtn = new JButton("Добавить палочку");
        JButton sellWandBtn = new JButton("Продать палочку");
        JButton addSupplyBtn = new JButton("Добавить поставку");
        JButton showWandsBtn = new JButton("Показать палочки");
        JButton showCustomersBtn = new JButton("Показать покупателей");
        JButton showSuppliesBtn = new JButton("Показать поставки");
        JButton showComponentsBtn = new JButton("Показать компоненты");
        JButton clearBtn = new JButton("Очистить все данные");

        buttonPanel.add(addWandBtn);
        buttonPanel.add(sellWandBtn);
        buttonPanel.add(addSupplyBtn);
        buttonPanel.add(showWandsBtn);
        buttonPanel.add(showCustomersBtn);
        buttonPanel.add(showSuppliesBtn);
        buttonPanel.add(showComponentsBtn);

        add(buttonPanel, BorderLayout.CENTER);
        add(clearBtn, BorderLayout.SOUTH);

        addWandBtn.addActionListener(e -> addWandDialog());
        sellWandBtn.addActionListener(e -> sellWandDialog());
        addSupplyBtn.addActionListener(e -> addSupplyDialog());
        showWandsBtn.addActionListener(e -> showWandsDialog());
        showCustomersBtn.addActionListener(e -> showCustomersDialog());
        showSuppliesBtn.addActionListener(e -> showSuppliesDialog());
        showComponentsBtn.addActionListener(e -> showComponentsDialog());
        clearBtn.addActionListener(e -> {
            manager.clearAll();
            JOptionPane.showMessageDialog(this, "Данные очищены.");
        });
    }

    private void addWandDialog() {
        // Получаем доступные древесины только из разрешённых и с остатком
        java.util.List<String> woodList = new java.util.ArrayList<>();
        for (String wood : ALLOWED_WOODS) {
            int qty = manager.getAvailableComponent("wood", wood);
            if (qty > 0) woodList.add(wood);
        }
        JComboBox<String> woodBox = new JComboBox<>(woodList.toArray(new String[0]));

        // Получаем доступные сердцевины из разрешенного списка и с остатком
        java.util.List<String> coreList = new java.util.ArrayList<>();
        for (String core : ALLOWED_CORES) {
            int qty = manager.getAvailableComponent("core", core);
            if (qty > 0) coreList.add(core);
        }
        JComboBox<String> coreBox = new JComboBox<>(coreList.toArray(new String[0]));

        Object[] fields = {"Древесина:", woodBox, "Сердцевина:", coreBox};
        int res = JOptionPane.showConfirmDialog(this, fields, "Добавить палочку", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            String wood = (String) woodBox.getSelectedItem();
            String core = (String) coreBox.getSelectedItem();
            boolean success = manager.addWand(wood, core);
            if (success) {
                JOptionPane.showMessageDialog(this, "Палочка добавлена.");
            } else {
                JOptionPane.showMessageDialog(this, "Недостаточно компонентов для изготовления палочки!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sellWandDialog() {
        java.util.List<Wand> unsold = new java.util.ArrayList<>();
        for (Wand w : manager.getWands()) {
            if (!w.isSold()) unsold.add(w);
        }
        String[] wandOptions = unsold.stream().map(w -> w.getId() + ": " + w.getWoodType() + ", " + w.getCoreType()).toArray(String[]::new);
        JComboBox<String> wandBox = new JComboBox<>(wandOptions);
        JTextField nameField = new JTextField();
        Object[] fields = {"Палочка:", wandBox, "Имя покупателя:", nameField};
        int res = JOptionPane.showConfirmDialog(this, fields, "Продать палочку", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(((String) wandBox.getSelectedItem()).split(":")[0]);
                String name = nameField.getText();
                if (manager.sellWand(id, name)) {
                    JOptionPane.showMessageDialog(this, "Палочка продана.");
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка продажи (возможно, палочка уже продана или не найдена).", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addSupplyDialog() {
        String[] typeOptions = {"wood", "core"};
        JComboBox<String> typeBox = new JComboBox<>(typeOptions);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Тип компонента (wood/core):"));
        panel.add(typeBox);
        panel.add(new JLabel("Название:"));
        JComboBox<String> coreNameBox = new JComboBox<>(ALLOWED_CORES);
        JComboBox<String> woodNameBox = new JComboBox<>(ALLOWED_WOODS);
        panel.add(woodNameBox); // по умолчанию
        panel.add(new JLabel("Количество:"));
        JTextField qtyField = new JTextField();
        panel.add(qtyField);
        panel.add(new JLabel("Дата (yyyy-mm-dd):"));
        JTextField dateField = new JTextField();
        panel.add(dateField);

        // Слушатель для смены типа компонента
        typeBox.addActionListener(e -> {
            panel.remove(3); // удаляем текущее поле названия
            if (typeBox.getSelectedItem().equals("core")) {
                panel.add(coreNameBox, 3);
            } else {
                panel.add(woodNameBox, 3);
            }
            panel.revalidate();
            panel.repaint();
        });

        int res = JOptionPane.showConfirmDialog(this, panel, "Добавить поставку", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                String type = (String) typeBox.getSelectedItem();
                String name;
                if (type.equals("core")) {
                    name = (String) coreNameBox.getSelectedItem();
                } else {
                    name = (String) woodNameBox.getSelectedItem();
                }
                int qty = Integer.parseInt(qtyField.getText());
                if (qty <= 0) {
                    JOptionPane.showMessageDialog(this, "В минус уйти нельзя!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalDate date = LocalDate.parse(dateField.getText());
                manager.addSupply(type, name, qty, date);
                JOptionPane.showMessageDialog(this, "Поставка добавлена.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка ввода данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showWandsDialog() {
        StringBuilder sb = new StringBuilder();
        for (Wand w : manager.getWands()) {
            sb.append(String.format("ID: %d, Древесина: %s, Сердцевина: %s, Владелец: %s, Продана: %s\n",
                w.getId(), w.getWoodType(), w.getCoreType(),
                w.getOwner() == null ? "-" : w.getOwner(), w.isSold() ? "+" : "-"));
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, 200));
        JOptionPane.showMessageDialog(this, scroll, "Список палочек", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showCustomersDialog() {
        StringBuilder sb = new StringBuilder();
        for (Customer c : manager.getCustomers()) {
            sb.append(String.format("ID: %d, Имя: %s, ID палочки: %d\n", c.getId(), c.getName(), c.getWandId()));
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, 200));
        JOptionPane.showMessageDialog(this, scroll, "Список покупателей", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSuppliesDialog() {
        StringBuilder sb = new StringBuilder();
        for (ComponentSupply s : manager.getSupplies()) {
            sb.append(String.format("ID: %d, Тип: %s, Название: %s, Кол-во: %d, Дата: %s\n",
                s.getId(), s.getComponentType(), s.getName(), s.getQuantity(), s.getSupplyDate()));
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, 200));
        JOptionPane.showMessageDialog(this, scroll, "Список поставок", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showComponentsDialog() {
        StringBuilder sb = new StringBuilder();
        for (Component c : manager.getComponentsList()) {
            sb.append(String.format("Тип: %s, Название: %s, Остаток: %d\n", c.getType(), c.getName(), c.getQuantity()));
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, 200));
        JOptionPane.showMessageDialog(this, scroll, "Список компонентов", JOptionPane.INFORMATION_MESSAGE);
    }
} 