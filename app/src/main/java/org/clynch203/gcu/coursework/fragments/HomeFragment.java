package org.clynch203.gcu.coursework.fragments;

import android.content.Intent;
import android.os.Bundle;
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

import static org.clynch203.gcu.coursework.util.Constants.ITEM_ACTIVITY_REQUEST_CODE;

public class HomeFragment extends Fragment {

    private ChannelController channelController;
    private LinearLayout itemContainer;
    private int currentItemCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        itemContainer = fragmentView.findViewById(R.id.homeItemContainer);
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        displayItems(channelController.items());
    }

    private ConstraintLayout createItemView(final Item item) {
        LayoutInflater inflater = getLayoutInflater();
        ConstraintLayout layout = ObjectToView.createSimpleItemView(inflater, itemContainer, requireActivity(), item, true);

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
                startActivityForResult(intent, ITEM_ACTIVITY_REQUEST_CODE);
            }
        });

        return layout;
    }

    public void setChannelController(ChannelController channelController) {
        this.channelController = channelController;
    }

    public void displayItems(final ArrayList<Item> items) {
        ArrayList<ConstraintLayout> layouts = new ArrayList<>();
        for (Item item : items)
            layouts.add(createItemView(item));
        itemContainer.removeAllViews();
        for (ConstraintLayout layout : layouts)
            itemContainer.addView(layout);
    }

    public void sendRequest(Intent requestIntent) {
        String startDate = requestIntent.getStringExtra("startDate");
        String endDate = requestIntent.getStringExtra("endDate");
        ArrayList<Item> items = channelController.itemsBetweenDate(startDate, endDate);

        Intent intent = new Intent(getActivity(), ResultActivity.class);

        intent.putExtra("itemsEmpty", items.size() == 0);

        if (items.size() > 0) {

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

        }
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((MainActivity) requireActivity()).activityResult(requestCode, resultCode, data);
    }
}
