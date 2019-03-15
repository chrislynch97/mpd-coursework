package org.clynch203.gcu.coursework.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.clynch203.gcu.coursework.R;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String dataToParse = getIntent().getStringExtra("data");
        Channel channel = parseData(dataToParse);
    }

    private Channel parseData(String dataToParse) {
        Channel channel = null;
        Image image = null;
        Item item = null;
        String pattern = "EEE, dd MMM yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(dataToParse));
            int eventType = xpp.getEventType();
            String type = "";
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
                            item = new Item();
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
                                    item.setDescription(xpp.nextText());
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
//                                    String[] dateArray = splitDate(xpp.nextText());
//                                    Month month =
//                                    LocalDateTime date = LocalDateTime.of(
//                                            Integer.parseInt(dateArray[3]), //year
//                                            dateArray[2], // month
//                                            Integer.parseInt(dateArray[1]), // day
//                                            Integer.parseInt(dateArray[4]),// hour
//                                            Integer.parseInt(dateArray[5]),// minute
//                                            Integer.parseInt(dateArray[6])// second
//
//                                    )

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
                            System.out.println(channel.toString());
                            break;
                        case "image":
                            channel.setImage(image);
                            break;
                        case "item":
                            channel.addItem(item);
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

//    private String[] splitDate(String dateString) {
//        String[] dateArray = new String[7];
//        dateArray[0] = dateString.substring(0, 3); // day of week e.g Thu
//        dateArray[1] = dateString.substring(5, 7); // day of month e.g. 14
//        dateArray[2] = dateString.substring(8, 11); // month of year e.g. Mar
//        dateArray[3] = dateString.substring(12, 16); // year e.g. 2019
//        dateArray[4] = dateString.substring(17, 19); // hour of day e.g. 19
//        dateArray[5] = dateString.substring(20, 22); // minute of hour e.g. 40
//        dateArray[6] = dateString.substring(23, 25); // second of minute e.g. 01
//        return dateArray;
//    }
//
//    private Month strToMonth(String monthString) {
//        switch (monthString) {
//            case "Jan":
//                return new Month();
//            case "Feb"
//            case "Mar"
//            case "Apr"
//            case "May"
//            case "Jun"
//            case "Jul"
//            case "Aug"
//            case "Sep"
//            case "Oct"
//            case "Nov"
//            case "Dec"
//        }
//    }

}
