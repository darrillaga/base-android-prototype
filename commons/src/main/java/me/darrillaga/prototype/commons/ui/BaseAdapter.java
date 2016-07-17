package me.darrillaga.prototype.commons.ui;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class BaseAdapter<T, R extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<R> {

    private List<T> mObjects;
    private ViewHolderBinder<T, R> mViewHolderBinder;
    private ViewHolderCreator<R> mViewHolderCreator;

    public BaseAdapter(List<T> objects, ViewHolderBinder<T, R> viewHolderBinder, ViewHolderCreator<R> viewHolderCreator) {
        mObjects = objects;
        mViewHolderBinder = viewHolderBinder;
        mViewHolderCreator = viewHolderCreator;
    }

    @Override
    public R onCreateViewHolder(ViewGroup parent, int viewType) {
        return mViewHolderCreator.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(R holder, int position) {
        mViewHolderBinder.bindTo(holder, mObjects.get(position));
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    public interface ViewHolderBinder<T, R extends RecyclerView.ViewHolder> {
        void bindTo(R viewHolder, T data);
    }

    public interface ViewHolderCreator<T extends RecyclerView.ViewHolder> {
        T createViewHolder(ViewGroup parent, int viewType);
    }
}
