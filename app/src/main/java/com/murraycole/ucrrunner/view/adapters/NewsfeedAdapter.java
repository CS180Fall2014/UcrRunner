package com.murraycole.ucrrunner.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.DAO.Post;

import java.util.ArrayList;

/**
 * Created by C on 11/22/2014.
 */
public class NewsfeedAdapter extends ArrayAdapter {
    ArrayList <Post> newsPosts;
    public NewsfeedAdapter(Context context, ArrayList resource) {
        super(context, 0, resource);
        newsPosts = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_newsfeed, parent, false);

            ImageView likeButton = (ImageView) view.findViewById(R.id.newsfeed_like_button);
            ImageView commentButton = (ImageView) view.findViewById(R.id.newsfeed_comment_button);
            TextView authorTV = (TextView) view.findViewById(R.id.newsfeed_author_textview);
            TextView descriptionTV = (TextView) view.findViewById(R.id.newsfeed_text_textview);
            ImageView imageView = (ImageView) view.findViewById(R.id.newsfeed_image_imageview);

            String description = newsPosts.get(position).getDescription();
            String author = newsPosts.get(position).getAuthorNickname();
            descriptionTV.setText(description);
            authorTV.setText(author);

            setupLikeOnClickListener(likeButton);
            setupCommentOnClickListener(commentButton);


        }
        return view;
    }

    private void setupLikeOnClickListener(ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Incomplete", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setupCommentOnClickListener(ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Incomplete", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
