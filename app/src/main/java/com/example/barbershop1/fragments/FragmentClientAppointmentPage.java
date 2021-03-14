package com.example.barbershop1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.barbershop1.R;
import com.example.barbershop1.activity.MainActivity;
import com.example.barbershop1.activity.MainPage;
import com.example.barbershop1.classes.AppointmentInfo;
import com.example.barbershop1.classes.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentClientAppointmentPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentClientAppointmentPage extends Fragment {
    // haircut
    private Spinner haircutChoose;

    // date
    private Spinner dateDay;
    private Spinner dateMonth;
    private Spinner dateYear;


    //hour
    private Spinner hour;
    String endOfTheAppoint;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentClientAppointmentPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentClientAppointmentPage.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentClientAppointmentPage newInstance(String param1, String param2) {
        FragmentClientAppointmentPage fragment = new FragmentClientAppointmentPage();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_appointment_page, container, false);

        EditText emailTextSet = view.findViewById(R.id.emailText);
        //haircut
         haircutChoose = view.findViewById(R.id.haircutSpinnerClientAppoint);

        // date
        dateDay = view.findViewById(R.id.daySpinnerClientAppoint);


        dateMonth = view.findViewById(R.id.monthSpinnerClientAppoint);
        dateYear = view.findViewById(R.id.yearSpinnerClientAppoint);
        //hour
        hour= view.findViewById(R.id.hourSpinnerClientAppoint);

        List<String> Hour = new ArrayList<>();
        Hour.add("choose");
        for(int i=8 ; i<=24 ; i++)
        {
            if(i<10)
            {
                Hour.add("0"+i+":00");
            }
            else
            {
                Hour.add(i+":00");
            }
        }
        List<String> Day = new ArrayList<>();
        Day.add("choose");
        for (int i = 1; i<=31;i++)
        {
            Day.add(String.valueOf(i));
        }
        List<String> Month = new ArrayList<>();
        Month.add("choose");
        for (int i = 1; i<=12;i++)
        {
            Month.add(String.valueOf(i));
        }
        List<String> Year = new ArrayList<>();
        Year.add("choose");
        for (int i = 2021; i<=2035;i++)
        {
            Year.add(String.valueOf(i));
        }
        List<String> HaircutType = new ArrayList<>();
        HaircutType.add("choose");
        HaircutType.add("woman haircut");
        HaircutType.add("man haircut");
        //HaircutType.add("woman haircut and color");




        //set up hour spinner
        ArrayAdapter<String> HourAdapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.selected_item_spinner,Hour);
        HourAdapter.setDropDownViewResource(R.layout.dropdown_item_spinner);
        hour.setAdapter(HourAdapter);
        //set up date spinner
        ArrayAdapter<String> DayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),  R.layout.selected_item_spinner,Day);
        DayAdapter.setDropDownViewResource(R.layout.dropdown_item_spinner);
        dateDay.setAdapter(DayAdapter);
        ArrayAdapter<String> MonthAdapter = new ArrayAdapter(getActivity().getApplicationContext(),  R.layout.selected_item_spinner,Month);
        MonthAdapter.setDropDownViewResource(R.layout.dropdown_item_spinner);
        dateMonth.setAdapter(MonthAdapter);
        ArrayAdapter<String> YearAdapter = new ArrayAdapter(getActivity().getApplicationContext(),  R.layout.selected_item_spinner,Year);
        YearAdapter.setDropDownViewResource(R.layout.dropdown_item_spinner);
        dateYear.setAdapter(YearAdapter);
        //set up haircut spinner
        ArrayAdapter<String> HaircutAdapter = new ArrayAdapter(getActivity().getApplicationContext(),  R.layout.selected_item_spinner,HaircutType);
        HaircutAdapter.setDropDownViewResource(R.layout.dropdown_item_spinner);
        haircutChoose.setAdapter(HaircutAdapter);


        Button MakeAppointment = view.findViewById(R.id.okButton);

        MakeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hourAppoin =  hour.getSelectedItem().toString();
                String yearAppoin =  dateYear.getSelectedItem().toString();
                String dayAppoin =  dateDay.getSelectedItem().toString();
                String monthAppoin =  dateMonth.getSelectedItem().toString();
                String HairChoose = haircutChoose.getSelectedItem().toString();

                 //check if the client fill all and did not left a "choose"
                if(!((HairChoose.equals("choose"))||(dayAppoin.equals("choose"))||(monthAppoin.equals("choose"))||(yearAppoin.equals("choose"))||(hourAppoin.equals("choose")))) {

                    // split the hour without ":" and casting to int for add more 2 hours to the end of the appointment
                    if (HairChoose.equals("woman haircut")) {
                        String splitHour = splitFunc(hourAppoin);
                        int hourInt = Integer.parseInt(splitHour);
                        hourInt = hourInt + 2;
                        String temp = Integer.toString(hourInt);
                        endOfTheAppoint = temp + ":00";
                    }
                    // split the hour without ":" and casting to int for add more 2 hours to the end of the appointment
                    if (HairChoose.equals("man haircut")) {
                        String splitHour = splitFunc(hourAppoin);
                        int hourInt = Integer.parseInt(splitHour);
                        hourInt = hourInt + 1;
                        String temp = Integer.toString(hourInt);
                        endOfTheAppoint = temp + ":00";
                    }
                    // make an object and then send to make appointment - there will be checks if its available and legal date
                    AppointmentInfo A = new AppointmentInfo(dayAppoin, monthAppoin, yearAppoin, hourAppoin, HairChoose, endOfTheAppoint);
                    MainPage mainPage = (MainPage) getActivity();
                    mainPage.makeAppointmentToClient(A);
                }
                else{
                    // if he left a "choose" and did not fill all the details we need then it send a error message
                    MainPage mainPage = (MainPage) getActivity();
                    mainPage.chooseError();
                }
            }
        });

        return view;
    }
    public String splitFunc(String S)
    {
        String currentString = S;
        String[] separated = currentString.split(":");
        String num =  separated[0]; // this will contain the number

        return num;
    }
}