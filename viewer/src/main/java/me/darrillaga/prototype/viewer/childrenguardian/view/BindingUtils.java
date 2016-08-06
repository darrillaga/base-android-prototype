package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import me.darrillaga.databinding.utils.ObservableListAdapterObservers;
import me.darrillaga.prototype.commons.ui.BaseAdapter;
import me.darrillaga.prototype.commons.ui.DataBindingAdapterUtils;
import me.darrillaga.prototype.viewer.BR;
import me.darrillaga.prototype.viewer.R;
import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.ChildViewModel;

public class BindingUtils {

    @BindingAdapter(value = { "app:dataSet", "app:eventsHandler" })
    public static void bindDataSet(RecyclerView recyclerView, ObservableList<ChildViewModel> childrenList, EventsHandler eventsHandler) {

        if (recyclerView.getTag(R.id.adapterAdded) != null) {
            return;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setAutoMeasureEnabled(true);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(
                new BaseAdapter<>(
                        childrenList,
                        (viewHolder, data) -> viewHolder.bindTo(data).execute(
                                element -> element.setVariable(BR.eventsHandler, eventsHandler)
                        ).bind(),
                        DataBindingAdapterUtils.createDataBindingViewHolderCreator(R.layout.view_children_image, BR.childViewModel)
                )
        );

        childrenList.addOnListChangedCallback(
                ObservableListAdapterObservers.createObservableListAdapterBridge(
                        childrenList, null, recyclerView.getAdapter()
                )
        );

        recyclerView.setTag(R.id.adapterAdded, new Object());
    }

    @BindingAdapter(value = { "android:src", "app:placeholder", "app:placeholderTint" })
    public static void imageUri(ImageView imageView, String uri, Drawable placeholder, int backgroundTint) {
        placeholder.setTint(backgroundTint);

        Picasso.with(imageView.getContext())
                .load(uri)
                .placeholder(placeholder)
                .into(imageView);
    }
}
