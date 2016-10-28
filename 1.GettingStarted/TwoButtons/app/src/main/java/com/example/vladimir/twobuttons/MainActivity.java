package com.example.vladimir.twobuttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mFirstButton;
    Integer mFirstButtonClicks;
    Integer mSecondButtonClicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirstButton = (Button) findViewById(R.id.button);
        mFirstButtonClicks=0;
        mSecondButtonClicks=0;

        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirstButtonClicks++;
                mFirstButton.setText(mFirstButtonClicks.toString());
            }
        });
    }

    protected void onSecondButtonClick (View view) {
        if(view.getId()==R.id.button2){
            mSecondButtonClicks++;
            Button mSecondButton=(Button)view;
            mSecondButton.setText(mSecondButtonClicks.toString());
        }
    }
}
