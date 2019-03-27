package org.clynch203.gcu.coursework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.controllers.ChannelController;
import org.clynch203.gcu.coursework.fragments.DateRangeFragment;
import org.clynch203.gcu.coursework.models.Item;
import org.clynch203.gcu.coursework.util.ObjectToView;
import org.clynch203.gcu.coursework.util.XMLParser;

import java.text.ParseException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements DateRangeFragment.InterfaceCommunicator {

    private LinearLayout itemContainer;
    private int currentItemCount = 0;
    private ChannelController channelController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        channelController = new ChannelController(
                XMLParser.parseData(getIntent().getStringExtra("data")));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - SHOW MENU
            }
        });

        itemContainer = findViewById(R.id.homeItemContainer);

        displayItems(channelController.items());
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String query) {
                displayItems(channelController.searchItemsByLocation(query));
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_date:
                DateRangeFragment fragment = new DateRangeFragment();
                fragment.show(getSupportFragmentManager(), "dateRangeFragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ConstraintLayout createItemView(final Item item) {
        LayoutInflater inflater = getLayoutInflater();

        ConstraintLayout layout = ObjectToView.createSimpleItemView(inflater, itemContainer, this, item);

        // set background color
        int backgroundColor = ResourcesCompat.getColor(getResources(), R.color.item_1_background, null);
        switch (currentItemCount) {
            case 0:
                currentItemCount++;
                break;
            case 1:
                backgroundColor = ResourcesCompat.getColor(getResources(), R.color.item_2_background, null);
                currentItemCount++;
                break;
            case 2:
                backgroundColor = ResourcesCompat.getColor(getResources(), R.color.item_3_background, null);
                currentItemCount++;
                break;
            case 3:
                backgroundColor = ResourcesCompat.getColor(getResources(), R.color.item_4_background, null);
                currentItemCount = 0;
                break;
        }

        layout.setBackgroundColor(backgroundColor);

        return layout;

    }

    private void displayItems(final ArrayList<Item> items) {
        ArrayList<ConstraintLayout> layouts = new ArrayList<>();
        for (Item item : items)
            layouts.add(createItemView(item));
        itemContainer.removeAllViews();
        for (ConstraintLayout layout : layouts)
            itemContainer.addView(layout);
    }

    @Override
    public void sendRequest(int code, Intent requestIntent) {
        String startDate = requestIntent.getStringExtra("startDate");
        String endDate = requestIntent.getStringExtra("endDate");
        ArrayList<Item> items = new ArrayList<>();
        try {
            items = channelController.itemsBetweenDate(startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(HomeActivity.this, ResultActivity.class);

        Item mostNorthernItem = channelController.mostNorthernItem(items);
        intent.putExtra("mostNorthernItem", mostNorthernItem);

        Item mostEasternItem = channelController.mostEasternItem(items);
        intent.putExtra("mostEasternItem", mostEasternItem);

        Item mostSouthernItem = channelController.mostSouthernItem(items);
        intent.putExtra("mostSouthernItem", mostSouthernItem);

        Item mostWesternItem = channelController.mostWesternItem(items);
        intent.putExtra("mostWesternItem", mostWesternItem);

        Item largestMagnitudeItem = channelController.largestMagnitudeItem(items);
        intent.putExtra("largestMagnitudeItem", largestMagnitudeItem);

        Item deepestItem = channelController.deepestItem(items);
        intent.putExtra("deepestItem", deepestItem);

        Item shallowestItem = channelController.shallowestItem(items);
        intent.putExtra("shallowestItem", shallowestItem);

        startActivity(intent);

    }
}
