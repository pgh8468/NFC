package com.example.hotelnfc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class frag_reserve extends Fragment {

    String Firstday, Lastday, result;
    private static final String stay ="result";

    OneDayDecorator oneDayDecorator = new OneDayDecorator();

    public frag_reserve() {
    }

    View view;

    MaterialCalendarView calendarView;

    Button reserve_room_button;

    MainActivity mainActivity;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_reserve, null);

        reserve_room_button = view.findViewById(R.id.reserve_room_button);

        mainActivity = (MainActivity) getActivity();

        final FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();

        reserve_room_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frag_reserve_room frag_reserve_room = new frag_reserve_room();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentManager.beginTransaction().replace(R.id.content_fragment, frag_reserve_room, null).addToBackStack(null).commit();
                fragmentTransaction.commit();

//                Bundle bundle = new Bundle();
//                bundle.putString("stayDay", result);
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                Frag_nfc frag_nfc = new Frag_nfc();
//                frag_nfc.setArguments(bundle);
//                transaction.replace(R.id.content_fragment, frag_nfc);
//                transaction.commit();
            }
        });

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setDateTextAppearance(R.style.myEditTextStyle);
        calendarView.setWeekDayTextAppearance(R.style.myEditTextStyle);

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020,01,01))
                .setMaximumDate(CalendarDay.from(2030,12,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator
        );

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(getContext(), "Click"+date, Toast.LENGTH_SHORT).show();

            }
        });

        calendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {


                Log.e("check dates", Integer.toString(dates.size()));
                Log.e("first last", dates.get(0).toString()+"/"+dates.get(dates.size()-1).toString());

                Firstday = dates.get(0).toString(); //체크인 날짜
                Lastday = dates.get(dates.size()-1).toString(); //체크아웃 날짜
                result = Integer.toString(dates.size());
            }
        });



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
