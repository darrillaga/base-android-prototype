package me.darrillaga.prototype.viewer.shelters.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import me.darrillaga.prototype.commons.BaseActivity;
import me.darrillaga.prototype.viewer.R;
import me.darrillaga.prototype.viewer.shelters.viewmodel.SheltersItemViewModel;

public class SheltersActivity extends BaseActivity
        implements SheltersFragment.Interactions, ShelterShowFragment.Interactions {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shelters);

        showSheltersFragment();
    }

    private void showSheltersFragment() {
        String tag = SheltersFragment.class.getName();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment != null) {
            getSupportFragmentManager().popBackStack(tag, 0);
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(
                        R.id.fragment_container,
                        new SheltersFragmentBuilder().build(),
                        tag
                )
                .commit();
    }

    private void showSheltersFragment(long id) {
        String tag = ShelterShowFragment.class.getName();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment != null) {
            getSupportFragmentManager().popBackStack(tag, 0);
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(
                        R.id.fragment_container,
                        new ShelterShowFragmentBuilder(id).build(),
                        tag
                )
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onAddShelterClick() {
        Toast.makeText(this, R.string.shelters_limit_reached, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(SheltersItemViewModel sheltersItemViewModel) {
        showSheltersFragment(sheltersItemViewModel.getId());
    }
}
