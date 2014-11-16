package com.murraycole.ucrrunner.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
        final Context myContext = getActivity();
        Log.i("LoginButtonListener", "Logging in with credentials: [" + userET.getText().toString() + ", " + passET.getText().toString() + "]");
        ref.authWithPassword(userET.getText().toString(), passET.getText().toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.i("LoginButtonListener", "Authentication successful. " + authData.getUid() + " | " + authData.getProvider());
                //add auth data to SharedPreferences
                SharedPreferences fbPrefs = mView.getContext().getSharedPreferences("com.firebase.test", Context.MODE_PRIVATE) ;
                SharedPreferences.Editor fbPrefEditor = fbPrefs.edit();
                Log.d("MT", "TRying to save " + userET.getText().toString());
                fbPrefEditor.putString("userData.uid", userET.getText().toString());
                fbPrefEditor.putString("userData.username", userET.getText().toString());
                fbPrefEditor.apply();
                //preferably also do a getUser and then populate those fields.

                Intent intent = new Intent(mView.getContext(), Profile.class);
                mView.getContext().startActivity(intent);
                ((Activity) mView.getContext()).overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
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

