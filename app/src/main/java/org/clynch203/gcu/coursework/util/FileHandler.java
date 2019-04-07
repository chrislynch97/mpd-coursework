//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

package org.clynch203.gcu.coursework.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Class for performing operations on Files in storage.
 */
public abstract class FileHandler {

    /**
     * Method to check if a file exists.
     *
     * @param context  Context to check for the file.
     * @param filename Name of the file.
     * @return True if file exists, otherwise false.
     */
    public static boolean fileExists(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        return file.exists();
    }

    /**
     * Method to save a file to storage.
     *
     * @param context  Context to save the file to.
     * @param filename Name of the file to save.
     * @param data     Data to write to the file.
     */
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

    /**
     * Method to read contents of file from storage.
     *
     * @param context  Context to read the file from.
     * @param filename NAme of the file to read.
     * @return String contents of the file.
     */
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

    /**
     * Method to delete a file from storage.
     *
     * @param context  Context to delete the file from.
     * @param filename Name of the file to delete.
     */
    public static void deleteFile(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        if (file.exists())
            //noinspection ResultOfMethodCallIgnored
            file.delete();
    }

}
