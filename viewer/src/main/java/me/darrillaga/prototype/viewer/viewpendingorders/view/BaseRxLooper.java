package me.darrillaga.prototype.viewer.viewpendingorders.view;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.IOIOLooper;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

public class BaseRxLooper implements RxLooper, IOIOLooper {

    private BehaviorSubject<IOIOState> mStateBehaviorSubject;
    private PublishSubject mLooperPublishSubject;
    private IOIO mIOIO;

    public BaseRxLooper() {
        mStateBehaviorSubject = BehaviorSubject.create();
        mLooperPublishSubject = PublishSubject.create();
        mStateBehaviorSubject.onNext(createIOIOState(IOIOLooperState.UNINITIALIZED));
    }

    @Override
    public void setup(IOIO ioio) throws ConnectionLostException, InterruptedException {
        mIOIO = ioio;
        mStateBehaviorSubject.onNext(createIOIOState(IOIOLooperState.INITIALIZED));
    }

    @Override
    public void loop() throws ConnectionLostException, InterruptedException {
        mLooperPublishSubject.onNext(null);
    }

    @Override
    public void disconnected() {
        mStateBehaviorSubject.onNext(createIOIOState(IOIOLooperState.DISCONNECTED));
    }

    @Override
    public void incompatible() {
        mStateBehaviorSubject.onNext(createIOIOState(IOIOLooperState.INCOMPATIBLE));
    }

    @Override
    public void incompatible(IOIO ioio) {
        mIOIO = ioio;
        mStateBehaviorSubject.onNext(createIOIOState(IOIOLooperState.INCOMPATIBLE));
    }

    @Override
    public Observable<IOIOState> getStateObservable() {
        return mStateBehaviorSubject.asObservable();
    }

    @Override
    public Observable<Void> getLooperObservable() {
        return mLooperPublishSubject.asObservable();
    }

    private IOIOState createIOIOState(IOIOLooperState state) {
        return new IOIOState() {
            @Override
            public IOIO getIOIO() {
                return mIOIO;
            }

            @Override
            public IOIOLooperState getLooperState() {
                return state;
            }
        };
    }
}
