package org.clynch203.gcu.coursework.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item implements Parcelable {

    private static final String pattern = "EEE, dd MMM yyyy HH:mm:ss";

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
    private final int id;

    public Item(final int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDateString() {
        DateFormat format = new SimpleDateFormat(pattern);
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
        DateFormat format = new SimpleDateFormat(pattern);
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

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }

    };

    @Override
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
