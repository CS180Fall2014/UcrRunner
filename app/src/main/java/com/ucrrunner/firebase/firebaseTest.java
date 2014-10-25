package com.ucrrunner.firebase;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class firebaseTest extends Activity {
    EditText userBox, passBox;
    Button registerButton, loginButton;
    String fireBaseURL = "https://torid-inferno-2246.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        Firebase.setAndroidContext(this);
        userBox = (EditText) findViewById(R.id.userBox);
        passBox = (EditText) findViewById(R.id.passBox);
        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase ref = new Firebase(fireBaseURL);
                String user = userBox.getText().toString();
                String pass = passBox.getText().toString();

                Log.d("debug", "Registering user: " + user + "pass: " + pass);

                ref.createUser(user, pass, new Firebase.ResultHandler(){
                   @Override
                    public void onSuccess(){
                       Log.d("debug", "Registration for  was successful");
                   }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Log.d("debug", "Registration for failed. \n" + firebaseError.getMessage());
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Firebase ref = new Firebase(fireBaseURL);

                String user = userBox.getText().toString();
                String pass = passBox.getText().toString();

                Log.d("debug", "Authenticating user: " + user + "pass: " + pass);
                ref.authWithPassword(user,pass, new Firebase.AuthResultHandler(){

                    @Override
                    public void onAuthenticated(AuthData authData) {
                       Log.d("debug", "Success");
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Log.d("debug", "Failed");
                    }
                });

            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.firebase_test, menu);
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



}
