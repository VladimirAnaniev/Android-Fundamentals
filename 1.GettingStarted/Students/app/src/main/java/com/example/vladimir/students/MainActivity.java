package com.example.vladimir.students;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onClick (View view) {
        if(view!=null){
            Intent intent = new Intent(this,DetailsActivity.class);

            if(view.getId()==R.id.Student1){
                Student student = new Student("Иван Иванов",40,"SoftUni");
                intent.putExtra("STUDENT_DETAILS",student);

            }

            if(view.getId()==R.id.Student2){
                Student student = new Student("Габриела Стефанова",25,"ТУ София");
                intent.putExtra("STUDENT_DETAILS",student);
            }

            if(view.getId()==R.id.Student3){
                Student student = new Student("Димитър Димитров",19,"УНСС");
                intent.putExtra("STUDENT_DETAILS",student);
            }

            if(view.getId()==R.id.Student4){
                Student student = new Student("Александра Петрова",22,"Софийски Университет");
                intent.putExtra("STUDENT_DETAILS",student);
            }

            startActivity(intent);
        }
    }
}
