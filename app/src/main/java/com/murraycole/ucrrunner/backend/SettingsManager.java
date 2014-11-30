package com.murraycole.ucrrunner.backend;

import android.view.View;

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

    //TOOD implement userRunCount and imeplement these methods
    public static double updateUserAvgSpeed(String uid, double newSpeed){
        return 0.0;
    }
    public static  double updateUserTopSpeed(String uid, double newSpeed){
        return 0.0;
    }
    public static double updateUserTotalCal(String uid, double newTotalCal){
        return 0.0;
    }
   /* public static int updateUserRunCount(String uid, int newValue){

    }*/
    public static double updateUserTotalDist(String uid, double newDistance){
        return 0.0;
    }
    public static double updateUserTotalDuration(String uid, double newDuration){
        return 0.0;
    }
}