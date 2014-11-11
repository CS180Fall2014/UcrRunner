package com.murraycole.ucrrunner.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.murraycole.ucrrunner.R;

public class MapPreStart extends BaseMapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_pre_start);

        Button StartRun = (Button)findViewById(R.id.map_pre_start_startRun_button);
        StartRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_time = System.currentTimeMillis();
                mapInfo.startRoute();
                startActivity(new Intent(getBaseContext(), MapRunning.class));
                //setContentView(R.layout.activity_maps_running);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map_pre_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
