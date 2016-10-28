package com.example.vladimir.worldtour;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Vladimir on 14-Sep-16.
 */
public class CapitalDetailsFragment extends Fragment {

    TextView mName;
    ImageView mImage;
    TextView mInfo;
    Capital mCapital;

    public void setCapital(Capital c){
        mCapital = c;
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
        View view = inflater.inflate(R.layout.capital_details_fragment,container,false);

        mName = (TextView) view.findViewById(R.id.capital_name);
        mImage = (ImageView) view.findViewById(R.id.capital_image);
        mInfo = (TextView) view.findViewById(R.id.capital_info);

        getActivity().setTitle(mCapital.getName());

        mName.setText(mCapital.getName());
        mImage.setImageResource(mCapital.getPicture());
        mInfo.setText(mCapital.getInfo());

        return view;
    }
}
