package com.mrengineer13.contact_congress.congress_list;

import com.mrengineer13.contact_congress.data.api.DataSubscription;
import com.mrengineer13.contact_congress.data.models.Legislator;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jon on 3/20/17.
 */

public class CongressListPresenter {
    private CongressListView view;
    private Realm realm;
    private CompositeSubscription subscriptions;

    public CongressListPresenter(CongressListView view, Realm realm) {
        this.view = view;
        this.realm = realm;
        subscriptions = new CompositeSubscription();
    }

    public void getLegislatorsFromDb() {
        Subscription subscription = realm.where(Legislator.class).findAll().asObservable()
                .subscribe(new DataSubscription<RealmResults<Legislator>>(new DataSubscription.CompleteRequestCallback<RealmResults<Legislator>>() {
                    @Override
                    public void onSuccess(RealmResults<Legislator> legislators) {
                        view.gotLegislatorsFromDatabase(legislators);
                    }
                }));
        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
