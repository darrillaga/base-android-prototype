package me.darrillaga.prototype.viewer.viewpendingorders.view;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.darrillaga.databinding.utils.ObservableListAdapterObservers;
import me.darrillaga.prototype.commons.ui.DataBindingAdapterUtils;
import me.darrillaga.prototype.viewer.R;
import me.darrillaga.prototype.viewer.BR;
import me.darrillaga.prototype.viewer.viewpendingorders.viewmodel.DeliveryOrderViewModel;

public class BindingUtils {

    @BindingAdapter("app:dataSet")
    public static void bindDataSet(RecyclerView recyclerView, ObservableList<DeliveryOrderViewModel> deliveryOrderViewModels) {

        if (recyclerView.getTag(R.id.adapterAdded) != null) {
            return;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setAutoMeasureEnabled(true);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(
                DataBindingAdapterUtils.createAdapter(
                        deliveryOrderViewModels, R.layout.view_delivery_order_list_item, BR.viewModel
                )
        );

        deliveryOrderViewModels.addOnListChangedCallback(
                ObservableListAdapterObservers.createObservableListAdapterBridge(
                        deliveryOrderViewModels, null, recyclerView.getAdapter()
                )
        );

        recyclerView.setTag(R.id.adapterAdded, new Object());
    }
}
