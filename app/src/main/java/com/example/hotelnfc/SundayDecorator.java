package com.example.hotelnfc;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

public class SundayDecorator implements DayViewDecorator {

    private Calendar calendar = Calendar.getInstance();

    int weekDay;

    public SundayDecorator() {}

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        day.copyTo(calendar);

        weekDay = calendar.get(Calendar.DAY_OF_WEEK);

        return weekDay == Calendar.SUNDAY && weekDay == calendar.get(day.getMonth()+2);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}
