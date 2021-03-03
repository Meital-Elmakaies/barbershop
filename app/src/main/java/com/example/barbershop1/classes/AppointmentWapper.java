package com.example.barbershop1.classes;

import java.util.ArrayList;

public class AppointmentWapper {
    private static AppointmentWapper single_instance = null;

    private ArrayList<AppointmentInfo>  Appointment; // List of persons


    // Private CTOR for singleton class
    private AppointmentWapper() {
        Appointment = new ArrayList<>();
    }

    // Return list of Appointments
    public ArrayList<AppointmentInfo> GetAppointmentList() {
        return Appointment;
    }

    public void deleteAppointment(int i)
    {
        Appointment.remove(i);
    }

    // static method to create instance of Singleton class
    public static AppointmentWapper getInstance() {
        if (single_instance == null)
            single_instance = new AppointmentWapper();

        return single_instance;
    }
}


