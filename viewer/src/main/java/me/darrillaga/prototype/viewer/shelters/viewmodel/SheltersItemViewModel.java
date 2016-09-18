package me.darrillaga.prototype.viewer.shelters.viewmodel;

import android.databinding.BaseObservable;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;

public class SheltersItemViewModel extends BaseObservable {

    private long mId;

    private String mName;
    private int mDay;
    private String mDate;
    private List<Entry> mEntryList;
    private int mInitialAverageWeightInKg;
    private int mFinalAverageWeightInKg;
    private int mCurrentAverageWeightInKg;
    private int mMaxExpectedDays;
    private double mSellPrice;
    private double mRecipePrice;

    private String mThinThickIndex;
    private String mMeatPerKiloCost;

    public SheltersItemViewModel(long id) {
        mId = id;
        init();
    }

    public SheltersItemViewModel() {
        init();
        generateData();
    }

    private void init() {
        mEntryList = new ArrayList<>();
    }

    public String getName() {
        return mName  + " - " + mDate;
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

    public Observable<SheltersItemViewModel> fetch() {
        return Observable.just(this).delay(3, TimeUnit.SECONDS)
                .doOnNext(o -> generateData());
    }

    private void generateData() {
        mDate = "11-12-16";
        mDay = 40;
        mMaxExpectedDays = 100;
        mInitialAverageWeightInKg = 320;
        mCurrentAverageWeightInKg = 420;
        mFinalAverageWeightInKg = 520;
        mRecipePrice = 2.00;
        mSellPrice = 2.10;

        mThinThickIndex = "1,3108";
        mMeatPerKiloCost = "1,8 USD/Kg";

        mName = "Corral " + mInitialAverageWeightInKg + " Kg";

        mEntryList.clear();

        for (int index = 1; index <= mDay; index++) {
            addEntry(index);
        }
    }

    private void addEntry(int index) {
        Entry entry = new Entry();
        entry.setX(index);

        if (index == 1) {
            entry.setY(mInitialAverageWeightInKg);
        } else  {
            entry.setY(mInitialAverageWeightInKg + ((mCurrentAverageWeightInKg - mInitialAverageWeightInKg) * index) / mDay);
        }

        mEntryList.add(entry);
    }
}