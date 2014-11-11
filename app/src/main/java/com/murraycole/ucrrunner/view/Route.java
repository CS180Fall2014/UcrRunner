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
}
