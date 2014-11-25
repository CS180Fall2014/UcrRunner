package com.murraycole.ucrrunner.view.ProfileFragments.MessageFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.Message;
import com.murraycole.ucrrunner.view.adapters.MessagesAdapter;
import com.murraycole.ucrrunner.view.dialogfragments.MessageDialogFragment;
import com.murraycole.ucrrunner.view.interfaces.ArrayUpdateListener;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MailboxFragment extends Fragment implements ArrayUpdateListener{
    MessagesAdapter mAdapter;
    ArrayList<Message> mailbox;
    CircleButton composeNewButton;


    public MailboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mailbox, container, false);
        mailbox = new ArrayList<Message>();

        mAdapter = new MessagesAdapter(getActivity(),mailbox);
        // async updates messages using ArrayUpdateListner.update
        FirebaseManager.getMessages("89",this);
        ListView listView = (ListView) rootView.findViewById(R.id.message_listview);
        composeNewButton = (CircleButton) rootView.findViewById(R.id.message_compose_new_button);
        setupComposeNewOnClick(composeNewButton);
        listView.setClickable(true);
        listView.setAdapter(mAdapter);
        setupListViewOnItemClick(listView);

        return rootView;
    }


    @Override
    public void update(Object o) {
        mailbox.add((Message) o);
        mAdapter.notifyDataSetChanged();
    }

    private void setupListViewOnItemClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final TextView fromField = (TextView) view.findViewById(R.id.message_from_textview);
                final TextView content = (TextView) view.findViewById(R.id.message_content_textview);
                IndividualMessageFragment msgFrag = IndividualMessageFragment.
                        newInstance(fromField.getText().toString(), content.getText().toString());
                (getActivity()).getFragmentManager().beginTransaction()
                        .replace(R.id.container, msgFrag).addToBackStack(null).commit();
            }
        });
    }

    private void setupComposeNewOnClick(CircleButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialogFragment mdf = MessageDialogFragment.newInstance(R.string.message_dialog_title);
                mdf.show(getFragmentManager(), "messageDialog");
            }
        });
    }
}
