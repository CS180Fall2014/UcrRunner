package com.murraycole.ucrrunner.view;

import android.content.SharedPreferences;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
/**
 * Created by mbrevard on 11/9/14.
 */
public class FirebaseManager {
    private Firebase baseRef;


    FirebaseManager(){
        baseRef = getRef();
    }

    static User getUser(String uid){
        if(uid.contains(":")) {
            uid = uid.split(":")[1];
        }

        final User returnUser = new User();
        Log.d("MT", "firebaseManager()::getUser got uid " + uid);
        Firebase userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/"+uid+"/");
        Log.d("MT", "userJSON: " + userRef.getName() + " | " + userRef.toString());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String userJson = dataSnapshot.getValue().toString();
                Log.d("MT", "attempting to instantiate user");
                User tempUser = dataSnapshot.getValue(User.class);
                Log.d("MT" , "NICK: " + tempUser.getNickname());
                returnUser.setNickname(tempUser.getNickname());
                returnUser.setAge(tempUser.getAge());
                returnUser.setEmail(tempUser.getEmail());
                returnUser.setHeight(tempUser.getHeight());
                returnUser.setSex(tempUser.getSex());
                returnUser.setWeight(tempUser.getWeight());
                Log.d("MT", returnUser.getNickname() + " INSTANTIATED.");


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return returnUser;
    }

    static List<Route> getRoutes( String uid ){
        if(uid.contains(":")) {
            uid = uid.split(":")[1];
        }
        //reference to the user[uid]'s directory for their routes.
        Firebase userRoutesRef = new Firebase("https://torid-inferno-2246.firebaseio.com/routes/"+uid+"/");
        final ArrayList<Route> returnRouteList = new ArrayList<Route>();

        // this Listener will execute one time to populate DataSnapshot in the VEL::OnDataChange method.
        userRoutesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //iterating through every unique Push ID for every user.
                for (DataSnapshot route : dataSnapshot.getChildren()){
                    Route r = route.getValue(Route.class);
                    Log.d("MT", r.getCurrentRoute().get(0).get(0) + " means SUCCESS ON ROUTE");
                    Log.d("MT", r.getCurrentStats().getAverageSpeed() + " means SUCCESS ON STATS! FUCK ME");
                    returnRouteList.add(r);
                }
                //routes list is populated.
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //do error somehow.
            }
        });

        return returnRouteList;
    }

    static FirebaseError saveRoute( Route currRoute, String uid){
        if(uid.contains(":")) {
            uid = uid.split(":")[1];
        }
        Log.d("MT", "FirebaseManager::saveRoute() | uid recieved: " + uid);
        //routeRef to outes/uid/
        Firebase routesRef = getRef().child("routes").child(uid);

        //set to routes/uid/<genid_currRoute>/_currRouteData
        routesRef.push().setValue(currRoute);

        return null;
    }
    static FirebaseError saveUser( User currUser, String uid ){
        if(uid.contains(":")){
            uid = uid.split(":")[1];
        }
        Log.d("MT", "Firebaseanager::saveroute() | uid recieved: " + uid);

        //userRef to users/uid/
        Firebase userRef = getRef().child("users").child(uid);

        userRef.setValue(currUser);

        return null;
    }

    // use this to get baseRef in static functions.
    static Firebase getRef(){
        return new Firebase("https://torid-inferno-2246.firebaseio.com/");
    }
}
