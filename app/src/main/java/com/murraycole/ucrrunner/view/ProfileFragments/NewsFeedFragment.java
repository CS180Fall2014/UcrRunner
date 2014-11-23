package com.murraycole.ucrrunner.view.ProfileFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.adapters.FriendsAdapter;
import com.murraycole.ucrrunner.view.adapters.NewsfeedAdapter;

import java.util.ArrayList;

/**
 *
 */
public class NewsFeedFragment extends Fragment {
    NewsfeedAdapter mAdapter;

    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_news_feed, container, false);

        ArrayList<String> messages = new ArrayList<String>();
        messages.add("random");
        messages.add("fakeData");
        mAdapter = new NewsfeedAdapter(getActivity(),messages);

        ListView listView = (ListView) rootView.findViewById(R.id.newsfeed_listview);
        listView.setAdapter(mAdapter);
        // Inflate the layout for this fragment
        return rootView;
    }


}
