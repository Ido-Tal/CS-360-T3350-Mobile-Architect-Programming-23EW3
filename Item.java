package com.example.iteminventoryapp;

public class Item {

    private int id;
    private String text;
    private String quantity;

    public Item() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item(int id, String text, String quantity) {
        this.id = id;
        this.text = text;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return text;
    }

    public void setDescription(String text) {
        this.text = text;
    }

}