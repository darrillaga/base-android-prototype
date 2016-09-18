package me.darrillaga.prototype.viewer.recipes.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import me.darrillaga.prototype.commons.BaseActivity;
import me.darrillaga.prototype.viewer.R;
import me.darrillaga.prototype.viewer.shelters.view.SheltersFragmentBuilder;

public class RecipesActivity extends BaseActivity implements RecipesFragment.Interactions {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shelters);

        showSheltersFragment();
    }

    private void showSheltersFragment() {
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
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onAddShelterClick() {

    }
}
