package com.mrengineer13.contact_congress.data.api;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Jon on 3/11/17.
 */

public class DataSubscription<T> extends Subscriber<T> {

    DataStatusView view;
    CompleteRequestCallback callback;

    public interface CompleteRequestCallback<T> {
        void onSuccess(T items);
    }

    public DataSubscription(CompleteRequestCallback callback) {
        this.callback = callback;
    }

    public DataSubscription(DataStatusView view, CompleteRequestCallback callback) {
        this.view = view;
        this.callback = callback;
        this.view.showLoading();
    }

    @Override
    public void onCompleted() {
        view.hideLoading();
    }

    @Override
    public void onError(Throwable error) {
        view.hideLoading();
        if (error instanceof HttpException) {
            view.serverError();
        } else if (error instanceof IOException) {
            // A network or conversion error happened
            view.showNetworkErrorDialog();
        }
    }

    @Override
    public void onNext(T t) {
        callback.onSuccess(t);
    }
}
