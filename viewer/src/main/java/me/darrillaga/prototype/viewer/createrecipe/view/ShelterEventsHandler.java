package me.darrillaga.prototype.viewer.createrecipe.view;

import android.view.View;

import me.darrillaga.prototype.viewer.createrecipe.viewmodel.SheltersItemViewModel;

public interface ShelterEventsHandler {

    void onItemSelected(SheltersItemViewModel sheltersItemViewModel);
    void onAddShelterClick(View view);
}