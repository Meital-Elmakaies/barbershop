package com.example.barbershop1.classes;

import java.util.ArrayList;

public class BarberCalenderWrapper {

    private static BarberCalenderWrapper single_instance = null;

    private ArrayList<BarberCalendar> barberCalender; // List of barber calender


    // Private CTOR for singleton class
    private BarberCalenderWrapper() {
        barberCalender = new ArrayList<>();
    }

    // Return list of persons
    public ArrayList<BarberCalendar> GetBarberCalendarList() {
        return barberCalender;
    }

    // static method to create instance of Singleton class
    public static BarberCalenderWrapper getInstance() {
        if (single_instance == null)
            single_instance = new BarberCalenderWrapper();

        return single_instance;
    }
}
