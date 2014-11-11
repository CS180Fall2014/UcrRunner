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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dennisnguyen on 11/5/14.
 */

public class RegisterButtonListener extends CreateAccountFragment implements View.OnClickListener {
    final String tag = "MT";
    EditText userET, passET, nickET, heightET, weightET;
    Firebase ref = new Firebase("https://torid-inferno-2246.firebaseio.com/");

    @Override
    public void onClick(View view) {
        regView = view.getRootView();
        userET = (EditText) regView.findViewById(R.id.createaccount_username_edittext);
        passET = (EditText) regView.findViewById(R.id.createaccount_password_edittext);
        nickET = (EditText) regView.findViewById(R.id.createaccount_nickname_edittext);
        weightET = (EditText) regView.findViewById(R.id.createaccount_weight_edittext);
        heightET = (EditText) regView.findViewById(R.id.createaccount_height_edittext);

        Log.d(tag, "Got view." + regView.toString());

        Log.i("LoginButtonListener", "Logging in with credentials: [" + userET.getText().toString() + ", " + passET.getText().toString() + "]");

        ref.createUser(userET.getText().toString(), passET.getText().toString(), new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(regView.getContext(), LoginActivity.class);
                regView.getContext().startActivity(intent);

                //weird shit
                ref.authWithPassword(userET.getText().toString(), passET.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {

                        Log.i("Authenticate Registration Info", authData.getUid());

                        Firebase regRef = ref.child("users");
                        Map <String,String> regInfo = new HashMap<String, String>();
                        regInfo.put("Nickname", nickET.getText().toString());

                        regInfo.put("Email", userET.getText().toString());
                        regInfo.put("Weight", weightET.getText().toString());
                        regInfo.put("Height", heightET.getText().toString());

                        Map<String, Map<String, String>> users = new HashMap<String, Map<String, String>>();
                        users.put(nickET.getText().toString(), regInfo);
                        regRef.setValue(users);
                        Log.i("Pushed", "");
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        //should never error?
                        return;
                    }
                });
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Log.i("LoginButtonListener", "User authentication failed.");
                Log.e("LoginButtonListener", firebaseError.getMessage());

                //pop up message
                String fbLoginErrorMsg = firebaseError.getMessage();
                new AlertDialog.Builder(regView.getContext())
                        .setTitle("Registration Error")
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

    };
}








