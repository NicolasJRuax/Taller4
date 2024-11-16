package com.myproyect.taller_4.Fragments;

public class Item {
    private String name;
    private int quantity;
    private String brand;
    private double estimatedPrice;

    public Item(String name, int quantity, String brand, double estimatedPrice) {
        this.name = name;
        this.quantity = quantity;
        this.brand = brand;
        this.estimatedPrice = estimatedPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBrand() {
        return brand;
    }

    public double getEstimatedPrice() {
        return estimatedPrice;
    }
}
