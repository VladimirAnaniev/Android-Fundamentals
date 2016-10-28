package com.example.vladimir.students;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Vladimir on 31-Aug-16.
 */
public class DetailsActivity extends Activity {

    TextView mName,mAge,mShool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mName = (TextView) findViewById(R.id.Name);
        mAge = (TextView) findViewById(R.id.Age);
        mShool = (TextView) findViewById(R.id.School);

        if(getIntent()!=null){
            Student student = (Student) getIntent().getParcelableExtra("STUDENT_DETAILS");

            mName.setText("Име: "+student.getName());
            mAge.setText("Години: "+String.valueOf(student.getAge()));
            mShool.setText("Учебно заведение: "+student.getSchool());
        }
    }
}
