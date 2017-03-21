package com.mrengineer13.contact_congress.home;

import com.mrengineer13.contact_congress.data.api.DataStatusView;

/**
 * Created by Jon on 3/20/17.
 */

public interface HomeView extends DataStatusView {

    public void gotPostalCode(String postalCode);

    public void showZipErrorDialog();
}
