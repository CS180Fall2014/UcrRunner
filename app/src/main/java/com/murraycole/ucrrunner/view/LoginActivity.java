package com.murraycole.ucrrunner.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.murraycole.ucrrunner.R;

import com.murraycole.ucrrunner.view.Profile;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
        Firebase.setAndroidContext(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class LoginFragment extends Fragment {
        static public Button loginButton, registerButton;
        EditText userET, passET;

        public LoginFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.login_fragment, container, false);

            userET = (EditText) rootView.findViewById(R.id.login_username_edittext);
            passET = (EditText) rootView.findViewById(R.id.login_password_edittext);

            loginButton = (Button) rootView.findViewById(R.id.login_login_button);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Firebase ref = new Firebase("https://torid-inferno-2246.firebaseio.com/");
                    ref.setAndroidContext(getActivity());
                    ref.authWithPassword(userET.getText().toString(), passET.getText().toString(), new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                            //forward to next page
                            Intent intent = new Intent( getActivity(), Profile.class);
                            intent.putExtra("userData.username", userET.getText().toString());
                            intent.putExtra("userData.uid", authData.getUid());

                            //ActivityOptions options = ActivityOptions.makeScaleUpAnimation(this, androidRobotView, "robot");
                            // start the new activity
                            startActivity(intent);

                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            //pop up message
                            String fbLoginError = firebaseError.getMessage();

                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Login Error")
                                    .setMessage(fbLoginError)
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //do nothing at all
                                            return;
                                        }
                                    })
                                    .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //Reset fields
                                            userET.setText("");
                                            passET.setText("");
                                        }
                                    }).setIcon(android.R.drawable.ic_dialog_alert).show();


                        }
                    });
                }
            });

            registerButton = (Button) rootView.findViewById(R.id.login_register_button);
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return rootView;
        }
    }
}
