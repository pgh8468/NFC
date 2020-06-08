package com.example.hotelnfc;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Calendar;

import static com.prolificinteractive.materialcalendarview.CalendarUtils.getYear;

public class frag_reserve extends Fragment {

    public frag_reserve() {
    }

    View view;

    MaterialCalendarView calendarView;

    Button reserve_room_button;

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

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
