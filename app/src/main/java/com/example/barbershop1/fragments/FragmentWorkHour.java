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
 * Use the {@link FragmentWorkHour#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWorkHour extends Fragment {

    // Sunday
    private Spinner spnSunFrom;
    private Spinner spnSunTo;

    // Monday
    private Spinner spnMonFrom;
    private Spinner spnMonTo;

    //Tuesday
    private Spinner spnTuesFrom;
    private Spinner spnTuesTo;

    //Wednesday
    private Spinner spnWedFrom;
    private Spinner spnWedTo;

    //Thursday
    private Spinner spnThurFrom;
    private Spinner spnThurTo;

    //Friday
    private Spinner spnFriFrom;
    private Spinner spnFriTo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentWorkHour() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentWorkHour.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWorkHour newInstance(String param1, String param2) {
        FragmentWorkHour fragment = new FragmentWorkHour();
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
        View view = inflater.inflate(R.layout.fragment_work_hour, container, false);

        spnSunFrom = view.findViewById(R.id.SunFromHourTxt);
        spnSunTo = view.findViewById(R.id.SunToHourTxt);

        spnMonFrom = view.findViewById(R.id.MonfromHourTxt);
        spnMonTo = view.findViewById(R.id.MonToHourTxt);

        spnTuesFrom = view.findViewById(R.id.TuesfromHourTxt);
        spnTuesTo = view.findViewById(R.id.TuesToHourTxt);

        spnWedFrom = view.findViewById(R.id.WedfromHourTxt);
        spnWedTo = view.findViewById(R.id.WedToHourTxt);

        spnThurFrom = view.findViewById(R.id.ThurfromHourTxt);
        spnThurTo = view.findViewById(R.id.ThurToHourTxt);

        spnFriFrom = view.findViewById(R.id.FrifromHourTxt);
        spnFriTo = view.findViewById(R.id.FriToHourTxt);

        List<String> HourFrom = new ArrayList<>();
        HourFrom.add("From");
        for(int i=1 ; i<=24 ; i++)
        {
            if(i<10)
            {
                HourFrom.add("0"+i+":00");
            }
            else
            {
                HourFrom.add(i+":00");
            }
        }
        List<String> HourTo = new ArrayList<>();
        HourTo.add("Until");
        for(int i=1 ; i<=24 ; i++)
        {
            if(i<10)
            {
                HourTo.add("0"+i+":00");
            }
            else
            {
                HourTo.add(i+":00");
            }
        }
        ArrayAdapter<String> HourFromAdapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.selected_item_spinner,HourFrom);
        HourFromAdapter.setDropDownViewResource(R.layout.dropdown_item_spinner);

        ArrayAdapter<String> HourToAdapter = new ArrayAdapter(getActivity().getApplicationContext(),R.layout.selected_item_spinner,HourTo);
        HourToAdapter.setDropDownViewResource(R.layout.dropdown_item_spinner);

        spnSunFrom.setAdapter(HourFromAdapter);
        spnSunTo.setAdapter(HourToAdapter);

        spnMonFrom.setAdapter(HourFromAdapter);
        spnMonTo.setAdapter(HourToAdapter);

        spnTuesFrom.setAdapter(HourFromAdapter);
        spnTuesTo.setAdapter(HourToAdapter);

        spnWedFrom.setAdapter(HourFromAdapter);
        spnWedTo.setAdapter(HourToAdapter);

        spnThurFrom.setAdapter(HourFromAdapter);
        spnThurTo.setAdapter(HourToAdapter);

        spnFriFrom.setAdapter(HourFromAdapter);
        spnFriTo.setAdapter(HourToAdapter);



        return view;
    }
}