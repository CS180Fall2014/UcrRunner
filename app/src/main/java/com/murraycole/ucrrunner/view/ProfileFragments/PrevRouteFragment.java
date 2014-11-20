package com.murraycole.ucrrunner.view.ProfileFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.murraycole.ucrrunner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrevRouteFragment extends Fragment {


    public PrevRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prev_route, container, false);
    }


}
