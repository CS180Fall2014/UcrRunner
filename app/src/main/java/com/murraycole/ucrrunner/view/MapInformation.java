package com.murraycole.ucrrunner.view;

import android.graphics.Color;
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
 * MapInformaion class has the functionality for the Google Map. These includes start,pause, stop
 * route and the information about the route
 * Created by mbrevard on 11/9/14.
 */
public class MapInformation {

    //current route (entirety)
    private List<Polyline> entireRoute = new ArrayList<Polyline>();
    private List<Location> locationEntireRoute = new ArrayList<Location>();

    //current route (section)
    private List<LatLng> pointsSectionOfRoute;

    // flags to know current state
    private Boolean isStart = false;
    private Boolean isPause = false;

    // initializations
    private GoogleMap googleMap;
    private LocationStatsListener locationStatsListener;
    private String UUID = "12345:3423"; //TODO get real UUID
    private Boolean isPreviouslyCalculatedRoute = false;
    private Boolean isBookmarked = false;


    /**
     * initializes the MapInformation which is dependent on the Google Map API v2
     *
     * @param googleMap             the Google Map API service (v2)
     * @param locationStatsListener location listener to update the information on the UI
     */
    MapInformation(GoogleMap googleMap, LocationStatsListener locationStatsListener) {
        this.googleMap = googleMap;
        this.locationStatsListener = locationStatsListener;
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

    /**
     * (untested)
     * bookmarks the current route
     * @param isBookmarked is true if the route is to be bookmarked, otherwise false
     */
    public void bookmarkRoute(Boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    /**
     * (untested)
     * displays the given route and shows all information
     * @param route is the route to displayed on the map
     */
    public void displayRoute(Route route) {
        isPreviouslyCalculatedRoute  = true;
        locationEntireRoute = route.getLocationCurrentRoute();
        for (List<LatLng> pts : route.getCurrentRoute()) {
            PolylineOptions options = new PolylineOptions();
            options.color(Color.BLUE);
            Polyline polyline = googleMap.addPolyline(options);
            polyline.setPoints(pts);
            entireRoute.add(polyline);
        }

        //to display route on map
        stopRoute((int) route.getCurrentStats().duration);
    }

    /**
     * (untested)
     * displays preran route so user can run route again
     * @param route is the preran route
     */
    public void reRunBookmarkedRoute(Route route) {
        //isPreviouslyCalculatedRoute  = true;
        //locationEntireRoute = route.getLocationCurrentRoute();
        for (List<LatLng> pts : route.getCurrentRoute()) {
            PolylineOptions options = new PolylineOptions();
            options.color(Color.GRAY);
            Polyline polyline = googleMap.addPolyline(options);
            polyline.setPoints(pts);
        }
    }

    /**
     * stops the current route and saves to Firebase
     *
     * @param seconds is the duration of the run
     */
    public void stopRoute(int seconds) {
        isStart = isPause = false;
        //zoom map to entire route
        googleMap.setOnMyLocationChangeListener(null);
        zoomToFitRoute();

        if (!isPreviouslyCalculatedRoute) {
            List<List<LatLng>> r = new ArrayList<List<LatLng>>();
            for (Polyline l : entireRoute)
                r.add(l.getPoints());

            //save information from route to Firebase
            Route route = new Route();
            Stats stats = new Stats();
            stats.setAverageSpeed(getAverageSpeed());
            stats.setCaloriesBurned(getCalories(seconds));
            stats.setDistance(getDistance());
            stats.setElevation(-1.0); // set up elevation Milestone 1
            stats.setTopSpeed(getTopSpeed());
            stats.setDuration(seconds);
            route.setCurrentRoute(r);
            route.setCurrentStats(stats);
            route.setLocationCurrentRoute(locationEntireRoute);
            route.setId(null); //TODO: add real id
            route.setIsBookmarked(isBookmarked);
            FirebaseManager.saveRoute(route, UUID);
        }
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
    public double getCalories(double elapsed_time) {
        return new MapCalculation().calculateCalories(this,elapsed_time);
    }


    /**
     * zooms to fit the entire route. Used when the route is stopped
     */
    private void zoomToFitRoute() {
        LatLngBounds.Builder b = new LatLngBounds.Builder();
        for (Polyline p : entireRoute) {
            for (LatLng l : p.getPoints())
                b.include(l);
        }
        LatLngBounds bounds = b.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
    }


    /**
     * creates a new Polyline
     */
    private void newPolyline() {
        PolylineOptions options = new PolylineOptions();
        options.color(Color.BLUE);
        Polyline polyline = googleMap.addPolyline(options);
        entireRoute.add(polyline);
    }

    /**
     * saves a point to the line and adds the Location information to the respected fields.
     *
     * @param location is the current location
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
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));
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
