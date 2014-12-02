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
        final String myNickname = FirebaseManager.getNickname(SharedPrefUtils.getCurrUID(getActivity()));
        TextView nicknameTV = (TextView) rootView.findViewById(R.id.profile_username_tv);
        nicknameTV.setText(myNickname);


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
