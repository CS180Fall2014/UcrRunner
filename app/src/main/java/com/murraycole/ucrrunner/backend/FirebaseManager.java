package com.murraycole.ucrrunner.backend;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.murraycole.ucrrunner.view.DAO.Message;
import com.murraycole.ucrrunner.view.DAO.Post;
import com.murraycole.ucrrunner.view.DAO.Route;
import com.murraycole.ucrrunner.view.DAO.Stats;
import com.murraycole.ucrrunner.view.DAO.User;
import com.murraycole.ucrrunner.view.interfaces.ArrayUpdateListener;
import com.murraycole.ucrrunner.view.interfaces.ManTransportListener;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fbutil.firebase4j.error.FirebaseException;

/* Categories
 * Message functions
 * Friends functions
 * User functions
 * Route functions
 * HELPER functions
 */

public class FirebaseManager {
    public static enum Setting {
        NICKNAME, AGE, HEIGHT, WEIGHT, PASSWORD
    }

    //=======================
    private static final String FIREBASEURL = "https://torid-inferno-2246.firebaseio.com/";
    private static final String FIREBASEURL_MESSAGES = "https://torid-inferno-2246.firebaseio.com/messages/";
    private static final String FIREBASEURL_USERS = "https://torid-inferno-2246.firebaseio.com/users/";
    private static final String FIREBASEURL_ROUTES = "https://torid-inferno-2246.firebaseio.com/routes/";
    private static final String FIREBASEURL_POSTS = "https://torid-inferno-2246.firebaseio.com/posts/";
    private static final String FIREBASEURL_NICKNAMES = "https://torid-inferno-2246.firebaseio.com/regrec/";
/*
    public static FirebaseError saveRoute(Route currRoute, String uid) {
        if (uid.contains(":")) {
            uid = uid.split(":")[1];
        }
        Log.d("MT", "FirebaseManager::saveRoute() | uid recieved: " + uid);
        //routeRef to outes/uid/
        Firebase routesRef = new Firebase("https://torid-inferno-2246.firebaseio.com/").child("routes").child(uid);
        Firebase routeIdRef = routesRef.push();

        //set to routes/uid/<genid_currRoute>/_currRouteData
        routeIdRef.setValue(currRoute);
        Log.d("DN", "routeID" + routeIdRef.getName());
        return null;
    }
 */
    // NewsFeed functions =========================================================================co

