package com.murraycole.ucrrunner.view;

import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;

/**
 * Created by mbrevard on 11/9/14.
 */
public class FirebaseManager {
    private Firebase baseRef;


    FirebaseManager(){
        baseRef = getRef();
    }

    static FirebaseError saveRoute( Route currRoute, String uid){
        uid = uid.split(":")[1];
        Log.d("MT", "uid recieved: " + uid);
        //routeRef to outes/uid/
        Firebase routesRef = getRef().child("routes").child(uid);

        //set to routes/uid/<genid_currRoute>/currRoute
        routesRef.push().setValue(currRoute);

        return null;
    }

    // use this to get baseRef in static functions.
    static Firebase getRef(){
        return new Firebase("https://torid-inferno-2246.firebaseio.com/");
    }
}
