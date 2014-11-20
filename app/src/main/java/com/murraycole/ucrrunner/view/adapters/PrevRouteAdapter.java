package com.murraycole.ucrrunner.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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

        //check if existing view is being reused, otherwise inflate
        if (convertView == null){
            //inflate layout of individual listItem.xml
            //LayoutInflater.from(getContext()).inflate(LAYOUTID,parent,false);
        }

        return convertView;
    }
}
