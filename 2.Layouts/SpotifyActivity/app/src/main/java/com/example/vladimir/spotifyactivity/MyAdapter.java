package com.example.vladimir.spotifyactivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Song> mDataset;
    final int ITEM_TYPE_DOWNLOAD=0;
    final  int ITEM_TYPE_EXPLICIT=1;


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

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Song> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_TYPE_DOWNLOAD){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_download, parent, false);
            return new ViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_explicit, parent, false);
            return new ViewHolder(v);
        }
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
}
