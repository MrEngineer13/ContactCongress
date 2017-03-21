package com.mrengineer13.contact_congress.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.mrengineer13.contact_congress.ContactCongress;
import com.mrengineer13.contact_congress.data.api.ApiService;
import com.mrengineer13.contact_congress.data.api.DataStatusView;
import com.mrengineer13.contact_congress.utils.Prefs;
import com.mrengineer13.contact_congress.utils.RxBus;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.realm.Realm;

import static com.mrengineer13.contact_congress.utils.DialogUtils.hideLoadingDialog;
import static com.mrengineer13.contact_congress.utils.DialogUtils.showLoadingDialog;
import static com.mrengineer13.contact_congress.utils.DialogUtils.showNetworkDialog;
import static com.mrengineer13.contact_congress.utils.DialogUtils.showServerDialog;

public abstract class BaseActivity extends RxAppCompatActivity implements DataStatusView {

    @Inject
    public ApiService api;

    @Inject
    public Prefs prefs;

    @Inject
    public RxBus bus;

    @Inject
    public Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ButterKnife.bind(this);

        ((ContactCongress) getApplication())
                .getComponent()
                .inject(this);

        onViewInjected(savedInstanceState);
    }

    protected void setContentView() {
        int layout = getLayout();
        if (layout > 0) {
            setContentView(layout);
        }
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
        showServerDialog(this);
    }

    @Override
    public void showNetworkErrorDialog() {
        showNetworkDialog(this);
    }

    public void onViewInjected(Bundle savedInstanceState) {
    }

    @LayoutRes
    public abstract int getLayout();
}
