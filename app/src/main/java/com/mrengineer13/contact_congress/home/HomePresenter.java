package com.mrengineer13.contact_congress.home;

import android.location.Address;
import android.location.Location;

import com.mrengineer13.contact_congress.data.api.ApiService;
import com.mrengineer13.contact_congress.data.api.DataSubscription;
import com.mrengineer13.contact_congress.data.models.Legislator;
import com.mrengineer13.contact_congress.data.models.LegislatorResults;
import com.mrengineer13.contact_congress.utils.Prefs;

import java.util.List;

import io.realm.Realm;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.charmas.android.reactivelocation.observables.GoogleAPIConnectionException;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Jon on 3/20/17.
 */

public class HomePresenter {
    public HomeView view;
    private ApiService api;
    private Realm realm;
    public Prefs prefs;
    private ReactiveLocationProvider locationProvider;
    private CompositeSubscription subscriptions;

    public HomePresenter(HomeView view, ApiService api, Realm realm, Prefs prefs, ReactiveLocationProvider locationProvider) {
        this.view = view;
        this.api = api;
        this.realm = realm;
        this.prefs = prefs;
        this.locationProvider = locationProvider;
        subscriptions = new CompositeSubscription();
    }

    public void getLocation() {
        Subscription subscription = locationProvider.getLastKnownLocation()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Location, Observable<List<Address>>>() {
                    @Override
                    public Observable<List<Address>> call(Location location) {
                        Timber.d("location" + location.getAccuracy());
                        getLegislatorsFromLocation(location);
                        return locationProvider
                                .getReverseGeocodeObservable(location.getLatitude(), location.getLongitude(), 1);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Address>>() {
                    @Override
                    public void call(List<Address> addresses) {
                        String postalCode = addresses.get(0).getPostalCode();
                        prefs.saveZip(postalCode);
                        view.gotPostalCodeFromLocation(postalCode);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof GoogleAPIConnectionException) {
                            view.showZipErrorDialog();
                        }
                    }
                });

        subscriptions.add(subscription);
    }

    private void getLegislatorsFromLocation(Location location) {
        Subscription subscription = api.legislators(location.getLatitude(), location.getLongitude())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DataSubscription<LegislatorResults>(view, new DataSubscription.CompleteRequestCallback<LegislatorResults>() {
                    @Override
                    public void onSuccess(LegislatorResults legislatorResults) {
                        saveLegislators(legislatorResults);
                    }
                }));

        subscriptions.add(subscription);
    }

    public void getLegislatorsFromZipCode() {
        String zipCode = prefs.getZip();
        Subscription subscription = api.legislators(zipCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DataSubscription<LegislatorResults>(view, new DataSubscription.CompleteRequestCallback<LegislatorResults>() {
                    @Override
                    public void onSuccess(LegislatorResults legislatorResults) {
                        saveLegislators(legislatorResults);
                    }
                }));

        subscriptions.add(subscription);
    }

    private void saveLegislators(LegislatorResults legislatorResults) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
        realm.beginTransaction();
        for (final Legislator legislator : legislatorResults.legislators) {
            realm.insertOrUpdate(legislator);
        }
        realm.commitTransaction();
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
