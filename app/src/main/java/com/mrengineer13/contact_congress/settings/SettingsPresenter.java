package com.mrengineer13.contact_congress.settings;

import com.mrengineer13.contact_congress.data.api.ApiService;
import com.mrengineer13.contact_congress.home.HomePresenter;
import com.mrengineer13.contact_congress.utils.Prefs;

import io.realm.Realm;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

/**
 * Created by Jon on 3/20/17.
 */

public class SettingsPresenter extends HomePresenter {
    SettingsView view;

    public SettingsPresenter(SettingsView view, ApiService api, Realm realm, Prefs prefs, ReactiveLocationProvider locationProvider) {
        super(view, api, realm, prefs, locationProvider);
        this.view = view;
    }

    public void getUpdatedZipCode() {
        if (!prefs.hasZip()) {
            return;
        }

        view.updateZipCode(String.format("Postal Code: %s", prefs.getZip()));
    }

}
