package com.example.hotelnfc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.Calendar;
import java.util.List;

public class frag_reserve extends Fragment {

    final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    public frag_reserve() {
    }

    View view;

    MaterialCalendarView calendarView;

    Button reserve_room_button;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_reserve, null);

        reserve_room_button = view.findViewById(R.id.reserve_room_button);

        reserve_room_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag_reserve_room frag_reserve_room = new frag_reserve_room();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ((FragmentTransaction) fragmentTransaction).replace(R.id.content_fragment, frag_reserve_room);
                fragmentTransaction.commit();
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

            }
        });

        return view;
    }

//    public void SelectDate(Date date) {
//        Calendar startCalendar = new GregorianCalendar();
//        startCalendar.setTime(date);
//        Calendar endCalendar = calendarView.getCurrentDate().getCalendar();
//
//        calendarView.setSelectedDate(date);
//
//        int diffyear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
//        int diffMonth = diffyear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
//        int monthsToMove = Math.abs(diffMonth);
//
//        for (int i = 0; i <monthsToMove; i++) {
//            if (diffMonth < 0) {
//                calendarView.goToNext();
//            } else if (diffMonth > 0) {
//                calendarView.goToPrevious();
//            }
//        }
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
