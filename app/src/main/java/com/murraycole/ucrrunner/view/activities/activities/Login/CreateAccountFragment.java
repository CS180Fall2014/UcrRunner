package com.murraycole.ucrrunner.view.activities.activities.Login;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.dialogfragments.HeightDialogFragment;
import com.murraycole.ucrrunner.view.dialogfragments.NumberPickerDialogFragment;
import com.murraycole.ucrrunner.view.listeners.RegisterButtonListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccountFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public View regView;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAccountActivity.
     */


    Button join;
    TextView height, age, weight;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateAccountFragment newInstance(String param1, String param2) {
        CreateAccountFragment fragment = new CreateAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        regView = inflater.inflate(R.layout.fragment_create_account, container, false);
        join = (Button) regView.findViewById(R.id.createaccount_joinnow_button);
        height = (TextView) regView.findViewById(R.id.createaccount_height_edittext);
        age = (TextView) regView.findViewById(R.id.createaccount_age_edittext);
        weight = (TextView) regView.findViewById(R.id.createaccount_weight_edittext);

        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHeightDialog();
            }
        });
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenericDialog(R.string.age_alert_dialog_title, "age");
            }
        });
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenericDialog(R.string.weight_alert_dialog_title, "weight");
            }
        });
        setupLoginOnClick(join);
        return regView;


    }


    private void setupLoginOnClick(Button login) {
        RegisterButtonListener onclickRegButton = new RegisterButtonListener();
        login.setOnClickListener(onclickRegButton);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void showHeightDialog() {
        HeightDialogFragment newFragment = HeightDialogFragment.newInstance(R.string.height_alert_dialog_title);
        newFragment.show(getFragmentManager(), "dialog");
    }

    /**
     * @param title (string id of title)
     * @param type  (age or weight)
     */
    public void showGenericDialog(int title, String type) {
        NumberPickerDialogFragment newFragment = NumberPickerDialogFragment.newInstance(title, type);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void doPositiveClick(String text, String type) {
        switch (type) {
            case "height":
                height.setText(text);
                break;
            case "age":
                age.setText(text);
                break;
            case "weight":
                weight.setText(text);
                break;
        }

    }

    public void doNegativeClick() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
