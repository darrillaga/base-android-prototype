package me.darrillaga.prototype.viewer.recipes.viewmodel;

import android.databinding.BaseObservable;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class RecipesItemViewModel extends BaseObservable {

    private String mName;
    private int mDay;
    private String mDate;
    private List<Entry> mEntryList;
    private int mInitialAverageWeightInKg;
    private int mFinalAverageWeightInKg;
    private int mCurrentAverageWeightInKg;
    private int mMaxExpectedDays;

    public RecipesItemViewModel() {
        mDate = "11-12-16";
        mDay = 40;
        mMaxExpectedDays = 100;
        mInitialAverageWeightInKg = 320;
        mCurrentAverageWeightInKg = 420;
        mFinalAverageWeightInKg = 520;

        mName = "Corral " + mInitialAverageWeightInKg + " Kg";

        mEntryList = new ArrayList<>();

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
}