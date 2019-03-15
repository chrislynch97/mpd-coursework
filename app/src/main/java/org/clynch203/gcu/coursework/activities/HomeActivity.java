package org.clynch203.gcu.coursework.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.models.Channel;
import org.clynch203.gcu.coursework.models.Item;
import org.clynch203.gcu.coursework.util.XMLParser;

import java.text.DecimalFormat;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout itemContainer;
    private LayoutInflater inflater;
    private int currentItemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get data that was fetched on launched screen
        String dataToParse = getIntent().getStringExtra("data");
        Channel channel = XMLParser.parseData(dataToParse);

        inflater = getLayoutInflater();

        itemContainer = findViewById(R.id.itemContainer);

        ConstraintLayout layout;
        for (Item item : channel.getItems()) {
            layout = createItemSimple(item, item.getId());
            itemContainer.addView(layout);
        }
    }

    private ConstraintLayout createItemSimple(Item item, int id) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.template_item_simple, itemContainer, false);
        DecimalFormat format = new DecimalFormat("###.##");

        layout.setTag("item"+id);

        switch (currentItemCount) {
            case 0:
                layout.setBackgroundColor(Color.parseColor("#4C98CF"));
                currentItemCount++;
                break;
            case 1:
                layout.setBackgroundColor(Color.parseColor("#4874A8"));
                currentItemCount++;
                break;
            case 2:
                layout.setBackgroundColor(Color.parseColor("#595386"));
                currentItemCount++;
                break;
            case 3:
                layout.setBackgroundColor(Color.parseColor("#524364"));
                currentItemCount = 0;
                break;
        }

        // set location
        TextView location = (TextView) layout.getViewById(R.id.location);
        location.setText(item.getLocation().split(",")[0]);

        // set date
        TextView originDate = (TextView) layout.getViewById(R.id.originDate);
        originDate.setText(item.getOriginDateString());

        // set depth
        TextView depth = (TextView) layout.getViewById(R.id.depth);
        depth.setText(String.format(getResources().getString(R.string.depth), item.getDepth()));

        // set scale
        ImageView scale = (ImageView) layout.getViewById(R.id.scale);
        double mag = item.getMagnitude();
        if (mag >= 8) {
            scale.setImageResource(R.drawable.mag_great);
        } else if (mag >=7 && mag <= 7.9 ) {
            scale.setImageResource(R.drawable.mag_major);
        } else if (mag >=6.1 && mag <= 6.9 ) {
            scale.setImageResource(R.drawable.mag_strong);
        } else if (mag >=5.5 && mag <= 6 ) {
            scale.setImageResource(R.drawable.mag_moderate);
        } else if (mag > 2.5 && mag <= 5.4 ) {
            scale.setImageResource(R.drawable.mag_light);
        } else if (mag <= 2.5){
            scale.setImageResource(R.drawable.mag_minor);
        }

        // set magnitude
        TextView magnitude = (TextView) layout.getViewById(R.id.magnitude);
        magnitude.setText(format.format(item.getMagnitude()));

        // set category
        TextView category = (TextView) layout.getViewById(R.id.category);
        category.setText(String.format(getResources().getString(R.string.category), item.getCategory()));

        return layout;
    }
}
