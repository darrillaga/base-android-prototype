package me.darrillaga.prototype.viewer.recipes.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import me.darrillaga.prototype.commons.BaseActivity;
import me.darrillaga.prototype.viewer.R;
import me.darrillaga.prototype.viewer.recipes.viewmodel.RecipesItemViewModel;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder
public class RecipesActivity extends BaseActivity implements RecipesFragment.Interactions {

    public static final String RESULT_SELECTED_RECIPE_ID_LONG = "Result:SelectedRecipeId:Long";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shelters);

        showRecipesFragment();
    }

    private void showRecipesFragment() {
        String tag = RecipesFragment.class.getName();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment != null) {
            getSupportFragmentManager().popBackStack(tag, 0);
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(
                        R.id.fragment_container,
                        new RecipesFragmentBuilder().build(),
                        tag
                )
                .commit();
    }

    @Override
    public void onRecipeSelected(RecipesItemViewModel recipesItemViewModel) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_SELECTED_RECIPE_ID_LONG, recipesItemViewModel.getId());
        setResult(RESULT_OK, intent);
        finish();
    }
}
