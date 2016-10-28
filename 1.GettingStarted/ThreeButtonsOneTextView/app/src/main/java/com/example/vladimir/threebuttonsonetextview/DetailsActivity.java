package com.example.vladimir.threebuttonsonetextview;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class DetailsActivity extends Activity {

    TextView mId;
    TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mId=(TextView) findViewById(R.id.textView2);
        mName=(TextView) findViewById(R.id.textView3);

        if(getIntent()!=null){
            mId.setText(String.valueOf(getIntent().getIntExtra("BUTTON_ID",-1)));
            mName.setText(getIntent().getStringExtra("BUTTON_TEXT"));
        }
    }
}
