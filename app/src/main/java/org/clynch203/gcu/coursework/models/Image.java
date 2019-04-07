//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

package org.clynch203.gcu.coursework.models;

import android.support.annotation.NonNull;

public class Image {

    private String title;
    private String url;
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    @NonNull
    public String toString() {
        return "Image{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
