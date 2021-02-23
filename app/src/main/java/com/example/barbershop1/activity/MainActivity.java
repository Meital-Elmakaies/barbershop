package com.example.barbershop1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barbershop1.R;
import com.example.barbershop1.classes.Person;
import com.example.barbershop1.classes.UsersWrapper;
import com.example.barbershop1.fragments.FragmentLogInPage;
import com.example.barbershop1.fragments.FragmentMainPageBarber;
import com.example.barbershop1.fragments.FragmentRegClientPage;
import com.example.barbershop1.fragments.FragmentRegBarberPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String nameSalon;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    private ArrayList<Person> ArrayPersons;


    // create the Main activity- create the Fragment Login Page - and lead to other fragments and sing up and sing in users
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        //load the LOGIN page
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FragmentFrame, new FragmentLogInPage()).commit();

        mAuth = FirebaseAuth.getInstance();
        // check if already user login (without fill details all over again)
        sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        if (sharedPreferences.getString("keyUser", null) != null) //check if there is a user that login before and get in with his details
        {
            loginWithFirebase(sharedPreferences.getString("keyUser", null), sharedPreferences.getString("keyPassword", null));
        }
    }

    // load the barber reg page
    public void loadBarberRegFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.FragmentFrame, new FragmentRegBarberPage()).addToBackStack(null).commit();
    }

    // load the client reg page
    public void loadClientRegFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.FragmentFrame, new FragmentRegClientPage()).addToBackStack(null).commit();
    }

    // signed up the user and load the LogIn Fragment - for login to the app
    public void loadLogInFragment(Person p, String password) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (p.isAdmin()) // add barber to array list
        {
            ArrayList<Person> personsList = UsersWrapper.getInstance().GetPersonsList();
            personsList.add(p);
        }

        if ((p.isAdmin()) || (CheckIfSalonCodeExist(p))) {
            mAuth.createUserWithEmailAndPassword(p.getEmail(), password)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Toast.makeText(MainActivity.this, "reg ok",
                                //Toast.LENGTH_SHORT).show();

                                FirebaseUser user = mAuth.getCurrentUser();
                                String uid = user.getUid();
                                // Write a message to the database
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("persons").child(uid);
                                myRef.setValue(p);

                                if (p.isAdmin()) {
                                    Toast.makeText(MainActivity.this, "REG OK, your code salon is: " + p.getSalonCode(),
                                            Toast.LENGTH_SHORT).show();
                                } else if (!(p.isAdmin())) {
                                    Toast.makeText(MainActivity.this, "REG OK,you sing up to your hair salon "+ nameSalon,
                                            Toast.LENGTH_SHORT).show();
                                }
                                fragmentTransaction.replace(R.id.FragmentFrame, new FragmentLogInPage()).commit();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Reg failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "your code salon is not right try agian",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // check if the user exist - if it does save him at the shared Preferences and get in to the app
    public void loginWithFirebase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "login ok",
                                    Toast.LENGTH_SHORT).show();
                            //if no one was login before then save it to SHARED PREFERENCES - to spare the part of fill all over again
                            if (sharedPreferences.getString("keyUser", null) == null) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("keyUser", email);
                                editor.putString("keyPassword", password);
                                editor.apply();
                            }
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, MainPage.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "login failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    //check the code salon of the client if exist hair salon (with admin code)
    public boolean CheckIfSalonCodeExist(Person p) {
        UsersWrapper users = UsersWrapper.getInstance();

        for (int i = 0; i < users.GetPersonsList().size(); i++) {
            if (users.GetPersonsList().get(i).getSalonCode().equals(p.getSalonCode())) {
               nameSalon = users.GetPersonsList().get(i).getSalonName();
                return true;
            }
        }
        return false;
    }




}