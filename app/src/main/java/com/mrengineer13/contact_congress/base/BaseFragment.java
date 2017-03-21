package com.mrengineer13.contact_congress.base;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrengineer13.contact_congress.ContactCongress;
import com.mrengineer13.contact_congress.data.api.ApiService;
import com.mrengineer13.contact_congress.data.api.DataStatusView;
import com.mrengineer13.contact_congress.data.models.Legislator;
import com.mrengineer13.contact_congress.utils.RxBus;
import com.trello.rxlifecycle.components.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.realm.Realm;

import static com.mrengineer13.contact_congress.utils.DialogUtils.hideLoadingDialog;
import static com.mrengineer13.contact_congress.utils.DialogUtils.showLoadingDialog;
import static com.mrengineer13.contact_congress.utils.DialogUtils.showNetworkDialog;
import static com.mrengineer13.contact_congress.utils.DialogUtils.showServerDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends RxFragment implements DataStatusView {

    @Inject
    public ApiService api;

    @Inject
    public RxBus bus;

    @Inject
    public Realm realm;

    protected View.OnClickListener finishActivityOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };

    public BaseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ContactCongress) getActivity().getApplication())
                .getComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayout() > 0) {
            View view = inflater.inflate(getLayout(), container, false);
            ButterKnife.bind(this, view);
            onViewInjected(view);
            return view;
        }
        return null;
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

    public void callLegislator(Legislator legislator) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + legislator.phone));
        startActivity(intent);
    }

    public void emailLegislator(Legislator legislator) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", legislator.email, null));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, legislator.email);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void onViewInjected(View view) {
    }

    @LayoutRes
    public abstract int getLayout();

}
