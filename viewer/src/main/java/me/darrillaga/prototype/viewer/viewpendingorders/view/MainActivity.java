package me.darrillaga.prototype.viewer.viewpendingorders.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.darrillaga.prototype.commons.BaseActivity;
import me.darrillaga.prototype.viewer.R;
import se.emilsjolander.intentbuilder.IntentBuilder;


@IntentBuilder
public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MainActivityIntentBuilder.inject(getIntent(), this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        getSupportFragmentManager().beginTransaction()
                .replace(
                        R.id.container,
                        new MainFragmentBuilder().build(),
                        MainFragment.class.getName()
                ).commit();
    }
}
