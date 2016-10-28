package com.example.vladimir.homework;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vladimir on 29-Sep-16.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Item> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mMainText;
        public TextView mSubText;

        public ViewHolder(View v) {
            super(v);
            mMainText = (TextView) v.findViewById(R.id.recycler_view_text);
            mSubText = (TextView) v.findViewById(R.id.recycler_view_subtext);
        }
    }

    public Adapter(ArrayList<Item> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        holder.mMainText.setText(mDataset.get(position).getMainText());
        holder.mSubText.setText(mDataset.get(position).getSubText());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
