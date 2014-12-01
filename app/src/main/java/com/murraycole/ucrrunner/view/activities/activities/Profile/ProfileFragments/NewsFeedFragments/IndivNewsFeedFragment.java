package com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.NewsFeedFragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.DAO.Post;
import com.murraycole.ucrrunner.view.adapters.CommentAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndivNewsFeedFragment extends Fragment {
    CommentAdapter commentAdapter;
    ArrayList<Pair<String,String>> post;

    public IndivNewsFeedFragment() {
        // Required empty public constructor
    }

    public static IndivNewsFeedFragment newInstance(Post post) {
        Bundle args = new Bundle();
        args.putString("Post", post.getComment());
        IndivNewsFeedFragment newsfeedAdapter = new IndivNewsFeedFragment();
        newsfeedAdapter.setArguments(args);
        return newsfeedAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_indiv_news_feed, container, false);
        post = new ArrayList<Pair<String,String>>();

        
        String user_post = getArguments().getString("Post");
        for(String comment : user_post.split(":")){
            Pair<String, String> cPair = Pair.create(comment.split("-")[0], comment.split("-")[1]);
            Log.d("MT", "The Comment pair is : <" + cPair.first + ", " + cPair.second + ">");
            post.add(cPair);
        }

        commentAdapter = new CommentAdapter(getActivity(), post);
        ListView listView = (ListView) rootView.findViewById(R.id.indv_newsfeed_comment_list_view);
        listView.setAdapter(commentAdapter);


        //populate the listView here with the comment adapter

        return rootView;
    }


}
