package com.hp.Lab4;

public class Customer implements java.io.Serializable {
    private int id;
    private String name;
    private int wandId;

    public Customer(int id, String name, int wandId) {
        this.id = id;
        this.name = name;
        this.wandId = wandId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getWandId() { return wandId; }
} 