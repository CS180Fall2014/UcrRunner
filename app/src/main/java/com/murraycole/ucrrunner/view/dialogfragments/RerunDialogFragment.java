package com.murraycole.ucrrunner.view.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.murraycole.ucrrunner.R;
import com.murraycole.ucrrunner.view.DAO.Route;

/**
 * Created by C on 11/22/2014.
 */
public class RerunDialogFragment extends DialogFragment {
    Route route;

    public static RerunDialogFragment newInstance(int title) {
        RerunDialogFragment rerunDialogFragment = new RerunDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("title", title);
        rerunDialogFragment.setArguments(bundle);
        return rerunDialogFragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        AlertDialog ad = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "Rerun" + route.getCurrentStats().getImageRef(), Toast.LENGTH_SHORT).show();
                        //TODO FOR UI!! STOPSHIP

                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do something negative
                    }
                })
                .create();
        return ad;

    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
