package com.murraycole.ucrrunner.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.view.DAO.Message;
import com.murraycole.ucrrunner.view.dialogfragments.MessageDialogFragment;
import com.murraycole.ucrrunner.view.ProfileFragments.MessageFragments.IndividualMessageFragment;

import java.util.ArrayList;

/**
 * Created by C on 11/22/2014.
 */
public class MessagesAdapter extends ArrayAdapter {
    ArrayList<Message> mailbox;
    public MessagesAdapter(Context context, ArrayList resource) {
        super(context,0, resource);
        mailbox = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.message_list_item,parent,false);
            final TextView fromField = (TextView) view.findViewById(R.id.message_from_textview);
            final TextView content = (TextView) view.findViewById(R.id.message_content_textview);

            String fromNickname = FirebaseManager.getNickname(new Integer(mailbox.get(position).getFrom()).toString());

            fromField.setText(fromNickname);
            content.setText(mailbox.get(position).getMessage());

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
