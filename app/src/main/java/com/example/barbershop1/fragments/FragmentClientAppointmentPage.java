package com.example.barbershop1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.barbershop1.R;

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
        //haircut
         haircutChoose = view.findViewById(R.id.haircutSpinnerClientAppoint);
        // date
        dateDay = view.findViewById(R.id.daySpinnerClientAppoint);
        dateMonth = view.findViewById(R.id.monthSpinnerClientAppoint);
        dateYear = view.findViewById(R.id.yearSpinnerClientAppoint);
        //hour
        hour= view.findViewById(R.id.hourSpinnerClientAppoint);

        List<String> Hour = new ArrayList<>();
        Hour.add("    ");
        for(int i=1 ; i<=24 ; i++)
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
        List<Integer> Day = new ArrayList<>();
        for (int i = 1; i<=31;i++)
        {
            Day.add(i);
        }
        List<Integer> Month = new ArrayList<>();
        for (int i = 1; i<=12;i++)
        {
            Month.add(i);
        }
        List<Integer> Year = new ArrayList<>();
        for (int i = 2021; i<=2035;i++)
        {
            Year.add(i);
        }
        List<String> HaircutType = new ArrayList<>();
        HaircutType.add("    ");
        HaircutType.add("woman haircut");
        HaircutType.add("man haircut");
        HaircutType.add("woman haircut and color");




        //set up hour spinner
        ArrayAdapter<String> HourAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,Hour);
        HourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hour.setAdapter(HourAdapter);
        //set up date spinner
        ArrayAdapter<Integer> DayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,Day);
        DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateDay.setAdapter(DayAdapter);
        ArrayAdapter<Integer> MonthAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,Month);
        MonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateMonth.setAdapter(MonthAdapter);
        ArrayAdapter<Integer> YearAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,Year);
        YearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateYear.setAdapter(YearAdapter);
        //set up haircut spinner
        ArrayAdapter<String> HaircutAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,HaircutType);
        HaircutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        haircutChoose.setAdapter(HaircutAdapter);


        return view;
    }
}