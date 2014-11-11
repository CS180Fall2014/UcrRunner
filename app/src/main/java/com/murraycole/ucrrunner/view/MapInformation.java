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

    //current route (entirety)
    private List<Polyline> entireRoute = new ArrayList<Polyline>();
    private List<Location> locationEntireRoute = new ArrayList<Location>();
    private double entireDistance = 0.0;

    //current route (section)
    private Polyline sectionOfRoute;
    private List<LatLng> pointsSectionOfRoute;

    // flags to know current state
    private Boolean isStart = false;
    private Boolean isPause = false;

    // initializations
    private GoogleMap googleMap;
    private LocationStatsListener locationStatsListener;
    private String UUID = "12345"; //TODO get real UUID

    /**
     * initializes the MapInformation which is dependent on the Google Map API v2
     *
     * @param googleMap             the Google Map API service (v2)
     * @param locationStatsListener location listener to update the information on the UI
     */
    MapInformation(GoogleMap googleMap, LocationStatsListener locationStatsListener) {
        this.googleMap = googleMap;
        this.locationStatsListener = (LocationStatsListener) locationStatsListener;
        setUpMap();
    }

    /**
     * starts the route that is going to be displayed on the Google Map
     */
    public void startRoute() {
        pointsSectionOfRoute = new ArrayList<LatLng>(); //pts for polyline
        PolylineOptions options = new PolylineOptions();
        //options.color() match in app color
        sectionOfRoute = googleMap.addPolyline(options);
        isStart = true;
        isPause = false;
    }

    /**
     * pauses the current route, waits for a startRoute() to be called to resume route
     */
    public void pauseRoute() {
        entireRoute.add(sectionOfRoute);
        isStart = false;
        isPause = true;
    }

    /**
     * resumes the current route after the route is paused
     */
    public void resumeRoute() {
        //TODO;
    }

    /**
     * stops the current route and saves to Firebase
     *
     * @param seconds is the duration of the run
     */
    public void stopRoute(int seconds) {
        isStart = isPause = false;
        List<List<LatLng>> r = new ArrayList<List<LatLng>>();
        for (Polyline l : entireRoute)
            r.add(l.getPoints());

        FirebaseManager.saveRoute(new Route(r, new Stats()), UUID); //TODO: save real stuff

    }

    /**
     * current speed in miles per hour
     *
     * @return the current speed
     */
    public double getCurrentSpeed() {
        if (locationEntireRoute == null || locationEntireRoute.isEmpty())
            return -1.0;
        return locationEntireRoute.get(locationEntireRoute.size() - 1).getSpeed();
    }

    /**
     * gets the distance
     *
     * @return distance in miles per hour
     */
    public double getDistance() {
        //TODO
        //Location.distanceBetween();
//        double distance = 0.0;
//        for (Polyline p : entireRoute)
//            googleMap.
//
//
//        distance += p.
        return -1.0;
    }

    /**
     * gets average speed of current route
     *
     * @return average speed in miles per hour
     */
    public double getAverageSpeed() {
        double averageSpeed = 0.0;
        for (Location l : locationEntireRoute)
            averageSpeed += l.getSpeed();

        averageSpeed /= locationEntireRoute.size();

        return averageSpeed;
    }

    /**
     * gets calories of current route
     *
     * @return calories
     */
    public double getCalories() {
        //TODO:
        return -1.0;
    }

    private void savePoint(Location location) {
        locationEntireRoute.add(location);
        pointsSectionOfRoute.add(new LatLng(location.getLatitude(), location.getLongitude()));
        sectionOfRoute.setPoints(pointsSectionOfRoute);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that is not null.
     */
    private void setUpMap() {
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
