package com.ucrrunner.firebase.authenticate;

import android.util.Log;

import com.ucrrunner.firebase.FirebaseSettings;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Martin on 10/25/14.
 */
public class Authenticator {
    //! constants
    private final String tag = "Authenticator";
    //! Member variables
    private String myUsername, myPassword;

    public Authenticator(String username, String password){
        myUsername = username;
        myPassword = password;

    }

    public boolean logIn()
    {
        Firebase ref = new Firebase(FirebaseSettings.URL);

        Log.i(tag, "Authenticating user (" + myUsername + ", " + myPassword + ")");
        return true;



    }


}
