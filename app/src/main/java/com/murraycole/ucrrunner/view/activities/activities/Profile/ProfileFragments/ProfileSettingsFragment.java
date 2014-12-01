package com.murraycole.ucrrunner.view.activities.activities.Profile.ProfileFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.backend.FirebaseManager;
import com.murraycole.ucrrunner.backend.SettingsManager;
import com.murraycole.ucrrunner.view.dialogfragments.HeightSettingDialogFragment;
import com.murraycole.ucrrunner.view.dialogfragments.SettingsDialogFragment;


public class ProfileSettingsFragment extends Fragment {
    EditText nicknameET, oldpasswordET, newpasswordET;
    TextView heightET, weightET;


    public ProfileSettingsFragment() {
        // Required empty public constructor
    }


    public static ProfileSettingsFragment newInstance(String param1, String param2) {
        ProfileSettingsFragment fragment = new ProfileSettingsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.activity_settings, container, false);
        heightET = (TextView) rootView.findViewById(R.id.settings_height_edittext);
        weightET = (TextView) rootView.findViewById(R.id.settings_weight_edittext);

        heightET.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showHeightDialog();
            }
        });
        weightET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenericDialog(R.string.weight_alert_dialog_title, "weight");
            }
        });

        On_Button_Press(rootView);
        return rootView;
    }


    //setting button Pressed
    private void On_Button_Press(View v) {
        final Button Settings = (Button) v.findViewById(R.id.settings_button);
        nicknameET = (EditText) v.findViewById(R.id.settings_nickname_edittext);
        oldpasswordET = (EditText) v.findViewById(R.id.settings_oldPassword_edittext);
        newpasswordET = (EditText) v.findViewById(R.id.settings_newPassword_edittext);
        weightET = (TextView) v.findViewById(R.id.settings_weight_edittext);
        heightET = (TextView) v.findViewById(R.id.settings_height_edittext);

        final String readUID = FirebaseManager.getCurrUID(getActivity().getApplicationContext());
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handles changes
                changeNickName(readUID, nicknameET.getText().toString(), v);
                changePassword(readUID, oldpasswordET.getText().toString(), newpasswordET.getText().toString(), v);
                changeWeight(readUID, weightET.getText().toString());
                changeHeight(readUID, heightET.getText().toString());

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ProfileFragment())
                        .commit();
            }
        });
    }




    public void showGenericDialog(int title, String type) {
        SettingsDialogFragment newFragment = SettingsDialogFragment.newInstance(title, type);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void showHeightDialog() {
        HeightSettingDialogFragment newFragment = HeightSettingDialogFragment.newInstance(R.string.height_alert_dialog_title);
        newFragment.show(getFragmentManager(), "dialog");
    }


    public void doPositiveClick(String text, String type) {
        switch (type) {
            case "height":
                heightET.setText(text);
                break;
            case "weight":
                weightET.setText(text);
                break;
        }
    }
    private void changeWeight(String uid, String weight){
        if (weight.isEmpty()){
           return;
        }
        SettingsManager.changeWeight(uid, new Double(weight));
    }


    private void changeHeight(String uid, String height){
        if (height.isEmpty()){
            return;
        }
        SettingsManager.changeHeight(uid,  parseHeight());
    }

    private void changeNickName(String uid, String nickname, View v){
        if (nickname.isEmpty()){
           return;
        }
        //if checkAvailable is false, then nickname is not taken
        if (FirebaseManager.checkAvailableNick(nickname)) {
            Toast.makeText(v.getContext(), "nickname is taken" , Toast.LENGTH_SHORT).show();
            //clear nickname
            nicknameET.setText("");
        }
        else{
            Toast.makeText(v.getContext(), "changing nickname " + nickname, Toast.LENGTH_SHORT).show();
            SettingsManager.changeNickname(uid, nickname);
        }
    }

    private void changePassword(String uid, String oldpassword, String newpassword, View v){
        if (newpassword.isEmpty() && oldpassword.isEmpty()){
           return;
        }
        if (oldpassword.isEmpty() && !newpassword.isEmpty()){
            Toast.makeText(v.getContext(), "Missing old password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!oldpassword.isEmpty() && newpassword.isEmpty()){
            Toast.makeText(v.getContext(), "Missing new password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!oldpassword.isEmpty() && !newpassword.isEmpty()){
            SettingsManager.changePassword(uid, oldpassword, newpassword, v);
        }
    }
    private Double parseHeight() {
        String replacement;
        String finalReplacement;
        String preParse = heightET.getText().toString();
        replacement = preParse.replace("'", ".");
        finalReplacement = replacement.replace("\"", "");

        return new Double(finalReplacement);
    }


    public void doNegativeClick() {

    }



}
