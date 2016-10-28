package com.example.vladimir.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdit;
    private Button mAdd;
    private TextView mTotalElements;
    private Button mLoad;
    private Integer mElements;
    private DbManager mDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdit = (EditText) findViewById(R.id.editText);
        mAdd = (Button) findViewById(R.id.add_btn);
        mTotalElements = (TextView) findViewById(R.id.total_text);
        mLoad = (Button) findViewById(R.id.load_btn);

        mElements = 0;

        mDbManager = DbManager.getInstance(this);

        mAdd.setOnClickListener(this);
        mLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(mAdd)){
            try{
                int elementsToAdd=Integer.parseInt(mEdit.getText().toString());
                addElements(elementsToAdd);
                mElements+=elementsToAdd;
                mTotalElements.setText(mElements+" elements currently");
            }
            catch (NumberFormatException ex){
                Toast.makeText(this,"Please type a number first.",Toast.LENGTH_SHORT).show();
            }

        }
        else if(v.equals(mLoad)){
            Intent intent = new Intent(this,RecyclerViewActivity.class);
            intent.putExtra("elements",mElements);
            startActivity(intent);
        }

    }

    private void addElements(int elementsToAdd) {
        mDbManager.addItems(elementsToAdd);
    }


}
