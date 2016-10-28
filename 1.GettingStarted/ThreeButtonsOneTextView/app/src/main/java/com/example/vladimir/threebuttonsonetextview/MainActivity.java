package com.example.vladimir.threebuttonsonetextview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button mLastClicked;
    TextView mTextView;
    Integer mClicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    protected void onButtonClick (View view) {
        if(mLastClicked==null || view.getId()!=mLastClicked.getId()){
            mTextView.setText("Button ID: "+view.getId());
            mLastClicked=(Button) view;
            mClicks=1;
        }
        else {
            mClicks++;
            mTextView.setText("You clicked "+mLastClicked.getText() + " "+ mClicks + " Times");
        }

    }

    protected void onTextClick (View view) {
        Intent intent = new Intent(this, DetailsActivity.class);

        intent.putExtra("BUTTON_ID", mLastClicked.getId());
        intent.putExtra("BUTTON_TEXT", mLastClicked.getText());

        startActivity(intent);
    }
}
