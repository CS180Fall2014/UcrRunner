package com.murraycole.ucrrunner.view.DAO;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by mbrevard on 11/9/14.
 */
public class Route {
    private List<List<LatLng>> currentRoute;
    private Stats currentStats;
    private Boolean isBookmarked;
    private String id;
    private byte[] image;
    private String date;
    private String title;


    public Boolean getIsBookmarked() {
        return isBookmarked;
    }

    public void setIsBookmarked(Boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<List<LatLng>> getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(List<List<LatLng>> currentRoute) {
        this.currentRoute = currentRoute;
    }

    public Stats getCurrentStats() {
        return currentStats;
    }

    public void setCurrentStats(Stats currentStats) {
        this.currentStats = currentStats;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
