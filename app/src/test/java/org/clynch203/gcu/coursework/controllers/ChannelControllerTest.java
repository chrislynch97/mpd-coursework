package org.clynch203.gcu.coursework.controllers;

import org.clynch203.gcu.coursework.models.Channel;
import org.clynch203.gcu.coursework.models.Item;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

public class ChannelControllerTest {

    private ArrayList<Item> items;
    private Channel channel;
    private ChannelController channelController;
    private SimpleDateFormat simpleDateFormat;

    @Before
    public void setUp() {
        items = new ArrayList<>();
        channel = new Channel();
        channelController = new ChannelController(channel);
        String pattern = "EEE, dd MMM yyyy HH:mm:ss";
        simpleDateFormat = new SimpleDateFormat(pattern, Locale.UK);
    }

    @Test
    public void searchItemsByLocationTestNoMatches() {
        initialiseItems();
        channel.setItems(items);
        ArrayList<Item> matchingItems = channelController.searchItemsByLocation("qwerty");
        assertEquals(0, matchingItems.size());
    }

    @Test
    public void searchItemsByLocationTestMatches() {
        initialiseItems();
        channel.setItems(items);
        ArrayList<Item> matchingItems = channelController.searchItemsByLocation("her");

        assertEquals(3, matchingItems.size());

        assertEquals(0, matchingItems.get(0).getId());
        assertEquals(5, matchingItems.get(1).getId());
        assertEquals(6, matchingItems.get(2).getId());
    }

    @Test
    public void itemsBetweenDateTestNoMatches() {
        initialiseItems();
        channel.setItems(items);

        String startDate = "01-04-2019";
        String endDate = "03-04-2019";

        ArrayList<Item> matchingItems=  channelController.itemsBetweenDate(startDate, endDate);
        assertEquals(0, matchingItems.size());
    }

    @Test
    public void itemsBetweenDateTestMatches() {
        initialiseItems();
        channel.setItems(items);

        String startDate = "11-03-2019";
        String endDate = "19-03-2019";

        ArrayList<Item> matchingItems = channelController.itemsBetweenDate(startDate, endDate);
        assertEquals(3, matchingItems.size());

        assertEquals(3, matchingItems.get(0).getId());
        assertEquals(5, matchingItems.get(1).getId());
        assertEquals(6, matchingItems.get(2).getId());
    }

    @Test
    public void mostNorthernItemTestEmptyList() {
        assertNull(channelController.mostNorthernItem(items));
    }

    @Test
    public void mostNorthernItemTest() {
        initialiseItems();
        assertEquals(6, channelController.mostNorthernItem(items).getId());
    }

    @Test
    public void mostEasternItemTestEmptyList() {
        assertNull(channelController.mostEasternItem(items));
    }

    @Test
    public void mostEasternItemTest() {
        initialiseItems();
        assertEquals(4, channelController.mostEasternItem(items).getId());
    }

    @Test
    public void mostSouthernItemTestEmptyList() {
        assertNull(channelController.mostSouthernItem(items));
    }

    @Test
    public void mostSouthernItem() {
        initialiseItems();
        assertEquals(5, channelController.mostSouthernItem(items).getId());
    }

    @Test
    public void mostWesternItemTestEmptyList() {
        assertNull(channelController.mostWesternItem(items));
    }

    @Test
    public void mostWesternItemTest() {
        initialiseItems();
        assertEquals(2, channelController.mostWesternItem(items).getId());
    }

    @Test
    public void largestMagnitudeItemTestEmptyList() {
        assertNull(channelController.largestMagnitudeItem(items));
    }

    @Test
    public void largestMagnitudeItemTest() {
        initialiseItems();
        assertEquals(4, channelController.largestMagnitudeItem(items).getId());
    }

    @Test
    public void deepestItemTestEmptyList() {
        assertNull(channelController.deepestItem(items));
    }

    @Test
    public void deepestItemTest() {
        initialiseItems();
        assertEquals(5, channelController.deepestItem(items).getId());
    }

    @Test
    public void shallowestItemTestEmptyList() {
        assertNull(channelController.deepestItem(items));
    }

    @Test
    public void shallowestItemTest() {
        initialiseItems();
        assertEquals(6, channelController.shallowestItem(items).getId());
    }

    private void initialiseItems() {
        // deepest id: 5
        // shallowest id: 6
        int[] depths = {2, 2, 3, 5, 6, 10, 1}; // length 7

        //largest magnitude id: 4
        double[] magnitudes = {-0.1, 1, 2.5, 1.2, 5.6, 3.4, 4};

        // most north id: 6
        // most south id: 5
        double[] latitudes = {-50.6, 11.5, 20.36, -31.4, 55.31, -61.2, 71.3};

        // most east id: 4
        // most west id: 2
        double[] longitudes = {-25.6, 12.31, -30.512, 26.6, 37.46, -3.5, 1.56};

        // matching location ids: 0, 5, 6
        String[] locations = {"Southern North Sea", "Firth of Clyde", "Skye Highland", "Islay, Argyll and Bute", "Firth of Clyde", "Byford, Hereford", "Southern North Sea"};

        // matching date id: 3, 5, 6
        String[] dateStrings = {
                "Sat, 30 Mar 2019 05:03:46",
                "Sun, 31 Mar 2019 12:30:41",
                "Fri, 22 Mar 2019 17:01:13",
                "Thu, 14 Mar 2019 03:23:17",
                "Wed, 06 Mar 2019 02:13:25",
                "Mon, 11 Mar 2019 00:01:20",
                "Tue, 12 Mar 2019 01:37:46"
        };

        Date[] dates = new Date[7];

        try {
            dates[0] = simpleDateFormat.parse(dateStrings[0]);
            dates[1] = simpleDateFormat.parse(dateStrings[1]);
            dates[2] = simpleDateFormat.parse(dateStrings[2]);
            dates[3] = simpleDateFormat.parse(dateStrings[3]);
            dates[4] = simpleDateFormat.parse(dateStrings[4]);
            dates[5] = simpleDateFormat.parse(dateStrings[5]);
            dates[6] = simpleDateFormat.parse(dateStrings[6]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Item item;
        for (int i = 0; i < depths.length; i++) {
            item = new Item(i);
            item.setDepth(depths[i]);
            item.setMagnitude(magnitudes[i]);
            item.setLat(latitudes[i]);
            item.setLon(longitudes[i]);
            item.setLocation(locations[i]);
            item.setOriginDate(dates[i]);
            items.add(item);
        }

    }
}