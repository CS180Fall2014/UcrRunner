package com.murraycole.ucrrunner.view.activities.activities.Map;

import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.controller.Map.MapInformation;
import com.murraycole.ucrrunner.view.activities.activities.PostRun.PostRunFragment;

import java.text.DecimalFormat;

public class MapRunning extends BaseMapActivity {
    TextView currspeed;
    TextView duration;
    TextView distance;
    TextView calories;
    TextView avgspeed;
    Boolean isRunning = true;
    PopupWindow pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_running);

        currspeed = (TextView) findViewById(R.id.maprunning_currspeed_textview);
        duration = (TextView) findViewById(R.id.maprunning_duration_textview);
        distance = (TextView) findViewById(R.id.maprunning_dist_textview);
        calories = (TextView) findViewById(R.id.maprunning_cal_textview);
        avgspeed = (TextView) findViewById(R.id.maprunning_avgspeed_textview);

        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();

        final Button PauseRun = (Button) findViewById(R.id.maprunning_pause_textview);
        PauseRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PauseRun.getText().equals("Pause")) {
                    time_when_paused = mChronometer.getBase() - SystemClock.elapsedRealtime();
                    mChronometer.stop();
                    isRunning = false;
                    PauseRun.setText("Start");
                    mapInfo.pauseRoute();
                } else if (PauseRun.getText().equals("Start")) {
                    mChronometer.setBase(SystemClock.elapsedRealtime() + time_when_paused);
                    mChronometer.start();
                    isRunning = true;
                    PauseRun.setText("Pause");
                    mapInfo.resumeRoute();
                }

            }
        });

        final Button StopRun = (Button) findViewById(R.id.maprunning_stop_textview);
        StopRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat valuesRounded = new DecimalFormat("#.##");
                time_when_stopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
                time_when_paused = 0;
                isRunning = false;
                mChronometer.stop();
                currspeed.setText(String.valueOf(valuesRounded.format(mapInfo.getTopSpeed())));
                //All buttons are gone
                StopRun.setVisibility(View.GONE);
                PauseRun.setVisibility(View.GONE);
                String uid = FirebaseManager.getCurrUID(v.getContext());
                mapInfo.stopRoute(-1, uid);
                //Prompt the user to enter a title
                Initiate_PopupWindow();

            }
        });


        setupLocationStatsListener();
        setUpMapIfNeeded();
    }

    private void Initiate_PopupWindow() {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.title_popup_window,
                    (ViewGroup) findViewById(R.id.popup_element));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 300, 470, true);
            // display the popup in the center
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

            TextView Title = (TextView) layout.findViewById(R.id.Popup_Title_editText);
            Button Ok = (Button) layout.findViewById(R.id.Popup_ok_button);

            Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pw.dismiss();
                    //Transitions to the post run to news feed scene
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new PostRunFragment())
                            .commit();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setupLocationStatsListener() {
        locationStatsListener = new MapInformation.LocationStatsListener() {
            @Override
            public void onLocationUpdate(Location location) {
                DecimalFormat valuesRounded = new DecimalFormat("#.##");
                currspeed.setText(String.valueOf(valuesRounded.format(mapInfo.getCurrentSpeed())));
                distance.setText(
                        String.valueOf(valuesRounded.format(mapInfo.getDistance())) + "\nDistance (m)");
                if (isRunning) {
                    delta_time = SystemClock.elapsedRealtime() - mChronometer.getBase();
                    calories.setText(String.valueOf(valuesRounded.format(mapInfo.getCalories(delta_time))) +
                            "\nCalories");
                }
                avgspeed.setText(String.valueOf(valuesRounded.format(mapInfo.getAverageSpeed())) + "\n Avg Speed");

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
            mMap.getUiSettings().setZoomControlsEnabled(false);

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mapInfo = new MapInformation(mMap, locationStatsListener);
                mapInfo.startRoute();
            }
        }
    }
}
