package com.example.barbershop1.classes;

public class AppointmentInfo {

    private String name;
    private String day;
    private String month;
    private String year;
    private  String hour;
    private  String haircut;
    private  String hairSalonCode;

    public AppointmentInfo( String day, String month, String year,String hour,String haircut) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.haircut = haircut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHaircut() {
        return haircut;
    }

    public void setHaircut(String haircut) {
        this.haircut = haircut;
    }

    public String getHairSalonCode() {
        return hairSalonCode;
    }

    public void setHairSalonCode(String hairSalonCode) {
        this.hairSalonCode = hairSalonCode;
    }
}
