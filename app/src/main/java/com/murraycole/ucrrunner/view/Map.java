package com.murraycole.ucrrunner.view;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.murraycole.ucrrunner.R;


import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Map extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private List<Polyline> entireRoute;
    private Polyline sectionOfRoute;
    private List<LatLng> pointsSectionOfRoute;
    private Boolean isStart = false;
    private Boolean isPause = false;

    private Boolean testFlag = true;

    //TODO:
    public void startRoute() {
        entireRoute = new ArrayList<Polyline>();
        pointsSectionOfRoute = new ArrayList<LatLng>();
        //options.color() match in app color
        PolylineOptions options = new PolylineOptions();
        sectionOfRoute = mMap.addPolyline(options);
        isStart = true;
    }

    //TODO:
    public void pauseRoute() {

    }

    //TODO:
    public void stopRoute() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);
        setContentView(R.layout.activity_maps_home);
        Button Start_Run = (Button) this.findViewById(R.id.map_home_start_run_button);

        Start_Run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_maps);
            }
        });


        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

//    private void drawRoute() {
//        mMap.clear();
//        PolylineOptions rectOptions = new PolylineOptions()
//                .add(new LatLng(loc.getLatitude(), loc.getLongitude()))
//                .add(new LatLng(loc.getLatitude() + 0.006, loc.getLongitude() + 0.006))
//                .add(new LatLng(loc.getLatitude() + 0.007, loc.getLongitude() + 0.007))
//                .add(new LatLng(loc.getLatitude() + 0.008, loc.getLongitude() + 0.008))
//                .add(new LatLng(loc.getLatitude() + 0.01, loc.getLongitude() + 0.01))
//                .add(new LatLng(loc.getLatitude() + 0.01, loc.getLongitude() + 0.02))
//                .add(new LatLng(loc.getLatitude() - 0.01, loc.getLongitude() + 0.03))
//                .add(new LatLng(loc.getLatitude() - 0.01, loc.getLongitude() + 0.04));
//        Polyline polyline = mMap.addPolyline(rectOptions);
//    }


    private void savePoint(Location location) {
        pointsSectionOfRoute.add(new LatLng(location.getLatitude(), location.getLongitude()));
        sectionOfRoute.setPoints(pointsSectionOfRoute);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                //testing area
                if (testFlag) {
                    startRoute();
                    testFlag = false;
                }

                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 25));
                        if (isStart && !isPause)
                            savePoint(location);
                        //drawRoute();
                    }
                });

            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
