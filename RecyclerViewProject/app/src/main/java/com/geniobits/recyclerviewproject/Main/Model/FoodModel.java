package com.geniobits.recyclerviewproject.Main.Model;

public class FoodModel {

    public int id;
    public String name;
    public String description;
    public int price;
    public String image_url;

    public FoodModel(int id, String name, String description, int price, String image_url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
    }


}
