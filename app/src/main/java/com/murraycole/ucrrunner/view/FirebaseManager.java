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
    public static enum Setting{
        NICKNAME, AGE, EMAIL, HEIGHT, SEX, WEIGHT, USERNAME, PASSWORD
    }

    private Firebase baseRef;


    FirebaseManager(){
        baseRef = getRef();
    }

    static FirebaseError changeSetting( Setting setEnum, String uid){
        switch(setEnum){
            case Setting.NICKNAME:

                break;
            case Setting.AGE:

                break;
            case Setting.EMAIL:

                break;
            case Setting.HEIGHT:

                break;
            case Setting.SEX:

                break;
            case Setting.WEIGHT:

                break;
            case Setting.USERNAME:

                break;
            case Setting.PASSWORD:

                break;
            default:

                break;
        }
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

    static ArrayList<Route> getRoutes( String uid ){
        if(uid.contains(":")) {
            uid = uid.split(":")[1];
        }
        Log.d("MT", "firebaseManager::getRoutes() got uid: " + uid);
        //reference to the user[uid]'s directory for their routes.
        Firebase userRoutesRef = new Firebase("https://torid-inferno-2246.firebaseio.com/routes/"+uid+"/");
        final ArrayList<Route> returnRouteList = new ArrayList<Route>();
        // this Listener will execute one time to populate DataSnapshot in the VEL::OnDataChange method.
        userRoutesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //iterating through every unique Push ID for every user.
                for (DataSnapshot route : dataSnapshot.getChildren()){
                    /*Route r = route.getValue(Route.class);
                    Log.d("MT", r.getCurrentRoute().get(0).get(0) + " means SUCCESS ON ROUTE");
                    Log.d("MT", r.getCurrentStats().getAverageSpeed() + " means SUCCESS ON STATS! FUCK ME");
                    returnRouteList.add(r);*/
                    //        unique push ID               json string for all of one user's routes
                    Log.d("MT", route.getName() + "  | " + route.getValue().toString());
                    String jsonData = route.getValue().toString();

                    //objects to populate
                    Route readRoute = new Route();
                    List<List<LatLng>> currRoute = new ArrayList<List<LatLng>>();
                    Stats currStats = new Stats();

                    try {//populate a Route object
                        //Populating currRoute
                        JSONObject routeJSON = new JSONObject(jsonData);
                        JSONArray routesArray = routeJSON.getJSONArray("currentRoute");
                        for(int i = 0; i < routesArray.length(); ++i){ //iterate each subroute
                            JSONArray subRoute = routesArray.getJSONArray(i);

                            //Log.d("MT", "i: " + (i+1) + "/"+routesArrayJ.length());
                            currRoute.add(new ArrayList<LatLng>());
                            for(int j = 0; j < subRoute.length(); ++j){ //iterate each coord in subroute
                                //Log.d("MT", "..j: " + (j+1) + "/"+subRoute.length());
                                JSONObject coord = subRoute.getJSONObject(j);
                                double lat = coord.getDouble("latitude");
                                double lon = coord.getDouble("longitude");
                                currRoute.get(currRoute.size()-1).add(new LatLng(lat,lon));
                                //Log.d("MT", "....Coord: " + lat + " , " + lon);
                            }
                        }
                        //currRoute is now populated

                        //Populate a stat
                        JSONObject currentStats = routeJSON.getJSONObject("currentStats");
                        currStats.setAverageSpeed(currentStats.getDouble("averageSpeed"));
                        currStats.setTopSpeed(currentStats.getDouble("topSpeed"));
                        currStats.setCaloriesBurned(currentStats.getDouble("caloriesBurned"));
                        currStats.setDistance(currentStats.getDouble("distance"));

                        /*Log.d("MT", "Populated Stat.");
                        Log.d("MT", "..averageSpeed: " + currentStats.getDouble("averageSpeed"));
                        Log.d("MT", "..topSpeed: " + currentStats.getDouble("topSpeed"));
                        Log.d("MT", "..caloriesBurned: " + currentStats.getDouble("caloriesBurned"));
                        Log.d("MT", "..distance: " + currentStats.getDouble("distance"));
                        Log.d("MT", ".......................................");*/
                        //Stat now populated
                    }catch(Exception e){
                        Log.d("MT", e.getMessage());
                    }
                    readRoute.setCurrentRoute(currRoute);
                    readRoute.setCurrentStats(currStats);
                    returnRouteList.add(readRoute);
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
        Firebase userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/"+uid+"/");

        userRef.setValue(currUser);

        return null;
    }

    // use this to get baseRef in static functions.
    static Firebase getRef(){
        return new Firebase("https://torid-inferno-2246.firebaseio.com/");
    }


}
