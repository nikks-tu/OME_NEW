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


public class DealersFilterAdapter extends ArrayAdapter<GetDealerResultObject> {

    private int selectedPosition = -1;
    LayoutInflater flater;
    List<GetDealerResultObject> list;

    public DealersFilterAdapter(AppCompatActivity context, int resouceId, int textviewId, List<GetDealerResultObject> list){

        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GetDealerResultObject rowItem = getItem(position);

        View rowview = flater.inflate(R.layout.item_dealers,null,true);

        selectedPosition = rowItem.getDealerId();

        TextView txtTitle = (TextView) rowview.findViewById(R.id.tv_dealers_name);
        txtTitle.setText(rowItem.getDealerName());
        txtTitle.setTextColor(getContext().getResources().getColor(R.color.white));
        Typeface faceLight = Typeface.createFromAsset(rowview.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        txtTitle.setTypeface(faceLight);
        return rowview;

    }

    public int getSelectedItem() {
        if ( selectedPosition!= -1) {
            // Toast.makeText(context, "Selected Item : " + arrayList.get(selectedPosition).getCompanyName(), Toast.LENGTH_SHORT).show();
            return selectedPosition;
        }
        return 0;
    }

    @Override
    public GetDealerResultObject getItem(int position) {
        return super.getItem(position);
    }
}