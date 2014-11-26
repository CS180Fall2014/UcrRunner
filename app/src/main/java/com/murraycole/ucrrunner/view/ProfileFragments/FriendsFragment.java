package com.murraycole.ucrrunner.view.ProfileFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.Utils.SharedPrefUtils;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.User;
import com.murraycole.ucrrunner.view.adapters.FriendsAdapter;
import com.murraycole.ucrrunner.view.dialogfragments.MessageDialogFragment;
import com.murraycole.ucrrunner.view.interfaces.ArrayUpdateListener;

import java.util.ArrayList;

/* Keshav */

public class FriendsFragment extends Fragment implements ArrayUpdateListener{

    FriendsAdapter mAdapter;
    ArrayList<User> friendList;
    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        FirebaseManager.getFriends("98",this);
        friendList = new ArrayList<User>();
        mAdapter = new FriendsAdapter(getActivity(),friendList);
        Log.d("MT", "Oncreateview");
        ListView listView = (ListView) rootView.findViewById(R.id.friends_listview);
        listView.setAdapter(mAdapter);
        setupListViewOnItemClick(listView);
        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void update(Object o) {
        Log.d("MT", "FriendsFragment updates: " + ((User) o).getNickname());
        friendList.add((User) o);
        mAdapter.notifyDataSetChanged();
    }

    private void setupListViewOnItemClick(ListView listview){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView fromET = (TextView) view.findViewById(R.id.friend_contact_name);
                MessageDialogFragment messageDialogFragment =
                        MessageDialogFragment.newInstance(
                                R.string.message_dialog_title,
                                fromET.getText().toString());
                messageDialogFragment.show(getFragmentManager(),"dialog");
            }
        });
    }
}
