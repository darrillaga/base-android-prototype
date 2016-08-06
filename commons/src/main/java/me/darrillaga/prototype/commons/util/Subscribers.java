package me.darrillaga.prototype.commons.util;

import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;

public class Subscribers {

    public static <T> Subscriber<? super T> subscribeWithSubscription(
            Action2<Subscription, T> onNext, Action1<Throwable> onError, Action0 onComplete) {

        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                onComplete.call();
            }

            @Override
            public void onError(Throwable e) {
                onError.call(e);
            }

            @Override
            public void onNext(T t) {
                onNext.call(this, t);
            }
        };
    }

    public static <T> Subscriber<? super T> subscribeWithSubscription(
            Action2<Subscription, T> onNext, Action1<Throwable> onError) {

        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                onError.call(e);
            }

            @Override
            public void onNext(T t) {
                onNext.call(this, t);
            }
        };
    }

    public static <T> Subscriber<? super T> subscribeWithSubscription(
            Action2<Subscription, T> onNext) {

        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                throw new OnErrorNotImplementedException(e);
            }

            @Override
            public void onNext(T t) {
                onNext.call(this, t);
            }
        };
    }
}
