package com.murraycole.ucrrunner.model;


import com.firebase.client.Firebase;

/**
 * Created by User on 10/21/14.
 */

/* This class is in charge of creating the db's and updating */
public class FirebaseConnector {
    private static final String FIREBASE_URL = "";
    private Firebase ref;


    public FirebaseConnector() {
        ref = new Firebase(FIREBASE_URL);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

    private void writeData(String key , String value ){
        ref.child(key).setValue(value);
    }

}
