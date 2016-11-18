package me.darrillaga.prototype.viewer.shelters.view;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import me.darrillaga.databinding.utils.ObservableListAdapterObservers;
import me.darrillaga.prototype.commons.ui.BaseAdapter;
import me.darrillaga.prototype.commons.ui.DataBindingAdapterUtils;
import me.darrillaga.prototype.viewer.BR;
import me.darrillaga.prototype.viewer.R;
import me.darrillaga.prototype.viewer.shelters.viewmodel.SheltersItemViewModel;

public class SheltersBindingUtils {

    @BindingAdapter(value = { "app:dataSet", "app:eventsHandler" })
    public static void bindDataSet(RecyclerView recyclerView, ObservableList<SheltersItemViewModel> sheltersItemViewModels, ShelterEventsHandler eventsHandler) {

        if (recyclerView.getTag(R.id.adapterAdded) != null) {
            return;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setAutoMeasureEnabled(true);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(
                new BaseAdapter<>(
                        sheltersItemViewModels,
                        (viewHolder, data) -> viewHolder.bindTo(data).execute(
                                element -> element.setVariable(BR.eventsHandler, eventsHandler)
                        ).bind(),
                        DataBindingAdapterUtils.createDataBindingViewHolderCreator(R.layout.view_shelter_item, BR.recipesItemViewModel)
                )
        );

        sheltersItemViewModels.addOnListChangedCallback(
                ObservableListAdapterObservers.createObservableListAdapterBridge(
                        sheltersItemViewModels, null, recyclerView.getAdapter()
                )
        );

        recyclerView.setTag(R.id.adapterAdded, new Object());
    }

    @BindingAdapter(value = { "app:dataSet", "app:eventsHandler"}, requireAll = false)
    public static void bindDataSet(LineChart lineChart, SheltersItemViewModel sheltersItemViewModel, ShelterEventsHandler eventsHandler) {

        if (sheltersItemViewModel.getEntryList().isEmpty()) {
            lineChart.setData(null);
            lineChart.invalidate();
            return;
        }

        LineDataSet dataSet = new LineDataSet(sheltersItemViewModel.getEntryList(), ""); // add entries to dataset

        dataSet.setColor(ContextCompat.getColor(lineChart.getContext(), R.color.primary));
        dataSet.setValueTextColor(ContextCompat.getColor(lineChart.getContext(), R.color.primary));

        dataSet.setLineWidth(4);
        dataSet.setDrawCircles(false);
        dataSet.setDrawCircleHole(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineDataSet estimatedDataSet = new LineDataSet(sheltersItemViewModel.getEstimatedEntryList(), ""); // add entries to dataset

        if (sheltersItemViewModel.getCalculatedFinalDays() < sheltersItemViewModel.getMaxExpectedDays()) {
            estimatedDataSet.setColor(ContextCompat.getColor(lineChart.getContext(), R.color.yellow_500));
            estimatedDataSet.setValueTextColor(ContextCompat.getColor(lineChart.getContext(), R.color.yellow_500));
        } else if (sheltersItemViewModel.getCalculatedFinalDays() > sheltersItemViewModel.getMaxExpectedDays()) {
            estimatedDataSet.setColor(ContextCompat.getColor(lineChart.getContext(), R.color.red_500));
            estimatedDataSet.setValueTextColor(ContextCompat.getColor(lineChart.getContext(), R.color.red_500));
        } else {
            estimatedDataSet.setColor(ContextCompat.getColor(lineChart.getContext(), R.color.primary));
            estimatedDataSet.setValueTextColor(ContextCompat.getColor(lineChart.getContext(), R.color.primary));
        }

        estimatedDataSet.setLineWidth(4);
        estimatedDataSet.setDrawCircles(false);
        estimatedDataSet.setDrawCircleHole(false);
        estimatedDataSet.enableDashedLine(8, 8, 0);

        LineData lineData = new LineData(dataSet, estimatedDataSet);
        lineData.setDrawValues(false);

        lineChart.setData(lineData);

        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setScaleXEnabled(false);
        lineChart.setScaleYEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(false);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setAxisMaxValue(Math.max(sheltersItemViewModel.getMaxExpectedDays(), sheltersItemViewModel.getCalculatedFinalDays()));

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

        lineChart.invalidate(); // refresh
    }

    @BindingAdapter(value = { "app:dataSetProfitability", "app:eventsHandler"}, requireAll = false)
    public static void bindDataSetProfitability(LineChart lineChart, SheltersItemViewModel sheltersItemViewModel, ShelterEventsHandler eventsHandler) {

        if (sheltersItemViewModel.getEntryList().isEmpty()) {
            lineChart.setData(null);
            lineChart.invalidate();
            return;
        }

        List<Entry> data = new ArrayList<>();
        data.add(new Entry(0, 400));
        data.add(new Entry(40, 420));

        LineDataSet dataSet = new LineDataSet(data, ""); // add entries to dataset
        dataSet.setColor(ContextCompat.getColor(lineChart.getContext(), R.color.primary));
        dataSet.setValueTextColor(ContextCompat.getColor(lineChart.getContext(), R.color.primary));

        dataSet.setLineWidth(4);
        dataSet.setDrawCircles(false);
        dataSet.setDrawCircleHole(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(dataSet);
        lineData.setDrawValues(false);

        lineChart.setData(lineData);

        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setScaleXEnabled(false);
        lineChart.setScaleYEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(false);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setAxisMaxValue(Math.max(sheltersItemViewModel.getMaxExpectedDays(), sheltersItemViewModel.getCalculatedFinalDays()));

        lineChart.getAxisLeft().setAxisMinValue(sheltersItemViewModel.getInitialAverageWeightInKg());
        lineChart.getAxisLeft().setAxisMaxValue(sheltersItemViewModel.getFinalAverageWeightInKg());

        lineChart.getXAxis().setDrawGridLines(false);

        lineChart.getAxisLeft().setDrawGridLines(false);

        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawLabels(false);

        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisRight().setDrawLabels(false);

        lineChart.setDescription(null);

        lineChart.invalidate(); // refresh
    }
}