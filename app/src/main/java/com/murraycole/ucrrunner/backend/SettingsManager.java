package com.murraycole.ucrrunner.backend;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;

import fbutil.firebase4j.error.FirebaseException;
import fbutil.firebase4j.service.Firebase;

/**
 * Created by Martin on 11/24/14.
 */
// Wrapper for FirebaseManager::changeSettings() because enums..
public class SettingsManager {
    public static void changeNickname(String uid, String nickname){
        FirebaseManager.changeSetting(FirebaseManager.Setting.NICKNAME, uid, nickname);
    }
    public static void changeAge(String uid, int age){
        FirebaseManager.changeSetting(FirebaseManager.Setting.NICKNAME, uid, age);
    }
    public static void changeHeight(String uid, double height){
        FirebaseManager.changeSetting(FirebaseManager.Setting.HEIGHT, uid, height);
    }
    public static void changeWeight(String uid, double weight){
        FirebaseManager.changeSetting(FirebaseManager.Setting.WEIGHT, uid, weight);
    }
    public static void changePassword(String uid, String oldPassword, String newPassword, View v){
        FirebaseManager.changePassword(uid, oldPassword, newPassword, v);
    }

    //TOOD implement userRunCount and implement these methods
    public static void updateUserAvgSpeed(String uid, double newSpeed){
        String link = FirebaseManager.FIREBASEURL_USERS+uid+"/stats/averageSpeed";

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            String jsonData = FirebaseManager.readJsonFromUrl(link+".json");
            jsonData = jsonData.replace("\"", "");

            if(jsonData == null || jsonData.matches("") || jsonData.isEmpty()){
                jsonData = new String(Double.valueOf(newSpeed).toString());
                Log.d("MT", "updateUserAvgSpeed got empty avg speed. new: " + jsonData);
            }else{
                //average
                jsonData = Double.valueOf((Double.valueOf(newSpeed) + Double.valueOf(jsonData))/2).toString();
            }
            Firebase saveRef = new Firebase(link);
        } catch (FirebaseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static  void updateUserTopSpeed(String uid, double newSpeed){
        String link = FirebaseManager.FIREBASEURL_USERS+uid+"/stats/averageSpeed";

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            String jsonData = FirebaseManager.readJsonFromUrl(link+".json");
            jsonData = jsonData.replace("\"", "");

            if(jsonData == null || jsonData.matches("") || jsonData.isEmpty()){
                jsonData = new String(Double.valueOf(newSpeed).toString());
                Log.d("MT", "updateUserAvgSpeed got empty avg speed. new: " + jsonData);
            }else{
                //replace
                jsonData = Double.valueOf(newSpeed).toString();
            }
            Firebase saveRef = new Firebase(link);
        } catch (FirebaseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updateUserTotalCal(String uid, double newTotalCal){

        String link = FirebaseManager.FIREBASEURL_USERS+uid+"/stats/averageSpeed";

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            String jsonData = FirebaseManager.readJsonFromUrl(link+".json");
            jsonData = jsonData.replace("\"", "");

            if(jsonData == null || jsonData.matches("") || jsonData.isEmpty()){
                jsonData = new String(Double.valueOf(newTotalCal).toString());
                Log.d("MT", "updateUserAvgSpeed got empty avg speed. new: " + jsonData);
            }else{
                //add
                jsonData = Double.valueOf((Double.valueOf(newTotalCal) + Double.valueOf(jsonData))).toString();
            }
            Firebase saveRef = new Firebase(link);
        } catch (FirebaseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updateUserTotalDist(String uid, double newDistance){

        String link = FirebaseManager.FIREBASEURL_USERS+uid+"/stats/averageSpeed";

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            String jsonData = FirebaseManager.readJsonFromUrl(link+".json");
            jsonData = jsonData.replace("\"", "");

            if(jsonData == null || jsonData.matches("") || jsonData.isEmpty()){
                jsonData = new String(Double.valueOf(newDistance).toString());
                Log.d("MT", "updateUserAvgSpeed got empty avg speed. new: " + jsonData);
            }else{
                //add
                jsonData = Double.valueOf((Double.valueOf(newDistance) + Double.valueOf(jsonData))).toString();
            }
            Firebase saveRef = new Firebase(link);
        } catch (FirebaseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updateUserTotalDuration(String uid, double newDuration){

        String link = FirebaseManager.FIREBASEURL_USERS+uid+"/stats/duration";

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            String jsonData = FirebaseManager.readJsonFromUrl(link+".json");
            jsonData = jsonData.replace("\"", "");

            if(jsonData == null || jsonData.matches("") || jsonData.isEmpty()){
                jsonData = new String(Double.valueOf(newDuration).toString());
                Log.d("MT", "updateUserAvgSpeed got empty avg speed. new: " + jsonData);
            }else{
                //add
                jsonData = Double.valueOf((Double.valueOf(newDuration) + Double.valueOf(jsonData))).toString();
            }
            Firebase saveRef = new Firebase(link);
        } catch (FirebaseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}