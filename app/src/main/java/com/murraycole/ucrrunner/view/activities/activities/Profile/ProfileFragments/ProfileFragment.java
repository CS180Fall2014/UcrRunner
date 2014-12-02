package com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.Utils.SharedPrefUtils;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.backend.SettingsManager;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        String myUID = SharedPrefUtils.getCurrUID(getActivity());
        final String myNickname = FirebaseManager.getNickname(myUID);
        final double avgSpeed = SettingsManager.getUserAvgSpeed(myUID);
        final double totalDistance = SettingsManager.getUserTotalDist(myUID);
        final double totalCalories = SettingsManager.getUserTotalCal(myUID);
        final double age = SettingsManager.getUserAge(myUID);
        final double height = SettingsManager.getUserHeight(myUID);
        final double weight = SettingsManager.getUserWeight(myUID);

        String heightInString = String.valueOf(height);
        String heightParsed = heightInString.replace(".", "\"");

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        DecimalFormat ageFormat = new DecimalFormat("#");

        TextView nicknameTV = (TextView) rootView.findViewById(R.id.profile_username_tv);
        TextView avgSpeedTV = (TextView) rootView.findViewById(R.id.profile_avg_speed_value_tv);
        TextView totalDistanceTV = (TextView) rootView.findViewById(R.id.profile_miles_value_tv);
        TextView totalCaloriesTV = (TextView) rootView.findViewById(R.id.profile_total_cal_value_tv);
        TextView ageOfUser = (TextView) rootView.findViewById(R.id.profile_age_tv);
        TextView heightTV = (TextView) rootView.findViewById(R.id.profile_height_tv);
        TextView weightTV = (TextView) rootView.findViewById(R.id.profile_weight_tv);

        nicknameTV.setText(myNickname);
        ageOfUser.setText(String.valueOf(ageFormat.format(age)));
        heightTV.setText(heightParsed);
        weightTV.setText(String.valueOf(weight));
        avgSpeedTV.setText(String.valueOf(decimalFormat.format(avgSpeed)));

        totalDistanceTV.setText(String.valueOf(decimalFormat.format(totalDistance)));
        totalCaloriesTV.setText(String.valueOf(decimalFormat.format(totalCalories)));



        return rootView;
    }

/*
    private void On_Button_Press(View v) {
        Button Start_Run = (Button) v.findViewById(R.id.profile_startrun_button);
        Button Friends = (Button) v.findViewById(R.id.profile_friends_button);
        final Button Settings = (Button) v.findViewById(R.id.profile_settings_button);
        Button News_Feed = (Button) v.findViewById(R.id.profile_newsfeed_button);
        Button Previous_Routes = (Button) v.findViewById(R.id.profile_prevroutes_button);
        Button Mailbox = (Button) v.findViewById(R.id.profile_mailbox_button);

        Start_Run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapRunning.class));
            }
        });

        Friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new FriendsFragment()).addToBackStack(null).commit();

            }
        });

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ProfileSettingsFragment()).addToBackStack(null).commit();

            }
        });

        News_Feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new NewsFeedFragment()).addToBackStack(null).commit();

            }
        });

        Previous_Routes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new PrevRouteFragment()).addToBackStack(null).commit();

            }
        });

        Mailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MailboxFragment()).addToBackStack(null).commit();

            }
        });
    }

   */
}
