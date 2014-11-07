package com.murraycole.ucrrunner.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends Activity{
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
     * Fragment: LoginFragment
     * LoginFragment contains the view with user/pass text boxes with a Login and Register button.
     * This fragment transitions to profile on successful login or user registration screen.
     * On failure to authenticate user, a dialog will pop up.
     */
    public static class LoginFragment extends Fragment {
        Context mContext;
        View mView;

        EditText userET, passET;
        Button login, register;

        public LoginFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            mView = inflater.inflate(R.layout.login_fragment, container, false);
            mContext = mView.getContext();
            register = (Button) mView.findViewById(R.id.login_register_button);
            login = (Button) mView.findViewById(R.id.login_login_button);

            assignButtons(login);

            return mView;
        }

        private void assignButtons(Button login) {
            login.setOnClickListener(new LoginButtonListener());
            register.setOnClickListener(new LoginButtonListener());
        }
    }
}