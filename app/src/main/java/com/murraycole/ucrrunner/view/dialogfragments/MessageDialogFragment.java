package com.murraycole.ucrrunner.view.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.Utils.SharedPrefUtils;
import com.murraycole.ucrrunner.backend.FirebaseManager;

/**
 * Created by User on 11/23/14.
 */
public class MessageDialogFragment extends DialogFragment {
    public static String LOG_TAG = MessageDialogFragment.class.getSimpleName();

    public static MessageDialogFragment newInstance(int title) {
        Bundle args = new Bundle();
        args.putInt("title", title);
        MessageDialogFragment messageDialogFragment = new MessageDialogFragment();
        messageDialogFragment.setArguments(args);
        return messageDialogFragment;
    }

    public static MessageDialogFragment newInstance(int title, String toNickname) {
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putString("recipent", toNickname);
        MessageDialogFragment messageDialogFragment = new MessageDialogFragment();
        messageDialogFragment.setArguments(args);
        return messageDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        String recipent = getArguments().getString("recipent", "-1");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_message_fragment, null);
        final EditText recipientET = (EditText) view.findViewById(R.id.message_dialog_recipient_edittext);
        final EditText msgContentET = (EditText) view.findViewById(R.id.message_dialog_content_edittext);

        if (!recipent.equals("-1")) {
            recipientET.setText(recipent);
        }


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
                        sendMessageToFirebase(recipient, msg);
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

    private void sendMessageToFirebase(String recipent, String message) {
        String friendUID = String.valueOf(FirebaseManager.getUID(recipent));
        String myUID = SharedPrefUtils.getCurrUID(getActivity());
        Log.d(LOG_TAG, "UID is:" + friendUID);
        Log.d(LOG_TAG, "MyUID is: " + myUID);


        FirebaseManager.sendMessage(myUID, friendUID, message);
    }
}
