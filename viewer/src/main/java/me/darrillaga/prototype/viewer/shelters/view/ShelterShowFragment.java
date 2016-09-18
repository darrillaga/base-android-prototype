package me.darrillaga.prototype.viewer.shelters.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import me.darrillaga.prototype.commons.ui.ActivityFragmentsInteractionsHelper;
import me.darrillaga.prototype.viewer.databinding.FragmentShelterShowBinding;
import me.darrillaga.prototype.viewer.recipes.view.RecipesActivity;
import me.darrillaga.prototype.viewer.recipes.view.RecipesActivityIntentBuilder;
import me.darrillaga.prototype.viewer.shelters.viewmodel.SheltersItemViewModel;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

@FragmentWithArgs
public class ShelterShowFragment extends Fragment implements SheltersItemEventsHandler {

    @Arg
    long shelterItemId;

    private static final int CHANGE_RECEIPT_CODE = 0;

    private FragmentShelterShowBinding mFragmentShelterShowBinding;

    private CompositeSubscription mCompositeSubscription;
    private SheltersItemViewModel mSheltersItemViewModel;

    private Interactions mInteractions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInteractions = ActivityFragmentsInteractionsHelper
                .ensureFragmentHasAttachedRequiredClassObject(this, Interactions.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentShelterShowBinding = FragmentShelterShowBinding.inflate(inflater, container, false);

        mSheltersItemViewModel = new SheltersItemViewModel(shelterItemId);
        mFragmentShelterShowBinding.setSheltersItemViewModel(mSheltersItemViewModel);
        mFragmentShelterShowBinding.setEventsHandler(this);

        return mFragmentShelterShowBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(
                subscribeToFetchItems()
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        mCompositeSubscription.unsubscribe();
    }

    private Subscription subscribeToFetchItems() {
        return mSheltersItemViewModel.fetch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sheltersItemViewModel -> mFragmentShelterShowBinding.invalidateAll());
    }

    @Override
    public void onChangeRecipeClick(View view) {
        startActivityForResult(new RecipesActivityIntentBuilder().build(getActivity()), CHANGE_RECEIPT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHANGE_RECEIPT_CODE && resultCode == Activity.RESULT_OK) {
            onRecipeChanged(data.getLongExtra(RecipesActivity.RESULT_SELECTED_RECIPE_ID_LONG, -1));
        }
    }

    private void onRecipeChanged(long id) {
        mSheltersItemViewModel.onRecipeChanged(id);
    }

    @Override
    public void refresh() {
        mCompositeSubscription.add(
                subscribeToFetchItems()
        );
    }

    public interface Interactions {
    }
}