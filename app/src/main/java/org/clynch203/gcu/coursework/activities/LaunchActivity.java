package org.clynch203.gcu.coursework.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import org.clynch203.gcu.coursework.R;

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
        new DownloadDataAsyncTask().execute();
    }

    private class DownloadDataAsyncTask extends AsyncTask<Void, Integer, Void> {

        int progressStatus;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressStatus = 0;
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (progressStatus < 100) {
                progressStatus += 2;
                System.out.println(progressStatus);
                publishProgress(progressStatus);
                SystemClock.sleep(300);
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
        }
    }

}
