package com.murraycole.ucrrunner.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
    String genderValue;
    Firebase ref = new Firebase("https://torid-inferno-2246.firebaseio.com/users");

    private boolean validateFields(View view){
        nickET = (EditText) regView.findViewById(R.id.createaccount_nickname_edittext);
        weightET = (EditText) regView.findViewById(R.id.createaccount_weight_edittext);
        heightET = (EditText) regView.findViewById(R.id.createaccount_height_edittext);

        int validateFlag = 0;
        if(nickET.getText().toString().isEmpty() || nickET.getText().toString().matches("")){
            Toast nickToast = Toast.makeText(view.getContext(), "Missing Nickname", Toast.LENGTH_LONG);
            nickToast.show();
            validateFlag+=1;
        }

        if(weightET.getText().toString().isEmpty() || weightET.getText().toString().matches("")){
            Toast weightToast = Toast.makeText(view.getContext(), "Missing weight", Toast.LENGTH_LONG);
            weightToast.show();
            validateFlag+=1;
        }
        if(heightET.getText().toString().isEmpty() || heightET.getText().toString().matches("")){
            Toast heightToast = Toast.makeText(view.getContext(), "Missing height", Toast.LENGTH_LONG);
            heightToast.show();
            validateFlag+=1;
        }
        String genderString = getGender(view);
        if (genderString.matches("")) {
            Toast genderToast = Toast.makeText(view.getContext(), "Missing gender", Toast.LENGTH_LONG);
            genderToast.show();
            validateFlag+=1;
        }

        return (validateFlag == 0 ? true : false);
    }
    private String getGender(View view){
        RadioGroup rgGender = (RadioGroup) regView.findViewById(R.id.gender_group);
        String selection = "";
        if(rgGender.getCheckedRadioButtonId()!=-1){
            int id= rgGender.getCheckedRadioButtonId();
            View radioButton = rgGender.findViewById(id);
            int radioId = rgGender.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) rgGender.getChildAt(radioId);
            selection = (String) btn.getText();
            Log.d("sex", selection);
        }
        return selection;
    }



    @Override
    public void onClick(View view) {
        regView = view.getRootView();
        userET = (EditText) regView.findViewById(R.id.createaccount_username_edittext);
        passET = (EditText) regView.findViewById(R.id.createaccount_password_edittext);
        nickET = (EditText) regView.findViewById(R.id.createaccount_nickname_edittext);
        weightET = (EditText) regView.findViewById(R.id.createaccount_weight_edittext);
        heightET = (EditText) regView.findViewById(R.id.createaccount_height_edittext);


        if (validateFields(view) == false){
            //invalid fields
           return;
        }

        genderValue = getGender(view);

        Log.d(tag, "Got view." + regView.toString());
        Log.i("LoginButtonListener", "Logging in with credentials: [" + userET.getText().toString() + ", " + passET.getText().toString() + "]");


        //attempts to create user into firebase
        ref.createUser(userET.getText().toString(), passET.getText().toString(), new Firebase.ResultHandler() {
            //if username and passwords are valid, store user registration info to firebase route
            @Override
            public void onSuccess() {
                Intent intent = new Intent(regView.getContext(), LoginActivity.class);
                regView.getContext().startActivity(intent);

                ref.authWithPassword(userET.getText().toString(), passET.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Log.i("Authenticate Registration Info", authData.getUid());
                        String uid = authData.getUid().split(":")[1];

                        User regInfo = new User();
                        regInfo.setNickname(nickET.getText().toString());
                        regInfo.setEmail(userET.getText().toString());
                        regInfo.setWeight(new Double(weightET.getText().toString()));
                        regInfo.setSex(genderValue);
                        regInfo.setAge(22);
                        regInfo.setHeight(new Double(heightET.getText().toString()));
                        regInfo.setNickname(nickET.getText().toString());

                        FirebaseManager.saveUser(regInfo, uid);

                        Toast.makeText(regView.getContext(),"Success",Toast.LENGTH_SHORT).show();
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








