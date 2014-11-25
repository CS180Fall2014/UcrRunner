package com.murraycole.ucrrunner.backend;

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
    public static void changePassword(String uid, String oldPassword, String newPassword){
        FirebaseManager.changePassword(uid, oldPassword, newPassword);
    }
}