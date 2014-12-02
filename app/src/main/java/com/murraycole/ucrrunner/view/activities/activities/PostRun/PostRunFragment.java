package com.murraycole.ucrrunner.view.activities.activities.PostRun;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.Utils.SharedPrefUtils;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.Post;
import com.murraycole.ucrrunner.view.DAO.Route;
import com.murraycole.ucrrunner.view.activities.activities.NavDrawer.ProfileNavDrawer;
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.ProfileFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostRunFragment extends Fragment {
    String routeID;

    public static PostRunFragment newInstance (String r){
        PostRunFragment fragment = new PostRunFragment();
        fragment.routeID = r;
        return  fragment;
    }
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
        final TextView description = (TextView) v.findViewById(R.id.Post_Run_Add_Description_editText);

        Post_Run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
                String myID = SharedPrefUtils.getCurrUID(getActivity());
                String myNickName = FirebaseManager.getNickname(myID);
                post.setAuthorUID(myID);
                post.setAuthorNickname(myNickName);
                post.setDescription(description.getText().toString());
                post.setRouteID(routeID);

                FirebaseManager.savePost(SharedPrefUtils.getCurrUID(getActivity()),post);
                startActivity(new Intent(getActivity(), ProfileNavDrawer.class));

            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileNavDrawer.class));
            }
        });
    }

}
