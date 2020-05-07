package com.example.ordermadeeasy.adapters;

import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.object_models.QuatityList;

import java.util.List;

public class QuantityAdapter extends ArrayAdapter<QuatityList> {

    LayoutInflater flater;
    int selectedPosition =-1;

    public QuantityAdapter(AppCompatActivity context, int resouceId, int textviewId, List<QuatityList> list){

        super(context, resouceId, textviewId, list);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        QuatityList rowItem = getItem(position);

        View rowview = flater.inflate(R.layout.item_qty,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(R.id.tv_qty);
        txtTitle.setTextColor(getContext().getResources().getColor(R.color.menu_text_color));
        txtTitle.setText(String.valueOf(rowItem.getQuantity()));
        Typeface faceLight = Typeface.createFromAsset(rowview.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        txtTitle.setTypeface(faceLight);
        selectedPosition = position;

        return rowview;
    }


    public int getPosition(int quantity) {
        return selectedPosition;

    }
}