package com.murraycole.ucrrunner.view.ProfileFragments.MessageFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.Message;
import com.murraycole.ucrrunner.view.adapters.MessagesAdapter;
import com.murraycole.ucrrunner.view.interfaces.ArrayUpdateListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MailboxFragment extends Fragment implements ArrayUpdateListener{
    MessagesAdapter mAdapter;
    ArrayList<Message> mailbox;


    public MailboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mailbox, container, false);
        mailbox = new ArrayList<Message>();
        /*ArrayList<String> messages = new ArrayList<String>();
        messages.add("random");
        messages.add("fakeData");*/
        mAdapter = new MessagesAdapter(getActivity(),mailbox);
        FirebaseManager.getMessages("89",this);
        ListView listView = (ListView) rootView.findViewById(R.id.message_listview);
        listView.setAdapter(mAdapter);
        return rootView;
    }


    @Override
    public void update(Object o) {
        mailbox.add((Message) o);
        mAdapter.notifyDataSetChanged();
    }
}
