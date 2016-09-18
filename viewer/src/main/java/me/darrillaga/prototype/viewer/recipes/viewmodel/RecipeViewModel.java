package me.darrillaga.prototype.viewer.recipes.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import java.util.concurrent.TimeUnit;

import me.darrillaga.databinding.utils.ObservableDispatcherList;
import rx.Observable;
import rx.schedulers.Schedulers;

public class RecipeViewModel extends BaseObservable {

    public final ObservableList<RecipesItemViewModel> recipesItemViewModels;
    public final ObservableBoolean refreshing;

    private final ObservableList<RecipesItemViewModel> mInternalRecipesItemViewModels;

    public RecipeViewModel() {
        recipesItemViewModels = new ObservableArrayList<>();
        refreshing = new ObservableBoolean();

        mInternalRecipesItemViewModels = ObservableDispatcherList
                .createMainThreadObservableList(recipesItemViewModels);
    }

    public Observable<RecipesItemViewModel> fetchItems() {
        return Observable.range(0, 20, Schedulers.computation())
                .flatMap(
                        integer -> Observable.just(integer).delay(1000, TimeUnit.MILLISECONDS)
                )
                .map(this::buildMarketplaceItemViewModel)
                .doOnNext(mInternalRecipesItemViewModels::add)
                .doOnSubscribe(() -> refreshing.set(true))
                .doAfterTerminate(() -> refreshing.set(false));
    }

    private RecipesItemViewModel buildMarketplaceItemViewModel(int index) {
        RecipesItemViewModel recipesItemViewModel = new RecipesItemViewModel();

//        recipesItemViewModel.setCategory("Elementos");
//        recipesItemViewModel.setDescription("Esta es una pokebola especial que los atrapa más fácil");
//        recipesItemViewModel.setName("Pokebola " + index);
//        recipesItemViewModel.setPrice(index * 2);
//        recipesItemViewModel.setQuantity(index);

        return recipesItemViewModel;
    }
}