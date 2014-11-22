package com.murraycole.ucrrunner.view.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.CreateAccountFragment;

/**
 * Created by C on 11/18/2014.
 */
public class HeightDialogFragment extends DialogFragment {
    public static HeightDialogFragment newInstance(int title) {
        HeightDialogFragment hfrag = new HeightDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        hfrag.setArguments(args);
        return hfrag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        //Get layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.height_dialog_fragment, null);
        final NumberPicker feet = (NumberPicker) view.findViewById(R.id.numberPicker);
        final NumberPicker inches = (NumberPicker) view.findViewById(R.id.numberPicker2);
        setFeetMinMaxValue(feet);
        setInchesMinMaxValue(inches);
        AlertDialog ad = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_launcher)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((CreateAccountFragment)getFragmentManager().findFragmentById(R.id.container)).doPositiveClick(getValueHeightandFeet(feet,inches),"height");
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                CreateAccountFragment.newInstance(null,null).doNegativeClick();

                            }
                        }
                )

                .create();
        return ad;

    }

    private void setFeetMinMaxValue(NumberPicker numberPicker){
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(7);
        numberPicker.setValue(5);
    }
    private void setInchesMinMaxValue (NumberPicker numberPicker){
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(11);
        numberPicker.setValue(7);
    }

    private String getValueHeightandFeet(NumberPicker feet, NumberPicker inches){
        String output;

        output = feet.getValue() + "'" + inches.getValue() + "\"" ;
        return output;

    }
}


