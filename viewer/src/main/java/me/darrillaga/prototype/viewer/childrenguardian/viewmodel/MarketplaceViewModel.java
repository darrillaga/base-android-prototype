package me.darrillaga.prototype.viewer.childrenguardian.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.concurrent.TimeUnit;

import me.darrillaga.databinding.utils.ObservableDispatcherList;
import rx.Observable;
import rx.schedulers.Schedulers;

public class MarketplaceViewModel extends BaseObservable {

    public final ObservableList<MarketplaceItemViewModel> marketplaceItemViewModels;
    private final ObservableList<MarketplaceItemViewModel> mInternalMarketplaceItemViewModels;

    public MarketplaceViewModel() {
        marketplaceItemViewModels = new ObservableArrayList<>();
        mInternalMarketplaceItemViewModels = ObservableDispatcherList
                .createMainThreadObservableList(marketplaceItemViewModels);
    }

    public Observable<MarketplaceItemViewModel> fetchItems() {
        return Observable.range(0, 20, Schedulers.computation())
                .flatMap(
                        integer -> Observable.just(integer).delay(1000, TimeUnit.MILLISECONDS)
                )
                .map(this::buildMarketplaceItemViewModel)
                .doOnNext(mInternalMarketplaceItemViewModels::add);
    }

    private MarketplaceItemViewModel buildMarketplaceItemViewModel(int index) {
        MarketplaceItemViewModel marketplaceItemViewModel = new MarketplaceItemViewModel();

        marketplaceItemViewModel.setCategory("Elementos");
        marketplaceItemViewModel.setDescription("Esta es una pokebola especial que los atrapa más fácil");
        marketplaceItemViewModel.setName("Pokebola " + index);
        marketplaceItemViewModel.setPrice(index * 2);
        marketplaceItemViewModel.setQuantity(index);

        return marketplaceItemViewModel;
    }
}
