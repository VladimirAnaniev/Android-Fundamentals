package com.example.vladimir.newsapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Article> mArticles;
    private ILeftFragmentHandler mCallback;

    public interface ILeftFragmentHandler{
        void onMenuItemClick(Integer index);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleText;

        public ViewHolder(View v) {
            super(v);
            mTitleText = (TextView) v.findViewById(R.id.menu_article_title);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArticleAdapter(ArrayList<Article> Articles, ILeftFragmentHandler callback) {
        mArticles = Articles;
        mCallback = callback;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_fragment_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitleText.setText(mArticles.get(position).getTitle());
        holder.mTitleText.setId(position);
        holder.mTitleText.setOnClickListener(this);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public void onClick(View v) {
        mCallback.onMenuItemClick(v.getId());
    }
}
