package com.murraycole.ucrrunner.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.Route;

import java.util.ArrayList;

/**
 * Created by C on 11/20/2014.
 */
public class PrevRouteAdapter extends ArrayAdapter<Route> {

    public PrevRouteAdapter(Context context, ArrayList<Route> routes){
        super(context,0,routes);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        //get route
        Route route = getItem(position);
        View rowView = convertView;

        //check if existing view is being reused, otherwise inflate
        if (rowView == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.route_list_item,parent,false);

            TextView routeDate = (TextView) rowView.findViewById(R.id.route_item_date_textview);
            TextView routeDist = (TextView) rowView.findViewById(R.id.route_item_dist_textview);
            ImageView routeImg = (ImageView) rowView.findViewById(R.id.route_item_map_imageview);


        }

        return rowView;
    }
}
