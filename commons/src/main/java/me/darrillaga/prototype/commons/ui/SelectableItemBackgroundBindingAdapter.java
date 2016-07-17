package me.darrillaga.prototype.commons.ui;

import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;

import me.darrillaga.prototype.commons.R;

public class SelectableItemBackgroundBindingAdapter {

    @BindingAdapter(value = { "app:selectableItemBackground" })
    public static void setSelectableItemBackground(View view, int drawableItemId) {
        LayerDrawable layerDrawable = (LayerDrawable) view.getBackground();
        Drawable drawableToChange = layerDrawable.findDrawableByLayerId(drawableItemId);

        if (view.getTag(R.id.selectableItemBackground) != null || drawableToChange == null) {
            return;
        }

        TypedArray typedArray = view.getContext().obtainStyledAttributes(new int[] { R.attr.selectableItemBackground});
        Drawable drawable = typedArray.getDrawable(0);
        typedArray.recycle();

        drawable.setBounds(drawableToChange.getBounds());
        layerDrawable.setDrawableByLayerId(drawableItemId, drawable);

        layerDrawable = copyLayerDrawable(layerDrawable);

        setBackgroundKeepingPadding(view, layerDrawable);

        view.setTag(R.id.selectableItemBackground, new Object());
    }

    private static LayerDrawable copyLayerDrawable(LayerDrawable layerDrawable) {
        Drawable[] drawables = new Drawable[layerDrawable.getNumberOfLayers()];

        for (int index = 0; index < layerDrawable.getNumberOfLayers(); index++) {
            drawables[index] = layerDrawable.getDrawable(index);
        }

        LayerDrawable newLayerDrawable = new LayerDrawable(drawables);
        newLayerDrawable.setState(layerDrawable.getState());
        newLayerDrawable.setBounds(layerDrawable.getBounds());

        return newLayerDrawable;
    }

    private static void setBackgroundKeepingPadding(View item, Drawable drawable) {
        if (item != null) {
            int paddingTop = item.getPaddingTop();
            int paddingLeft = item.getPaddingLeft();
            int paddingRight = item.getPaddingRight();
            int paddingBottom = item.getPaddingBottom();

            item.setBackgroundDrawable(drawable);

            item.setPadding(paddingLeft, paddingTop, paddingRight,
                    paddingBottom);
        }
    }
}
