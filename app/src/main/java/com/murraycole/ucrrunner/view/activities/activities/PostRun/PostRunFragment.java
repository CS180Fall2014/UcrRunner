package com.murraycole.ucrrunner.view.activities.activities.PostRun;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.activities.activities.NavDrawer.ProfileNavDrawer;
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.ProfileFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostRunFragment extends Fragment {


    public PostRunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_post_run, container, false);
        ButtonPress(rootView);
        return rootView;
    }

    private void ButtonPress(View v) {
        Button Post_Run = (Button) v.findViewById(R.id.Post_Run_Button);
        Button Home = (Button) v.findViewById(R.id.Post_Run_Home_Button);

        Post_Run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new NewsFeedFragment()).addToBackStack(null).commit(); */
                startActivity(new Intent(getActivity(), ProfileNavDrawer.class));

            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ProfileFragment()).addToBackStack(null).commit();

            }
        });
    }

}
