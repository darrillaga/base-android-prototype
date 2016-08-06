package me.darrillaga.prototype.viewer.childrenguardian.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.google.android.gms.maps.model.LatLng;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.bloco.faker.Faker;
import me.darrillaga.databinding.utils.ObservableDispatcherList;
import rx.Observable;
import rx.schedulers.Schedulers;

public class ChildrenViewModel extends BaseObservable {

    public final ObservableList<ChildViewModel> childViewModels;
    private final ObservableList<ChildViewModel> mInternalChildViewModels;

    private int[] mColors;

    public ChildrenViewModel(int[] colors) {
        childViewModels = new ObservableArrayList<>();
        mInternalChildViewModels = ObservableDispatcherList
                .createMainThreadObservableList(childViewModels);

        mColors = colors;
    }

    public Observable<ChildViewModel> fetchChildren() {
        return Observable.range(0, 20, Schedulers.computation())
                .flatMap(
                        integer -> Observable.just(integer).delay(1000, TimeUnit.MILLISECONDS)
                )
                .map(this::buildChildViewModel)
                .doOnNext(mInternalChildViewModels::add);
    }

    private ChildViewModel buildChildViewModel(int index) {
        ChildViewModel childViewModel = new ChildViewModel();

        LatLng montevideo = new LatLng(-34.90111, -56.16453);

        Random r = new Random();

        r = new Random();

        childViewModel.setLongitude(
                montevideo.longitude + r.nextInt(1001) / 1000D * 0.1 * (index % 2 == 0 ? 1 : -1)
        );

        childViewModel.setLatitude(
                montevideo.latitude + r.nextInt(1001) / 1000D * (montevideo.longitude > montevideo.latitude + 0.1 ? 0.1 : 0.15)
        );

        childViewModel.setColor(mColors[r.nextInt(mColors.length)]);

        Faker faker = new Faker("es");

        childViewModel.setName(faker.name.firstName());
        childViewModel.setAvatar(faker.avatar.image("baby " + index));

        return childViewModel;
    }
}
