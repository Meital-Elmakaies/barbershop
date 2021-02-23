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

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegBarberPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegBarberPage extends Fragment {

    private static final String ALLOWED_CHARACTERS ="QWERTYUIOPASDFGHJKLZXCVBNM0123456789Qqwertyuiopasdfghjklzxcvbnm";
    TextView textpassword;
    TextView textemail;
    TextView textphone;
    TextView textcity;
    TextView textuserName;
    TextView textaddress;
    TextView textnamesalon;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRegBarberPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegBarberPage newInstance(String param1, String param2) {
        FragmentRegBarberPage fragment = new FragmentRegBarberPage();
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

    // create the barber(admin) sing up page - and take the new user details and make object for the database and sing up the new user
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg_barber_page, container, false);

        Button SingUpBarberButton= view.findViewById(R.id.signUpButton);

       //click on Sing up button - take the details from the new barber user and save it to the database
        SingUpBarberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Take the password information
                textpassword = view.findViewById(R.id.passBarberRegText);
                String password = textpassword.getText().toString();

                // make new barber object - with the new user details
                Person Barber = BarberPerson(view);
                // sent to the main activity for sing up the barber with the password and the barber object
                MainActivity mainActivity = (MainActivity) getActivity();
            //    mainActivity.s
                mainActivity.loadLogInFragment(Barber,password);
            }
        });
        return view;
    }

    // take the details of the new user and make person object for the database
    public Person BarberPerson(View view) {
        textemail = view.findViewById(R.id.emailBarberRegText);
        textpassword = view.findViewById(R.id.passBarberRegText);
        textuserName = view.findViewById(R.id.nameBarberRegText);
        textphone = view.findViewById(R.id.phoneBarberRegText);
        textcity = view.findViewById(R.id.cityBarberRegText);
        textnamesalon=view.findViewById(R.id.salonNameBarberRegText);
        textaddress= view.findViewById(R.id.addressBarberRegText);

        String email = textemail.getText().toString();
        String password = textpassword.getText().toString();
        String userName = textuserName.getText().toString();
        String phone = textphone.getText().toString();
        String city = textcity.getText().toString();
        String SalonName = textnamesalon.getText().toString();
        String Address = textaddress.getText().toString();
        String Random =  getRandomString(6);

        Person p = new Person(userName, email, phone, city,Address,Random, SalonName,true);
        return p;
    }

    // do Random Code for the code salon
    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}