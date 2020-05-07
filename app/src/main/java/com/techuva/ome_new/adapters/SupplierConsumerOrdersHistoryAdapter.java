package com.techuva.ome_new.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.techuva.ome_new.R;
import com.techuva.ome_new.domain.PlaceOrderDO;
import com.techuva.ome_new.holders.AllOrderRCVHolder;
import com.techuva.ome_new.object_models.SupplierConsumerHistoryResultObject;
import com.techuva.ome_new.utilities.CartDatabase;

import java.util.ArrayList;

public class SupplierConsumerOrdersHistoryAdapter extends BaseAdapter
{
    private ArrayList<SupplierConsumerHistoryResultObject> arrayList;
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
    Activity activity;
    String demandOrderId, statusId, receivedQuantity;
    String accessToken ="";
    int pos;
    private AllOrdersListAdapter.EventListener eventListener;

    public SupplierConsumerOrdersHistoryAdapter(Activity activity, Context context, ArrayList<SupplierConsumerHistoryResultObject> arrayList) {
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
        SupplierConsumerOrdersHistoryAdapter.ViewHolder holder = null;
        View view = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.item_consumer_supplier_orders, parent, false);
        } else {
            view = convertView;

        }
        holder = new SupplierConsumerOrdersHistoryAdapter.ViewHolder();
        holder.tv_order_id =  view.findViewById(R.id.tv_order_id);
        holder.tv_product_count =  view.findViewById(R.id.tv_product_count);
        holder.tv_total_bill =  view.findViewById(R.id.tv_total_bill);
        holder.tv_order_date =  view.findViewById(R.id.tv_order_date);
        holder.tv_view_details =  view.findViewById(R.id.tv_view_details);
        holder.tv_order_status =  view.findViewById(R.id.tv_order_status);
        holder.tv_user_name =  view.findViewById(R.id.tv_user_name);
        holder.tv_mobile =  view.findViewById(R.id.tv_mobile);

        Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        holder.tv_order_id.setTypeface(facebold);
        holder.tv_product_count.setTypeface(facebold);
        holder.tv_total_bill.setTypeface(facebold);
        holder.tv_order_date.setTypeface(facebold);
        holder.tv_view_details.setTypeface(facebold);
        holder.tv_user_name.setTypeface(facebold);
        holder.tv_mobile.setTypeface(facebold);
        placeOrderDO = new PlaceOrderDO();
        SupplierConsumerHistoryResultObject model = arrayList.get(position);
        holder.tv_product_count.setText( "Products : "+model.getProducts().size());
        holder.tv_order_id.setText("ORDER # "+ model.getPoId());
        holder.tv_total_bill.setText("Total Items : "+model.getProducts().size());
        holder.tv_user_name.setText("Consumer : "+model.getOrderByName());
        holder.tv_mobile.setText("Contact : "+model.getMobileNo());
        holder.tv_order_status.setText("Status : "+model.getProducts().get(0).getOrderStatus());


        String date = model.getOrderedOn();
       /* SimpleDateFormat sdfIn = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = null;
        try {
            date = sdfIn.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        holder.tv_order_date.setText(date);


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
        public TextView tv_order_status;
        public TextView tv_user_name;
        public TextView tv_mobile;
    }


}