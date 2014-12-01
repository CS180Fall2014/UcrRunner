package com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.NewsFeedFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.Utils.SharedPrefUtils;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.Post;
import com.murraycole.ucrrunner.view.adapters.NewsfeedAdapter;
import com.murraycole.ucrrunner.view.interfaces.ArrayUpdateListener;

import java.util.ArrayList;

/**
 *
 */
public class NewsFeedFragment extends Fragment implements ArrayUpdateListener {
    NewsfeedAdapter mAdapter;
    ArrayList<Post> newsFeed;

    public NewsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);

        newsFeed = new ArrayList<Post>();
        mAdapter = new NewsfeedAdapter(getActivity(), newsFeed);

        //Get posts from firebase, uses an interface to populate newsfeed list
        FirebaseManager.getPostsForFriends(SharedPrefUtils.getCurrUID(getActivity()), this);


        ListView listView = (ListView) rootView.findViewById(R.id.newsfeed_listview);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the post object
                Log.d("NewsFeed", newsFeed.get(position).getPostID());
                IndivNewsFeedFragment indivNewsFeedFragment =
                        IndivNewsFeedFragment.newInstance(newsFeed.get(position));


                getFragmentManager().beginTransaction()
                        .replace(R.id.container, indivNewsFeedFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void update(Object o) {
        newsFeed.add((Post) o);
        mAdapter.notifyDataSetChanged();

    }
    public void clearAdapter(){
        newsFeed.clear();
        mAdapter.notifyDataSetChanged();
    }
}

