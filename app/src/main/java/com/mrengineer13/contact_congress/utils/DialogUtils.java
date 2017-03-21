package com.mrengineer13.contact_congress.utils;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.InputType;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mrengineer13.contact_congress.ContactCongress;
import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.dialogs.ProgressDialogFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jon on 6/30/16.
 */

public class DialogUtils {

    public static void showLoadingDialog(FragmentManager manager) {
        if (manager == null) {
            return;
        }
        Fragment dialog = manager.findFragmentByTag(ProgressDialogFragment.TAG);
        if (dialog == null) {
            DialogFragment dialogFragment = new ProgressDialogFragment();
            dialogFragment.show(manager, ProgressDialogFragment.TAG);
        }
    }

    public static void hideLoadingDialog(FragmentManager manager) {
        if (manager == null) {
            return;
        }
        Fragment dialog = manager.findFragmentByTag(ProgressDialogFragment.TAG);
        if (dialog != null) {
            manager.beginTransaction().remove(dialog).commit();
        }
    }

    public static void showNetworkDialog(Activity activity) {
        showOkDialog(activity, R.string.network_error, R.string.network_error_msg);
    }

    public static void showServerDialog(Activity activity) {
        showOkDialog(activity, R.string.server_error, R.string.retry);
    }

    public static void showNoCallDialog(Activity activity) {
        showOkDialog(activity, R.string.no_call, R.string.no_call_msg);
    }

    public static MaterialDialog showCallLegislatorDialog(Activity activity, String s) {
        String content = String.format("Calling: %s", s);
        return showOkDialog(activity, null, content);
    }

    private static void showOkDialog(Activity activity, @StringRes int titleId, @StringRes int contentId) {
        new MaterialDialog.Builder(activity)
                .title(titleId)
                .content(contentId)
                .neutralText(android.R.string.ok)
                .show();
    }

    private static MaterialDialog showOkDialog(Activity activity, String title, String content) {
        return new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .neutralText(android.R.string.ok)
                .show();
    }

    public static void showZipCodeErrorDialog(Activity activity) {
        showZipDialog(activity, "Error getting location.", "Please enter your postal code");
    }

    public static void showZipCodeDialog(Activity activity) {
        showZipDialog(activity, "Enter postal code Manually.", "Please enter your postal code");
    }

    private static void showZipDialog(Activity activity, String title, String content) {
        final Prefs prefs = ((ContactCongress) activity.getApplication()).getComponent().prefs();
        new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .alwaysCallInputCallback()
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .inputRangeRes(5, 5, android.R.color.holo_red_light)
                .autoDismiss(false)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            String zip = dialog.getInputEditText().getText().toString();
                            prefs.saveZip(zip);
                            dialog.dismiss();
                        }
                    }
                })
                .input("", prefs.getZip(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        Pattern mPattern = Pattern.compile("\\d{5}?");
                        Matcher matcher = mPattern.matcher(input.toString());
                        if (matcher.matches()) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        }
                    }
                }).show();
    }

}
