//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//
package org.clynch203.gcu.coursework.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.activities.LaunchActivity;
import org.clynch203.gcu.coursework.activities.MainActivity;
import org.clynch203.gcu.coursework.controllers.ChannelController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static org.clynch203.gcu.coursework.util.Constants.DATA_FILENAME;
import static org.clynch203.gcu.coursework.util.Constants.DATA_URL;

public class DownloadTask extends AsyncTask<Void, Integer, Void> {

    private int progressStatus;
    private StringBuilder result;
    private URL url;
    private WeakReference<AppCompatActivity> activityReference;

    public DownloadTask(AppCompatActivity context) {
        activityReference = new WeakReference<>(context);
    }

    /**
     * Checks if device has a network connection.
     *
     * @return true if has a connection.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;

        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressStatus = 0;
        result = new StringBuilder();

        try {
            url = new URL(DATA_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        URLConnection connection;
        BufferedReader in;

        try {
            connection = url.openConnection();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            // skip first 2 lines
            in.readLine();
            in.readLine();

            // roughly how many lines are in the XML file. Would use connection.ContentLength() but returns -1
            // used to calculate percentage complete of download
            int totalLines = 690;

            while ((line = in.readLine()) != null) {
                if (line.equalsIgnoreCase("</rss>")) break;

                // the ':' in the tag caused issues with the XML parser so remove them here
                line = line.replace("geo:lat", "lat");
                line = line.replace("geo:long", "lon");

                result.append(line);

                progressStatus++;
                double d = ((double) progressStatus / (double) totalLines) * 100;
                publishProgress((int) d);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        if (activityReference.get() instanceof LaunchActivity) {
            ProgressBar downloadProgress = activityReference.get().findViewById(R.id.downloadProgress);
            downloadProgress.setProgress(values[0]);
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        // once the data has been loaded, add it to an intent and load
        // the MainActivity
        if (!this.result.toString().equals("")) {
            if (activityReference.get() instanceof LaunchActivity) {
                Intent intent = new Intent(activityReference.get(), MainActivity.class);
                intent.putExtra("data", this.result.toString());
                activityReference.get().startActivity(intent);
                activityReference.get().finish();
            } else if (activityReference.get() instanceof MainActivity) {
                ChannelController channelController = new ChannelController(XMLParser.parseData(this.result.toString()));
                ((MainActivity) activityReference.get()).updateChannelController(channelController);
            }

            FileHandler.deleteFile(activityReference.get(), DATA_FILENAME);
            FileHandler.saveFile(activityReference.get(), DATA_FILENAME, this.result.toString());
        }
    }
}
