package com.murraycole.ucrrunner.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.controller.Map.MapInformation;
import com.murraycole.ucrrunner.view.DAO.Route;
import com.murraycole.ucrrunner.view.dialogfragments.RerunDialogFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by C on 11/20/2014.
 */
public class PrevRouteAdapter extends ArrayAdapter<Route> {
    Context mContext;
    ArrayList<Route> routes;

    public PrevRouteAdapter(Context context, ArrayList<Route> routes) {
        super(context, 0, routes);
        mContext = context;
        this.routes = routes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get route
        final Route route = routes.get(position);
        View rowView = convertView;

        //check if existing view is being reused, otherwise inflate

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_route, parent, false);

            TextView routeDate = (TextView) rowView.findViewById(R.id.route_item_date_textview);
            TextView routeDist = (TextView) rowView.findViewById(R.id.route_item_dist_textview);
            ImageView routeImg = (ImageView) rowView.findViewById(R.id.route_item_map_imageview);

            /* RouteImgBytes is currently returning null
               When it starts to work, uncomment the below
               //TODO
             */
           // Bitmap routeImgBitmap = MapInformation.byteArrayToBitmap(routeImgBytes);
          //  routeImg.setImageBitmap(routeImgBitmap);

            DecimalFormat valuesRounded = new DecimalFormat("#.##");



            routeDist.setText(String.valueOf(valuesRounded.format(routes.get(position).getCurrentStats().getDistance())) + " m.");
            String dateFromRoute = routes.get(position).getCurrentStats().getDate();
            /* This is returning null */
            routeDate.setText(dateFromRoute);

            String title = routes.get(position).getId();

            Log.d("MT", "PrevRouteAdapter got title (routeId) : " + title);

            String imageArr = FirebaseManager.getImage(title);
            Log.d("MTRAW:", imageArr);
            int firstOccurance = imageArr.indexOf(':');
            String formatedArr = imageArr.substring(firstOccurance+1, imageArr.length()-1);
            Log.d("MTRAW:", "substrung");
            Log.d("Received Image ", formatedArr);


            //routeImg.setImageBitmap(image);
            /**Bitmap b = MapInformation.stringToBitmap(formatedArr);
            routeImg.setImageBitmap(b);
**/
            routeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RerunDialogFragment rerunDialogFragment = RerunDialogFragment.newInstance(
                            R.string.rerun_route_dialog_title);
                    rerunDialogFragment.setRoute(route);
                    rerunDialogFragment.show(((Activity) mContext).getFragmentManager(), "dialog");
                }
            });




        return rowView;
    }
}
