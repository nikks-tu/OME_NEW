package com.techuva.ome_new.adapters;

import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.techuva.ome_new.R;
import com.techuva.ome_new.object_models.GetDealerResultObject;

import java.util.List;

public class DealersAdapter extends ArrayAdapter<GetDealerResultObject> {

    private int selectedPosition = -1;
    LayoutInflater flater;
    List<GetDealerResultObject> list;
    private String dealerName = "";
    TextView txtTitle;
    List<GetDealerResultObject> list1;

    public DealersAdapter(AppCompatActivity context, int resouceId, int textviewId, List<GetDealerResultObject> list){

        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();
        list1=list;
    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowview = flater.inflate(R.layout.item_dealers,null,true);

        GetDealerResultObject rowItem = getItem(position);
        selectedPosition = position;
        dealerName = rowItem.getDealerName();
        txtTitle= (TextView) rowview.findViewById(R.id.tv_dealers_name);
        txtTitle.setTag(rowItem.getDealerId());
        txtTitle.setText(rowItem.getDealerName());
        Typeface faceLight = Typeface.createFromAsset(rowview.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        txtTitle.setTypeface(faceLight);
        return rowview;

    }

    public int getSelectedItem() {
        if ( selectedPosition!= -1) {

            return (int) txtTitle.getTag();
        }
        return 0;
    }
}