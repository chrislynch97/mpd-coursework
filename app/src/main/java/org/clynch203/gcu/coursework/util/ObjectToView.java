//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

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

/**
 * Class used to create Views from Item objects.
 */
public abstract class ObjectToView {

    /**
     * Create a ConstraintLayout from an Item object.
     *
     * @param inflater      Inflater from calling Activity.
     * @param itemContainer Containing View.
     * @param context       Context from calling Activity.
     * @param item          Item to convert.
     * @return ConstraintLayout.
     */
    public static ConstraintLayout createSimpleItemView(
            final LayoutInflater inflater,
            final ViewGroup itemContainer,
            final Context context,
            final Item item) {
        return createSimpleItemView(inflater, itemContainer, context, item, false);
    }

    /**
     * Create a ConstraintLayout from an Item object.
     *
     * @param inflater        Inflater from calling Activity.
     * @param itemContainer   Containing View.
     * @param context         Context from calling Activity.
     * @param item            Item to convert.
     * @param startWithResult Boolean if item is to be called with results.
     *                        If true item will not have an onClick listener attached.
     *                        If false onClick will be attached.
     * @return ConstraintLayout.
     */
    public static ConstraintLayout createSimpleItemView(
            final LayoutInflater inflater,
            final ViewGroup itemContainer,
            final Context context,
            final Item item,
            final boolean startWithResult) {

        ConstraintLayout layout = (ConstraintLayout) inflater
                .inflate(R.layout.template_item_simple, itemContainer, false);

        DecimalFormat format = new DecimalFormat("###.##");

        final int backgroundColor = ResourcesCompat.getColor(context.getResources(), R.color.item_2_background, null);
        layout.setBackgroundColor(backgroundColor);

        ((TextView) layout.getViewById(R.id.item_template_location)).setText(item.getLocation());
        ((TextView) layout.getViewById(R.id.item_template_origin_date)).setText(item.getOriginDateString());
        ((TextView) layout.getViewById(R.id.item_template_depth)).setText(String.format(context.getResources().getString(R.string.item_depth), item.getDepth()));
        ((TextView) layout.getViewById(R.id.item_template_magnitude)).setText(format.format(item.getMagnitude()));
        ((TextView) layout.getViewById(R.id.item_template_category)).setText(String.format(context.getResources().getString(R.string.item_category), item.getCategory()));

        ImageView scale = (ImageView) layout.getViewById(R.id.item_template_scale);
        final double mag = item.getMagnitude();
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

        if (!startWithResult) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ItemActivity.class);
                    intent.putExtra("backgroundColor", backgroundColor);
                    intent.putExtra("item", item);
                    context.startActivity(intent);
                }
            });
        }

        return layout;
    }

}
