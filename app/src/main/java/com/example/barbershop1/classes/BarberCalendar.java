package com.example.barbershop1.classes;

public class BarberCalendar {

    private DateCal StartSickDay;
    private DateCal FinishSickDay;
    private DateCal StartDaysOff;
    private DateCal FinishDaysOff;
    private  String hairSalonCode;


    public BarberCalendar(DateCal StartSickDay, DateCal FinishSickDay, DateCal StartDaysOff, DateCal FinishDaysOff){
        this.StartSickDay= StartSickDay;
        this.FinishSickDay=FinishSickDay;
        this.StartDaysOff=StartDaysOff;
        this.FinishDaysOff=FinishDaysOff;
    }

    public BarberCalendar(){};



    public DateCal getStartSickDay() {
        return StartSickDay;
    }

    public void setStartSickDay(DateCal startSickDay) {
        StartSickDay = startSickDay;
    }

    public DateCal getFinishSickDay() {
        return FinishSickDay;
    }

    public void setFinishSickDay(DateCal finishSickDay) {
        FinishSickDay = finishSickDay;
    }

    public DateCal getStartDaysOff() {
        return StartDaysOff;
    }

    public void setStartDaysOff(DateCal startDaysOff) {
        StartDaysOff = startDaysOff;
    }

    public DateCal getFinishDaysOff() {
        return FinishDaysOff;
    }

    public void setFinishDaysOff(DateCal finishDaysOff) {
        FinishDaysOff = finishDaysOff;
    }

    public String getHairSalonCode() {
        return hairSalonCode;
    }

    public void setHairSalonCode(String hairSalonCode) {
        this.hairSalonCode = hairSalonCode;
    }
}
