package com.murraycole.ucrrunner.view.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.Post;

import java.util.ArrayList;

/**
 * Created by dasu on 11/30/14.
 */
public class CommentAdapter extends ArrayAdapter {
    ArrayList<Pair<String,String>> comments;


    public CommentAdapter(Context context, ArrayList<Pair<String,String>> resource) {
        super(context, 0, resource);
        comments = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_comment, parent, false);
            final TextView userName = (TextView)view.findViewById(R.id.comment_name_textView);
            final TextView userComment = (TextView)view.findViewById(R.id.comment_textView);


            String name = comments.get(position).first;
            String comment = comments.get(position).second;

            userName.setText(name);
            userComment.setText(comment);

        }
        return view;

    }
}
