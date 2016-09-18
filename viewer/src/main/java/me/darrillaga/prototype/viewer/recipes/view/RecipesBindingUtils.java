package me.darrillaga.prototype.viewer.recipes.view;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import me.darrillaga.databinding.utils.ObservableListAdapterObservers;
import me.darrillaga.prototype.commons.ui.BaseAdapter;
import me.darrillaga.prototype.commons.ui.DataBindingAdapterUtils;
import me.darrillaga.prototype.viewer.BR;
import me.darrillaga.prototype.viewer.R;
import me.darrillaga.prototype.viewer.recipes.viewmodel.RecipesItemViewModel;

public class RecipesBindingUtils {

    @BindingAdapter(value = { "app:dataSet", "app:eventsHandler" })
    public static void bindDataSet(RecyclerView recyclerView, ObservableList<RecipesItemViewModel> mRecipesItemViewModels, RecipesEventsHandler eventsHandler) {

        if (recyclerView.getTag(R.id.adapterAdded) != null) {
            return;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setAutoMeasureEnabled(true);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(
                new BaseAdapter<>(
                        mRecipesItemViewModels,
                        (viewHolder, data) -> viewHolder.bindTo(data).execute(
                                element -> element.setVariable(BR.eventsHandler, eventsHandler)
                        ).bind(),
                        DataBindingAdapterUtils.createDataBindingViewHolderCreator(R.layout.view_shelter_item, BR.sheltersItemViewModel)
                )
        );

        mRecipesItemViewModels.addOnListChangedCallback(
                ObservableListAdapterObservers.createObservableListAdapterBridge(
                        mRecipesItemViewModels, null, recyclerView.getAdapter()
                )
        );

        recyclerView.setTag(R.id.adapterAdded, new Object());
    }

    @BindingAdapter(value = { "app:dataSet", "app:eventsHandler" })
    public static void bindDataSet(LineChart lineChart, RecipesItemViewModel sheltersItemViewModel, RecipesEventsHandler eventsHandler) {

        if (sheltersItemViewModel.getEntryList().isEmpty()) {
            lineChart.setData(null);
            lineChart.invalidate();
            return;
        }

        LineDataSet dataSet = new LineDataSet(sheltersItemViewModel.getEntryList(), ""); // add entries to dataset
        dataSet.setColor(ContextCompat.getColor(lineChart.getContext(), R.color.primary));
        dataSet.setValueTextColor(ContextCompat.getColor(lineChart.getContext(), R.color.primary));

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        lineData.setDrawValues(false);

        dataSet.setLineWidth(6);
        dataSet.setDrawCircles(false);
        dataSet.setDrawCircleHole(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setScaleXEnabled(false);
        lineChart.setScaleYEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(false);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setAxisMaxValue(sheltersItemViewModel.getMaxExpectedDays());

        lineChart.getAxisLeft().setAxisMinValue(sheltersItemViewModel.getInitialAverageWeightInKg());
        lineChart.getAxisLeft().setAxisMaxValue(sheltersItemViewModel.getFinalAverageWeightInKg());

        lineChart.getXAxis().setDrawGridLines(false);

        lineChart.getAxisLeft().setDrawGridLines(false);

        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawLabels(false);

        lineChart.getXAxis().setLabelCount(3, true);
        lineChart.getAxisLeft().setLabelCount(3, true);
        lineChart.getAxisRight().setLabelCount(3, true);

        lineChart.setDescription(null);

        lineChart.notifyDataSetChanged();
        lineChart.invalidate(); // refresh
    }
}