package com.example.ordermadeeasy.adapters;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.holders.PlaceOrderRCVHolder;
import com.example.ordermadeeasy.object_models.PlaceOrderResultObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderRCVHolder>
{// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<PlaceOrderResultObject> arrayList;
    private Context context;
    private PlaceOrderRCVHolder listHolder;
    private CompoundButton lastCheckedRB = null;
    private String UserName="";
    private int selectedPosition = -1;
    private OnItemClicked listener;

    public PlaceOrderAdapter(Context context, ArrayList<PlaceOrderResultObject> arrayList, OnItemClicked listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        // return (null != arrayList ? arrayList.size() : 0);
        return arrayList.size();

    }

    @Override
    public void onBindViewHolder(PlaceOrderRCVHolder holder, int position) {
        PlaceOrderResultObject model = arrayList.get(position);
        Activity activity = (Activity) context;

            holder.tv_product_name.setText(model.getCustomer_name());
            holder.tv_product_desc.setText(model.getDisplay_name());
            holder.tv_dealers_name.setText(model.getDealerName());
            holder.tv_qty.setText(model.getQuantity().toString());

        if(!model.getImage_url().equals(""))
        {
            Picasso.with(context).load(model.getImage_url()).into(holder.iv_product_image);
        }
        else
            Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);

    }

    @Override
    public PlaceOrderRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_place_order_list, viewGroup, false);
        listHolder = new PlaceOrderRCVHolder(mainGroup);
        return listHolder;

    }


    public interface OnItemClicked {
        void onItemClick(View view, int position);
    }

    public String getSelectedItem() {
        if ( selectedPosition!= -1) {
            // Toast.makeText(context, "Selected Item : " + arrayList.get(selectedPosition).getCompanyName(), Toast.LENGTH_SHORT).show();
            return String.valueOf(selectedPosition);
        }
        return "";
    }
}