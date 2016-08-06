package me.darrillaga.prototype.commons.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

public class DataBindingAdapterUtils {

    public static <T, BINDING extends ViewDataBinding> BaseAdapter<T, DataBindingViewHolder<T, BINDING>> createAdapter(List<T> list, int layout, int viewModelVariableId) {
        return new BaseAdapter<>(
                list,
                DataBindingAdapterUtils.createDataBindingViewHolderBinder(),
                DataBindingAdapterUtils.createDataBindingViewHolderCreator(layout, viewModelVariableId)
        );
    }

    public static <T, BINDING extends ViewDataBinding, R extends DataBindingViewHolder<T, BINDING>> BaseAdapter.ViewHolderBinder<T, R> createDataBindingViewHolderBinder() {
        return (viewHolder, data) -> viewHolder.bindTo(data).bind();
    }

    public static <T, BINDING extends ViewDataBinding> BaseAdapter.ViewHolderCreator<DataBindingViewHolder<T, BINDING>> createDataBindingViewHolderCreator(int layout, int viewModelVariableId) {
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

    public static class DataBindingViewHolder<T, BINDING extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private BINDING mViewDataBinding;
        private int mViewModelVariableId;

        public DataBindingViewHolder(BINDING viewDataBinding, int viewModelVariableId) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewModelVariableId = viewModelVariableId;
        }

        public DataBindingViewHolder<T, BINDING> bindTo(T viewModel) {
            mViewDataBinding.setVariable(mViewModelVariableId, viewModel);

            return this;
        }

        public DataBindingViewHolder<T, BINDING> execute(Action1<BINDING> bindingAction) {
            bindingAction.call(mViewDataBinding);

            return this;
        }

        public void bind() {
            mViewDataBinding.executePendingBindings();
        }
    }

    public interface Action1<T> {
        void call(T element);
    }
}
