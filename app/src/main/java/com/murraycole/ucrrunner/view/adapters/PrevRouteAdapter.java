package com.murraycole.ucrrunner.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.Model.Route;
import com.murraycole.ucrrunner.view.dialogfragments.RerunDialogFragment;

import java.util.ArrayList;

/**
 * Created by C on 11/20/2014.
 */
public class PrevRouteAdapter extends ArrayAdapter<Route> {
    Context mContext;
    ArrayList<Route> routes;

    public PrevRouteAdapter(Context context, ArrayList<Route> routes){
        super(context,0,routes);
        mContext = context;
        this.routes = routes;
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


            //

            routeDist.setText(new Double(routes.get(position).getCurrentStats().getDistance()).toString());



            routeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RerunDialogFragment rerunDialogFragment = RerunDialogFragment.newInstance(
                            R.string.rerun_route_dialog_title);
                    rerunDialogFragment.show(((Activity) mContext).getFragmentManager(), "dialog");
                }
            });


        }

        return rowView;
    }
}
