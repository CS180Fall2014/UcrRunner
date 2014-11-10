package com.murraycole.ucrrunner.view;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbrevard on 11/9/14.
 */
public class Route {
    private List<List<LatLng>> currentRoute;
    private Stats currentStats;

    Route() {
        currentRoute = new ArrayList<List<LatLng>>();
        currentStats = new Stats();
    }

    Route(List<List<LatLng>> route, Stats stats) {
        currentRoute = route;
        currentStats = stats;
    }

    public void setCurrentRoute(List<List<LatLng>> route) {
        currentRoute = route;
    }

    public void setCurrentStats(Stats stats) {
        currentStats = stats;
    }

    public List<List<LatLng>> getCurrentRoute() {
        return currentRoute;
    }

    public Stats getCurrentStats() {
        return currentStats;
    }
}
