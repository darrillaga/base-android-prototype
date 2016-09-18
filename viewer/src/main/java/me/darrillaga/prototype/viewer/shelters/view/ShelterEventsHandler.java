package me.darrillaga.prototype.viewer.shelters.view;

import android.view.View;

import me.darrillaga.prototype.viewer.shelters.viewmodel.SheltersItemViewModel;

public interface ShelterEventsHandler {

    void onItemSelected(SheltersItemViewModel sheltersItemViewModel);
    void onAddShelterClick(View view);
}