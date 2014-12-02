package com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.NewsFeedFragments;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.Utils.BitmapToUri;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.controller.Map.MapCalculation;
import com.murraycole.ucrrunner.view.DAO.Post;
import com.murraycole.ucrrunner.view.adapters.CommentAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndivNewsFeedFragment extends Fragment {
    CommentAdapter commentAdapter;

    public IndivNewsFeedFragment() {
        // Required empty public constructor
    }

    public static IndivNewsFeedFragment newInstance(Post post) {
        Bundle args = new Bundle();
        args.putString("Post", post.getComment());
        args.putString("RouteID", post.getRouteID());
        args.putString("AuthorNickname", post.getAuthorNickname());
        Log.d("newsFeed", post.getComment()); //correct input
        IndivNewsFeedFragment newsfeedAdapter = new IndivNewsFeedFragment();
        newsfeedAdapter.setArguments(args);
        return newsfeedAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_indiv_news_feed, container, false);
        ImageView mapImage = (ImageView) rootView.findViewById(R.id.indv_newsfeed_map_iv);
        TextView authorNicknameTV = (TextView) rootView.findViewById(R.id.indv_newsfeed_author_tv);
        ArrayList<Pair<String, String>> post;
        post = new ArrayList<Pair<String,String>>();

        
        String user_post = getArguments().getString("Post");
        if (!user_post.equals("")) {
            for (String comment : user_post.split(":")) {
                Pair<String, String> cPair = Pair.create(comment.split("-")[0], comment.split("-")[1]);
                Log.d("MT", "The Comment pair is : <" + cPair.first + ", " + cPair.second + ">"); //also correct
                post.add(cPair);
            }
        }
        String routeID = getArguments().getString("RouteID");
        String authorNickname = getArguments().getString("AuthorNickname");
        authorNicknameTV.setText(authorNickname);
        String mapImageString = FirebaseManager.getImage(routeID);
        Bitmap mapBitmap = MapCalculation.decodeNotScaled(mapImageString);
        Uri mapURI = BitmapToUri.getImageUriFromBitmap(getActivity(), mapBitmap);
        Picasso.with(getActivity()).load(mapURI).fit().into(mapImage);


        commentAdapter = new CommentAdapter(getActivity(), post);
        ListView listView = (ListView) rootView.findViewById(R.id.indv_newsfeed_comment_list_view);
        listView.setAdapter(commentAdapter);


        //populate the listView here with the comment adapter
        commentAdapter.notifyDataSetChanged();
        return rootView;
    }
    public void notifyOnDataChanged(){
        commentAdapter.notifyDataSetChanged();
    }



}
