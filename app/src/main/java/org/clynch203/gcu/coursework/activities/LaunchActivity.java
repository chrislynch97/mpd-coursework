package org.clynch203.gcu.coursework.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import org.clynch203.gcu.coursework.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LaunchActivity extends AppCompatActivity {

    private ProgressBar downloadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        downloadProgress = findViewById(R.id.downloadProgress);
        startDownload();
    }

    private void startDownload() {
        new DownloadTask().execute();
    }

    private class DownloadTask extends AsyncTask<Void, Integer, Void> {

        int progressStatus;
        URL url;
        StringBuilder result = new StringBuilder();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressStatus = 0;
            try {
                url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
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
                in.readLine();
                in.readLine();
                int totalLines = 690; // roughly how many lines are in the XML file. Would use connection.ContentLength() but returns -1
                while ((line = in.readLine()) != null) {
                    if (line.equalsIgnoreCase("</rss>"))
                        break;
                    line = line.replace("geo:lat", "lat");
                    line = line.replace("geo:lon", "lon");
                    result.append(line);
                    progressStatus++;
                    double d = ((double) progressStatus / (double) totalLines) * 100;
                    publishProgress((int)d);
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
            downloadProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);

            intent.putExtra("data", this.result.toString());

            startActivity(intent);
        }
    }

}
