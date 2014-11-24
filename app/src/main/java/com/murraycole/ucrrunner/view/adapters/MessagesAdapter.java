package com.murraycole.ucrrunner.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.ProfileFragments.MessageFragments.IndividualMessageFragment;

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
            final TextView fromField = (TextView) view.findViewById(R.id.message_from_textview);
            final TextView content = (TextView) view.findViewById(R.id.message_content_textview);

            fromField.setText("Mark Wilson");
            content.setText("This is my message, and I'm exclaiming it to the world! Hear me roar" +
                    "and triumph");

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IndividualMessageFragment msgFrag = IndividualMessageFragment.
                            newInstance(fromField.getText().toString(), content.getText().toString());
                    ((Activity) getContext()).getFragmentManager().beginTransaction()
                            .replace(R.id.container, msgFrag).addToBackStack(null).commit();
                }
            });

        }
        return view;

    }
}
