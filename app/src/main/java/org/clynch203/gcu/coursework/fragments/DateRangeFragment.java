//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

package org.clynch203.gcu.coursework.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.activities.ResultActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.clynch203.gcu.coursework.util.Constants.DATE_RANGE_FRAGMENT_REQUEST_CODE;

/**
 * Fragment for displaying a Dialog to select a start and end date for search.
 */
public class DateRangeFragment extends DialogFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public InterfaceCommunicator interfaceCommunicator;
    private TextView startDate;
    private TextView endDate;
    private boolean settingStartDate;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
    private Calendar calendar;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        ViewGroup home = requireActivity().findViewById(R.id.home);
        View view = inflater.inflate(R.layout.dialog_date_range, home, false);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.DialogTheme)
                .setView(view)
                .setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitDialog();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();

        // set the text colour of the positive
        // and negative buttons
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.dialog_positive_button_text));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.dialog_negative_button_text));
            }
        });

        calendar = Calendar.getInstance();
        String date = format.format(calendar.getTime());

        startDate = view.findViewById(R.id.startDate);
        startDate.setText(date);
        startDate.setOnClickListener(this);

        endDate = view.findViewById(R.id.endDate);
        endDate.setText(date);
        endDate.setOnClickListener(this);

        return dialog;
    }

    @Override
    public void onClick(View v) {
        showDatePicker();
        if (v == startDate) {
            settingStartDate = true;
        } else if (v == endDate) {
            settingStartDate = false;
        }
    }

    /**
     * Displays a DatePickerDialog with the current date selected.
     */
    private void showDatePicker() {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                android.R.style.Theme_DeviceDefault_Light_Dialog,
                this,
                year, month, day);

        dialog.show();
    }

    /**
     * Called when the submit button is clicked for the fragment.
     * Passes the startDate and endDate back to the calling activity/fragment.
     */
    private void submitDialog() {
        if (!datesValid()) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
            dialogBuilder.setMessage("Selected dates are not valid.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = dialogBuilder.create();
            alert.show();

            return;
        }
        Intent intent = new Intent(requireActivity(), ResultActivity.class);
        intent.putExtra("startDate", startDate.getText());
        intent.putExtra("endDate", endDate.getText());
        interfaceCommunicator.sendRequest(DATE_RANGE_FRAGMENT_REQUEST_CODE, intent);
    }

    /**
     * Checks if the startDate and endDate are valid
     *
     * @return true if valid, otherwise false.
     */
    private boolean datesValid() {
        boolean valid = false;

        Date sDate = null;
        Date eDate = null;
        Date tomorrow;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        tomorrow = calendar.getTime();

        if (startDate != null) {
            try {
                sDate = format.parse(String.valueOf(startDate.getText()));
                eDate = format.parse(String.valueOf(endDate.getText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (sDate != null && eDate != null) {
            if (sDate.before(tomorrow) && eDate.before(tomorrow) && (sDate.before(eDate) || sDate.equals(eDate))) {
                valid = true;
            }
        }


        return valid;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interfaceCommunicator = (InterfaceCommunicator) context;
    }

    /**
     * Called when DatePickerDialog is closed.
     *
     * @param view       The DatePicker view.
     * @param year       The year that was selected.
     * @param month      The month that was selected.
     * @param dayOfMonth The day of the month that was selected.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        String date = format.format(calendar.getTime());
        if (settingStartDate) {
            startDate.setText(date);
        } else {
            endDate.setText(date);
        }
    }

    /**
     * Interface used to pass startDate and endDate back to calling activity/fragment.
     */
    public interface InterfaceCommunicator {
        void sendRequest(int code, Intent intent);
    }
}
