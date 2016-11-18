package me.darrillaga.prototype.viewer.shelters.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.darrillaga.prototype.viewer.recipes.viewmodel.RecipesItemViewModel;
import rx.Observable;

public class SheltersItemViewModel extends BaseObservable {

    public final ObservableBoolean refreshing;

    private long mId;

    private String mName;
    private int mDay;
    private String mDate;
    private List<Entry> mEntryList;
    private List<Entry> mEstimatedEntryList;
    private int mInitialAverageWeightInKg;
    private int mFinalAverageWeightInKg;
    private int mCurrentAverageWeightInKg;
    private int mCalculatedFinalDays;
    private int mCalculatedFinalWeight;
    private int mMaxExpectedDays;
    private double mSellPrice;
    private double mRecipePrice;

    private RecipesItemViewModel mCurrentRecipeViewModel;

    private String mThinThickIndex;
    private String mMeatPerKiloCost;

    public SheltersItemViewModel(long id) {
        mId = id;
        refreshing = new ObservableBoolean();
        init();
        
        generateData();
    }

    public SheltersItemViewModel() {
        refreshing = new ObservableBoolean();
        init();
        generateData();
    }

    private void init() {
        mEntryList = new ArrayList<>();
        mEstimatedEntryList = new ArrayList<>();
        mCurrentRecipeViewModel = new RecipesItemViewModel();
    }

    public String getName() {
        if (mName == null) {
            return "";
        } else {
            return mName + " - " + mDate;
        }
    }

    public String getDay() {
        return "Día " + mDay;
    }

    public String getDate() {
        return mDate;
    }

    public List<Entry> getEntryList() {
        return mEntryList;
    }

    public List<Entry> getEstimatedEntryList() {
        return mEstimatedEntryList;
    }

    public int getInitialAverageWeightInKg() {
        return mInitialAverageWeightInKg;
    }

    public int getFinalAverageWeightInKg() {
        return mFinalAverageWeightInKg;
    }

    public int getMaxExpectedDays() {
        return mMaxExpectedDays;
    }

    public long getId() {
        return mId;
    }

    public int getCurrentAverageWeightInKg() {
        return mCurrentAverageWeightInKg;
    }

    public String getSellPrice() {
        return String.valueOf(mSellPrice);
    }

    public String getRecipePrice() {
        return String.valueOf(mRecipePrice);
    }

    public String getThinThickIndex() {
        return mThinThickIndex;
    }

    public String getMeatPerKiloCost() {
        return mMeatPerKiloCost;
    }

    public RecipesItemViewModel getCurrentRecipeViewModel() {
        return mCurrentRecipeViewModel;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setEntryList(List<Entry> entryList) {
        mEntryList = entryList;
    }

    public void setEstimatedEntryList(List<Entry> estimatedEntryList) {
        mEstimatedEntryList = estimatedEntryList;
    }

    public void setInitialAverageWeightInKg(int initialAverageWeightInKg) {
        mInitialAverageWeightInKg = initialAverageWeightInKg;
    }

    public void setFinalAverageWeightInKg(int finalAverageWeightInKg) {
        mFinalAverageWeightInKg = finalAverageWeightInKg;
    }

    public void setCurrentAverageWeightInKg(int currentAverageWeightInKg) {
        mCurrentAverageWeightInKg = currentAverageWeightInKg;
    }

    public void setMaxExpectedDays(int maxExpectedDays) {
        mMaxExpectedDays = maxExpectedDays;
    }

    public void setSellPrice(double sellPrice) {
        mSellPrice = sellPrice;
    }

    public void setRecipePrice(double recipePrice) {
        mRecipePrice = recipePrice;
    }

    public void setCurrentRecipeViewModel(RecipesItemViewModel currentRecipeViewModel) {
        mCurrentRecipeViewModel = currentRecipeViewModel;
    }

    public void setThinThickIndex(String thinThickIndex) {
        mThinThickIndex = thinThickIndex;
    }

    public void setMeatPerKiloCost(String meatPerKiloCost) {
        mMeatPerKiloCost = meatPerKiloCost;
    }

    public int getCalculatedFinalDays() {
        return mCalculatedFinalDays;
    }

    public void setCalculatedFinalDays(int calculatedFinalDays) {
        mCalculatedFinalDays = calculatedFinalDays;
    }

    public int getCalculatedFinalWeight() {
        return mCalculatedFinalWeight;
    }

    public void setCalculatedFinalWeight(int calculatedFinalWeight) {
        mCalculatedFinalWeight = calculatedFinalWeight;
    }

    public Observable<SheltersItemViewModel> fetch() {
        return Observable.just(this).delay(3, TimeUnit.SECONDS)
                .doOnNext(o -> generateData())
                .doOnSubscribe(() -> refreshing.set(true))
                .doAfterTerminate(() -> refreshing.set(false));
    }

    public void onRecipeChanged(long recipeId) {
        mDay += 4;

        mEstimatedEntryList.clear();

        addEntries();
    }

    public void generateData() {
        // FIXME Tooooooooooo bad, SHAME! SHAME! SHAME!
        if (mId % 3 == 0) {
            setDate("10-7-16");
            setDay(40);
            setMaxExpectedDays(100);
            setInitialAverageWeightInKg(320);
            setCurrentAverageWeightInKg(420);
            setFinalAverageWeightInKg(520);
            setCalculatedFinalWeight(520);
            setCalculatedFinalDays(100);
            setRecipePrice(2.00);
            setSellPrice(2.10);

            setThinThickIndex("1,3108");
            setMeatPerKiloCost("1,8 USD/Kg");

            setName("Corral " + getInitialAverageWeightInKg() + " Kg");
        } else if (mId % 3 == 1) {
            setDate("29-6-16");
            setDay(60);
            setMaxExpectedDays(100);
            setInitialAverageWeightInKg(400);
            setCurrentAverageWeightInKg(500);
            setFinalAverageWeightInKg(520);
            setCalculatedFinalWeight(520);
            setCalculatedFinalDays(80);
            setRecipePrice(2.00);
            setSellPrice(2.10);

            setThinThickIndex("1,5108");
            setMeatPerKiloCost("1,8 USD/Kg");

            setName("Corral " + getInitialAverageWeightInKg() + " Kg");
        } else {
            setDate("11-8-16");
            setDay(20);
            setMaxExpectedDays(100);
            setInitialAverageWeightInKg(100);
            setCurrentAverageWeightInKg(120);
            setFinalAverageWeightInKg(520);
            setCalculatedFinalWeight(300);
            setCalculatedFinalDays(110);
            setRecipePrice(2.00);
            setSellPrice(2.10);

            setThinThickIndex("1,3108");
            setMeatPerKiloCost("1,8 USD/Kg");

            setName("Corral " + getInitialAverageWeightInKg() + " Kg");
        }

        updateData();
    }

    public void updateData() {
        mEntryList.clear();
        mEstimatedEntryList.clear();

        addEntries();
        notifyChange();
    }

    private void addEntries() {
        mEntryList.add(new Entry(1, mInitialAverageWeightInKg));
        mEntryList.add(new Entry(mDay, mCurrentAverageWeightInKg));

        mEstimatedEntryList.add(new Entry(mDay, mCurrentAverageWeightInKg));
        mEstimatedEntryList.add(new Entry(mCalculatedFinalDays, mCalculatedFinalWeight));
    }
}