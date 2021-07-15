package com.geniobits.sqlitedemo;

public class Contact {
    public int id;
    public String name;
    public String phone_number;
    public String alternative_phone;
    public String email;
    public String address;

    public Contact(int id, String name, String phone_number, String alternative_phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.alternative_phone = alternative_phone;
        this.email = email;
        this.address = address;
    }
}
