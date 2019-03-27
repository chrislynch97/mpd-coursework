package org.clynch203.gcu.coursework.activities;

import android.os.Bundle;
import android.support.constraint.Placeholder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.models.Item;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String startDate = getIntent().getStringExtra("startDate");
        TextView resultStartDate = findViewById(R.id.resultStartDate);
        resultStartDate.setText(String.format(getResources().getString(R.string.start_date), startDate));

        String endDate = getIntent().getStringExtra("endDate");
        TextView resultEndDate = findViewById(R.id.resultEndDate);
        resultEndDate.setText(String.format(getResources().getString(R.string.end_date), endDate));

        Item mostNorthernItem = getIntent().getExtras().getParcelable("mostNorthernItem");
        Item mostEasternItem = getIntent().getExtras().getParcelable("mostEasternItem");
        Item mostSouthernItem = getIntent().getExtras().getParcelable("mostSouthernItem");
        Item mostWesternItem = getIntent().getExtras().getParcelable("mostWesternItem");
        Item largestMagnitudeItem = getIntent().getExtras().getParcelable("largestMagnitudeItem");
        Item deepestItem = getIntent().getExtras().getParcelable("deepestItem");
        Item shallowestItem = getIntent().getExtras().getParcelable("shallowestItem");



        FrameLayout backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
