package org.clynch203.gcu.coursework.controllers;

import org.clynch203.gcu.coursework.models.Channel;
import org.clynch203.gcu.coursework.models.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChannelController {

    private final Channel channel;

    public ChannelController(Channel channel) {
        this.channel = channel;
    }

    public ArrayList<Item> items() {
        return channel.getItems();
    }

    public ArrayList<Item> searchItemsByLocation(final String searchQuery) {
        ArrayList<Item> searchResult = new ArrayList<>();
        for (Item item : items())
            if (item.getLocation().toLowerCase().contains(searchQuery.toLowerCase()))
                searchResult.add(item);
        return searchResult;
    }

    private ArrayList<Item> itemsBetweenDate(Date startDate, Date endDate) {
        ArrayList<Item> matchingItems = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);
        calendar.add(Calendar.SECOND, -1);
        startDate = calendar.getTime();

        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, 1);
        endDate = calendar.getTime();

        for (Item item : items()) {
            if (item.getOriginDate().after(startDate) && item.getOriginDate().before(endDate))
                matchingItems.add(item);
        }

        return matchingItems;
    }

    public ArrayList<Item> itemsBetweenDate(final String startDateString, final String endDateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = format.parse(startDateString);
        Date endDate = format.parse(endDateString);
        return itemsBetweenDate(startDate, endDate);
    }

    public Item mostNorthernItem(final ArrayList<Item> items) {
        Item mostNorthernItem = items.get(0);
        for (Item item : items)
            if (item.getLat() > mostNorthernItem.getLat())
                mostNorthernItem = item;
        return mostNorthernItem;
    }

    public Item mostEasternItem(final ArrayList<Item> items) {
        Item mostEasternItem = items.get(0);
        for (Item item : items)
            if (item.getLon() > mostEasternItem.getLon())
                mostEasternItem = item;
        return mostEasternItem;
    }

    public Item mostSouthernItem(final ArrayList<Item> items) {
        Item mostSouthernItem = items.get(0);
        for (Item item : items)
            if (item.getLat() < mostSouthernItem.getLat())
                mostSouthernItem = item;
        return mostSouthernItem;
    }

    public Item mostWesternItem(final ArrayList<Item> items) {
        Item mostWesternItem = items.get(0);
        for (Item item : items)
            if (item.getLon() < mostWesternItem.getLon())
                mostWesternItem = item;
        return mostWesternItem;
    }

    public Item largestMagnitudeItem(final ArrayList<Item> items) {
        Item largestMagnitudeItem = items.get(0);
        for (Item item : items)
            if (item.getMagnitude() > largestMagnitudeItem.getMagnitude())
                largestMagnitudeItem = item;
        return largestMagnitudeItem;
    }

    public Item deepestItem(final ArrayList<Item> items) {
        Item deepestItem = items.get(0);
        for (Item item : items)
            if (item.getDepth() > deepestItem.getDepth())
                deepestItem = item;
        return deepestItem;
    }

    public Item shallowestItem(final ArrayList<Item> items) {
        Item shallowestItem = items.get(0);
        for (Item item : items)
            if (item.getDepth() < shallowestItem.getDepth())
                shallowestItem = item;
        return shallowestItem;
    }
}
