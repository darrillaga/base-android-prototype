package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.view.View;

import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.MarketplaceItemViewModel;

public interface MarketplaceEventsHandler {

    void onItemSelected(MarketplaceItemViewModel marketplaceItemViewModel);
    void onGoToCheckoutClick(View view);
}
