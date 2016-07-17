package me.darrillaga.prototype.commons.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import me.darrillaga.prototype.commons.R;

public class BindingAdapters {

    @BindingAdapter({"app:dividerDrawable", "app:dividerDirection"})
    public static void setLayoutDivider(RecyclerView recyclerView, Drawable divider, int dividerDirection) {
        Object added = recyclerView.getTag(R.id.dividerAdded);

        if (added != null) {
            return;
        }

        recyclerView.addItemDecoration(
                new DividerItemDecoration(
                        recyclerView.getContext(), dividerDirection, divider
                )
        );

        recyclerView.setTag(R.id.dividerAdded, new Object());
    }

}
