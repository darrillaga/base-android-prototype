package me.darrillaga.prototype.viewer.recipes.viewmodel;

import android.databinding.BaseObservable;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.darrillaga.prototype.viewer.shelters.viewmodel.SheltersItemViewModel;
import rx.Observable;

public class RecipesItemViewModel extends BaseObservable {

    private long mId;

    private String mName;

    public RecipesItemViewModel(long id) {
        mId = id;
        init();
    }

    public RecipesItemViewModel() {
        init();
        generateData();
    }

    public String getName() {
        return mName;
    }

    public long getId() {
        return mId;
    }

    public Observable<RecipesItemViewModel> fetch() {
        return Observable.just(this).delay(3, TimeUnit.SECONDS)
                .doOnNext(o -> generateData());
    }

    private void init() {

    }

    private void generateData() {

    }

}