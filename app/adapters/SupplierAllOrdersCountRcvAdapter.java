package com.example.ordermadeeasy.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.holders.SupplierOrdersCountRCVHolder;
import com.example.ordermadeeasy.object_models.SupplierAllOrdersCountObject;

import java.util.ArrayList;

public class SupplierAllOrdersCountRcvAdapter extends RecyclerView.Adapter<SupplierOrdersCountRCVHolder>
{
    private ArrayList<SupplierAllOrdersCountObject> arrayList;
    private Context context;
    private SupplierOrdersCountRCVHolder listHolder;
    int selectedPosition = 0;
    public String selectedDate="";

    public SupplierAllOrdersCountRcvAdapter(Context context, ArrayList<SupplierAllOrdersCountObject> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
       // return (null != arrayList ? arrayList.size() : 0);
        return arrayList.size();

    }

    @Override
    public void onBindViewHolder(SupplierOrdersCountRCVHolder holder, int position) {
        SupplierAllOrdersCountObject model = arrayList.get(position);
        if(selectedPosition==position)
            holder.cv_day_view.setBackgroundColor(context.getResources().getColor(R.color.text_color_light_op));
        else
            holder.cv_day_view.setBackgroundColor(context.getResources().getColor(R.color.white));

         holder.tv_order_count.setText(String.valueOf(model.getTotal_Orders()));
         holder.tv_order_status.setText(model.getSTATUS());
    }

    @Override
    public SupplierOrdersCountRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_supplier_order_count, viewGroup, false);

        listHolder = new SupplierOrdersCountRCVHolder(mainGroup);
        return listHolder;

    }

    public String getSelectedItem() {
        if ( selectedPosition!= -1) {
            // Toast.makeText(context, "Selected Item : " + arrayList.get(selectedPosition).getCompanyName(), Toast.LENGTH_SHORT).show();
            return String.valueOf(selectedDate);
        }
        else
            return "";
    }
}