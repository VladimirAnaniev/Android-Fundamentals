package com.example.vladimir.spotifyactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Song> mDataset;
    final int ITEM_TYPE_DOWNLOAD=0;
    final  int ITEM_TYPE_EXPLICIT=1;
    private IRecyclerViewHandler mCallback;
    private int mCounter = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameView;
        public TextView mAuthorView;
        public TextView mSubNameView;

        public ViewHolder(View v) {
            super(v);
            mNameView = (TextView) v.findViewById(R.id.Name);
            mAuthorView = (TextView) v.findViewById(R.id.Author);
            mSubNameView = (TextView) v.findViewById(R.id.Album);
        }
    }

    public interface IRecyclerViewHandler{
        public void onSongClicked(Integer index);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Song> myDataset, IRecyclerViewHandler callback) {
        mDataset = myDataset;
        mCallback=callback;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if(viewType==ITEM_TYPE_DOWNLOAD){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_download, parent, false);
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_explicit, parent, false);
        }

        v.setOnClickListener(this);
        v.setId(mCounter);
        mCounter++;
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mNameView.setText(mDataset.get(position).getName());
        holder.mAuthorView.setText(mDataset.get(position).getAuthor());
        holder.mSubNameView.setText(mDataset.get(position).getAlbum());
    }

    //Set view type depending on position
    @Override
    public int getItemViewType(int position) {
        if (position%2==0) {
            return ITEM_TYPE_DOWNLOAD;
        } else {
            return ITEM_TYPE_EXPLICIT;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onClick(View v) {
        mCallback.onSongClicked(v.getId());
    }
}
