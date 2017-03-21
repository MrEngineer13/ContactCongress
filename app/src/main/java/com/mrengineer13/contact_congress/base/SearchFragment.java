package com.mrengineer13.contact_congress.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.data.models.events.LegislatorSearchEvent;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SearchFragment extends BaseFragment {

    public String query;
    private MenuItem searchItem;
    private SearchView searchView;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        listenToSearches();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchItem.setVisible(true);
        searchView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();

        searchItem.collapseActionView();
        searchItem.setVisible(false);
        searchView.setVisibility(View.GONE);
    }

    private void listenToSearches() {
        bus.toObserverable().share()
                .compose(SearchFragment.this.bindToLifecycle())
                .ofType(LegislatorSearchEvent.class)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LegislatorSearchEvent>() {
                    @Override
                    public void call(LegislatorSearchEvent event) {
                        String query = event.query;
                        if (!TextUtils.isEmpty(query)) {
                            search(query);
                        }
                    }
                });
    }

    public abstract void search(String query);

    @Override
    public int getLayout() {
        return 0;
    }
}