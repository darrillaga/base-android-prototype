package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import java.util.List;

import me.darrillaga.prototype.commons.ui.ActivityFragmentsInteractionsHelper;
import me.darrillaga.prototype.viewer.R;
import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.ChildViewModel;
import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.MarketplaceItemViewModel;
import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.MarketplaceViewModel;
import me.darrillaga.prototype.viewer.databinding.FragmentMarketplaceBinding;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

@FragmentWithArgs
public class MarketplaceFragment extends Fragment implements MarketplaceEventsHandler {

    @Arg(bundler = ChildViewModelSerializableArgsBundler.class)
    ChildViewModel mCurrentChild;

    @Arg(bundler = CastedSerializableArrayListArgsBundler.class)
    List<ChildViewModel> mChildViewModelList;

    private FragmentMarketplaceBinding mFragmentMarketplaceBinding;

    private CompositeSubscription mCompositeSubscription;
    private MarketplaceViewModel mMarketplaceViewModel;

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
        mFragmentMarketplaceBinding = FragmentMarketplaceBinding.inflate(inflater, container, false);

        mMarketplaceViewModel = new MarketplaceViewModel();
        mFragmentMarketplaceBinding.setMarketplaceViewModel(mMarketplaceViewModel);
        mFragmentMarketplaceBinding.setEventsHandler(this);
        mFragmentMarketplaceBinding.setChildViewModelList(mChildViewModelList);

        return mFragmentMarketplaceBinding.getRoot();
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
        return mMarketplaceViewModel.fetchItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void onItemSelected(MarketplaceItemViewModel marketplaceItemViewModel) {
        marketplaceItemViewModel.toggleShowChildren();
    }

    @Override
    public void onGoToCheckoutClick(View view) {
        Toast.makeText(view.getContext(), R.string.payment_confirmation, Toast.LENGTH_LONG).show();
        mInteractions.onPurchaseConfirmed();
    }

    public interface Interactions {
        void onPurchaseConfirmed();
    }
}
