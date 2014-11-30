package com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.Utils.SharedPrefUtils;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.Route;
import com.murraycole.ucrrunner.view.adapters.PrevRouteAdapter;
import com.murraycole.ucrrunner.view.interfaces.ArrayUpdateListener;

import java.util.ArrayList;

/**
 * Cole doing this
 */
public class PrevRouteFragment extends Fragment implements ArrayUpdateListener {
    ArrayList<Route> routes;
    PrevRouteAdapter mAdapter;

    public PrevRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_prev_route, container, false);
        routes = new ArrayList<Route>();
        mAdapter = new PrevRouteAdapter(getActivity(), routes);
        ListView listView = (ListView) rootview.findViewById(R.id.prev_route_listview);
        listView.setAdapter(mAdapter);
        FirebaseManager.getRoutes(SharedPrefUtils.getCurrUID(getActivity()), this);
        return rootview;
    }

    private ArrayList<Route> spoofData() {
        ArrayList<Route> routeData = new ArrayList<Route>();
        for (int i = 0; i < 5; i++)
            routeData.add(new Route());
        return routeData;
    }


    @Override
    public void update(Object o) {
        routes.add((Route) o);
        mAdapter.notifyDataSetChanged();

    }
}
