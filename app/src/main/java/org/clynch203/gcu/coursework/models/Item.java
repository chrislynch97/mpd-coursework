//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

package org.clynch203.gcu.coursework.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Item Model for all Items in the XML data.
 * Uses Parcelable interface to pass Item objects between Activities.
 */
public class Item implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }

    };
    private static final String pattern = "EEE, dd MMM yyyy HH:mm:ss";
    private final int id;
    private String title;
    private String description;
    private String link;
    private Date pubDate;
    private String category;
    private double lat;
    private double lon;
    private String location;
    private double magnitude;
    private int depth;
    private Date originDate;

    public Item(final int id) {
        this.id = id;
    }

    private Item(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.link = in.readString();
        this.pubDate = new Date(in.readLong());
        this.category = in.readString();
        this.lat = in.readDouble();
        this.lon = in.readDouble();
        this.location = in.readString();
        this.magnitude = in.readDouble();
        this.depth = in.readInt();
        this.originDate = new Date(in.readLong());
        this.id = in.readInt();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDateString() {
        DateFormat format = new SimpleDateFormat(pattern, Locale.UK);
        return format.format(pubDate);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Date getOriginDate() {
        return originDate;
    }

    public void setOriginDate(Date originDate) {
        this.originDate = originDate;
    }

    public String getOriginDateString() {
        DateFormat format = new SimpleDateFormat(pattern, Locale.UK);
        return format.format(originDate);
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeLong(pubDate.getTime());
        dest.writeString(category);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(location);
        dest.writeDouble(magnitude);
        dest.writeInt(depth);
        dest.writeLong(originDate.getTime());
        dest.writeInt(id);
    }

    @Override
    @NonNull
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", pubDate=" + pubDate +
                ", category='" + category + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", location='" + location + '\'' +
                ", magnitude=" + magnitude +
                ", depth=" + depth +
                ", originDate=" + originDate +
                ", id=" + id +
                '}';
    }
}
