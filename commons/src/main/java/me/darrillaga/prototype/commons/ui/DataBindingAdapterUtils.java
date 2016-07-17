package me.darrillaga.prototype.commons.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

public class DataBindingAdapterUtils {

    public static <T> BaseAdapter<T, DataBindingViewHolder<T>> createAdapter(List<T> list, int layout, int viewModelVariableId) {
        return new BaseAdapter<>(
                list,
                DataBindingAdapterUtils.createDataBindingViewHolderBinder(),
                DataBindingAdapterUtils.createDataBindingViewHolderCreator(layout, viewModelVariableId)
        );
    }

    public static <T, R extends DataBindingViewHolder<T>> BaseAdapter.ViewHolderBinder<T, R> createDataBindingViewHolderBinder() {
        return (viewHolder, data) -> viewHolder.bindTo(data);
    }

    public static <T> BaseAdapter.ViewHolderCreator<DataBindingViewHolder<T>> createDataBindingViewHolderCreator(int layout, int viewModelVariableId) {
        return (parent, viewType) -> new DataBindingViewHolder<>(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    layout,
                    parent,
                    false
                ),
                viewModelVariableId
        );
    }

    public static class DataBindingViewHolder<T> extends RecyclerView.ViewHolder {

        private ViewDataBinding mViewDataBinding;
        private int mViewModelVariableId;

        public DataBindingViewHolder(ViewDataBinding viewDataBinding, int viewModelVariableId) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewModelVariableId = viewModelVariableId;
        }

        public void bindTo(T viewModel) {
            mViewDataBinding.setVariable(mViewModelVariableId, viewModel);

            mViewDataBinding.executePendingBindings();
        }
    }
}
