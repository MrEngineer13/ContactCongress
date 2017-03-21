package com.mrengineer13.contact_congress.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Jon on 6/30/16.
 */

public class RxBus {
    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return bus;
    }
}
