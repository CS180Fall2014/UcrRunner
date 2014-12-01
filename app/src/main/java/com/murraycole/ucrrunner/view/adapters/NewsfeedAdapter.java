package com.murraycole.ucrrunner.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.Utils.SharedPrefUtils;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.Post;
import com.murraycole.ucrrunner.view.dialogfragments.CommentDialogFragment;

import java.util.ArrayList;

/**
 * Created by C on 11/22/2014.
 */
public class NewsfeedAdapter extends ArrayAdapter {
    ArrayList <Post> newsPosts;
    Context mContext;
    Post currPost;

    public NewsfeedAdapter(Context context, ArrayList resource) {
        super(context, 0, resource);
        newsPosts = resource;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;


            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_newsfeed, parent, false);
            currPost = newsPosts.get(position);
            Log.d("NewsFeedAdapter", "Cur pos: " + position + " " + currPost.getPostID());

            ImageView likeButton = (ImageView) view.findViewById(R.id.newsfeed_like_button);
            ImageView commentButton = (ImageView) view.findViewById(R.id.newsfeed_comment_button);
            TextView authorTV = (TextView) view.findViewById(R.id.newsfeed_author_textview);
            TextView descriptionTV = (TextView) view.findViewById(R.id.newsfeed_text_textview);
            ImageView imageView = (ImageView) view.findViewById(R.id.newsfeed_image_imageview);

            String description = newsPosts.get(position).getDescription();
            String author = newsPosts.get(position).getAuthorNickname();
            descriptionTV.setText(description);
            authorTV.setText(author);

            setupLikeOnClickListener(likeButton,currPost);
            setupCommentOnClickListener(commentButton,currPost);




        return view;
    }

    private void setupLikeOnClickListener(ImageView imageView, final Post currPost) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Like", Toast.LENGTH_SHORT).show();
                FirebaseManager.addLike(SharedPrefUtils.getCurrUID(v.getContext()),
                        currPost.getAuthorUID(),
                        currPost.getPostID());

            }
        });

    }

    private void setupCommentOnClickListener(ImageView imageView, final Post currPost) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add the Comment Dialogue Here
                CommentDialogFragment commentDialogFragment =
                        CommentDialogFragment.newInstance(R.string.comment_dialog_title, currPost);
                commentDialogFragment.show(((Activity)mContext).getFragmentManager(),"dialog");

                //Toast.makeText(getContext(), "Incomplete", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
