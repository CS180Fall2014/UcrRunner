package com.murraycole.ucrrunner.view.ProfileFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.Route;
import com.murraycole.ucrrunner.view.adapters.PrevRouteAdapter;

import java.util.ArrayList;

/**
 * Cole doing this
 */
public class PrevRouteFragment extends Fragment {

    PrevRouteAdapter mAdapter;
    public PrevRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_prev_route, container, false);
        ArrayList<Route> routes = spoofData();
        mAdapter = new PrevRouteAdapter(getActivity(),routes);
        ListView listView = (ListView) rootview.findViewById(R.id.prev_route_listview);
        listView.setAdapter(mAdapter);


        return rootview;
    }

    private ArrayList<Route> spoofData(){
        ArrayList<Route> routeData = new ArrayList<Route>();
        for (int i = 0; i < 5; i++)
        routeData.add(new Route());
        return routeData;
    }


}
