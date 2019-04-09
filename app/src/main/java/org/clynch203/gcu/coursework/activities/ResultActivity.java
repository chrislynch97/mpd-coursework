//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

package org.clynch203.gcu.coursework.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.models.Item;
import org.clynch203.gcu.coursework.util.ObjectToView;

/**
 * Activity for displaying results of searching between 2 dates.
 */
public class ResultActivity extends AppCompatActivity {

    private LinearLayout itemContainer;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        itemContainer = findViewById(R.id.resultItemActivity);
        inflater = getLayoutInflater();

        boolean itemsEmpty = getIntent().getBooleanExtra("itemsEmpty", true);

        if (itemsEmpty) {
            displayNoItems();
        } else {
            displayItems();
        }

        FrameLayout backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Removes the ScrollLayout containing the headers form the layout
     * and sets the TextView containing a no matching items string to visible.
     */
    private void displayNoItems() {
        ConstraintLayout resultLayout = findViewById(R.id.resultLayout);
        ScrollView resultScrollView = findViewById(R.id.resultScrollView);
        resultLayout.removeView(resultScrollView);
        findViewById(R.id.noMatchingText).setVisibility(View.VISIBLE);
        findViewById(R.id.noMatchingStudent).setVisibility(View.VISIBLE);
    }

    /**
     * Displays items returned from search.
     */
    private void displayItems() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            displayNoItems();
            return;
        }

        Item mostNorthernItem = bundle.getParcelable("mostNorthernItem");
        Item mostEasternItem = bundle.getParcelable("mostEasternItem");
        Item mostSouthernItem = bundle.getParcelable("mostSouthernItem");
        Item mostWesternItem = bundle.getParcelable("mostWesternItem");
        Item largestMagnitudeItem = bundle.getParcelable("largestMagnitudeItem");
        Item deepestItem = bundle.getParcelable("deepestItem");
        Item shallowestItem = bundle.getParcelable("shallowestItem");

        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, mostNorthernItem), 1);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, mostEasternItem), 3);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, mostSouthernItem), 5);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, mostWesternItem), 7);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, largestMagnitudeItem), 9);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, deepestItem), 11);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, shallowestItem), 13);
    }

}
