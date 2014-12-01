package com.murraycole.ucrrunner.view.activities.activities.NavDrawer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.activities.activities.Map.MapRunning;
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.FriendsFragment;
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.MessageFragments.MailboxFragment;
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.NewsFeedFragments.NewsFeedFragment;
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.PrevRouteFragment;
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.ProfileFragment;
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.ProfileSettingsFragment;

public class ProfileNavDrawer extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    final int PROFILE_SECTION = 0;
    final int FRIENDS_SECTION = 2;
    final int MAILBOX_SECTION = 3;
    final int NEWSFEED_SECTION = 1;
    final int SETTINGS_SECTION = 6;
    final int PREVROUTES_SECTION = 4;
    final int STARTRUN_SECTION = 5;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_nav_drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        if (position == PROFILE_SECTION) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new ProfileFragment())
                    .commit();
        }
        if (position == NEWSFEED_SECTION) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new NewsFeedFragment())
                    .commit();
        }
        if (position == FRIENDS_SECTION) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new FriendsFragment())
                    .commit();
        }
        if (position == MAILBOX_SECTION) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new MailboxFragment())
                    .commit();

        }

        if (position == PREVROUTES_SECTION) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new PrevRouteFragment())
                    .commit();

        }
        if (position == STARTRUN_SECTION) {
            Intent intent = new Intent(this, MapRunning.class);
            startActivity(intent);
        }
        if (position == SETTINGS_SECTION) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new ProfileSettingsFragment())
                    .commit();

        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case PROFILE_SECTION:
                mTitle = getString(R.string.title_section_profile);
                break;
            case FRIENDS_SECTION:
                mTitle = getString(R.string.title_section_friends);
                break;
            case MAILBOX_SECTION:
                mTitle = getString(R.string.title_section_mailbox);
                break;
            case NEWSFEED_SECTION:
                mTitle = getString(R.string.title_section_newsfeed);
                break;
            case STARTRUN_SECTION:
                mTitle = getString(R.string.title_section_startrun);
                break;
            case PREVROUTES_SECTION:
                mTitle = getString(R.string.title_section_prevroutes);
                break;
            case SETTINGS_SECTION:
                mTitle = getString(R.string.title_section_settings);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.profile_nav_drawer, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //startActivity(new Intent(this, SettingsActivity.class));
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new ProfileSettingsFragment())
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profile_nav_drawer, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((ProfileNavDrawer) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
