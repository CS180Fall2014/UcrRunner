package com.murraycole.ucrrunner.backend;

import android.os.StrictMode;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.murraycole.ucrrunner.view.DAO.Route;
import com.murraycole.ucrrunner.view.DAO.Stats;
import com.murraycole.ucrrunner.view.DAO.User;
import com.murraycole.ucrrunner.view.interfaces.ArrayUpdateListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fbutil.firebase4j.error.FirebaseException;

public class FirebaseManager {
    public static enum Setting{
        NICKNAME, AGE, HEIGHT, WEIGHT, PASSWORD
    }
    //=======================

    // Message functions ===========================================================================
    static public void getMessages( String uid ){
        return;
    }
    static public void sendMessage( String uid, String message, String frienduid ){
        return;
    }

    // Friends functions ===========================================================================
    //Works (Listener update)
    static public void getFriends( String uid , ArrayUpdateListener fragUpdateListener){
        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final ArrayUpdateListener updateListener = fragUpdateListener;
        //possible just return a list of usernames?
        try {
            fbutil.firebase4j.service.Firebase friendList =
                new fbutil.firebase4j.service.Firebase("https://torid-inferno-2246.firebaseio.com/users/"+uid+"/friends");
            //String ç = friendList.get().getRawBody();
            String friendsJson = readJsonFromUrl("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/friends.json");

            Log.d("MT", "God JSON: " + friendsJson);
            ArrayList<String> friendUIDlist = new ArrayList(Arrays.asList(friendsJson.split(":")));
            for(String s : friendUIDlist){
                s = s.replace("\"", "");
                Log.d("MT", "UID to query: " + s);
                Firebase fbRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/"+s+"/");
                Log.d("MT", "fbRef: " + fbRef.getName() + " | " + fbRef.toString());
                fbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("MT", "ONDATACHANGE: " + dataSnapshot.getRef().toString());
                        Object friend = dataSnapshot.getValue(User.class);
                        Log.d("MT", "friendListener updated: " + ((User)friend).getNickname());
                        updateListener.update(friend);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Log.e("MT", "friendJSONERROR");

                    }
                });
            }
        } catch (FirebaseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Works (appends to uid friends attr)
    static void addFriend(String uid, String friendNick){
        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            int _uid;
            String friendsJson = readJsonFromUrl("https://torid-inferno-2246.firebaseio.com/users/"+uid+"/friends.json");
            friendsJson = friendsJson.replace("\"", "");
            Log.d("MT", "addFriend got: " + friendsJson);
            _uid = getUID(friendNick);
            if(_uid == -1){
                Log.d("MT" , "Did not add " + friendNick + " because he is not recorded in the system.");
                return;
            }
            if(friendsJson.matches("null")){
                friendsJson = new Integer(_uid).toString();
            } else {
                friendsJson += ":" + new Integer(_uid).toString();
            }

            Log.d("MT", "addFriend is setting: " +friendsJson);
            Firebase friendRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/"+uid+"/friends");
            friendRef.setValue(friendsJson);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // User functions ==============================================================================
    //Works
    // TODO make wrappers for this
    static FirebaseError changeSetting( Setting setEnum, String uid, Object newSetting){
        Firebase userRef;
        switch(setEnum){
            case NICKNAME:
                userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/"+uid+"/nickname");
                userRef.setValue(newSetting);
                break;
            case AGE:
                userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/"+uid+"/age");
                userRef.setValue(newSetting);
                break;
            case HEIGHT:
                userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/"+uid+"/height");
                userRef.setValue(newSetting);
                break;
            case WEIGHT:
                userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/"+uid+"/weight");
                userRef.setValue(newSetting);
                break;
            case PASSWORD:
                    //REST API FUNCTIONALITY
                break;
            default:
                break;
        }
        return null;
    }
    // TODO needs work
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
    // Works FB register and save to regrec table
    static FirebaseError saveUser( User currUser, String uid ){
        if(uid.contains(":")){
            uid = uid.split(":")[1];
        }
        Log.d("MT", "Firebaseanager::saveroute() | uid recieved: " + uid);

        //userRef to users/uid/
        Firebase userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/"+uid+"/");
        Firebase recRef = new Firebase("https://torid-inferno-2246.firebaseio.com/regrec/" + currUser.getNickname());

        userRef.setValue(currUser);

        recRef.setValue(new Integer(uid).intValue());
        return null;
    }
    // Works
    static int getUID(String nickname){
        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            String friendsJson = readJsonFromUrl("https://torid-inferno-2246.firebaseio.com/regrec/"+nickname+".json");
            Log.d("MT", "getUID json: " + friendsJson);
            if(friendsJson.matches("null")){
                return -1;
            }
            return new Integer(friendsJson).intValue();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Route functions =============================================================================
    //works
    public static void getRoutes( String uid, ArrayUpdateListener fragUpdateListener){
        if(uid.contains(":")) {
            uid = uid.split(":")[1];
        }
        Log.d("MT", "firebaseManager::getRoutes() got uid: " + uid);
        //reference to the user[uid]'s directory for their routes.
        Firebase userRoutesRef = new Firebase("https://torid-inferno-2246.firebaseio.com/routes/"+uid+"/");
        final ArrayUpdateListener updateListener = fragUpdateListener;
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
                    updateListener.update(readRoute);
                }
                //routes list is populated.
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //do error somehow.
            }
        });
    }
    // Works saves to /Routes
    static FirebaseError saveRoute( Route currRoute, String uid){
        if(uid.contains(":")) {
            uid = uid.split(":")[1];
        }
        Log.d("MT", "FirebaseManager::saveRoute() | uid recieved: " + uid);
        //routeRef to outes/uid/
        Firebase routesRef = new Firebase("https://torid-inferno-2246.firebaseio.com/").child("routes").child(uid);

        //set to routes/uid/<genid_currRoute>/_currRouteData
        routesRef.push().setValue(currRoute);

        return null;
    }

    // HELPER functions ============================================================================
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    private static String readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            Log.d("MT", "readJsonFromURL:" + jsonText);
            return jsonText;
        } finally {
            is.close();
        }
    }
}
