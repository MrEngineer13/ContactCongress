package com.mrengineer13.contact_congress.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.base.BaseActivity;
import com.mrengineer13.contact_congress.data.models.events.LegislatorSearchEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

import static com.mrengineer13.contact_congress.utils.DialogUtils.showZipCodeErrorDialog;

public class HomeActivity extends BaseActivity implements SearchView.OnQueryTextListener, HomeView {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1337;
    private HomeNavAdapter adapter;
    private HomePresenter presenter;

    @Override
    public void onViewInjected(Bundle savedInstanceState) {
        super.onViewInjected(savedInstanceState);

        final ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(getApplicationContext());
        presenter = new HomePresenter(this, api, realm, prefs, locationProvider);

        adapter = new HomeNavAdapter(getFragmentManager());
        bottomNavigationView.setOnNavigationItemSelectedListener(adapter);

        selectFirstNavItem();
        checkPermissions();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private void selectFirstNavItem() {
        MenuItem firstItem = bottomNavigationView.getMenu().getItem(0);
        adapter.selectNavItem(firstItem);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        } else {
            if (!prefs.hasZip()) {
                presenter.getLocation();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    presenter.getLocation();
                } else {
                    showManuallyUpdateZipCodeDialog();
                }
            }
        }
    }

    @Override
    public void showZipErrorDialog() {
        showZipCodeErrorDialog(this);
    }

    @Override
    public void getLegislatorsForPostalCode() {
        presenter.getLegislators();
    }

    @Override
    public void gotPostalCodeFromLocation(String postalCode) {
        Snackbar.make(findViewById(android.R.id.content), String.format("Your postal code: %s", postalCode), Snackbar.LENGTH_SHORT)
                .setAction("Update", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showManuallyUpdateZipCodeDialog();
                    }
                }).show();
    }


    private void showManuallyUpdateZipCodeDialog() {
        new MaterialDialog.Builder(this)
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
                            HomeActivity.this.getLegislatorsForPostalCode();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        bus.send(new LegislatorSearchEvent(s));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
