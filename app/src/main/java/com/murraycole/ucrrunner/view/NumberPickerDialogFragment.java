package com.murraycole.ucrrunner.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.murraycole.ucrrunner.R;

/**
 * Created by C on 11/19/2014.
 */
public class NumberPickerDialogFragment extends DialogFragment {

    public static NumberPickerDialogFragment newInstance(int title, String type) {
        NumberPickerDialogFragment numberPickerDialogFragment = new NumberPickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putString("type", type);
        numberPickerDialogFragment.setArguments(args);
        return numberPickerDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        final String type = getArguments().getString("type");
        View view = getActivity().getLayoutInflater().inflate(
                R.layout.numberpicker_dialog_fragment, null);
        final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.generic_numberpicker);
        setupNumberPicker(numberPicker,type);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((CreateAccountFragment) getFragmentManager().
                                findFragmentById(R.id.container))
                                .doPositiveClick(String.valueOf(numberPicker.getValue()),type);
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((CreateAccountFragment) getFragmentManager().
                                findFragmentById(R.id.container)).doNegativeClick();
                    }
                })
                .create();
        return dialog;
    }

    private void setupNumberPicker(NumberPicker numberPicker,String type){
        switch (type){
            case "age" :
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(120);
                numberPicker.setValue(25); // average age of smartphone user
                break;

            case "weight" :
                numberPicker.setMinValue(25);
                numberPicker.setMaxValue(300);
                numberPicker.setValue(150);
                break;
            default:
                break;

        }
    }




}
