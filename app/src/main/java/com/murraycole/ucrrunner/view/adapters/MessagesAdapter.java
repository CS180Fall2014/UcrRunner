package com.murraycole.ucrrunner.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;

import java.util.ArrayList;

/**
 * Created by C on 11/22/2014.
 */
public class MessagesAdapter extends ArrayAdapter {
    public MessagesAdapter(Context context, ArrayList resource) {
        super(context,0, resource);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.message_list_item,parent,false);
            TextView fromField = (TextView) view.findViewById(R.id.message_from_textview);
            TextView content = (TextView) view.findViewById(R.id.message_content_textview);

            fromField.setText("Mark Wilson");
            content.setText("This is my message, and I'm exclaiming it to the world! Hear me roar" +
                    "and triumph");

        }
        return view;

    }
}