    //test
    // TODO NOTHING TODO READ THE NOTE!
    /** NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
        The uid that should be passed into this should be the uid of the post
        that is to be commented on..
     **/
    public static ArrayList<Pair<String, String>> getComments(String postAuthorUid, String postId){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String commentJson = "";
        try {
            commentJson = readJsonFromUrl(FIREBASEURL_POSTS+postAuthorUid+"/"+postId+"/comment.json");
            commentJson = commentJson.replace("\"", "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Pair<String,String>> commentList = new ArrayList<Pair<String, String>>();
        for(String comment : commentJson.split(":")){
            Pair<String, String> cPair = Pair.create(comment.split("-")[0], comment.split("-")[1]);
            Log.d("MT", "The Comment pair is : <" + cPair.first + ", " + cPair.second + ">");
            commentList.add(cPair);
        }
        return commentList;
    }
    //TODO
    public static void addLike(String uid, String postId)
    {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String commentJson = "";
        try {
            commentJson = readJsonFromUrl(FIREBASEURL_POSTS+uid+"/"+postId+"/comment.json");
            commentJson = commentJson.replace("\"", "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if( commentJson == null || commentJson.matches("") || commentJson.matches("null")){
            commentJson = uid;
        }else{
            commentJson += ":" + uid;
        }

        //TODO add set call
    }
    //test
    public static void addComment(String uid, String postId, String comment){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String commentJson = "";
        try {
            commentJson = readJsonFromUrl(FIREBASEURL_POSTS+uid+"/"+postId+"/comment.json");
            commentJson = commentJson.replace("\"", "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //DONE
        //DONE implement a way to  get commment author nickname for display
        // DONE motherfucker do I look like a bitch to you?
        // DONE use uid. only uid makes sense in this because only currentUSer comments on something.
        if( commentJson == null || commentJson.matches("") || commentJson.matches("null")){
            commentJson = uid + "-" + comment;
        }else{
            commentJson += ":" + uid + "-" + comment;
        }
        //TODO add set call
    }
    //test
    public static void getPostsForFriends(String uid, final ArrayUpdateListener fragUpdateListener){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String friendsJson = "";
        try {
            friendsJson = readJsonFromUrl("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/friends.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        friendsJson = friendsJson.replace("\"", "");
        if(friendsJson.matches("null") || friendsJson.matches("")){
            // null on friendsJson means user specified by uid has no friends.
            return;
        }
        Firebase postRef = new Firebase(FIREBASEURL_POSTS);

        ArrayList<String> friendUIDList = new ArrayList(Arrays.asList(friendsJson.split(":")));
        for(String frienduid : friendUIDList){
            Firebase friendPostList = postRef.child(frienduid);

            friendPostList.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        Post newPost = data.getValue(Post.class);
                        Log.d("MT", "getPostForFriends recieved post ID: " + newPost.getPostID());
                        fragUpdateListener.update(newPost);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d("MT", "Soemthing went wrong in getPostForFriends");
                }
            });
        }
/*
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Post currPost = dataSnapshot.getValue(Post.class);
                    fragUpdateListener.update(currPost);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("MT", "Something went wrong getting a posts for " + uid);
            }
        });*/
    }
    //works
     public static void getPostsForSingleUser(String uid, final ArrayUpdateListener fragUpdateListener){
        Firebase postRef = new Firebase(FIREBASEURL_POSTS + uid);
        Log.d("MT", "getPOsts for " + uid);
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("MT", "ONDATACHANGE");
                Log.d("MT", dataSnapshot.getName());
                Log.d("MT", dataSnapshot.getRef().toString());
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Log.d("MT", "In For loop");
                    Log.d("MT", data.getName());
                    Log.d("MT", data.getRef().toString());
                    Log.d("MT", data.toString());
                    Post currPost = data.getValue(Post.class);
                    Log.d("MT", "Update");
                    fragUpdateListener.update(currPost);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("MT", "Something went wrong getting posts for single user");
            }
        });
    }
    //works
    /*
    public static void savePost(String uid, Post newPost)
    Parameters:
    [uid : String] - uid for the current user.
    [newPost : Post] - Post object to be saved to the database

    A Firebase reference to FIREBASEURL_POSTS for the current user specified by uid is created and used
    to save the newPost object to the database.
    Note: postID does not need to be set before passing the post object in. DO NOT SET IT TO NULL.
     */
    public static void savePost(String uid, Post newPost){
        if (uid.contains(":")) {
            uid = uid.split(":")[1];
        }
        Log.d("MT", "FirebaseManager::savePost() | uid recieved: " + uid);
        Firebase postRef = new Firebase(FIREBASEURL_POSTS).child(uid);
        Firebase postIdRef = postRef.push();

        // check for uid. The prefered string to be passed in should match the same string in
        // shared preferences which is where the information for this arguement should always
        // be retrieved from.
        if(!newPost.getAuthorUID().matches(uid)){
            newPost.setAuthorUID(uid);
        }

        // set postId
        newPost.setPostID(postIdRef.getName());

        postIdRef.setValue(newPost);
    }
    // Message functions ===========================================================================
        /*
    static public void getMessages(String uid, ArrayUpdateListener fragUpdateListener)
    Parameters:
    [uid : String] - uid for the current user.
    [fragTransportListener : ArrayUpdateListener] - Listener for notifying activities of Firebase callbacks.

    getMessages returns Message objects by adding a listener to a Firebase reference to FIREBASEURL_MESSAGES path for current user.
    The listener will issue a call back to the Android activity via the updateListener.update() call to update when data is received.
     */
    static public void getMessages(String uid, ArrayUpdateListener fragUpdateListener) {
        Firebase mailboxRef = new Firebase(FIREBASEURL_MESSAGES + uid);
        final ArrayUpdateListener updateListener = fragUpdateListener;

        mailboxRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //iterating through every unique Push ID for every message.
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.e("MT", "Data snapshot: " + data.getName());
                    Log.d("MT", "Data value: " + data.getValue().toString());

                    Message currMessage = data.getValue(Message.class);
                    Log.e("FirebaseManager::getMessages()", "Got Message =======================================");
                    Log.d("FirebaseManager::getMessages()", "Message ids: [F:" + currMessage.getFrom() + " | T:" + currMessage.getTo());
                    Log.d("FirebaseManager::getMessages()", "Message body: " + currMessage.getMessage());
                    Log.d("FirebaseManager::getMessages()", "Message timestamp: " + currMessage.getTimestamp());
                    updateListener.update(currMessage);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("FirebaseManager::getMessages()", "Something went wrong retrieving messages");
            }
        });
        return;
    }

    /*
    static public void sendMessage(String uid, String frienduid, String message)
    Parameters:
    [uid : String] - uid for the current user.
    [frienduid : String] - uid for the reciever of the message
    [message : String] - String representation of the message to be sent

    sendMessage sends a Message to the user specified by frienduid.
    A Message object is created and set to the firebase reference to FIREBASEURL_MESSAGES for the frienduid user.
     */
    static public void sendMessage(String uid, String frienduid, String message) {
        Firebase toMailboxRef = new Firebase(FIREBASEURL_MESSAGES + frienduid);
        java.util.Date date = new java.util.Date();

        Message msg = new Message();
        msg.setTo(Integer.valueOf(frienduid));
        msg.setFrom(Integer.valueOf(uid));
        msg.setMessage(message);
        msg.setTimestamp(new Timestamp(date.getTime()).toString());

        toMailboxRef.push().setValue(msg);
    }

    // Friends functions ===========================================================================
    //Works (Listener update)
    static public void getFriends(String uid, ArrayUpdateListener fragUpdateListener) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final ArrayUpdateListener updateListener = fragUpdateListener;
        //possible just return a list of usernames?
        try {
            String friendsJson = readJsonFromUrl("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/friends.json");
            friendsJson = friendsJson.replace("\"", "");
            Log.d("MT", "Got JSON: [" + friendsJson + "]");
            if(friendsJson.matches("null") || friendsJson.matches("")){
                // since friendsJson is a delmited list of their friends, if it returns null they have no friends
                // this prevents us from using null as a uid. [which results in a crash]
                Log.d("MT", "User has no friends.");
                return;
            }
            ArrayList<String> friendUIDlist = new ArrayList(Arrays.asList(friendsJson.split(":")));
            for (String s : friendUIDlist) {
                s = s.replace("\"", "");
                Log.d("MT", "UID to query: " + s);
                Firebase fbRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/" + s + "/");
                Log.d("MT", "fbRef: " + fbRef.getName() + " | " + fbRef.toString());
                fbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("MT", "ONDATACHANGE: " + dataSnapshot.getRef().toString());
                        Object friend = dataSnapshot.getValue(User.class);
                        Log.d("MT", "friendListener updated: " + ((User) friend).getNickname());
                        updateListener.update(friend);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Log.e("MT", "friendJSONERROR");

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Works (appends to uid friends attr)
    static public void addFriend(String uid, String friendNick) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            int _uid;
            String friendsJson = readJsonFromUrl("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/friends.json");
            friendsJson = friendsJson.replace("\"", "");
            Log.d("MT", "addFriend got: " + friendsJson);
            _uid = getUID(friendNick);
            if (_uid == -1) {
                Log.d("MT", "Did not add " + friendNick + " because he is not recorded in the system.");
                return;
            }
            if (friendsJson.matches("null") || friendsJson.matches("")) {
                friendsJson = new Integer(_uid).toString();
            } else {
                friendsJson += ":" + new Integer(_uid).toString();
            }

            Log.d("MT", "addFriend is setting: " + friendsJson);
            Firebase friendRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/friends");
            friendRef.setValue(friendsJson);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // User functions ==============================================================================
    //Works
    /*
    static FirebaseError changeSetting(Setting setEnum, String uid, Object newSetting)
    Parameters:
    [setEnum : Setting] - Setting enum defined for the setting to be changed.
    [uid : String] - uid for the current user.
    [newSetting : Object] - Generic class parameter to set any type of data as a new setting

    changeSetting returns a FirebaseError if there is a problem changing a setting on the database.
    It sets the generic object class to the FIREBASEURL_* reference specified by the setEnum parameter.
     */
    static FirebaseError changeSetting(Setting setEnum, String uid, Object newSetting) {
        Firebase userRef;
        switch (setEnum) {
            case NICKNAME:
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                try {
                    userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/nickname");
                    //get current nickname
                    String oldNickName = readJsonFromUrl(userRef.toString()+ ".json");
                    oldNickName = oldNickName.replace("\"","");
                    Firebase nickRef = new Firebase(FIREBASEURL_NICKNAMES).child(oldNickName);
                    //remove nickname from regrec namename
                    nickRef.removeValue();
                    int stripUID = Integer.parseInt(uid.replace("\"", ""));
                    nickRef = new Firebase(FIREBASEURL_NICKNAMES).child(newSetting.toString());
                    nickRef.setValue(stripUID);
                    //save user nickname to user's userroute
                    userRef.setValue(newSetting);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case AGE:
                userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/age");
                userRef.setValue(newSetting);
                break;
            case HEIGHT:
                userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/height");
                userRef.setValue(newSetting);
                break;
            case WEIGHT:
                userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/weight");
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

    //Works
    /*
    static User getUser(String uid, final ManTransportListener fragTransportListener)
    Parameters:
    [uid : String] - uid for the current user.
    [fragTransportListener : ManTransportListener] - Listener for notifying activities of Firebase callbacks.

     getUser returns a User object by adding a listener to a Firebase reference to the FIREBASEURL_USERS path for the current user.
     The listener will issue a call back to the Android activity to update via the updateListener.update() call when data is received.
     */
    static User getUser(String uid, final ManTransportListener fragTransportListener) {
        if (uid.contains(":")) {
            uid = uid.split(":")[1];
        }

        final User returnUser = new User();
        Log.d("MT", "firebaseManager()::getUser got uid " + uid);
        Firebase userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/");
        Log.d("MT", "userJSON: " + userRef.getName() + " | " + userRef.toString());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String userJson = dataSnapshot.getValue().toString();
                Log.d("MT", "attempting to instantiate user");
                User tempUser = dataSnapshot.getValue(User.class);
                Log.d("MT", "NICK: " + tempUser.getNickname());
                returnUser.setNickname(tempUser.getNickname());
                returnUser.setAge(tempUser.getAge());
                returnUser.setEmail(tempUser.getEmail());
                returnUser.setHeight(tempUser.getHeight());
                returnUser.setSex(tempUser.getSex());
                returnUser.setWeight(tempUser.getWeight());
                Log.d("MT", returnUser.getNickname() + " INSTANTIATED.");
                fragTransportListener.update(returnUser);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("MT", "Something went wrong with getting a User.");
            }
        });

        return returnUser;
    }

    /*
    public static String getNickname(String uid)
    Parameters:
    [uid : String] - uid for the current user.

     getNickname returns the String representation of the nickname for the current User specified by uid.
     It is read instantaneously by opening an httpconnection and capturing the json data.
     */
    public static String getNickname(String uid) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            String friendsJson = readJsonFromUrl(FIREBASEURL_USERS + uid + "/nickname.json");
            friendsJson = friendsJson.replace("\"", "");
            Log.d("FirebaseManager::getUser()", "returning clean json: " + friendsJson);
            return friendsJson;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static FirebaseError changePassword(String uid, String oldPassword, String newPassword, View v){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            String userEmail = readJsonFromUrl(FIREBASEURL_USERS + uid + "/email.json");
            userEmail = userEmail.replace("\"","");


            Log.d("DN", "User email:" + userEmail);
            Firebase ref = new Firebase(FIREBASEURL);
            userEmail = userEmail.replace("\"","");
            final View passwordView = v;

            Log.d("DN", "striped email:" + userEmail);

            ref.changePassword(userEmail, oldPassword, newPassword, new Firebase.ResultHandler() {
                @Override
                public void onSuccess() {
                    // password changed
                    Log.d("DN", "Email change success");
                    Toast.makeText(passwordView.getContext(), "Password Change Success!" , Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onError(FirebaseError firebaseError) {
                    // error encountered
                    Log.d("DN", "Email change failed" + firebaseError.getMessage());
                    Toast.makeText(passwordView.getContext(), firebaseError.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }





    // Works FB register and save to regrec table
    /*
    public static FirebaseError saveUser(User currUser, String uid)
    Parameters:
    [uid : String] - uid for the current user.
    [currUser : User] - the User DAO representation of the current User.

    saveUser returns a FirebaseError object in the case that there is an error with saving User data.
    A Firebase reference to FIREBASEURL_USERS for the current user specified by uid is created and used
    to save the currentUser object to the database.
    Note: it also stores a < nickname, uid > pair in the regrec Firebase reference to use as a hashmap.
     */
    public static FirebaseError saveUser(User currUser, String uid) {
        if (uid.contains(":")) {
            uid = uid.split(":")[1];
        }
        Log.d("MT", "Firebaseanager::saveUser() | uid recieved: " + uid);

        //userRef to users/uid/
        Firebase userRef = new Firebase("https://torid-inferno-2246.firebaseio.com/users/" + uid + "/");
        Firebase recRef = new Firebase("https://torid-inferno-2246.firebaseio.com/regrec/" + currUser.getNickname());

        userRef.setValue(currUser);

        recRef.setValue(new Integer(uid).intValue());
        return null;
    }

    /*
    public static boolean checkAvailableNick(String nickname)
    Parameters:
    nickname - nickname for current User

    checkAvailableNick returns a boolean value if nickname is currently taken in regrec table
    --true nickname is taken
    --false nickname is not taken
     */
   public static boolean checkAvailableNick(String nickname){
       if (android.os.Build.VERSION.SDK_INT > 9) {
           StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
           StrictMode.setThreadPolicy(policy);
       }
       Firebase ref = new Firebase("https://torid-inferno-2246.firebaseio.com/regrec/");
       try {
           String userEmail = readJsonFromUrl("https://torid-inferno-2246.firebaseio.com/regrec.json");

           nickname.replace("\"", "");
           Log.d("DN", "read available nick" +  userEmail.toString());

           JSONObject nickNameJSON = new JSONObject(userEmail.toString());
           nickname.trim();

           return nickNameJSON.getInt(nickname) != -1;
           //== -1  not taken
           //!= -1 taken

       } catch (IOException e) {
           e.printStackTrace();
       } catch (JSONException e) {
           e.printStackTrace();
       }
       return false;
   }




    // Works
    /*
    static int getUID(String nickname)
    Parameters:
    [nickname : String] - nickname for a user.

    getUID returns the int representation of the uid for any user specified by nickname.
     It is read instantaneously by opening an httpconnection and capturing the json data.
     */
    public static int getUID(String nickname) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            String friendsJson = readJsonFromUrl("https://torid-inferno-2246.firebaseio.com/regrec/" + nickname + ".json");
            friendsJson = friendsJson.replace("\"", "");
            Log.d("MT", "getUID json: " + friendsJson);
            if (friendsJson.matches("null") || friendsJson.matches("")) {
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
    /*
    public static void getRoutes(String uid, ArrayUpdateListener fragUpdateListener)
    Parameters:
    [uid : String] - uid for the current user.
    [fragUpdateListener : ArrayUpdateListener] - Listener for notifying activities of Firebase callbacks.

    getRoutes returns route objects by adding a listener to a Firebase reference to the FIREBASEURL_ROUTES path for the current user.
    The listener will issue a call back to the Android activity via the updateListener.update() call to update when data is received.

     */
    public static void getRoutes(String uid, ArrayUpdateListener fragUpdateListener) {
        if (uid.contains(":")) {
            uid = uid.split(":")[1];
        }
        Log.d("MT", "firebaseManager::getRoutes() got uid: " + uid);
        //reference to the user[uid]'s directory for their routes.
        Firebase userRoutesRef = new Firebase("https://torid-inferno-2246.firebaseio.com/routes/" + uid + "/");
        final ArrayUpdateListener updateListener = fragUpdateListener;
        // this Listener will execute one time to populate DataSnapshot in the VEL::OnDataChange method.
        userRoutesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //iterating through every unique Push ID for every user.
                for (DataSnapshot route : dataSnapshot.getChildren()) {
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

                    String date = null;
                    String title = null;
                    byte[] image = null;

                    try {//populate a Route object
                        //Populating currRoute
                        JSONObject routeJSON = new JSONObject(jsonData);
                        JSONArray routesArray = routeJSON.getJSONArray("currentRoute");
                        for (int i = 0; i < routesArray.length(); ++i) { //iterate each subroute
                            JSONArray subRoute = routesArray.getJSONArray(i);

                            //Log.d("MT", "i: " + (i+1) + "/"+routesArrayJ.length());
                            currRoute.add(new ArrayList<LatLng>());
                            for (int j = 0; j < subRoute.length(); ++j) { //iterate each coord in subroute
                                //Log.d("MT", "..j: " + (j+1) + "/"+subRoute.length());
                                JSONObject coord = subRoute.getJSONObject(j);
                                double lat = coord.getDouble("latitude");
                                double lon = coord.getDouble("longitude");
                                currRoute.get(currRoute.size() - 1).add(new LatLng(lat, lon));
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


                        title = new JSONObject(jsonData).get("title").toString();
                        date = new JSONObject(jsonData).get("date").toString();
                        image = new JSONObject(jsonData).get("image").toString().getBytes();


                        /*Log.d("MT", "Populated Stat.");
                        Log.d("MT", "..averageSpeed: " + currentStats.getDouble("averageSpeed"));
                        Log.d("MT", "..topSpeed: " + currentStats.getDouble("topSpeed"));
                        Log.d("MT", "..caloriesBurned: " + currentStats.getDouble("caloriesBurned"));
                        Log.d("MT", "..distance: " + currentStats.getDouble("distance"));
                        Log.d("MT", ".......................................");*/
                        //Stat now populated
                    } catch (Exception e) {
                        Log.d("MT", e.getMessage());
                    }
                    readRoute.setCurrentRoute(currRoute);
                    readRoute.setCurrentStats(currStats);
                    readRoute.setDate(date);
                    readRoute.setTitle(title);
                    readRoute.setImage(image);
                    updateListener.update(readRoute);
                }
                //routes list is populated.
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //do error somehow.
                Log.e("MT", "There was an error retrieving routes for some reason.");
            }
        });
    }

    // Works saves to /Routes
    public static FirebaseError saveRoute(Route currRoute, String uid) {
        if (uid.contains(":")) {
            uid = uid.split(":")[1];
        }
        Log.d("MT", "FirebaseManager::saveRoute() | uid recieved: " + uid);
        //routeRef to outes/uid/
        Firebase routesRef = new Firebase("https://torid-inferno-2246.firebaseio.com/").child("routes").child(uid);
        Firebase routeIdRef = routesRef.push();

        //set to routes/uid/<genid_currRoute>/_currRouteData
        routeIdRef.setValue(currRoute);
        Log.d("DN", "routeID" + routeIdRef.getName());
        return null;
    }

    public static String getCurrUID(Context context){
        SharedPreferences fbPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return fbPrefs.getString("userData.uid", null);
    }





    // HELPER functions ============================================================================
    /* Ya'll don't need to worry about this stuff. They are helper functions to read in json. */
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
            return jsonText;
        } finally {
            is.close();
        }
    }

}
