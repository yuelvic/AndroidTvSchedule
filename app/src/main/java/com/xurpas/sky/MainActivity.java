package com.xurpas.sky;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xurpas.epg.EPG;
import com.xurpas.epg.EPGClickListener;
import com.xurpas.epg.domain.EPGChannel;
import com.xurpas.epg.domain.EPGEvent;
import com.xurpas.epg.misc.EPGDataImpl;
import com.xurpas.epg.misc.MockDataService;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity {

    private HorizontalCalendar mHorizontalCalendar;
    private EPG mEpg;

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
                mEpg.setEPGData(new EPGDataImpl(new MockDataService().getMockData()));
                mEpg.recalculateAndRedraw(false);
            }
        });

        Map<EPGChannel, List<EPGEvent>> map = new MockDataService().getMockData();

        // mock epg data
        mEpg = findViewById(R.id.epg_view);
        mEpg.setEPGData(new EPGDataImpl(map));
        mEpg.recalculateAndRedraw(false);
        mEpg.setEPGClickListener(new EPGClickListener() {
            @Override
            public void onChannelClicked(int channelPosition, EPGChannel epgChannel) {

            }

            @Override
            public void onEventClicked(int channelPosition, int programPosition, EPGEvent epgEvent) {

            }

            @Override
            public void onResetButtonClicked() {
                mEpg.recalculateAndRedraw(true);
            }
        });
    }
}
