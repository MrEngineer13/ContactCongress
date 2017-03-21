package com.mrengineer13.contact_congress.settings;

import com.mrengineer13.contact_congress.data.api.DataStatusView;

/**
 * Created by Jon on 3/20/17.
 */

public interface SettingsView extends DataStatusView {

    public void gotPostalCode(String postalCode);

    public void showZipErrorDialog();

    public void updateZipCode(String title);

}
