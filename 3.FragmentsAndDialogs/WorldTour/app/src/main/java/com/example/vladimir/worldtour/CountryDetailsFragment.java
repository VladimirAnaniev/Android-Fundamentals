package com.example.vladimir.worldtour;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Vladimir on 14-Sep-16.
 */
public class CountryDetailsFragment extends Fragment implements View.OnClickListener {

    TextView mName;
    ImageView mFlag;
    TextView mLanguage;
    Button mCapital;
    TextView mInfo;
    ICountryHandler mContext;
    Integer mIndex;
    Destination mDestination;

    public interface ICountryHandler{
        void onCapitalBtnClick(Integer index);
    }

    public void setCountry(Destination d, Integer index){
        mDestination = d;
        mIndex = index;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (ICountryHandler) context;
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
        View view = inflater.inflate(R.layout.country_details_fragment,container,false);

        mName = (TextView) view.findViewById(R.id.country_name);
        mFlag = (ImageView) view.findViewById(R.id.country_flag);
        mLanguage = (TextView) view.findViewById(R.id.country_language);
        mCapital = (Button) view.findViewById(R.id.country_capital);
        mInfo = (TextView) view.findViewById(R.id.country_info);

        getActivity().setTitle(mDestination.getName());

        mName.setText(mDestination.getName());
        mFlag.setImageResource(mDestination.getFlag());
        mLanguage.setText("Language: "+ mDestination.getLanguage());
        mInfo.setText(mDestination.getInformation());
        mCapital.setText("Capital: " + mDestination.getCapital().getName());

        mCapital.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==mCapital.getId()){
            mContext.onCapitalBtnClick(mIndex);
        }
    }
}
