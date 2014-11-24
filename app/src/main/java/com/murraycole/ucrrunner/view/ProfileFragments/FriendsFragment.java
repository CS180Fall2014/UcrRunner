package com.murraycole.ucrrunner.view.ProfileFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.User;
import com.murraycole.ucrrunner.view.adapters.FriendsAdapter;
import com.murraycole.ucrrunner.view.interfaces.ArrayUpdateListener;

import java.util.ArrayList;

/* Keshav */

public class FriendsFragment extends Fragment implements ArrayUpdateListener{

    FriendsAdapter mAdapter;
    ArrayList<User> friendList;
    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        FirebaseManager.getFriends("89",this);
        friendList = new ArrayList<User>();
        mAdapter = new FriendsAdapter(getActivity(),friendList);
        Log.d("MT", "Oncreateview");
        ListView listView = (ListView) rootView.findViewById(R.id.friends_listview);
        listView.setAdapter(mAdapter);
        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void update(Object o) {
        Log.d("MT", "FriendsFragment updates: " + ((User) o).getNickname());
        friendList.add((User) o);
        mAdapter.notifyDataSetChanged();
    }
}
