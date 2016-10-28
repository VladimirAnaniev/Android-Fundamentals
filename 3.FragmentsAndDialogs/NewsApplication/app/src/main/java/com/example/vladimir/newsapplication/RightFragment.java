package com.example.vladimir.newsapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RightFragment extends Fragment {
    private TextView mTitle;
    private TextView mDetails;
    private TextView mContent;
    private ImageView mImageView;

    public void changeArticle(Article article){
        mTitle.setText(article.getTitle());
        mDetails.setText(article.getDetails());
        mContent.setText(article.getContent());
        mImageView.setImageResource(article.getImage());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_side_fragment,container,false);

        mTitle = (TextView) view.findViewById(R.id.article_title);
        mDetails = (TextView) view.findViewById(R.id.article_details);
        mContent = (TextView) view.findViewById(R.id.article_content);
        mImageView = (ImageView) view.findViewById(R.id.imageView);

        return view;
    }
}