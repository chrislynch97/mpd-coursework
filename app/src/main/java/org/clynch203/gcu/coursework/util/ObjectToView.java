package org.clynch203.gcu.coursework.util;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.activities.ItemActivity;
import org.clynch203.gcu.coursework.models.Item;

import java.text.DecimalFormat;

public abstract class ObjectToView {

    public static ConstraintLayout createSimpleItemView(
            final LayoutInflater inflater,
            final ViewGroup itemContainer,
            final Context context,
            final Item item) {

        ConstraintLayout layout = (ConstraintLayout) inflater
                .inflate(R.layout.template_item_simple, itemContainer, false);

        DecimalFormat format = new DecimalFormat("###.##");

        int backgroundColor = ResourcesCompat.getColor(context.getResources(), R.color.item_2_background, null);

        // set location
        TextView location = (TextView) layout.getViewById(R.id.item_template_location);
        if (item.getLocation().contains(","))
            location.setText(item.getLocation().split(",")[0]);
        else
            location.setText(item.getLocation().split("\\.")[0]);

        // set date
        TextView originDate = (TextView) layout.getViewById(R.id.item_template_origin_date);
        originDate.setText(item.getOriginDateString());

        // set depth
        TextView depth = (TextView) layout.getViewById(R.id.item_template_depth);
        depth.setText(String.format(context.getResources().getString(R.string.item_depth), item.getDepth()));

        // set scale
        ImageView scale = (ImageView) layout.getViewById(R.id.item_template_scale);
        double mag = item.getMagnitude();
        if (mag >= 8) {
            scale.setImageResource(R.drawable.scale_great);
        } else if (mag >= 7 && mag <= 7.9) {
            scale.setImageResource(R.drawable.scale_major);
        } else if (mag >= 6.1 && mag <= 6.9) {
            scale.setImageResource(R.drawable.scale_strong);
        } else if (mag >= 5.5 && mag <= 6) {
            scale.setImageResource(R.drawable.scale_moderate);
        } else if (mag > 2.5 && mag <= 5.4) {
            scale.setImageResource(R.drawable.scale_light);
        } else if (mag <= 2.5) {
            scale.setImageResource(R.drawable.scale_minor);
        }

        // set magnitude
        TextView magnitude = (TextView) layout.getViewById(R.id.item_template_magnitude);
        magnitude.setText(format.format(item.getMagnitude()));

        // set category
        TextView category = (TextView) layout.getViewById(R.id.item_template_category);
        category.setText(String.format(context.getResources().getString(R.string.item_category), item.getCategory()));

        layout.setBackgroundColor(backgroundColor);

        final int finalBackgroundColor = backgroundColor;
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra("backgroundColor", finalBackgroundColor);
                intent.putExtra("item", item);
                context.startActivity(intent);
            }
        });

        return layout;
    }

}
