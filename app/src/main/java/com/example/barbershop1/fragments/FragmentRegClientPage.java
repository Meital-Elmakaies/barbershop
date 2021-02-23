package com.example.barbershop1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.barbershop1.R;
import com.example.barbershop1.activity.MainActivity;
import com.example.barbershop1.classes.Person;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegClientPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegClientPage extends Fragment {

    TextView textpassword;
    TextView textemail;
    TextView textphone;
    TextView textcity;
    TextView textuserName;
    TextView textsaloncode;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRegClientPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentThird.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegClientPage newInstance(String param1, String param2) {
        FragmentRegClientPage fragment = new FragmentRegClientPage();
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

    // create the client sing up page - and take the new user details and make object for the database and sing up the new user
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg_client_page, container, false);

        Button SingUpClientButton = view.findViewById(R.id.signUpClientButton);

        //click on Sing up button - take the details from the new barber user and save it to the database
        SingUpClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Take the password information
                textpassword = view.findViewById(R.id.passClientRegText);
                String password = textpassword.getText().toString();

                // make new client object - with the new user details
                Person client = ClientPerson(view);
                // sent to the main activity for sing up the client with the password and the client object
                MainActivity mainActivity = (MainActivity) getActivity();
                //   mainActivity.saveData();
                mainActivity.loadLogInFragment(client, password);
            }
        });
        return view;
    }

    // take the details of the new user and make person object for the database
    public Person ClientPerson(View view) {

        textemail = view.findViewById(R.id.emailClientRegText);
        textuserName = view.findViewById(R.id.nameClientRegText);
        textphone = view.findViewById(R.id.phoneClientRegText);
        textcity = view.findViewById(R.id.cityClientRegText);
        textsaloncode = view.findViewById(R.id.salonCodeClientRegText);


        String email = textemail.getText().toString();
        String userName = textuserName.getText().toString();
        String phone = textphone.getText().toString();
        String city = textcity.getText().toString();
        String SalonCode = textsaloncode.getText().toString();

        Person p = new Person(userName, email, phone, city, null, SalonCode, null, false);

        return p;
    }


}