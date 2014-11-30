package com.murraycole.ucrrunner.view.DAO;

/**
 * Created by Martin on 11/23/14.
 */
public class Post {
    int uid = -1;
    String description = new String();
    String authorNickname = new String();
    String authorUID = new String();
    String routeID = new String();  // get this from the list route object
    String postID = new String();  // Don't bother setting this.
    String comment = new String(); //Don't bother setting this.
    String likes = new String();   // Don't bother setting this.
    byte[] image;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
    public String getAuthorUID() {
        return authorUID;
    }

    public void setAuthorUID(String postAuthorUID) {
        this.authorUID = postAuthorUID;
    }

    public String getAuthorNickname() {
        return authorNickname;
    }

    public void setAuthorNickname(String postAuthorNickname) {
        this.authorNickname = postAuthorNickname;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

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
