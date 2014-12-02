package com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.Utils.SharedPrefUtils;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.User;
import com.murraycole.ucrrunner.view.adapters.FriendsAdapter;
import com.murraycole.ucrrunner.view.dialogfragments.AddFriendDialogFragment;
import com.murraycole.ucrrunner.view.dialogfragments.MessageDialogFragment;
import com.murraycole.ucrrunner.view.interfaces.ArrayUpdateListener;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

/* Keshav */

public class FriendsFragment extends Fragment implements ArrayUpdateListener {

    FriendsAdapter mAdapter;
    ArrayList<User> friendList;
    CircleButton newFriendButton;
    ListView listView;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        //Async call to get usersFriends from firebase
        FirebaseManager.getFriends(SharedPrefUtils.getCurrUID(getActivity()), this);
        initializeMemberVariables(rootView);
        listView.setAdapter(mAdapter);


        setupListViewOnItemClick(listView);
        setupNewFriendButton(newFriendButton);

        return rootView;
    }


    @Override
    public void update(Object o) {
        Log.d("MT", "FriendsFragment updates: " + ((User) o).getNickname());
        friendList.add((User) o);
        mAdapter.notifyDataSetChanged();
    }

    private void initializeMemberVariables(View rootView) {
        friendList = new ArrayList<User>();
        mAdapter = new FriendsAdapter(getActivity(), friendList);
        listView = (ListView) rootView.findViewById(R.id.friends_listview);
        newFriendButton = (CircleButton) rootView.findViewById(R.id.friends_newfriend_button);
    }

    private void setupListViewOnItemClick(ListView listview) {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView fromET = (TextView) view.findViewById(R.id.friend_contact_name);
                MessageDialogFragment messageDialogFragment =
                        MessageDialogFragment.newInstance(
                                R.string.message_dialog_title,
                                fromET.getText().toString());
                messageDialogFragment.show(getFragmentManager(), "dialog");
            }
        });
    }

    private void setupNewFriendButton(CircleButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call dialogFragment to sendFriendRequest
                AddFriendDialogFragment addFriendDialogFragment =
                        AddFriendDialogFragment.newInstance(R.string.add_friend_dialog_title);
                addFriendDialogFragment.show(getFragmentManager(),"dialog");
                //Toast.makeText(getActivity(), "FriendDialogFragment", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clearAdapter(){
        friendList.clear();
        mAdapter.notifyDataSetChanged();
    }
}
