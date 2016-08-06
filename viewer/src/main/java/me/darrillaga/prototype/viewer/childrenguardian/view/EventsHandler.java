package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.view.View;

import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.ChildViewModel;

public interface EventsHandler {

    void onChildSelected(ChildViewModel childViewModel);
    void onToolsToggleClick(View view);
    void onDangerZoneCreatorClick(View view);
    void onSecureZoneCreatorClick(View view);
    void onMarketPlaceClick(View view);
}
