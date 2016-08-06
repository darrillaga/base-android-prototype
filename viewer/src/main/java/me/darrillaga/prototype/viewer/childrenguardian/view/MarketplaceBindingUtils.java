package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.darrillaga.databinding.utils.ObservableListAdapterObservers;
import me.darrillaga.prototype.commons.ui.BaseAdapter;
import me.darrillaga.prototype.commons.ui.DataBindingAdapterUtils;
import me.darrillaga.prototype.viewer.BR;
import me.darrillaga.prototype.viewer.R;
import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.MarketplaceItemViewModel;

public class MarketplaceBindingUtils {

    @BindingAdapter(value = { "app:dataSet", "app:eventsHandler" })
    public static void bindDataSet(RecyclerView recyclerView, ObservableList<MarketplaceItemViewModel> marketplaceItemViewModels, MarketplaceEventsHandler eventsHandler) {

        if (recyclerView.getTag(R.id.adapterAdded) != null) {
            return;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setAutoMeasureEnabled(true);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(
                new BaseAdapter<>(
                        marketplaceItemViewModels,
                        (viewHolder, data) -> viewHolder.bindTo(data).execute(
                                element -> element.setVariable(BR.eventsHandler, eventsHandler)
                        ).bind(),
                        DataBindingAdapterUtils.createDataBindingViewHolderCreator(R.layout.view_marketplace_item, BR.marketplaceItemViewModel)
                )
        );

        marketplaceItemViewModels.addOnListChangedCallback(
                ObservableListAdapterObservers.createObservableListAdapterBridge(
                        marketplaceItemViewModels, null, recyclerView.getAdapter()
                )
        );

        recyclerView.setTag(R.id.adapterAdded, new Object());
    }
}
