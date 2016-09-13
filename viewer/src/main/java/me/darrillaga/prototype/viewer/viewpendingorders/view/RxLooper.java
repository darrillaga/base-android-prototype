package me.darrillaga.prototype.viewer.viewpendingorders.view;

import rx.Observable;

public interface RxLooper {

    Observable<IOIOState> getStateObservable();
    Observable<Void> getLooperObservable();
}
