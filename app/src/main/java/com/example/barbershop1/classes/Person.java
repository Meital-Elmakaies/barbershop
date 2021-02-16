package com.example.barbershop1.classes;

public class Person {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String salonName;
    private String salonCode;
    boolean Admin;

    public Person() {
    }

    public Person(String name, String email, String Phone, String City, String Address, String SalonCode,String SalonName,boolean admin) {
        Admin = admin;
        this.name = name;
        this.email = email;
        this.phone = Phone;
        this.city = City;
        this.salonCode = SalonCode;
        this.salonName = SalonName;
        this.address = Address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getSalonName() {
        return salonName;
    }

    public String getSalonCode() {
        return salonCode;
    }

    public boolean isAdmin() {
        return Admin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public void setSalonCode(String salonCode) {
        this.salonCode = salonCode;
    }

    public void setAdmin(boolean admin) {
        Admin = admin;
    }
}
