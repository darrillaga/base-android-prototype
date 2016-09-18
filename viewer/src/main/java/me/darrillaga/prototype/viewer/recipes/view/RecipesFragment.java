package me.darrillaga.prototype.viewer.recipes.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import me.darrillaga.prototype.commons.ui.ActivityFragmentsInteractionsHelper;
import me.darrillaga.prototype.viewer.databinding.FragmentRecipesBinding;
import me.darrillaga.prototype.viewer.databinding.FragmentSheltersBinding;
import me.darrillaga.prototype.viewer.recipes.viewmodel.RecipeViewModel;
import me.darrillaga.prototype.viewer.recipes.viewmodel.RecipesItemViewModel;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

@FragmentWithArgs
public class RecipesFragment extends Fragment implements RecipesEventsHandler {

    private FragmentRecipesBinding mFragmentRecipesBinding;

    private CompositeSubscription mCompositeSubscription;
    private RecipeViewModel mRecipeViewModel;

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
        mFragmentRecipesBinding = FragmentRecipesBinding.inflate(inflater, container, false);

        mRecipeViewModel = new RecipeViewModel();
        mFragmentRecipesBinding.setRecipesViewModel(mRecipeViewModel);
        mFragmentRecipesBinding.setEventsHandler(this);

        return mFragmentRecipesBinding.getRoot();
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
        return mRecipeViewModel.fetchItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void onItemSelected(RecipesItemViewModel recipesItemViewModel) {
        mInteractions.onRecipeSelected(recipesItemViewModel);
    }

    @Override
    public void onAddRecipeClick(View view) {
        mInteractions.onRecipeAdded();
    }

    public interface Interactions {
        void onRecipeAdded();
        void onRecipeSelected(RecipesItemViewModel recipesItemViewModel);
    }
}