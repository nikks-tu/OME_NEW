package com.techuva.ome_new.adapters;

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
import com.techuva.ome_new.object_models.ConsumerAllOrderResultObject;
import com.techuva.ome_new.object_models.OrderStatusResultObject;
import com.techuva.ome_new.utilities.CartDatabase;

import java.util.ArrayList;

public class ConsumerOrdersAdapter extends BaseAdapter
{   // Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<ConsumerAllOrderResultObject> arrayList;
    private Context context;
    private AllOrderRCVHolder listHolder;
    private int selectedPosition = -1;
    private OnItemClicked listener;
    private PlaceOrderDO placeOrderDO;
    String dealersList ="";
    StringBuilder sb;
    int dealerId = 0;
    String retailerId = "";
    int qty = 05;
    int totalDealers= 0;
    String dealerName="";
    CartDatabase cartDatabase;
    OrderStatusAdapter orderStatusAdapter;
    ArrayList<OrderStatusResultObject> statusList;
    ArrayList<OrderStatusResultObject> statusListFilter;
    Activity activity;
    String demandOrderId, statusId, receivedQuantity;
    String accessToken ="";
    int pos;
    private EventListener eventListener;
    String userType;
    Activity mActivity;

    public ConsumerOrdersAdapter(Activity activity, Context context, ArrayList<ConsumerAllOrderResultObject> arrayList, OnItemClicked listener, EventListener eventListener, String userType) {
        this.activity = activity;
        this.statusList = statusList;
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
        this.eventListener = eventListener;
        this.userType = userType;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConsumerOrdersAdapter.ViewHolder holder = null;
        View view = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.item_consumer_orders, parent, false);
        } else {
            view = convertView;

        }
        holder = new ConsumerOrdersAdapter.ViewHolder();
        holder.tv_order_id =  view.findViewById(R.id.tv_order_id);
        holder.tv_company_name =  view.findViewById(R.id.tv_company_name);
        holder.tv_total_items =  view.findViewById(R.id.tv_total_items);
        holder.tv_order_date =  view.findViewById(R.id.tv_order_date);
        holder.tv_view_details =  view.findViewById(R.id.tv_view_details);
        holder.tv_order_status =  view.findViewById(R.id.tv_order_status);


        Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        holder.tv_order_id.setTypeface(facebold);
        holder.tv_company_name.setTypeface(facebold);
        holder.tv_total_items.setTypeface(facebold);
        holder.tv_order_date.setTypeface(facebold);
        holder.tv_view_details.setTypeface(facebold);
        holder.tv_order_status.setTypeface(facebold);
        placeOrderDO = new PlaceOrderDO();
        ConsumerAllOrderResultObject model = arrayList.get(position);
        holder.tv_company_name.setText( "Company: "+model.getCompanyName());
        holder.tv_order_id.setText("ORDER # "+ model.getPoId());
        holder.tv_total_items.setText("Total Items: "+model.getProducts().size());
        holder.tv_order_status.setText("Status: "+model.getOrderStatus());


       /* String date1 = model.getOrderedOn();
        SimpleDateFormat sdfIn = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = null;
        try {
            date = sdfIn.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        holder.tv_order_date.setText(model.getOrderedOn());


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
        public TextView tv_company_name;
        public TextView tv_total_items;
        public TextView tv_order_date;
        public TextView tv_view_details;
        public TextView tv_order_status;
    }


}