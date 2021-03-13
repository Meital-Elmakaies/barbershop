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
import com.example.barbershop1.classes.BarberCalendar;
import com.example.barbershop1.classes.DateCal;
import com.example.barbershop1.classes.DatesType;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBarberSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBarberSetting extends Fragment {

    private Button mDatePickerBtnSickDay;
    private Button mDatePickerBtnOffDay;

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

        mDatePickerBtnSickDay = view.findViewById(R.id.sick_days_btn);
        mDatePickerBtnOffDay = view.findViewById(R.id.free_day_Btn);
        DatePickerShow(mDatePickerBtnSickDay,mDatePickerBtnOffDay);


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

    public void DatePickerShow(Button mDatePickerBtnSickDay,Button mDatePickerBtnOffDay)
    {
        //date picker sick days
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a Date");
        MaterialDatePicker materialDatePicker =builder.build();

        //date picker free days
        MaterialDatePicker.Builder<Pair<Long, Long>> builder1 = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a Date");
        MaterialDatePicker materialDatePicker1 =builder1.build();



        //If you clicked the sick days button, take the data you entered
        mDatePickerBtnSickDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getFragmentManager(),"DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override public void onPositiveButtonClick(Pair<Long,Long> selection) {
                        Long startDateEpochMili = selection.first;
                        Date startDateObject = new Date(startDateEpochMili);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(startDateObject);
                        int startDay= cal.get(Calendar.DAY_OF_MONTH);
                        int startMonth= cal.get(Calendar.MONTH)+1;// Calendar is from 0->11 so December = 11
                        int startYear = cal.get(Calendar.YEAR);
                        /* => Work with strings
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String dateStartString= format.format(startDateObject);
                        */
                        Long endDateEpochMili = selection.second;
                        Date endDateObject =  new Date(endDateEpochMili);
                        cal.setTime(endDateObject);
                        int endDay = cal.get(Calendar.DAY_OF_MONTH);
                        int endMonth = cal.get(Calendar.MONTH)+1;
                        int endYear =  cal.get(Calendar.YEAR);

                        //convert int to string
                        String  sDay = String.valueOf(startDay);
                        String  sMonth= String.valueOf(startMonth);
                        String  sYear = String.valueOf(startYear);
                        String  eDay = String.valueOf(endDay);
                        String  eMonth = String.valueOf(endMonth);
                        String  eYear = String.valueOf(endYear);

                        //Create two date objects
                        DateCal startDateCal = new DateCal(sDay,sMonth,sYear);
                        DateCal endDateCal = new DateCal(eDay,eMonth,eYear);

                        MainPage mainPage = (MainPage) getActivity();
                        mainPage.changesInCalendar( startDateCal, endDateCal , DatesType.SICK_DAYS);

                    }
                });
            }
        });

        mDatePickerBtnOffDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker1.show(getFragmentManager(),"DATE_PICKER");
                materialDatePicker1.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override public void onPositiveButtonClick(Pair<Long,Long> selection) {
                        Long startDateEpochMili = selection.first;
                        Date startDateObject = new Date(startDateEpochMili);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(startDateObject);
                        int startDay= cal.get(Calendar.DAY_OF_MONTH);
                        int startMonth= cal.get(Calendar.MONTH)+1;// Calendar is from 0->11 so December = 11
                        int startYear = cal.get(Calendar.YEAR);

                        Long endDateEpochMili = selection.second;
                        Date endDateObject =  new Date(endDateEpochMili);
                        cal.setTime(endDateObject);
                        int endDay = cal.get(Calendar.DAY_OF_MONTH);
                        int endMonth = cal.get(Calendar.MONTH)+1;
                        int endYear =  cal.get(Calendar.YEAR);

                        //convert int to string
                        String  sDay = String.valueOf(startDay);
                        String  sMonth= String.valueOf(startMonth);
                        String  sYear = String.valueOf(startYear);
                        String  eDay = String.valueOf(endDay);
                        String  eMonth = String.valueOf(endMonth);
                        String  eYear = String.valueOf(endYear);

                        //Create two date objects
                        DateCal startDateCal = new DateCal(sDay,sMonth,sYear);
                        DateCal endDateCal = new DateCal(eDay,eMonth,eYear);

                        MainPage mainPage = (MainPage) getActivity();
                        mainPage.changesInCalendar( startDateCal, endDateCal , DatesType.OFF_DAYS);

                    }
                });

            }
        });

    }
}