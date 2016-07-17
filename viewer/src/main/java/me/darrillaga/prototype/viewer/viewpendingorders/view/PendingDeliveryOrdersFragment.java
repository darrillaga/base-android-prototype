package me.darrillaga.prototype.viewer.viewpendingorders.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import me.darrillaga.prototype.commons.ui.ErrorsHandler;
import me.darrillaga.prototype.viewer.databinding.FragmentPendingDeliveryOrdersBinding;
import me.darrillaga.prototype.viewer.viewpendingorders.viewmodel.ViewPendingOrdersViewModel;
import rx.functions.Actions;
import rx.subscriptions.CompositeSubscription;

@FragmentWithArgs
public class PendingDeliveryOrdersFragment extends Fragment {

    private FragmentPendingDeliveryOrdersBinding mFragmentPendingDeliveryOrdersBinding;
    private ViewPendingOrdersViewModel mViewPendingOrdersViewModel;
    private CompositeSubscription mCompositeSubscription;
    private ErrorsHandler mErrorsHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mErrorsHandler = new ErrorsHandler(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPendingOrdersViewModel = new ViewPendingOrdersViewModel(FirebaseDatabase.getInstance().getReference());
        mCompositeSubscription = new CompositeSubscription();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentPendingDeliveryOrdersBinding = FragmentPendingDeliveryOrdersBinding
                .inflate(inflater, container, false);

        mFragmentPendingDeliveryOrdersBinding.setViewModel(mViewPendingOrdersViewModel);

        return mFragmentPendingDeliveryOrdersBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCompositeSubscription.add(
                mViewPendingOrdersViewModel.fetchOrders().subscribe(
                        Actions.empty(), mErrorsHandler::handle
                )
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        mCompositeSubscription.unsubscribe();
    }
}
