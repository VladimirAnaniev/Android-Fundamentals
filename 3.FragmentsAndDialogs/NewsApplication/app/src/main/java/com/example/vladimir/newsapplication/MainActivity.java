package com.example.vladimir.newsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ArticleAdapter.ILeftFragmentHandler {

    LeftFragment leftFragment;
    RightFragment rightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leftFragment = (LeftFragment) getFragmentManager().findFragmentById(R.id.left_fragment);
        rightFragment = (RightFragment) getFragmentManager().findFragmentById(R.id.right_fragment);
    }


    @Override
    public void onMenuItemClick(Integer index) {
        Article article = leftFragment.getmArticles().get(index);
        rightFragment.changeArticle(article);
    }
}
