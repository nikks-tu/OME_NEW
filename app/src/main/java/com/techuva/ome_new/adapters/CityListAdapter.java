package com.techuva.ome_new.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.techuva.ome_new.R;
import com.techuva.ome_new.object_models.CityNamesObject;
import com.techuva.ome_new.object_models.StateListResultObject;

import java.util.List;

public class CityListAdapter extends ArrayAdapter<CityNamesObject> {

    private int selectedPosition = -1;
    LayoutInflater flater;
    List<StateListResultObject> list;

    public CityListAdapter(Activity context, int resouceId, int textviewId, List<CityNamesObject> list){

        super(context, resouceId, textviewId, list);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CityNamesObject rowItem = getItem(position);

        View rowview = flater.inflate(R.layout.item_city_list,null,true);

        selectedPosition = rowItem.getCityId();
        TextView txtTitle = (TextView) rowview.findViewById(R.id.tv_city_name);
        Typeface faceLight = Typeface.createFromAsset(rowview.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        txtTitle.setTypeface(faceLight);
        txtTitle.setText(rowItem.getCityName());
        return rowview;
    }


    @Override
    public CityNamesObject getItem(int position) {
        return super.getItem(position);
    }
}