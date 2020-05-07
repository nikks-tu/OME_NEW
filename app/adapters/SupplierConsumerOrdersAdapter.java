package com.example.ordermadeeasy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.domain.PlaceOrderDO;
import com.example.ordermadeeasy.holders.AllOrderRCVHolder;
import com.example.ordermadeeasy.object_models.SupplierConsumerOrderResultObject;
import com.example.ordermadeeasy.utilities.CartDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SupplierConsumerOrdersAdapter extends BaseAdapter
{
    private ArrayList<SupplierConsumerOrderResultObject> arrayList;
    private Context context;
    private AllOrderRCVHolder listHolder;
    private int selectedPosition = -1;
    private AllOrdersListAdapter.OnItemClicked listener;
    private PlaceOrderDO placeOrderDO;
    int qty = 05;
    int totalDealers= 0;
    String dealerName="";
    CartDatabase cartDatabase;
    OrderStatusAdapter orderStatusAdapter;
    AppCompatActivity activity;
    String demandOrderId, statusId, receivedQuantity;
    String accessToken ="";
    int pos;
    private AllOrdersListAdapter.EventListener eventListener;

    public SupplierConsumerOrdersAdapter(AppCompatActivity activity, Context context, ArrayList<SupplierConsumerOrderResultObject> arrayList) {
        this.activity = activity;
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SupplierConsumerOrdersAdapter.ViewHolder holder = null;
        View view = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.item_consumer_supplier_new, parent, false);
        } else {
            view = convertView;

        }
        holder = new SupplierConsumerOrdersAdapter.ViewHolder();
        holder.tv_order_id =  view.findViewById(R.id.tv_order_id);
        holder.tv_product_count =  view.findViewById(R.id.tv_product_count);
        holder.tv_total_bill =  view.findViewById(R.id.tv_total_bill);
        holder.tv_order_date =  view.findViewById(R.id.tv_order_date);
        holder.tv_view_details =  view.findViewById(R.id.tv_view_details);
        holder.tv_total_items =  view.findViewById(R.id.tv_total_items);
        holder.tv_order_status =  view.findViewById(R.id.tv_order_status);

        Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        holder.tv_order_id.setTypeface(facebold);
        holder.tv_product_count.setTypeface(facebold);
        holder.tv_total_bill.setTypeface(facebold);
        holder.tv_order_date.setTypeface(facebold);
        holder.tv_view_details.setTypeface(facebold);
        holder.tv_order_status.setTypeface(facebold);
        placeOrderDO = new PlaceOrderDO();
        SupplierConsumerOrderResultObject model = arrayList.get(position);
        holder.tv_product_count.setText( "Products : "+model.getProducts().size());
        holder.tv_order_id.setText("ORDER # "+ model.getPoId());
        holder.tv_total_items.setText("Status : "+model.getProducts().get(0).getOrderStatus());
        holder.tv_total_bill.setText("Consumer : "+model.getUserName());
        holder.tv_view_details.setText("Contact : "+model.getMobileNo());

        String date1 = model.getOrderedOn();
        SimpleDateFormat sdfIn = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = null;
        try {
            date = sdfIn.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tv_order_date.setText(sdfOut.format(date));


        return view;
    }


    public interface OnItemClicked {
        void onItemClick(View view, int position);
    }

    public interface EventListener{
        void onSuccess(boolean data);
    }

    private class ViewHolder {
        public TextView tv_order_id;
        public TextView tv_product_count;
        public TextView tv_total_bill;
        public TextView tv_order_date;
        public TextView tv_view_details;
        public TextView tv_total_items;
        public TextView tv_order_status;
    }


}