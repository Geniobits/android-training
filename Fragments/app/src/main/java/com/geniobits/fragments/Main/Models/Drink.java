package com.geniobits.fragments.Main.Models;

public class Drink {
    public String name;
    public String des;
    public String price;
    public String image;

    public Drink(String name, String des, String price, String image) {
        this.name = name;
        this.des = des;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
