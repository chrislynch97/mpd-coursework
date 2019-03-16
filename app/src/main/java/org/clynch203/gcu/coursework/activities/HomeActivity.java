package org.clynch203.gcu.coursework.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.models.Channel;
import org.clynch203.gcu.coursework.models.Item;
import org.clynch203.gcu.coursework.util.XMLParser;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout itemContainer;
    private int currentItemCount = 0;
    private Channel channel;
    ArrayList<ConstraintLayout> itemViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get data that was fetched on launched screen
        String dataToParse = getIntent().getStringExtra("data");
        channel = XMLParser.parseData(dataToParse);

        // init toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show menu
            }
        });

        // init container
        itemContainer = findViewById(R.id.itemContainer);

        // init and display views
        itemViews = new ArrayList<>(channel.getItems().size());

        for (Item item : channel.getItems())
            itemViews.add(createItemView(item));

        for (ConstraintLayout layout : itemViews)
            itemContainer.addView(layout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchItems(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // search
                return true;
            case R.id.action_sort:
                // sort
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ConstraintLayout createItemView(Item item) {
        LayoutInflater inflater = getLayoutInflater();
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.template_item_simple, itemContainer, false);
        DecimalFormat format = new DecimalFormat("###.##");

        layout.setTag("item"+item.getId());

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
            scale.setImageResource(R.drawable.scale_great);
        } else if (mag >=7 && mag <= 7.9 ) {
            scale.setImageResource(R.drawable.scale_major);
        } else if (mag >=6.1 && mag <= 6.9 ) {
            scale.setImageResource(R.drawable.scale_strong);
        } else if (mag >=5.5 && mag <= 6 ) {
            scale.setImageResource(R.drawable.scale_moderate);
        } else if (mag > 2.5 && mag <= 5.4 ) {
            scale.setImageResource(R.drawable.scale_light);
        } else if (mag <= 2.5){
            scale.setImageResource(R.drawable.scale_minor);
        }

        // set magnitude
        TextView magnitude = (TextView) layout.getViewById(R.id.magnitude);
        magnitude.setText(format.format(item.getMagnitude()));

        // set category
        TextView category = (TextView) layout.getViewById(R.id.category);
        category.setText(String.format(getResources().getString(R.string.category), item.getCategory()));

        return layout;
    }

    private void searchItems(String text) {
        for (ConstraintLayout itemView : itemViews) {
            TextView textView = (TextView) itemView.getViewById(R.id.location);
            if (! textView.getText().toString().toLowerCase().contains(text.toLowerCase())) {
                itemView.setVisibility(View.GONE);
            } else {
                itemView.setVisibility(View.VISIBLE);
            }
        }
    }
}
