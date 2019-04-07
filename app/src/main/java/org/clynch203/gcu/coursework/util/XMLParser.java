//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

package org.clynch203.gcu.coursework.util;

import org.clynch203.gcu.coursework.models.Channel;
import org.clynch203.gcu.coursework.models.Image;
import org.clynch203.gcu.coursework.models.Item;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class for parsing XML data into Channel, Image, and Item objects.
 */
public abstract class XMLParser {

    public static Channel parseData(String dataToParse) {
        Channel channel = null;
        Image image = null;
        Item item = null;

        String pattern = "EEE, dd MMM yyyy HH:mm:ss";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.UK);

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(dataToParse));

            int eventType = xpp.getEventType();

            // used to keep track of current type, Channel, Image, or Item
            // as there are some tags inside each, i.e. all 3 have a title tag
            String type = "";

            int itemCount = 0;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    switch (xpp.getName().toLowerCase()) {
                        case "channel":
                            channel = new Channel();
                            type = "channel";
                            break;
                        case "image":
                            image = new Image();
                            type = "image";
                            break;
                        case "item":
                            item = new Item(itemCount);
                            itemCount++;
                            type = "item";
                            break;
                        case "title":
                            switch (type) {
                                case "channel":
                                    channel.setTitle(xpp.nextText());
                                    break;
                                case "image":
                                    image.setTitle(xpp.nextText());
                                    break;
                                case "item":
                                    item.setTitle(xpp.nextText());
                                    break;
                            }
                            break;
                        case "link":
                            switch (type) {
                                case "channel":
                                    channel.setLink(xpp.nextText());
                                    break;
                                case "image":
                                    image.setLink(xpp.nextText());
                                    break;
                                case "item":
                                    item.setLink(xpp.nextText());
                                    break;
                            }
                            break;
                        case "description":
                            switch (type) {
                                case "channel":
                                    channel.setDescription(xpp.nextText());
                                    break;
                                case "item":
                                    String description = xpp.nextText();
                                    item.setDescription(description);

                                    String[] descriptionItems = description.split(";");

                                    String origin = descriptionItems[0].substring(18);
                                    Date date = simpleDateFormat.parse(origin);
                                    item.setOriginDate(date);

                                    String[] location = descriptionItems[1].split(":");
                                    item.setLocation(location[1].substring(1));

                                    String[] depth = descriptionItems[3].split(":");
                                    String d = depth[1].replace("km", "").replace(" ", "");
                                    int depthInt = 0;
                                    if (!d.equals("")) depthInt = Integer.parseInt(d);
                                    item.setDepth(depthInt);

                                    String[] magnitude = descriptionItems[4].split(":");
                                    item.setMagnitude(Double.parseDouble(magnitude[1]));

                                    break;
                            }
                            break;
                        case "language":
                            switch (type) {
                                case "channel":
                                    channel.setLanguage(xpp.nextText());
                                    break;
                            }
                            break;
                        case "lastbuilddate":
                            switch (type) {
                                case "channel":
                                    Date date = simpleDateFormat.parse(xpp.nextText());
                                    channel.setLastBuildDate(date);
                                    break;
                            }
                            break;
                        case "url":
                            switch (type) {
                                case "image":
                                    image.setUrl(xpp.nextText());
                                    break;
                            }
                            break;
                        case "pubdate":
                            switch (type) {
                                case "item":
                                    Date date = simpleDateFormat.parse(xpp.nextText());
                                    item.setPubDate(date);
                                    break;
                            }
                            break;
                        case "category":
                            switch (type) {
                                case "item":
                                    item.setCategory(xpp.nextText());
                                    break;
                            }
                            break;
                        case "lat":
                            switch (type) {
                                case "item":
                                    item.setLat(Double.parseDouble(xpp.nextText()));
                                    break;
                            }
                            break;
                        case "lon":
                            switch (type) {
                                case "item":
                                    item.setLon(Double.parseDouble(xpp.nextText()));
                                    break;
                            }
                            break;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    switch (xpp.getName().toLowerCase()) {
                        case "channel":
                            break;
                        case "image":
                            if (channel != null) {
                                channel.setImage(image);
                            }
                            break;
                        case "item":
                            if (channel != null) {
                                channel.addItem(item);
                            }
                            break;
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return channel;
    }

}
