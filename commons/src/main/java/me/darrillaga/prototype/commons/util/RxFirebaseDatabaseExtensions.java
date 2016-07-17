package me.darrillaga.prototype.commons.util;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kelvinapps.rxfirebase.exceptions.RxFirebaseDataCastException;
import com.kelvinapps.rxfirebase.exceptions.RxFirebaseDataException;

import rx.Observable;
import rx.Subscriber;

public class RxFirebaseDatabaseExtensions {

    @NonNull
    public static <T> Observable<T> observeValue(@NonNull final Query query, @NonNull final Class<T> clazz) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        T value = dataSnapshot.getValue(clazz);
                        if (value != null) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(value);
                            }
                        } else {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(new RxFirebaseDataCastException("unable to cast firebase data response to " + clazz.getSimpleName()));
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(new RxFirebaseDataException(error));
                        }
                    }
                });

            }
        });
    }
}
