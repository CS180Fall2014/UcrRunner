package com.murraycole.ucrrunner.view;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbrevard on 11/9/14.
 */
public class MapInformation {
    private List<Polyline> entireRoute = new ArrayList<Polyline>();
    private Polyline sectionOfRoute;
    private List<LatLng> pointsSectionOfRoute;
    private List<Location> locationEntireRoute = new ArrayList<Location>();
    private Boolean isStart = false;
    private Boolean isPause = false;
    private GoogleMap googleMap;
    private LocationStatsListener locationStatsListener;
    //test flags
    private Boolean testFlagOnce = true;
    private Boolean isTesting = true;

    MapInformation(GoogleMap googleMap, LocationStatsListener locationStatsListener) {
        this.googleMap = googleMap;
        this.locationStatsListener = (LocationStatsListener) locationStatsListener;
        setUpMap();
    }

    //TODO:
    public void startRoute() {
        pointsSectionOfRoute = new ArrayList<LatLng>(); //pts for polyline
        PolylineOptions options = new PolylineOptions();
        //options.color() match in app color
        sectionOfRoute = googleMap.addPolyline(options);
        isStart = true;
        isPause = false;
    }

    //TODO:
    public void pauseRoute() {
        entireRoute.add(sectionOfRoute);
        isStart = false;
        isPause = true;
    }

    //TODO:
    public void stopRoute() {
        //save to firebase
    }

    /**
     * current speed in miles per hour
     *
     * @return the current speed
     */
    public double getCurrentSpeed() {
        if (locationEntireRoute.size() != 0)
            return locationEntireRoute.get(locationEntireRoute.size()).getSpeed();
        return -1.0;
    }

    private void savePoint(Location location) {
        locationEntireRoute.add(location);
        pointsSectionOfRoute.add(new LatLng(location.getLatitude(), location.getLongitude()));
        sectionOfRoute.setPoints(pointsSectionOfRoute);

        //testing (information)
        if (isTesting) {
            //Toast.makeText("Hello World")
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that is not null.
     */
    private void setUpMap() {
        //TODO: testing area (delete)
        if (testFlagOnce) {
            startRoute();
            testFlagOnce = false;
        }

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 25));
                if (isStart && !isPause)
                    savePoint(location);
                locationStatsListener.onLocationUpdate(location);

            }
        });

    }

    public interface LocationStatsListener {
        public void onLocationUpdate(Location location);
    }

}
