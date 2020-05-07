package com.techuva.ome_new.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.techuva.ome_new.R;
import com.techuva.ome_new.views.MApplication;

public class NoInternetConnectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    TextView tv_retry;
    TextView tv_no_internet;
    FragmentManager fragmentManager;

    public NoInternetConnectionFragment() {
        // Required empty public constructor
    }

    public static NoInternetConnectionFragment newInstance() {
        NoInternetConnectionFragment fragment = new NoInternetConnectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_no_interent_connection, null, false);
        tv_retry= contentView.findViewById(R.id.tv_retry);
        tv_no_internet= contentView.findViewById(R.id.tv_no_internet);
        Typeface faceLight = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        tv_retry.setTypeface(faceLight);
        tv_no_internet.setTypeface(faceLight);
        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MApplication.isNetConnected(getContext()))
                {
                    loadFragment(Dashboard.newInstance(), "Home");
                }
                else {
                    Toast.makeText(getContext(), "Please check internet!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return  contentView;
    }
    public  void loadFragment(Fragment fragment, String tag)
    {
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment, tag)
                .commit();
    }
}
