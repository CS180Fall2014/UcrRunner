package com.murraycole.ucrrunner.view.ProfileFragments.MessageFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndividualMessageFragment extends Fragment {
    private String messageSender;
    private String messageBody;

    private TextView fromTextView;
    private TextView bodyTextView;
    private Button replyButton;


    private IndividualMessageFragment() {
        // Required empty public constructor
    }

    public static IndividualMessageFragment newInstance(String messageSender, String messageBody) {
        Bundle args = new Bundle();
        args.putString("from", messageSender);
        args.putString("body", messageBody);
        IndividualMessageFragment newFrag = new IndividualMessageFragment();
        newFrag.setArguments(args);
        return newFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        messageSender = getArguments().getString("from");
        messageBody = getArguments().getString("body");

        View view = inflater.inflate(R.layout.fragment_individual_message, container, false);
        fromTextView = (TextView) view.findViewById(R.id.indv_msg_sender_textview);
        bodyTextView = (TextView) view.findViewById(R.id.indv_msg_content_textview);

        fromTextView.setText(messageSender);
        bodyTextView.setText(messageBody);
        return view;


    }


}
