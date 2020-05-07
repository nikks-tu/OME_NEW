package com.example.ordermadeeasy.adapters;

import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.object_models.OrderStatusResultObject;

import java.util.List;

public class OrderStatusFilterAdapter extends ArrayAdapter<OrderStatusResultObject> {

    private int selectedPosition = -1;
    LayoutInflater flater;
    List<OrderStatusResultObject> list;

    public OrderStatusFilterAdapter(AppCompatActivity context, int resouceId, int textviewId, List<OrderStatusResultObject> list){
        super(context,resouceId, textviewId, list);
        flater = context.getLayoutInflater();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OrderStatusResultObject rowItem = getItem(position);

        View rowview = flater.inflate(R.layout.item_order_status,null,true);

        selectedPosition = rowItem.getStatusId();
        LinearLayout ll_main = (LinearLayout) rowview.findViewById(R.id.ll_main_layout);
         TextView txtTitle = (TextView) rowview.findViewById(R.id.tv_status_name);
        Typeface faceLight = Typeface.createFromAsset(rowview.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        txtTitle.setTypeface(faceLight);
       /* if(rowItem.getStatusId()== 1)
        {
            ll_main.setBackground(getContext().getDrawable(R.drawable.pending_backgound));
        }
        else if(rowItem.getStatusId()==2)
        {
            ll_main.setBackground(getContext().getDrawable(R.drawable.received_backgound));
        }
        else if(rowItem.getStatusId()==3)
        {
            ll_main.setBackground(getContext().getDrawable(R.drawable.part_disp_backgound));
        }
        else if(rowItem.getStatusId()==4)
        {
            ll_main.setBackground(getContext().getDrawable(R.drawable.dispatched_backgound));
        }
        else if(rowItem.getStatusId()==5)
        {
            ll_main.setBackground(getContext().getDrawable(R.drawable.canceled_backgound));
        }
        else if(rowItem.getStatusId()==6)
        {
            ll_main.setBackground(getContext().getDrawable(R.drawable.part_rece_backgound));
        }*/
        txtTitle.setText(rowItem.getStatusDescription());
        txtTitle.setTextColor(getContext().getResources().getColor(R.color.white));
        return rowview;
    }

    public int getSelectedItem() {
        if ( selectedPosition!= -1) {
            // Toast.makeText(context, "Selected Item : " + arrayList.get(selectedPosition).getCompanyName(), Toast.LENGTH_SHORT).show();
            return selectedPosition;
        }
        return 0;
    }
}