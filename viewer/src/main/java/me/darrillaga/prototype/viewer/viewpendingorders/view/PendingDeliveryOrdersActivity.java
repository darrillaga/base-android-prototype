package me.darrillaga.prototype.viewer.viewpendingorders.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.darrillaga.prototype.commons.BaseActivity;
import me.darrillaga.prototype.viewer.MapsActivityIntentBuilder;
import me.darrillaga.prototype.viewer.R;
import se.emilsjolander.intentbuilder.IntentBuilder;


@IntentBuilder
public class PendingDeliveryOrdersActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        PendingDeliveryOrdersActivityIntentBuilder.inject(getIntent(), this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        getSupportFragmentManager().beginTransaction()
                .replace(
                        R.id.container,
                        new PendingDeliveryOrdersFragmentBuilder().build(),
                        PendingDeliveryOrdersFragment.class.getName()
                ).commit();

        startActivity(new MapsActivityIntentBuilder().build(this));
    }
}
