package com.example.barbershop1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbershop1.FragmentQueueList;
import com.example.barbershop1.R;
import com.example.barbershop1.classes.AppointmentInfo;
import com.example.barbershop1.classes.AppointmentWapper;
import com.example.barbershop1.classes.BarberCalendar;
import com.example.barbershop1.classes.BarberCalenderWrapper;
import com.example.barbershop1.classes.DateCal;
import com.example.barbershop1.classes.DatesType;
import com.example.barbershop1.classes.Person;
import com.example.barbershop1.fragments.FragmentBarberSetting;
import com.example.barbershop1.fragments.FragmentClientAppointmentPage;
import com.example.barbershop1.fragments.FragmentMainPageBarber;
import com.example.barbershop1.fragments.FragmentMainPageClient;
import com.example.barbershop1.fragments.FragmentWorkHour;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainPage extends AppCompatActivity {
    TextView nameTV;
    TextView MainTitle;
    private final String KEY = "MainActivityKeyName";
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private FragmentManager fragmentManager;
    private boolean flag;
    private String Error;
    Person loggedPerson;



    // create the Main Page of the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        nameTV = findViewById(R.id.welcomeIdPerson);
        MainTitle = findViewById(R.id.DefMainTitle);

        mAuth = FirebaseAuth.getInstance();
        ReadData();

        //calling fragment barber main page
        fragmentManager = getSupportFragmentManager();
    }

    // read from data - to show the name of the user in top of the main page and to use his data
    public void ReadData() {
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("persons").child(uid);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loggedPerson = dataSnapshot.getValue(Person.class);
                nameTV.setText("Welcome " + loggedPerson.getName());

                // If admin logged in show admin panel
                if (loggedPerson.isAdmin()) {
                    MainTitle.setText("Barber Page");
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.FragmentMainFrame, new FragmentMainPageBarber()).commit();
                    return; // return to avoid Else code - just add necessary code
                } else {
                    MainTitle.setText("Client Page");
                    SetAppointmentTitle();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.FragmentMainFrame, new FragmentMainPageClient()).commit();
                    return;
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    // to log out from the user (shared Preferences will be null) - and back to the logIn page
    public void LogOutFunc(View view) {
        sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(MainPage.this, MainActivity.class);
        startActivity(intent);
    }

    public void loadSettingBarberFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.FragmentMainFrame, new FragmentBarberSetting()).addToBackStack(null).commit();
    }

    public void loadWorkHourFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.FragmentMainFrame, new FragmentWorkHour()).addToBackStack(null).commit();


    }

    public void loadAppoinClientFragment() {

        if (AppointmentExist()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.FragmentMainFrame, new FragmentMainPageClient()).commit();
            Toast.makeText(MainPage.this, "you already have appointment, cancel it and make a new one",
                    Toast.LENGTH_SHORT).show();
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.FragmentMainFrame, new FragmentClientAppointmentPage()).addToBackStack(null).commit();
        }
    }

    //add client Appointment info to the list and set the info on the screen
    public void makeAppointmentToClient(AppointmentInfo A) {
        A.setName(loggedPerson.getName());
        A.setHairSalonCode(loggedPerson.getSalonCode());
        //check if the date is legal before the others checks
        if(checkIfLegalDate(A.getDay(),A.getMonth(),A.getYear(),A.getHour())) {
            //check if the date that the client choose available in the Hair salon
            if (checkIfTheDateIsAvailable(A)) {
                ArrayList<AppointmentInfo> appointmentInfoArrayList = AppointmentWapper.getInstance().GetAppointmentList();
                appointmentInfoArrayList.add(A);
                SetAppointmentTitle();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FragmentMainFrame, new FragmentMainPageClient()).addToBackStack(null).commit();
                return;
            }
        }
        //cant make appointment send Error message respectively
        Toast.makeText(MainPage.this, Error,
                Toast.LENGTH_SHORT).show();

    }


    // check if there is exist appointment if exist set the info on the screen
    public void SetAppointmentTitle() {

        AppointmentWapper appointment = AppointmentWapper.getInstance();

        for (int i = 0; i < appointment.GetAppointmentList().size(); i++) {
            if (appointment.GetAppointmentList().get(i).getName().equals(loggedPerson.getName())) {
                AppointmentInfo chooseAppointment = appointment.GetAppointmentList().get(i);
                String FullDate = chooseAppointment.getDay() + "/" + chooseAppointment.getMonth() + "/" + chooseAppointment.getYear() + " at hour: " + chooseAppointment.getHour();
                nameTV.setText(loggedPerson.getName() + " your next appointment is on date: " + FullDate);
            }
        }

    }

    //cancel the appointment and delete from the list
    public void cancelAppointment() {
        AppointmentWapper appointment = AppointmentWapper.getInstance();

        for (int i = 0; i < appointment.GetAppointmentList().size(); i++) {
            if (appointment.GetAppointmentList().get(i).getName().equals(loggedPerson.getName())) {
                appointment.deleteAppointment(i);
                nameTV.setText("Welcome " + loggedPerson.getName());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.FragmentMainFrame, new FragmentMainPageClient()).commit();
                break;
            }

        }
    }

    // check if there is a appointment to the client if exist appointment then return true and dont let order more appointments
    public Boolean AppointmentExist() {
        AppointmentWapper appointment = AppointmentWapper.getInstance();

        for (int i = 0; i < appointment.GetAppointmentList().size(); i++) {
            if (appointment.GetAppointmentList().get(i).getName().equals(loggedPerson.getName())) {
                return true;
            }
        }
        return false;
    }

    // check if there is a place on the list of the appointment
    public Boolean checkIfTheDateIsAvailable(AppointmentInfo A) {
        AppointmentWapper appointment = AppointmentWapper.getInstance();
        for (int i = 0; i < appointment.GetAppointmentList().size(); i++)
        {
            AppointmentInfo AExist = appointment.GetAppointmentList().get(i);
            // check the info just for the same hair salon code == specific hair salon
            if (AExist.getHairSalonCode().equals(A.getHairSalonCode())) {
                //check if there is exist appointment in this date
                if ((AExist.getDay().equals(A.getDay())) && (AExist.getMonth().equals(A.getMonth())) && (AExist.getYear().equals(A.getYear()))) {
                    //check if the choose hour for haircut don't fall on exist appointment hour
                    if (AExist.getHour().equals(A.getHour())) {
                        Error = "Try different hour - this hour is not available";
                        return false;
                    }
                    //do casting from string to int + split the string hour without the ":"
                    String SplitTakenStartHour = splitHour(AExist.getHour());
                    int takenStartHour = Integer.parseInt(SplitTakenStartHour);

                    String SplitTakenFinishHour = splitHour(AExist.getEndOfTheAppoint());
                    int takenFinishHour = Integer.parseInt(SplitTakenFinishHour);

                    String SplitStartHour = splitHour(A.getHour());
                    int startHour = Integer.parseInt(SplitStartHour);

                    String SplitFinishHour = splitHour(A.getEndOfTheAppoint());
                    int finishHour = Integer.parseInt(SplitFinishHour);

                    //check if the choose hour for haircut don't fall between exist appointment
                    if ((takenStartHour < startHour) && (startHour < takenFinishHour)) {
                        Error = "This hour is not available try " + AExist.getEndOfTheAppoint() + " it may be available";
                        return false;
                    }
                    //check if the choose hour for haircut is not to much long and fall on start exist appointment hour
                    if (finishHour == takenStartHour + 1) {
                        Error = "Your appointment is too long for this hour,try different hour";
                        return false;
                    }
                }
            }
        }
        return true;

    }
    //split the hour
    public String splitHour(String S)
    {
        String currentString = S;
        String[] separated = currentString.split(":");
        String num =  separated[0]; // this will contain the number

        return num;
    }

    //check if the date is legal return true if it does
    public boolean checkIfLegalDate(String day,String month,String year,String hour)
    {
        String dtStart = year+"-"+month+"-"+day+"T"+hour+":00Z"; //2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(dtStart);
            System.out.println(date);
            int numOfDay = date.getDay();
            System.out.println(numOfDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }






        //CHECK WITH CORRECT DATE - OF THE COMPUTER
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/ G /HH:mm:ss z");
        String currentDateAndTime = sdf.format(new Date());

        // SPLIT THE DATE AND HOUR
        String S = currentDateAndTime;
        String[] separated = S.split("/");
        String currentYear =  separated[0]; // this will contain the year
        String currentMonth = separated[1]; // this will contain the month
        String currentDay = separated[2]; // this will contain the day
        String temp = separated[4]; //this will contain the hour
        String currentHour = splitHour(temp); //this is the hour without ":" just the first one

        // DO CASTING FROM STRING TO INT
        int IntCurrentYear = Integer.parseInt(currentYear);
        int IntCurrentMonth = Integer.parseInt(currentMonth);
        int IntCurrentDay = Integer.parseInt(currentDay);
        int IntCurrentHour = Integer.parseInt(currentHour);

        int checkDay =  Integer.parseInt(day);
        int checkMonth = Integer.parseInt(month);
        int checkYear = Integer.parseInt(year);
        String splitToHour = splitHour(hour);
        int checkHour = Integer.parseInt(splitToHour);

        //ALL THE CHECKS - RETURN FALSE IF THE DATE OR HOUR AS PASSED
        if ((IntCurrentYear>checkYear))
        {
            Error ="this year passed, change the year ";
            return false;
        }
        if ((IntCurrentYear == checkYear))
        {
            if(checkMonth<IntCurrentMonth)
            {
                Error ="this month passed, change the month ";
                return false;
            }
            if(checkMonth==IntCurrentMonth)
            {
                if(checkDay<IntCurrentDay)
                {
                    Error ="this day passed, change the day ";
                    return false;
                }
                if(checkDay==IntCurrentDay)
                {
                    if(checkHour<IntCurrentHour)
                    {
                        Error ="this hour passed, change the hour ";
                        return false;
                    }
                }
            }
        }

        // AFTER CHECKING WITH THE CORRECT DATE - CHECK IF THE DAYS & MONTHS THAT FILLED ARE LEGAL
        if(month.equals("4"))
        {
            if(day.equals("31")){
                Error = "In month April there is only 30 days, please choose other day";
                return false;
            }
        }
        if(month.equals("6"))
        {
            if(day.equals("31")){
                Error = "In month June there is only 30 days, please choose other day";
                return false;
            }
        }
        if(month.equals("9"))
        {
            if(day.equals("31")){
                Error = "In month September there is only 30 days, please choose other day";
                return false;
            }
        }
        if(month.equals("11"))
        {
            if(day.equals("31")){
                Error = "In month November there is only 30 days, please choose other day";
                return false;
            }
        }
        if(month.equals("2"))
        {
            if((day.equals("31"))||(day.equals("30"))||(day.equals("29")))

            {
                Error = "In month February there is only 28 days, please choose other day";
                return false;
            }
        }

        return true;
    }
     //if the client didn't change the "choose" filled
    public void chooseError() {
        //cant make appointment send Error message respectively
        Toast.makeText(MainPage.this, "You did not fill in all the details and left a choose" ,
                Toast.LENGTH_SHORT).show();
    }

    //Calendar changes, entry of sick days or vacation days of a hairdresser
    public void changesInCalendar(DateCal start , DateCal end , DatesType type) {

        ArrayList<BarberCalendar> barberCalendarInfoArrayList = BarberCalenderWrapper.getInstance().GetBarberCalendarList();
        String salonCode = loggedPerson.getSalonCode();

        //Run on the array and check if the salon code is already on the list. And if found then update new entries entered
        for(int i=0 ; i<barberCalendarInfoArrayList.size() ; i++)
        {
            if(barberCalendarInfoArrayList.get(i).getHairSalonCode().equals( salonCode)){
                if(type==DatesType.OFF_DAYS){
                    barberCalendarInfoArrayList.get(i).setStartDaysOff(start);
                    barberCalendarInfoArrayList.get(i).setFinishDaysOff(end);
                    return;
                }
                barberCalendarInfoArrayList.get(i).setStartSickDay(start);
                barberCalendarInfoArrayList.get(i).setFinishSickDay(end);
                return;
            }
        }
        //If the salon code is not in the list then creates a new object and puts it in the list
        BarberCalendar newBarberCalendar= new BarberCalendar();
        newBarberCalendar.setHairSalonCode(loggedPerson.getSalonCode());
        if(type==DatesType.OFF_DAYS){
            newBarberCalendar.setStartDaysOff(start);
            newBarberCalendar.setFinishDaysOff(end);
        }
        if(type==DatesType.SICK_DAYS){
            newBarberCalendar.setStartSickDay(start);
            newBarberCalendar.setFinishSickDay(end);
        }
        barberCalendarInfoArrayList.add(newBarberCalendar);

    }

    public void changesHours(String fromHour, String toHour) {

        ArrayList<BarberCalendar> barberCalendarInfoArrayList = BarberCalenderWrapper.getInstance().GetBarberCalendarList();
        String salonCode = loggedPerson.getSalonCode();

        //Run on the array and check if the salon code is already on the list. And if found then update new entries entered
        for(int i=0 ; i<barberCalendarInfoArrayList.size() ; i++)
        {
            if(barberCalendarInfoArrayList.get(i).getHairSalonCode().equals( salonCode)){

                    barberCalendarInfoArrayList.get(i).setStartTime(fromHour);
                    barberCalendarInfoArrayList.get(i).setEndTime(toHour);
                    return;
            }
        }

        BarberCalendar newBarberCalendar= new BarberCalendar();
        newBarberCalendar.setHairSalonCode(loggedPerson.getSalonCode());
        newBarberCalendar.setStartTime(fromHour);
        newBarberCalendar.setEndTime(toHour);

        barberCalendarInfoArrayList.add(newBarberCalendar);
        SetWorkHoursTitle();

    }

    public void SetWorkHoursTitle(){
        BarberCalenderWrapper workHours = BarberCalenderWrapper.getInstance();

        for (int i = 0; i < workHours.GetBarberCalendarList().size(); i++) {
            if (workHours.GetBarberCalendarList().get(i).getHairSalonCode().equals(loggedPerson.getSalonCode())) {
                BarberCalendar chooseWorkHours = workHours.GetBarberCalendarList().get(i);
                String Hours ="from " + chooseWorkHours.getStartTime() + " to " + chooseWorkHours.getEndTime();
                nameTV.setText(loggedPerson.getName() + " your working hours are " + Hours);
            }
        }

    }

    public void loadQueueListBarberFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.FragmentMainFrame, new FragmentQueueList()).addToBackStack(null).commit();
    }

    public void loadAppointment() {

        AppointmentWapper appointment = AppointmentWapper.getInstance();

        for (int i = 0; i < appointment.GetAppointmentList().size(); i++) {

        }

    }
}

