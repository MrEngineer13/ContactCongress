package com.mrengineer13.contact_congress.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.mrengineer13.contact_congress.R;


/**
 * Created by Jon on 3/11/17.
 */

public class ProgressDialogFragment extends DialogFragment {
    public static final String TAG = "loading_dialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new android.app.ProgressDialog(getActivity(), getTheme());
        dialog.setMessage(getString(R.string.loading_message));
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        return dialog;
    }
}
