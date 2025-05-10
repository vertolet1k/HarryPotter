package com.hp.Lab4;

import java.io.*;

public class DataPersistence {
    public static void save(InventoryManager manager, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(manager);
        }
    }

    public static InventoryManager load(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (InventoryManager) in.readObject();
        }
    }
} 