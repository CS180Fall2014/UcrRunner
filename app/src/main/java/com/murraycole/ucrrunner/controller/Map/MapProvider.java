package com.murraycole.ucrrunner.controller.Map;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Martin on 11/9/14.
 */
public class MapProvider {
    int userID;

    MapProvider(int userID) {

    }

    ////////////////// STATIC FUNCTIONS //////////////////////
    static ArrayList<LatLng> retrieveRoute(int routeID) {
        return null;
    }

    ////////////////// MapProvider FUNCTIONS //////////////////////
    ArrayList<LatLng> getRoutesForUser(int userID) {
        return null;
    }

    //data structure for all routes... create a route object? yes.
    private class route {
        public route(int routeID) {
            // retrieve the information and store in fields.
            // routeid
            //

        }
    }
}
