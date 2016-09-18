package me.darrillaga.prototype.viewer.recipes.view;

import android.view.View;

import me.darrillaga.prototype.viewer.recipes.viewmodel.RecipesItemViewModel;

public interface RecipesEventsHandler {

    void onItemSelected(RecipesItemViewModel recipesItemViewModel);
    void onAddRecipeClick(View view);
    void refresh();
}