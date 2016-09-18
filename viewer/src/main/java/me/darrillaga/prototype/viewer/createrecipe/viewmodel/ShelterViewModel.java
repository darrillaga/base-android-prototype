//package me.darrillaga.prototype.viewer.createrecipe.viewmodel;
//
//import android.databinding.BaseObservable;
//import android.databinding.ObservableArrayList;
//import android.databinding.ObservableList;
//
//import java.util.concurrent.TimeUnit;
//
//import me.darrillaga.databinding.utils.ObservableDispatcherList;
//import rx.Observable;
//import rx.schedulers.Schedulers;
//
//public class ShelterViewModel extends BaseObservable {
//
//    public final ObservableList<SheltersItemViewModel> sheltersItemViewModels;
//    private final ObservableList<SheltersItemViewModel> mInternalSheltersItemViewModels;
//
//    public ShelterViewModel() {
//        sheltersItemViewModels = new ObservableArrayList<>();
//        mInternalSheltersItemViewModels = ObservableDispatcherList
//                .createMainThreadObservableList(sheltersItemViewModels);
//    }
//
//    public Observable<SheltersItemViewModel> fetchItems() {
//        return Observable.range(0, 20, Schedulers.computation())
//                .flatMap(
//                        integer -> Observable.just(integer).delay(1000, TimeUnit.MILLISECONDS)
//                )
//                .map(this::buildMarketplaceItemViewModel)
//                .doOnNext(mInternalSheltersItemViewModels::add);
//    }
//
//    private SheltersItemViewModel buildMarketplaceItemViewModel(int index) {
//        SheltersItemViewModel sheltersItemViewModel = new SheltersItemViewModel();
//
////        sheltersItemViewModel.setCategory("Elementos");
////        sheltersItemViewModel.setDescription("Esta es una pokebola especial que los atrapa más fácil");
////        sheltersItemViewModel.setName("Pokebola " + index);
////        sheltersItemViewModel.setPrice(index * 2);
////        sheltersItemViewModel.setQuantity(index);
//
//        return sheltersItemViewModel;
//    }
//}