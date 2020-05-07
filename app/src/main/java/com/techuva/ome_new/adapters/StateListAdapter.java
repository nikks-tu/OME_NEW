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
import com.techuva.ome_new.object_models.StateListResultObject;

import java.util.List;

public class StateListAdapter extends ArrayAdapter<StateListResultObject> {

    private int selectedPosition = -1;
    LayoutInflater flater;
    List<StateListResultObject> list;

    public StateListAdapter(Activity context, int resouceId, int textviewId, List<StateListResultObject> list){
        super(context, resouceId, textviewId, list);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StateListResultObject rowItem = getItem(position);

        View rowview = flater.inflate(R.layout.item_state_list,null,true);

        selectedPosition = rowItem.getStateId();
        TextView txtTitle = (TextView) rowview.findViewById(R.id.tv_state_name);
        txtTitle.setText(rowItem.getStateName());
        Typeface faceLight = Typeface.createFromAsset(rowview.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        txtTitle.setTypeface(faceLight);
        return rowview;
    }


    @Override
    public StateListResultObject getItem(int position) {
        return super.getItem(position);
    }

    public int ReturnPosition( String value)
    {
        return selectedPosition;
    }
}