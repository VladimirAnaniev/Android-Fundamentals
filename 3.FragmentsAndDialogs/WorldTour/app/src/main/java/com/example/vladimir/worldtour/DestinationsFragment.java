package com.example.vladimir.worldtour;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Vladimir on 14-Sep-16.
 */
public class DestinationsFragment  extends Fragment implements View.OnClickListener {

    private ArrayList<Button> mDestinations;
    private IDestinationsHandler mCallback;

    public interface IDestinationsHandler{
        void onDestinationClicked(Integer index);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (IDestinationsHandler) context;
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
        View view = inflater.inflate(R.layout.destinations_fragment_layout,container,false);

        //Add all buttons to array
        mDestinations = new ArrayList<>();
        mDestinations.add((Button)view.findViewById(R.id.btnBulgaria));
        mDestinations.add((Button)view.findViewById(R.id.btnGermany));
        mDestinations.add((Button)view.findViewById(R.id.btnItaly));
        mDestinations.add((Button)view.findViewById(R.id.btnFrance));
        mDestinations.add((Button)view.findViewById(R.id.btnChina));
        mDestinations.add((Button)view.findViewById(R.id.btnSpain));
        mDestinations.add((Button)view.findViewById(R.id.btnGreece));

        //set their onClickListeners
        for (Button btn:mDestinations){
            btn.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if(mDestinations.contains((Button)v)){
            mCallback.onDestinationClicked(mDestinations.indexOf(v));
        }
    }
}
