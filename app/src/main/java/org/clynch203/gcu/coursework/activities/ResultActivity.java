package org.clynch203.gcu.coursework.activities;

import android.os.Bundle;
import android.support.constraint.Placeholder;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.models.Item;
import org.clynch203.gcu.coursework.util.ObjectToView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Item mostNorthernItem = getIntent().getExtras().getParcelable("mostNorthernItem");
        Item mostEasternItem = getIntent().getExtras().getParcelable("mostEasternItem");
        Item mostSouthernItem = getIntent().getExtras().getParcelable("mostSouthernItem");
        Item mostWesternItem = getIntent().getExtras().getParcelable("mostWesternItem");
        Item largestMagnitudeItem = getIntent().getExtras().getParcelable("largestMagnitudeItem");
        Item deepestItem = getIntent().getExtras().getParcelable("deepestItem");
        Item shallowestItem = getIntent().getExtras().getParcelable("shallowestItem");

        LinearLayout itemContainer = findViewById(R.id.resultItemActivity);
        LayoutInflater inflater = getLayoutInflater();

        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, mostNorthernItem), 1);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, mostEasternItem), 3);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, mostSouthernItem), 5);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, mostWesternItem), 7);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, largestMagnitudeItem), 9);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, deepestItem), 11);
        itemContainer.addView(ObjectToView.createSimpleItemView(inflater, itemContainer, this, shallowestItem), 13);

        FrameLayout backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
