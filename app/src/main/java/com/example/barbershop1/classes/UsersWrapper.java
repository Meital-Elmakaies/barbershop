package com.example.barbershop1.classes;

import java.util.ArrayList;

public class UsersWrapper {
    private static UsersWrapper single_instance = null;

    private ArrayList<Person> persons; // List of persons


    // Private CTOR for singleton class
    private UsersWrapper() {
        persons = new ArrayList<>();
    }

    // Return list of persons
    public ArrayList<Person> GetPersonsList() {
        return persons;
    }

    // static method to create instance of Singleton class
    public static UsersWrapper getInstance() {
        if (single_instance == null)
            single_instance = new UsersWrapper();

        return single_instance;
    }
}