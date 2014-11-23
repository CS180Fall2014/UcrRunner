package com.murraycole.ucrrunner.view.ProfileFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.adapters.FriendsAdapter;
import com.murraycole.ucrrunner.view.adapters.MessagesAdapter;

import java.util.ArrayList;

/* Keshav */

public class FriendsFragment extends Fragment {

    FriendsAdapter mAdapter;
    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("random");
        messages.add("fakeData");
        mAdapter = new FriendsAdapter(getActivity(),messages);

        ListView listView = (ListView) rootView.findViewById(R.id.friends_listview);
        listView.setAdapter(mAdapter);
        // Inflate the layout for this fragment
        return rootView;
    }


}
