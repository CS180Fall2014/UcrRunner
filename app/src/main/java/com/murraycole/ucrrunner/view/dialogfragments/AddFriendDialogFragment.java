package com.murraycole.ucrrunner.view.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.murraycole.ucrrunner.R;

/**
 * Created by dasu on 11/29/14.
 */
public class AddFriendDialogFragment extends DialogFragment {

    public static AddFriendDialogFragment newInstance(int title) {
        Bundle args = new Bundle();
        args.putInt("title", title);
        AddFriendDialogFragment addFriendDialogFragment = new AddFriendDialogFragment();
        addFriendDialogFragment.setArguments(args);
        return addFriendDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_friend_fragment, null);
        final EditText recipientET = (EditText) view.findViewById(R.id.add_friend_dialog_recipient_edittext);
        final EditText msgContentET = (EditText) view.findViewById(R.id.add_friend_dialog_content_edittext);


        AlertDialog alertDialog = new AlertDialog.Builder
                (getActivity())
                .setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //sendMsg To wherever
                        String recipient = recipientET.getText().toString();
                        String msg = msgContentET.getText().toString();
                        //sendMessageToFirebase(recipient, msg);
                        Toast.makeText(getActivity(), "Sent", Toast.LENGTH_LONG).show();
                        // sendMsg(recipient,msg);
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                    }
                })
                .create();

        return alertDialog;

    }


}
