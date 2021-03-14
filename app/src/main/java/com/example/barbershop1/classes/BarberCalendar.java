package com.example.barbershop1.classes;

public class BarberCalendar {

    private DateCal StartSickDay;
    private DateCal FinishSickDay;
    private DateCal StartDaysOff;
    private DateCal FinishDaysOff;
    private  String hairSalonCode;
    private String startTime;
    private String endTime;


    public BarberCalendar(){
        this.startTime = "09:00";
        this.endTime= "20:00";
    };



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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
