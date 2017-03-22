package com.mrengineer13.contact_congress.settings;

import com.mrengineer13.contact_congress.home.HomeView;

/**
 * Created by Jon on 3/20/17.
 */

public interface SettingsView extends HomeView {

    public void showZipErrorDialog();

    public void updateZipCode(String title);

}
