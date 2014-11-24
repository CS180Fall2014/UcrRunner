package com.murraycole.ucrrunner.view.DAO;

/**
 * Created by Martin on 11/23/14.
 */
public class Post {
    int uid = 0;
    String description = new String(),
           routeID = new String(),
           postID = new String();

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
