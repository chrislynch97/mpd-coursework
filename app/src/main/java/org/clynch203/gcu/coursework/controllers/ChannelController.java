//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

package org.clynch203.gcu.coursework.controllers;

import org.clynch203.gcu.coursework.models.Channel;
import org.clynch203.gcu.coursework.models.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Controller for Channel object.
 * Used for performing searches on items.
 */
public class ChannelController {

    private final Channel channel;

    public ChannelController(Channel channel) {
        this.channel = channel;
    }

    /**
     * Get all Items in Channel.
     *
     * @return ArrayList of Items.
     */
    public ArrayList<Item> items() {
        return channel.getItems();
    }

    /**
     * Used to get all Items which Location contains the searchQuery text.
     *
     * @param searchQuery Text to search for
     * @return ArrayList of Items matching search.
     */
    public ArrayList<Item> searchItemsByLocation(final String searchQuery) {
        ArrayList<Item> searchResult = new ArrayList<>();
        for (Item item : items())
            if (item.getLocation().toLowerCase().contains(searchQuery.toLowerCase()))
                searchResult.add(item);
        return searchResult;
    }

    /**
     * Search for all Items which are between two Dates.
     *
     * @param startDate Date of start date.
     * @param endDate   Date of end date.
     * @return ArrayList of Items between the two Dates inclusive.
     */
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

    /**
     * Search for all Items which are between two Dates.
     *
     * @param startDateString String of start date.
     * @param endDateString   String of end date.
     * @return ArrayList of Items between the two dates inclusive.
     */
    public ArrayList<Item> itemsBetweenDate(final String startDateString, final String endDateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        Date startDate;
        Date endDate;
        try {
            startDate = format.parse(startDateString);
            endDate = format.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return itemsBetweenDate(startDate, endDate);
    }

    /**
     * Search ArrayList of Items for most northern Item.
     *
     * @param items ArrayList of Items to search.
     * @return Most northern Item.
     */
    public Item mostNorthernItem(final ArrayList<Item> items) {
        if (items.size() == 0) return null;
        Item mostNorthernItem = items.get(0);
        for (Item item : items)
            if (item.getLat() > mostNorthernItem.getLat())
                mostNorthernItem = item;
        return mostNorthernItem;
    }

    /**
     * Search ArrayList of Items for most eastern Item.
     *
     * @param items ArrayList of Items to search.
     * @return Most eastern Item.
     */
    public Item mostEasternItem(final ArrayList<Item> items) {
        if (items.size() == 0) return null;
        Item mostEasternItem = items.get(0);
        for (Item item : items)
            if (item.getLon() > mostEasternItem.getLon())
                mostEasternItem = item;
        return mostEasternItem;
    }

    /**
     * Search ArrayList of Items for most southern Item.
     *
     * @param items ArrayList of Items to search.
     * @return Most southern Item.
     */
    public Item mostSouthernItem(final ArrayList<Item> items) {
        if (items.size() == 0) return null;
        Item mostSouthernItem = items.get(0);
        for (Item item : items)
            if (item.getLat() < mostSouthernItem.getLat())
                mostSouthernItem = item;
        return mostSouthernItem;
    }

    /**
     * Search ArrayList of Items for most western Item.
     *
     * @param items ArrayList of Items to search.
     * @return Most western Item.
     */
    public Item mostWesternItem(final ArrayList<Item> items) {
        if (items.size() == 0) return null;
        Item mostWesternItem = items.get(0);
        for (Item item : items)
            if (item.getLon() < mostWesternItem.getLon())
                mostWesternItem = item;
        return mostWesternItem;
    }

    /**
     * Search ArrayList of Items for Item with largest magnitude.
     *
     * @param items ArrayList of Items to search.
     * @return Item with largest magnitude.
     */
    public Item largestMagnitudeItem(final ArrayList<Item> items) {
        if (items.size() == 0) return null;
        Item largestMagnitudeItem = items.get(0);
        for (Item item : items)
            if (item.getMagnitude() > largestMagnitudeItem.getMagnitude())
                largestMagnitudeItem = item;
        return largestMagnitudeItem;
    }

    /**
     * Search ArrayList of Items for the deepest Item.
     *
     * @param items ArrayList of Items to search.
     * @return Item with largest depth.
     */
    public Item deepestItem(final ArrayList<Item> items) {
        if (items.size() == 0) return null;
        Item deepestItem = items.get(0);
        for (Item item : items)
            if (item.getDepth() > deepestItem.getDepth())
                deepestItem = item;
        return deepestItem;
    }

    /**
     * Search ArrayList of Items for the shallowest Item.
     *
     * @param items ArrayList of Items to search.
     * @return Item with lowest depth.
     */
    public Item shallowestItem(final ArrayList<Item> items) {
        if (items.size() == 0) return null;
        Item shallowestItem = items.get(0);
        for (Item item : items)
            if (item.getDepth() < shallowestItem.getDepth())
                shallowestItem = item;
        return shallowestItem;
    }
}
