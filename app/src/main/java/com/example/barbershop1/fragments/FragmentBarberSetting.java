package com.example.barbershop1.fragments;

import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.barbershop1.R;
import com.example.barbershop1.activity.MainPage;
import com.google.android.material.datepicker.MaterialDatePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBarberSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBarberSetting extends Fragment {

    private Button mDatePickerBtn;
    private Button mDatePickerBtn1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentBarberSetting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBarberSetting.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBarberSetting newInstance(String param1, String param2) {
        FragmentBarberSetting fragment = new FragmentBarberSetting();
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
        View view = inflater.inflate(R.layout.fragment_barber_setting, container, false);

        mDatePickerBtn = view.findViewById(R.id.sick_days_btn);
        mDatePickerBtn1 = view.findViewById(R.id.free_day_Btn);
        DatePickerShow(mDatePickerBtn,mDatePickerBtn1);

        Button b = view.findViewById(R.id.WorkHoursBtn);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainPage mainPage = (MainPage) getActivity();
                mainPage.loadWorkHourFragment();
            }
        });

        return view;
    }

    public void DatePickerShow(Button mDatePickerBtn,Button mDatePickerBtn1)
    {
        //date picker sick days
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a Date");
        MaterialDatePicker materialDatePicker =builder.build();

        //date picker free days
        MaterialDatePicker.Builder<Pair<Long, Long>> builder1 = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a Date");
        MaterialDatePicker materialDatePicker1 =builder1.build();

        mDatePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getFragmentManager(),"DATE_PICKER");
            }
        });
        mDatePickerBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker1.show(getFragmentManager(),"DATE_PICKER");
            }
        });

    }
}