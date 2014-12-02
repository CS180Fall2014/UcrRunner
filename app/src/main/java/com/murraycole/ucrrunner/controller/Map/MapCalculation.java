package com.murraycole.ucrrunner.controller.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.*;

import com.murraycole.ucrrunner.view.DAO.User;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mbrevard on 11/10/14.
 */
public class MapCalculation {

    private final static int QUALITY = 10;
    private final static int OFFSET = 0;
    private final static int SIZE = 300;

    /**
     * Base 64 encode (Base64.java)
     * @param map
     * @return
     */
    public static String encode(Bitmap map) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //map.reconfigure(100,100, Bitmap.Config.ARGB_8888);
        map.compress(Bitmap.CompressFormat.JPEG, QUALITY, out);
        map.recycle();
        byte[] byteArray = out.toByteArray();
        String value = null;
        try {
            value = Base64.encodeBytes(byteArray,OFFSET, byteArray.length, Base64.URL_SAFE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //saveRouteValuesEncodeDecode(value, "ENCODE");
        return value;
    }

    /**
     * base 64 decode (Base64.java)
     * @param value
     * @return
     */
    public static Bitmap decode(String value) {
        //saveRouteValuesEncodeDecode(value, "DECODE");
        byte[] bytes = new byte[0];
        Bitmap bitmap = null;
        try {
            bytes = Base64.decode(value.getBytes(), OFFSET, value.getBytes().length, Base64.URL_SAFE);
            bitmap = BitmapFactory.decodeByteArray(bytes, OFFSET, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap answer = Bitmap.createScaledBitmap(bitmap, SIZE, SIZE, false);
        //System.out.println("Size of decoded bitmap (in bytes): " + answer.getByteCount());
        return answer;
    }

    /**
     * only used for testing purposes
     * @param header
     * @return
     */
    public static File getOutputMediaFile(String header) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/com.murraycole.ucrrunner/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = header + "_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    /**
     * only used for testing purposes
     * @param image
     * @param header
     */
   public static void storeImage(Bitmap image, String header) {
        File pictureFile = getOutputMediaFile(header);
        if (pictureFile == null) {
            System.out.println("Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("File not found: ", e.getMessage());
        } catch (IOException e) {
            Log.d("Error accessing file: ", e.getMessage());
        }
    }

    public static void saveRouteValuesEncodeDecode(String value, String header) {
        System.out.println("save images with header name of " + header + " and size of value of " + value.length());
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String filename = header + "_" + timeStamp + ".txt";
        //String string = "Hello world!";
        FileOutputStream outputStream;

        //String outputString = "Hello world!";
        //File myDir = getFilesDir();
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/com.murraycole.ucrrunner/Files");
        try {
            File secondFile = new File(mediaStorageDir + "/text/", filename);
            if (secondFile.getParentFile().mkdirs()) {
                secondFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(secondFile);

                fos.write(value.getBytes());
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double calculateCalories(MapInformation mapInfo, double T) {
        //seconds -> hours?
        User u = new User(); //for milestone 0 fixing a case user

        double avgSPeed = mapInfo.getAverageSpeed() * 3.6;
        double Wkg = 165 * 0.453592; // lbs -> kg
        double Height = 70 * 2.54; //feet, inches -> cm
        double age = 22;
        double BMR = 0;
        double MET = 0;
        double calorie_burn = 0;

        T = T * 2.77778e-7;


        if (true) {
            BMR = (13.75 * Wkg) + (5 * Height) - (6.76 * age) + 66;
        } else {
            BMR = (9.56 * Wkg) + (1.85 * Height) - (4.68 * age) + 655;

        }

        if (avgSPeed < 24.1402) {
            MET = 3.80;
        } else {
            MET = 7.50;
        }

        calorie_burn = (BMR / 24) * MET * T;

        //System.out.println("Calorie: " + calorie_burn);
        return calorie_burn;
    }
}
