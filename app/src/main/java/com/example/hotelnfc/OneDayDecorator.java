package com.example.hotelnfc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

import static android.graphics.Typeface.BOLD;

public class OneDayDecorator implements DayViewDecorator {

    private CalendarDay date;
    private Drawable highlightDrawable;
    private Context context;

    public OneDayDecorator() {
       date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
//        return  day.equals(CalendarDay.today());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(BOLD));
        view.addSpan(new RelativeSizeSpan(1.4f));
        view.addSpan(new ForegroundColorSpan(Color.GREEN));

//        view.setBackgroundDrawable(highlightDrawable);
//        view.addSpan(new ForegroundColorSpan(Color.WHITE));
//        view.addSpan(new StyleSpan(Typeface.BOLD));
//        view.addSpan(new RelativeSizeSpan(1.5f));
    }

    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
