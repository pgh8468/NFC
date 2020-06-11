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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class frag_reserve extends Fragment {

    String Firstday, Lastday, result;
    SimpleDateFormat changeFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String stay ="result";

    OneDayDecorator oneDayDecorator = new OneDayDecorator();

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
//                frag_reserve_room frag_reserve_room = new frag_reserve_room();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                ((FragmentTransaction) fragmentTransaction).replace(R.id.content_fragment, frag_reserve_room);
//                fragmentTransaction.commit();

                if(Firstday == null){
                    Snackbar.make(v,"체크인 날짜를 지정해주세요", Snackbar.LENGTH_INDEFINITE ).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();

                }
                else if(Lastday == null){
                    Snackbar.make(v,"체크아웃 날짜를 지정해주세요", Snackbar.LENGTH_INDEFINITE ).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                }
                else {


                    Date today, target;
                    try {
                        target = changeFormat.parse(Firstday);
                        String now = changeFormat.format(new Date());
                        Log.e("print now",now);
                        today = changeFormat.parse(now);
                        Log.e("print today",today.toString());
                        Log.e("print target", target.toString());
                        int compare = today.compareTo(target);
                        Log.e("print compare", Integer.toString(compare));

                        if(compare >0){
                            Snackbar.make(v,"오늘 이전의 날짜부터 예약할 수 없습니다.", Snackbar.LENGTH_LONG).show();

                        }
                        else{
                            Bundle bundle = new Bundle();
                            bundle.putString("stayDay", result);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            Frag_nfc frag_nfc = new Frag_nfc();
                            frag_nfc.setArguments(bundle);
                            transaction.replace(R.id.content_fragment, frag_nfc);
                            transaction.commit();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

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
                Calendar cal = Calendar.getInstance();


                Toast.makeText(getContext(), "Click"+date.getMonth(), Toast.LENGTH_SHORT).show();

            }
        });

        calendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {


                Log.e("check dates", Integer.toString(dates.size()));
                Log.e("first last", dates.get(0).toString()+"/"+dates.get(dates.size()-1).toString());
                Log.e("only year print", Integer.toString(dates.get(0).getYear()));
                Log.e("only month print", Integer.toString(dates.get(0).getMonth()+1));
                Log.e("only day print", Integer.toString(dates.get(0).getDay()));
                String compare = dates.get(0).toString();
                Firstday = Integer.toString(dates.get(0).getYear())+"-"+Integer.toString(dates.get(0).getMonth()+1)+"-"+Integer.toString(dates.get(0).getDay()); //체크인 날짜
                Log.e("firstday","make:"+Firstday+"/ maked: "+compare);
                Lastday = Integer.toString(dates.get(dates.size()-1).getYear())+"-"+Integer.toString(dates.get(dates.size()-1).getMonth()+1)+"-"+Integer.toString(dates.get(dates.size()-1).getDay()); //체크아웃 날짜
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
