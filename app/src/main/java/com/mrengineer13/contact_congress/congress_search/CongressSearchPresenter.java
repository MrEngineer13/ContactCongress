package com.mrengineer13.contact_congress.congress_search;

import com.mrengineer13.contact_congress.data.api.ApiService;
import com.mrengineer13.contact_congress.data.api.DataSubscription;
import com.mrengineer13.contact_congress.data.models.LegislatorResults;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jon on 3/20/17.
 */

public class CongressSearchPresenter {
    private CongressSearchView view;
    private ApiService api;
    private CompositeSubscription subscriptions;

    public CongressSearchPresenter(CongressSearchView view, ApiService api) {
        this.view = view;
        this.api = api;
        subscriptions = new CompositeSubscription();
    }

    public void searchForLegislators(String query) {
        Subscription subscription = api.legislatorsSearch(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(new DataSubscription<LegislatorResults>(view, new DataSubscription.CompleteRequestCallback<LegislatorResults>() {
                    @Override
                    public void onSuccess(LegislatorResults results) {
                        view.legislatorSearchResults(results);
                    }
                }));
        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
