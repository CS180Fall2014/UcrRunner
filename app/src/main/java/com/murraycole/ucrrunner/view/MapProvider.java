package com.murraycole.ucrrunner.view;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 11/9/14.
 */
public class MapProvider {
    int userID;
    //data structure for all routes... create a route object? yes.
    private class route{
        public route(int routeID){
            // retrieve the information and store in fields.
            // routeid
            //

        }
    }

    MapProvider(int userID){

    }

    ////////////////// MapProvider FUNCTIONS //////////////////////
    ArrayList<LatLng> getRoutesForUser(int userID){
        return null;
    }


    ////////////////// STATIC FUNCTIONS //////////////////////
    static ArrayList<LatLng> retrieveRoute(int routeID){
        return null;
    }
}
