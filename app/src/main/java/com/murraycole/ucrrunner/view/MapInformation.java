package com.murraycole.ucrrunner.view;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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
    private String UUID = "12345:3423"; //TODO get real UUID

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

        //options.color() match in app color
    }

    /**
     * starts the route that is going to be displayed on the Google Map
     */
    public void startRoute() {
        pointsSectionOfRoute = new ArrayList<LatLng>(); //pts for polyline
        setStartPause(true);
    }

    /**
     * pauses the current route, waits for a startRoute() to be called to resume route
     */
    public void pauseRoute() {
        pointsSectionOfRoute = new ArrayList<LatLng>();
        setStartPause(false);
    }

    /**
     * resumes the current route after the route is paused
     */
    public void resumeRoute() {
        newPolyline();
        setStartPause(true);
    }

    private void zoomToFitRoute() {
        LatLngBounds.Builder b = new LatLngBounds.Builder();
        for (Polyline p : entireRoute) {
            for (LatLng l : p.getPoints())
                b.include(l);
        }
        LatLngBounds bounds = b.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,5));
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

        //zoom map to entire route
        googleMap.setOnMyLocationChangeListener(null);
        zoomToFitRoute();


        //save information from route to Firebase
        Route route = new Route();
        Stats stats = new Stats();
        stats.setAverageSpeed(getAverageSpeed());
        stats.setCaloriesBurned(getCalories());
        stats.setDistance(getDistance());
        stats.setElevation(-1.0); // set up elevation Milestone 1
        stats.setTopSpeed(getTopSpeed());
        route.setCurrentRoute(r);
        route.setCurrentStats(stats);
        FirebaseManager.saveRoute(route, UUID);
    }

    /**
     * current speed in meters per hour
     *
     * @return the current speed
     */
    public double getCurrentSpeed() {
        if (locationEntireRoute == null || locationEntireRoute.isEmpty())
            return -1.0;
        return locationEntireRoute.get(locationEntireRoute.size() - 1).getSpeed();
    }

    /**
     * gets average speed of current route
     *
     * @return average speed in meters per hour
     */
    public double getAverageSpeed() {
        double averageSpeed = 0.0;
        for (Location l : locationEntireRoute)
            averageSpeed += l.getSpeed();

        averageSpeed /= locationEntireRoute.size();

        return averageSpeed;
    }

    /**
     * gets the top speed of current route
     *
     * @return top speed in meters per hour
     */
    public double getTopSpeed() {
        double topSpeed = 0.0;
        for (Location l : locationEntireRoute)
            if (l.getSpeed() > topSpeed)
                topSpeed = l.getSpeed();

        return topSpeed;
    }


    /**
     * gets the distance
     *
     * @return distance in meters
     */
    public double getDistance() {
        double distance = 0.0;
        LatLng p1, p2;
        float[] results = new float[1];
        for (Polyline p : entireRoute) {
            for (int i = 0; i < p.getPoints().size() - 1; i++) {
                p1 = p.getPoints().get(i);
                p2 = p.getPoints().get(i + 1);
                Location.distanceBetween(p1.latitude, p1.longitude, p2.latitude, p2.longitude, results);
                distance += results[0];
            }
        }

        return distance;
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

    /**
     * creates a new Polyline
     */
    private void newPolyline() {
        Polyline polyline = googleMap.addPolyline(new PolylineOptions());
        entireRoute.add(polyline);
    }

    /**
     * saves a point to the line and adds the Location information to the respected fields.
     *
     * @param location
     */
    private void savePoint(Location location) {
        if (entireRoute.isEmpty())
            newPolyline();

        locationEntireRoute.add(location);
        pointsSectionOfRoute.add(new LatLng(location.getLatitude(), location.getLongitude()));
        entireRoute.get(entireRoute.size() - 1).setPoints(pointsSectionOfRoute);
    }

    /**
     * location listener that is used to follow the user
     */
    private GoogleMap.OnMyLocationChangeListener listener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 25));
            locationStatsListener.onLocationUpdate(location);

            if (isStart && !isPause)
                savePoint(location);

        }
    };

    /**
     * map fields are set
     */
    private void setUpMap() {
        googleMap.setOnMyLocationChangeListener(listener);
    }

    /**
     * set the flags for Start and Pause
     *
     * @param isStart is start than true, otherwise false
     */
    private void setStartPause(boolean isStart) {
        this.isStart = isStart;
        isPause = !isStart;
    }

    /**
     * Location listener for UI side
     */
    public interface LocationStatsListener {
        public void onLocationUpdate(Location location);
    }


}
