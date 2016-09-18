package me.darrillaga.prototype.viewer.shelters.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import me.darrillaga.prototype.commons.ui.ActivityFragmentsInteractionsHelper;
import me.darrillaga.prototype.viewer.databinding.FragmentSheltersBinding;
import me.darrillaga.prototype.viewer.shelters.viewmodel.SheltersItemViewModel;
import me.darrillaga.prototype.viewer.shelters.viewmodel.ShelterViewModel;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

@FragmentWithArgs
public class SheltersFragment extends Fragment implements ShelterEventsHandler {

    private FragmentSheltersBinding mFragmentShelterBinding;

    private CompositeSubscription mCompositeSubscription;
    private ShelterViewModel mShelterViewModel;

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
        mFragmentShelterBinding = FragmentSheltersBinding.inflate(inflater, container, false);

        mShelterViewModel = new ShelterViewModel();
        mFragmentShelterBinding.setSheltersViewModel(mShelterViewModel);
        mFragmentShelterBinding.setEventsHandler(this);

        return mFragmentShelterBinding.getRoot();
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
        return mShelterViewModel.fetchItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void onItemSelected(SheltersItemViewModel sheltersItemViewModel) {
        mInteractions.onItemSelected(sheltersItemViewModel);
    }

    @Override
    public void onAddShelterClick(View view) {
        mInteractions.onAddShelterClick();
    }

    @Override
    public void refresh() {
        mCompositeSubscription.add(
                subscribeToFetchItems()
        );
    }

    public interface Interactions {
        void onAddShelterClick();
        void onItemSelected(SheltersItemViewModel sheltersItemViewModel);
    }
}