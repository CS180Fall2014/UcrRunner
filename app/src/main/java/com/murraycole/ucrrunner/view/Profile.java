package com.murraycole.ucrrunner.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.ProfileFragments.ProfileFragment;

public class Profile extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ProfileFragment()).commit();

            /*
            //Redirect from Login to MapPreStart Screen
                MapPreStart mapPreStart = new MapPreStart();
                Intent mapActivity = new Intent(this, mapPreStart.getClass());
            // mike : mapRunning stuff (testing here)
                // MapRunning mapRunning = new MapRunning();
                //Intent mapActivity = new Intent(this, mapRunning.getClass());
            startActivity(mapActivity); */
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

    }
}
