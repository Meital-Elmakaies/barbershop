package com.example.barbershop1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.barbershop1.R;
import com.example.barbershop1.classes.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity {
    TextView nameTV;
    private final String KEY="MainActivityKeyName";
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

   // create the Main Page of the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        nameTV = findViewById(R.id.welcomeIdPerson);

        mAuth = FirebaseAuth.getInstance();
        ReadData();
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
                Person value = dataSnapshot.getValue(Person.class);
                nameTV.setText("Welcome " + value.getName());
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
}