package com.mrengineer13.contact_congress.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mrengineer13.contact_congress.ContactCongress;
import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.base.RxSettingsFragment;
import com.mrengineer13.contact_congress.data.api.ApiService;
import com.mrengineer13.contact_congress.utils.Keys;
import com.mrengineer13.contact_congress.utils.Prefs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.realm.Realm;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

import static com.mrengineer13.contact_congress.utils.DialogUtils.hideLoadingDialog;
import static com.mrengineer13.contact_congress.utils.DialogUtils.showLoadingDialog;
import static com.mrengineer13.contact_congress.utils.DialogUtils.showNetworkDialog;
import static com.mrengineer13.contact_congress.utils.DialogUtils.showServerDialog;
import static com.mrengineer13.contact_congress.utils.DialogUtils.showZipCodeErrorDialog;

/**
 * Created by Jon on 6/28/16.
 */

public class SettingsFragment extends RxSettingsFragment implements SharedPreferences.OnSharedPreferenceChangeListener, SettingsView {
    @Inject
    public ApiService api;

    @Inject
    public Prefs prefs;

    @Inject
    public Realm realm;

    private SettingsPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ContactCongress) getActivity().getApplication())
                .getComponent()
                .inject(this);

        addPreferencesFromResource(R.xml.settings);

        final ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(getActivity());
        presenter = new SettingsPresenter(this, api, realm, prefs, locationProvider);

        presenter.getUpdatedZipCode();

        findPreference("zip_preference").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showManuallyUpdateZipCodeDialog();
                return false;
            }
        });

        findPreference("sync_zip_preference").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.getLocation();
                return false;
            }
        });
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equalsIgnoreCase(Keys.ZIP_CODE)) {
            presenter.getUpdatedZipCode();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void updateZipCode(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        Preference zip_preference = findPreference("zip_preference");
        zip_preference.setTitle(title);
    }

    @Override
    public void getLegislatorsForPostalCode() {
        presenter.getLegislatorsFromZipCode();
    }

    @Override
    public void gotPostalCodeFromLocation(String postalCode) {
        if (getView() == null) {
            return;
        }
        Snackbar.make(getView(), String.format("Your postal code: %s", postalCode), Snackbar.LENGTH_SHORT)
                .setAction("Update", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showManuallyUpdateZipCodeDialog();
                    }
                }).show();
    }

    @Override
    public void showLoading() {
        showLoadingDialog(getFragmentManager());
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog(getFragmentManager());
    }

    @Override
    public void serverError() {
        showServerDialog(getActivity());
    }

    @Override
    public void showNetworkErrorDialog() {
        showNetworkDialog(getActivity());
    }

    @Override
    public void showZipErrorDialog() {
        showZipCodeErrorDialog(getActivity());
    }

    private void showManuallyUpdateZipCodeDialog() {
        new MaterialDialog.Builder(getActivity())
                .title("Enter postal code Manually.")
                .content("Please enter your postal code")
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
                            SettingsFragment.this.getLegislatorsForPostalCode();
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
