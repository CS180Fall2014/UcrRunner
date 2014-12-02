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
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.FriendsFragment;

/**
 * Created by dasu on 11/29/14.
 */
public class AddFriendDialogFragment extends DialogFragment {
    public static String LOG_TAG = AddFriendDialogFragment.class.getSimpleName();

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
                        if (addFriendToFirebase(recipient, msg)){
                            Toast.makeText(getActivity(), "Sent", Toast.LENGTH_LONG).show();
                            FriendsFragment friendsFragment = (FriendsFragment) getFragmentManager()
                                                                .findFragmentById(R.id.container);
                            friendsFragment.clearAdapter();
                            FirebaseManager.getFriends(SharedPrefUtils.getCurrUID(getActivity()), friendsFragment);


                        }else{
                            Toast.makeText(getActivity(),"Invalid User",Toast.LENGTH_LONG).show();
                        }
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

    private boolean addFriendToFirebase(String recipient, String message) {
        String UID = String.valueOf(FirebaseManager.getUID(recipient));
        String myUID = SharedPrefUtils.getCurrUID(getActivity());
        Log.d(LOG_TAG, "UID is:" + UID);
        Log.d(LOG_TAG, "MyUID is: " + myUID);


        return FirebaseManager.addFriend(myUID, recipient);
        //if this returns false throw error message that it failed
    }
}
