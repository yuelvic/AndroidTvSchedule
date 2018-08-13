package com.xurpas.epg.misc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xurpas.epg.EPG;
import com.xurpas.epg.domain.EPGChannel;
import com.xurpas.epg.domain.EPGEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Kristoffer on 15-05-24.
 */
public class MockDataService {

    private static Random rand = new Random();
    private static List<Integer> availableEventLength = Lists.newArrayList(
            1000*60*15,  // 15 minutes
            1000*60*30,  // 30 minutes
            1000*60*45,  // 45 minutes
            1000*60*60,  // 60 minutes
            1000*60*120  // 120 minutes
    );

    private static List<String> availableEventTitles = Lists.newArrayList(
            "Avengers",
            "How I Met Your Mother",
            "Silicon Valley",
            "Late Night with Jimmy Fallon",
            "The Big Bang Theory",
            "Leon",
            "Die Hard"
    );

    private static List<String> availableChannelLogos = Lists.newArrayList(
            "https://s3-us-west-1.amazonaws.com/companies.comparably.com/26141/26141_logo_netflix.png",
            "https://pbs.twimg.com/profile_images/3751137674/1cbcb476107f46273ee50f29ccdd07bd.png",
            "https://www.seeklogo.net/wp-content/uploads/2017/06/fox-movies-logo.png",
            "https://static-s.aa-cdn.net/img/ios/454468674/5d1509d685803c6a2a5cb65b5b00c732?v=1",
            "https://kodi.tv/sites/default/files/styles/medium_crop/public/addon_assets/plugin.video.disneychannel_de/icon/icon.png?itok=g431wOpG"
    );

    public static Map<EPGChannel, List<EPGEvent>> getMockData() {
        HashMap<EPGChannel, List<EPGEvent>> result = Maps.newLinkedHashMap();

        long nowMillis = System.currentTimeMillis();

        for (int i=0 ; i < 20 ; i++) {
            EPGChannel epgChannel = new EPGChannel(availableChannelLogos.get(i % 5),
                    "Channel " + (i+1), Integer.toString(i));

            result.put(epgChannel, createEvents(epgChannel, nowMillis));
        }

        return result;
    }

    private static List<EPGEvent> createEvents(EPGChannel epgChannel, long nowMillis) {
        List<EPGEvent> result = Lists.newArrayList();

        long epgStart = nowMillis - EPG.DAYS_BACK_MILLIS;
        long epgEnd = nowMillis + EPG.DAYS_FORWARD_MILLIS;

        long currentTime = epgStart;

        while (currentTime <= epgEnd) {
            long eventEnd = getEventEnd(currentTime);
            EPGEvent epgEvent = new EPGEvent(currentTime, eventEnd, availableEventTitles.get(randomBetween(0, 6)));
            result.add(epgEvent);
            currentTime = eventEnd;
        }

        return result;
    }

    private static long getEventEnd(long eventStartMillis) {
        long length = availableEventLength.get(randomBetween(0,4));
        return eventStartMillis + length;
    }

    private static int randomBetween(int start, int end) {
        return start + rand.nextInt((end - start) + 1);
    }
}
