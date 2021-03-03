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

import com.example.barbershop1.R;
import com.example.barbershop1.classes.AppointmentInfo;
import com.example.barbershop1.classes.AppointmentWapper;
import com.example.barbershop1.classes.Person;
import com.example.barbershop1.classes.UsersWrapper;
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

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {
    TextView nameTV;
   TextView MainTitle;
    private final String KEY="MainActivityKeyName";
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private FragmentManager fragmentManager;
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
    public void ReadData()
    {
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
                if(loggedPerson.isAdmin()) {
                    MainTitle.setText("Barber Page");
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.FragmentMainFrame, new FragmentMainPageBarber()).commit();
                    return; // return to avoid Else code - just add necessary code
                }
                else {
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
    public void LogOutFunc(View view)
    {
        sharedPreferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(MainPage.this,MainActivity.class);
        startActivity(intent);
    }

    public void loadSettingBarberFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.FragmentMainFrame,new FragmentBarberSetting()).addToBackStack(null).commit();
    }

    public void loadWorkHourFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.FragmentMainFrame,new FragmentWorkHour()).addToBackStack(null).commit();
    }
    public void loadAppoinClientFragment()
    {

        if(AppointmentExist()){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.FragmentMainFrame, new FragmentMainPageClient()).commit();
            Toast.makeText(MainPage.this, "you already have appointment, cancel it and make a new one",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.FragmentMainFrame, new FragmentClientAppointmentPage()).addToBackStack(null).commit();
        }
    }
    //add client Appointment info to the list and set the info on the screen
    public void makeAppointmentToClient(AppointmentInfo A) {
        A.setName(loggedPerson.getName());
        A.setHairSalonCode(loggedPerson.getSalonCode());
        ArrayList<AppointmentInfo> appointmentInfoArrayList = AppointmentWapper.getInstance().GetAppointmentList();
        appointmentInfoArrayList.add(A);
            SetAppointmentTitle();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentMainFrame, new FragmentMainPageClient()).addToBackStack(null).commit();
    }


       // check if there is exist appointment if exist set the info on the screen
        public void SetAppointmentTitle()
        {
            AppointmentWapper appointment = AppointmentWapper.getInstance();

            for (int i = 0; i < appointment.GetAppointmentList().size(); i++) {
                if (appointment.GetAppointmentList().get(i).getName().equals(loggedPerson.getName())) {
                    AppointmentInfo chooseAppointment =  appointment.GetAppointmentList().get(i);
                    String FullDate = chooseAppointment.getDay()+"/"+chooseAppointment.getMonth()+"/"+chooseAppointment.getYear()+ " at hour: "+chooseAppointment.getHour();
                    nameTV.setText(loggedPerson.getName()+ " your next appointment is on date: "+FullDate);
                }
        } }
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

        } }


    public Boolean AppointmentExist() {
        AppointmentWapper appointment = AppointmentWapper.getInstance();

        for (int i = 0; i < appointment.GetAppointmentList().size(); i++) {
            if (appointment.GetAppointmentList().get(i).getName().equals(loggedPerson.getName())) {
                return true;
            }
         }
        return false;
    }


}

