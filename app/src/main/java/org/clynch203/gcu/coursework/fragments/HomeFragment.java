//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

package org.clynch203.gcu.coursework.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.activities.ItemActivity;
import org.clynch203.gcu.coursework.activities.MainActivity;
import org.clynch203.gcu.coursework.activities.ResultActivity;
import org.clynch203.gcu.coursework.controllers.ChannelController;
import org.clynch203.gcu.coursework.models.Item;
import org.clynch203.gcu.coursework.util.ObjectToView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static org.clynch203.gcu.coursework.util.Constants.ITEM_ACTIVITY_REQUEST_CODE;

/**
 * Home Fragment for displaying information on all Items.
 */
public class HomeFragment extends Fragment {

    private ChannelController channelController;
    private LinearLayout itemContainer;
    private int currentItemCount;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        itemContainer = fragmentView.findViewById(R.id.homeItemContainer);
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        displayItems(channelController.items());
    }

    /**
     * Creates ConstraintLayout for an Item.
     *
     * @param item Item to create layout for.
     * @return ConstraintLayout with Item data.
     */
    private ConstraintLayout createItemView(final Item item) {
        LayoutInflater inflater = getLayoutInflater();
        ConstraintLayout layout = ObjectToView.createSimpleItemView(inflater, itemContainer, requireActivity(), item, true);

        // cycle through these colors when displaying Items
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

        final int finalBackgroundColor = backgroundColor;
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ItemActivity.class);
                intent.putExtra("backgroundColor", finalBackgroundColor);
                intent.putExtra("item", item);
                // use startActivityForResult to get information back from Activity
                // used to get if the 'view on map' button is clicked on the Item
                startActivityForResult(intent, ITEM_ACTIVITY_REQUEST_CODE);
            }
        });

        return layout;
    }

    public void setChannelController(ChannelController channelController) {
        this.channelController = channelController;
    }

    /**
     * Displays all Items in ArrayList.
     *
     * @param items ArrayList of Items to display.
     */
    public void displayItems(final ArrayList<Item> items) {
        currentItemCount = 0;
        ArrayList<ConstraintLayout> layouts = new ArrayList<>();
        for (Item item : items)
            layouts.add(createItemView(item));
        itemContainer.removeAllViews();
        for (ConstraintLayout layout : layouts)
            itemContainer.addView(layout);
    }

    /**
     * Called when DateRangeFragment is submitted to search between dates.
     *
     * @param requestIntent Intent with startDate and endDate for search.
     */
    public void sendRequest(Intent requestIntent) {
        String startDate = requestIntent.getStringExtra("startDate");
        String endDate = requestIntent.getStringExtra("endDate");
        ArrayList<Item> items = channelController.itemsBetweenDate(startDate, endDate);

        Intent intent = new Intent(getActivity(), ResultActivity.class);

        intent.putExtra("itemsEmpty", items.size() == 0);

        if (items.size() > 0) {
            intent.putExtra("mostNorthernItem", channelController.mostNorthernItem(items));
            intent.putExtra("mostEasternItem", channelController.mostEasternItem(items));
            intent.putExtra("mostSouthernItem", channelController.mostSouthernItem(items));
            intent.putExtra("mostWesternItem", channelController.mostWesternItem(items));
            intent.putExtra("largestMagnitudeItem", channelController.largestMagnitudeItem(items));
            intent.putExtra("deepestItem", channelController.deepestItem(items));
            intent.putExtra("shallowestItem", channelController.shallowestItem(items));
        }

        startActivity(intent);
    }

    /**
     * Called when Activity ends and returns here.
     * Used to pass if 'view on map' button was clicked on ItemActivity.
     *
     * @param requestCode Request code that was used when calling the Activity.
     * @param resultCode  The result code that the Activity returned.
     * @param data        The data returned from the Activity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle;
        Item item;

        if (requestCode == ITEM_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if ((bundle = data.getExtras()) != null) {
                        if ((item = bundle.getParcelable("item")) != null) {
                            ((MainActivity) requireActivity()).displayItemOnMap(item);
                        }
                    }
                }
            }
        }

    }
}
