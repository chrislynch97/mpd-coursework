//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

package org.clynch203.gcu.coursework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.models.Item;

/**
 * Activity for displaying all information on an Item.
 */
public class ItemActivity extends AppCompatActivity implements View.OnClickListener {

    private Item item;
    private Button viewOnMapButton;
    private FrameLayout backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ConstraintLayout layout = findViewById(R.id.item_activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return; // TODO display error message

        // sets the background color as the color passed in
        // if no color is passed in then set as default color
        layout.setBackgroundColor(getIntent().getIntExtra("backgroundColor",
                ResourcesCompat.getColor(getResources(), R.color.item_2_background, null)));

        item = bundle.getParcelable("item");

        displayItemData();

        viewOnMapButton = findViewById(R.id.viewOnMapButton);
        viewOnMapButton.setOnClickListener(this);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
    }

    /**
     * Displays all Item data in table.
     */
    private void displayItemData() {
        setViewText(R.id.item_location, item.getLocation());
        setViewText(R.id.item_magnitude, String.valueOf(item.getMagnitude()));
        setViewText(R.id.item_depth, String.format(getResources().getString(R.string.depth_km), item.getDepth()));
        setViewText(R.id.item_category, item.getCategory());
        setViewText(R.id.item_latitude, String.valueOf(item.getLat()));
        setViewText(R.id.item_longitude, String.valueOf(item.getLon()));
        setViewText(R.id.item_origin_date, item.getOriginDateString());
        setViewText(R.id.item_pub_date, item.getPubDateString());
    }

    /**
     * Sets the text of given item id.
     *
     * @param id   ID of the view to set the text for.
     * @param text Text to set.
     */
    private void setViewText(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }

    @Override
    public void onClick(View v) {
        if (v == viewOnMapButton) {
            Intent intent = new Intent();
            intent.putExtra("item", item);
            setResult(RESULT_OK, intent);
            finish();
        } else if (v == backButton) {
            finish();
        }
    }
}
