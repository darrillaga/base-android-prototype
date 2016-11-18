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
    private String mDescription;
    private String mEnergy;
    private String mKgIncreaseIntoAnimal;
    private String mPricePerKg;
    private String mTotalKg;

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

    public String getDescription() {
        return mDescription;
    }

    public String getEnergy() {
        return mEnergy;
    }

    public String getKgIncreaseIntoAnimal() {
        return mKgIncreaseIntoAnimal;
    }

    public String getPricePerKg() {
        return mPricePerKg;
    }

    public String getTotalKg() {
        return mTotalKg;
    }

    private void init() {

    }

    private void generateData() {
        mName = "Dieta de engorde inicial";
        mDescription = "Dieta compuesta de lupo, girasol y proteína";
        mEnergy = "1500 kcal/kg";
        mKgIncreaseIntoAnimal = "1.200 Kg/día";
        mPricePerKg = "2.00 USD";
        mTotalKg = "15000 Kg";
    }

}