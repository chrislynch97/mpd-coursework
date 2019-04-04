package org.clynch203.gcu.coursework.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public abstract class FileHandler {

    public static boolean fileExists(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        return file.exists();
    }

    public static void saveFile(Context context, String filename, String data) {
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFile(Context context, String filename) {
        if (!fileExists(context, filename)) return null;

        FileInputStream inputStream;
        StringBuilder fileContents = new StringBuilder();

        try {
            inputStream = context.openFileInput(filename);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileContents.toString();
    }

    public static void deleteFile(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        if (file.exists())
            file.delete();
    }

}
