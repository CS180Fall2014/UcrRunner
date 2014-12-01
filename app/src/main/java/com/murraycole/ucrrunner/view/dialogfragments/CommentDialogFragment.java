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
import com.murraycole.ucrrunner.view.DAO.Post;
import com.murraycole.ucrrunner.view.DAO.Route;
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.NewsFeedFragments.IndivNewsFeedFragment;
import com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments.NewsFeedFragments.NewsFeedFragment;

import fbutil.firebase4j.service.Firebase;

/**
 * Created by dasu on 11/30/14.
 */
public class CommentDialogFragment extends DialogFragment {
    public static String LOG_TAG = CommentDialogFragment.class.getSimpleName();
    public Post post;

    public static CommentDialogFragment newInstance(int title, Post post) {
        Bundle args = new Bundle();
        args.putInt("Title", title);

        CommentDialogFragment commentDialogFragment = new CommentDialogFragment();
        commentDialogFragment.post = post;
        Log.d(LOG_TAG,"POST ID:" + post.getPostID());
        commentDialogFragment.setArguments(args);
        return commentDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("Title");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment_fragment, null);
        final EditText msgContentET = (EditText) view.findViewById(R.id.comment_dialog_content_edittext);


        AlertDialog alertDialog = new AlertDialog.Builder
                (getActivity())
                .setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //sendMsg To wherever
                        Log.d(LOG_TAG,post.getAuthorUID() +" : " + post.getPostID());
                        String msg = msgContentET.getText().toString();
                        if (postComment(msg)){
                            Toast.makeText(getActivity(), "Sent", Toast.LENGTH_LONG).show();
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

    public boolean postComment(String message)
    {
        String myUID = SharedPrefUtils.getCurrUID(getActivity());
        boolean statement  = FirebaseManager.addComment(myUID,post.getAuthorUID(),post.getPostID(),message);
        NewsFeedFragment fragment = (NewsFeedFragment)getFragmentManager().findFragmentById(R.id.container);
        fragment.clearAdapter();
        FirebaseManager.getPostsForFriends(SharedPrefUtils.getCurrUID(getActivity()), ((NewsFeedFragment)getFragmentManager().findFragmentById(R.id.container)));
        //getActivity().notify();
        return statement;



    }
}
