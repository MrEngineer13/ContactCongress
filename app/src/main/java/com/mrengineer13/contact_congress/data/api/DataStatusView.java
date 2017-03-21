package com.mrengineer13.contact_congress.data.api;


/**
 * Created by Jon on 3/19/17.
 */

public interface DataStatusView {

    public void showLoading();

    public void hideLoading();

    public void serverError();

    public void showNetworkErrorDialog();
}
