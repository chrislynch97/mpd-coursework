package org.clynch203.gcu.coursework.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {

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

    public int getId() {
        return id;
    }

    public String getOriginDateString() {
        String pattern = "EEE, dd MMM yyyy HH:mm:ss";
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(originDate);
    }
}
