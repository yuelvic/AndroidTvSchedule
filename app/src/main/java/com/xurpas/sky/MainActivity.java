package com.xurpas.sky;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity {

    private HorizontalCalendar mHorizontalCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        mHorizontalCalendar = new HorizontalCalendar.Builder(this, R.id.cln_view)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .configure()
                .textSize(12f, 16f, 12f)
                .end()
                .build();
        mHorizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
//                mEpg.setEPGData(new EPGDataImpl(new MockDataService().getMockData()));
//                mEpg.recalculateAndRedraw(false);
            }
        });
    }
}
