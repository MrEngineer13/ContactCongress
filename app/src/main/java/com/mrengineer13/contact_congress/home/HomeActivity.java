package com.mrengineer13.contact_congress.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.base.BaseActivity;
import com.mrengineer13.contact_congress.data.models.events.LegislatorSearchEvent;

import butterknife.BindView;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

import static com.mrengineer13.contact_congress.utils.DialogUtils.showZipCodeDialog;
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
                    showZipCodeDialog(HomeActivity.this);
                }
            }
        }
    }

    @Override
    public void showZipErrorDialog() {
        showZipCodeErrorDialog(HomeActivity.this);
    }

    @Override
    public void gotPostalCode(String postalCode) {
        Snackbar.make(findViewById(android.R.id.content), String.format("Your postal code: %s", postalCode), Snackbar.LENGTH_SHORT)
                .setAction("Update", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showZipCodeDialog(HomeActivity.this);
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
