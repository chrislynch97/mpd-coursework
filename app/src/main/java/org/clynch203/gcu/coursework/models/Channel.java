package org.clynch203.gcu.coursework.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Channel {

    private String title;
    private String link;
    private String description;
    private String language;
    private LocalDateTime lastBuildDate;
    private Image image;
    private ArrayList<Item> items;

    public Channel(String title, String link, String description, String language, LocalDateTime lastBuildDate, Image image, ArrayList<Item> items) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
        this.lastBuildDate = lastBuildDate;
        this.image = image;
        this.items = items;
    }

}
