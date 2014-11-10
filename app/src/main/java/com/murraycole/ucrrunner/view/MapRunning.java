package com.murraycole.ucrrunner.view;

import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.murraycole.ucrrunner.R;

public class MapRunning extends BaseMapActivity {
    TextView currspeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_running);
        currspeed = (TextView) findViewById(R.id.maprunning_currspeed_textview);
        setupLocationStatsListener();


        setUpMapIfNeeded();
    }

    private void setupLocationStatsListener() {
        locationStatsListener = new MapInformation.LocationStatsListener() {
            @Override
            public void onLocationUpdate(Location location) {
                currspeed.setText("Speed:\n" + String.valueOf(location.getSpeed()));

            }
        };
    }

    public MapInformation getMapInformation() {
        return mapInfo;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /* Attempting override */
    @Override
    protected void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mapInfo = new MapInformation(mMap, locationStatsListener);

            }
        }
    }
}
