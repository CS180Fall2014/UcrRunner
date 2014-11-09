package com.murraycole.ucrrunner.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.murraycole.ucrrunner.R;

/**
 * Created by dennisnguyen on 11/5/14.
 */
public class LoginButtonListener extends LoginActivity.LoginFragment implements View.OnClickListener {
    final String tag = "MT";
    Firebase ref = new Firebase("https://torid-inferno-2246.firebaseio.com/");

    EditText userET, passET;


    @Override
    public void onClick(View view) {
        mView = view.getRootView();
        Log.d(tag, "Got view." + mView.toString());
        userET = (EditText) mView.findViewById(R.id.login_username_edittext);
        passET = (EditText) mView.findViewById(R.id.login_password_edittext);
        Log.d(tag, "ETs are instantiated.");
        Log.i("LoginButtonListener", "Logging in with credentials: [" + userET.getText().toString() + ", " + passET.getText().toString() + "]");
        ref.authWithPassword(userET.getText().toString(), passET.getText().toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.i("LoginButtonListener", "Authentication successful. " + authData.getUid() + " | " + authData.getProvider());


                //forward to next page
                Intent intent = new Intent(mView.getContext(), Profile.class);
                intent.putExtra("userData.username", userET.getText().toString());
                intent.putExtra("userData.uid", authData.getUid());
                mView.getContext().startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.i("LoginButtonListener", "User authentication failed.");
                Log.e("LoginButtonListener", firebaseError.getMessage());

                //pop up message
                String fbLoginErrorMsg = firebaseError.getMessage();
                new AlertDialog.Builder(mView.getContext())
                        .setTitle("Login Error")
                        .setMessage(fbLoginErrorMsg)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //do nothing at all
                                Log.i("LoginbuttonListener", "Dialog okay clicked.");
                                return;
                            }
                        })
                        .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.i("LoginButtonListener", "Dialog retry clicked.");
                                //Reset fields
                                userET.setText("");
                                passET.setText("");
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        });
    }

}

