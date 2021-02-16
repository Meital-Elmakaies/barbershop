package com.example.barbershop1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.barbershop1.R;
import com.example.barbershop1.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogInPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogInPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLogInPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogInPage newInstance(String param1, String param2) {
        FragmentLogInPage fragment = new FragmentLogInPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //create the login page- option to connect with an existing user or register - as a customer or barber
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in_page, container, false);
        // the 3 buttons in the LogIn Page
        Button BarberRegButton = view.findViewById(R.id.barberRegButton);
        Button ClientRegButton = view.findViewById(R.id.clientRegButton);
        Button logInButton = view.findViewById(R.id.loginButton);

        //click on the Barber sing up page - going to main activity for load the barber sing up page
        BarberRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.loadBarberRegFragment();
            }
        });
        //click on the client sing up page - going to main activity for load the client sing up page
        ClientRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.loadClientRegFragment();
            }
        });

        //click on login button - take details from the user and then sent to the main activity func that check if the user exist
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText = view.findViewById(R.id.emailText);
                String email = emailText.getText().toString();
                EditText passText = view.findViewById(R.id.passText);
                String password = passText.getText().toString();

                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.loginWithFirebase(email,password);   // called to firebase func - to login
            }
        });
        return view;
    }
}