//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

package org.clynch203.gcu.coursework.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.util.DownloadTask;
import org.clynch203.gcu.coursework.util.FileHandler;

import static org.clynch203.gcu.coursework.util.Constants.DATA_FILENAME;

/**
 * Entry point for the application.
 * Displays launch screen while loading required data.
 */
public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        if (isNetworkAvailable()) {
            new DownloadTask(this).execute();
        } else {
            loadData();
        }
    }

    /**
     * Called if the device doesn't have a network connection.
     * Attempts to load data from file on decide, otherwise
     * quits the application.
     */
    private void loadData() {
        if (FileHandler.fileExists(this, DATA_FILENAME)) {

            final String data = FileHandler.readFile(this, DATA_FILENAME);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("No internet connection detected. Loading most recent save data.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                            intent.putExtra("data", data);

                            startActivity(intent);
                            finish();
                        }
                    });
            AlertDialog alert = dialogBuilder.create();
            alert.show();

        } else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("No internet connection detected or data saved in storage. App exiting.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    });
            AlertDialog alert = dialogBuilder.create();
            alert.show();
        }
    }

    /**
     * Checks if device has a network connection.
     *
     * @return true if has a connection.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;

        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return networkInfo != null && networkInfo.isConnected();
    }
}
