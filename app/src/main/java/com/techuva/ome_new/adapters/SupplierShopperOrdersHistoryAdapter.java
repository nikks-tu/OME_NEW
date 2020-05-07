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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.techuva.ome_new.R;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.domain.PlaceOrderDO;
import com.techuva.ome_new.holders.AllOrderRCVHolder;
import com.techuva.ome_new.object_models.SupplierShopperOrderResultObject;
import com.techuva.ome_new.utilities.CartDatabase;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;

public class SupplierShopperOrdersHistoryAdapter extends BaseAdapter implements View.OnClickListener
{
    private ArrayList<SupplierShopperOrderResultObject> arrayList;
    private Context context;
    private AllOrderRCVHolder listHolder;
    private int selectedPosition = -1;
    private AllOrdersListAdapter.OnItemClicked listener;
    private PlaceOrderDO placeOrderDO;
    int qty = 05;
    int totalDealers= 0;
    String dealerName="";
    Activity activity;
    String accessToken ="";
    int pos;
    int decQty;
    String userType;
    private EventListener eventListener;
    SupplierShopperOrdersHistoryAdapter.ViewHolder holder = null;

    public SupplierShopperOrdersHistoryAdapter(Activity activity, Context context, ArrayList<SupplierShopperOrderResultObject> arrayList, EventListener listener) {
        this.activity = activity;
        this.context = context;
        this.arrayList = arrayList;
        this.eventListener = listener;
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

        View view = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {

            view = inflater.inflate(R.layout.item_supplier_shopper_orders, parent, false);

            accessToken = "Bearer "+ MApplication.getString(context, Constants.AccessToken);
            userType = MApplication.getString(context, Constants.UserType);
            holder = new SupplierShopperOrdersHistoryAdapter.ViewHolder();
            holder.iv_product_image =  view.findViewById(R.id.iv_product_image);
            holder.iv_dec_qty =  view.findViewById(R.id.iv_dec_qty);
            holder.iv_inc_qty =  view.findViewById(R.id.iv_inc_qty);
            holder.tv_qty_new =  view.findViewById(R.id.tv_qty_new);
            holder.tv_dis_qty =  view.findViewById(R.id.tv_dis_qty);
            holder.tv_product_desc =  view.findViewById(R.id.tv_product_desc);
            holder.txt_order_status =  view.findViewById(R.id.txt_order_status);
            holder.ll_dispatch_qty =  view.findViewById(R.id.ll_dispatch_qty);
            holder.tv_company_name =  view.findViewById(R.id.tv_company_name);
            holder.tv_oder_num =  view.findViewById(R.id.tv_oder_num);
            holder.tv_total_amt =  view.findViewById(R.id.tv_total_amt);
            holder.tv_qty =  view.findViewById(R.id.tv_qty);
            holder.cb_available =  view.findViewById(R.id.cb_available);
            holder.cb_available.setVisibility(View.GONE);
            holder.ll_dispatch_qty.setVisibility(View.GONE);
            holder.txt_order_status.setVisibility(View.VISIBLE);
            holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
           /* if(userType.equals(Constants.Supplier))
            {
                holder.txt_order_status.setVisibility(View.GONE);
            }
            else {
                holder.txt_order_status.setVisibility(View.VISIBLE);
            }*/
            Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                    "fonts/Roboto-Regular.ttf");
            holder.tv_qty_new.setTypeface(facebold);
            holder.tv_product_desc.setTypeface(facebold);
            holder.tv_company_name.setTypeface(facebold);
            holder.tv_total_amt.setTypeface(facebold);
            holder.tv_qty.setTypeface(facebold);
            holder.tv_oder_num.setTypeface(facebold);
            SupplierShopperOrderResultObject model = arrayList.get(position);
           /* if(model.getStatusShortCode().equals(Constants.Dispatched))
            {
                holder.ll_dispatch_qty.setVisibility(View.GONE);
                holder.txt_order_status.setVisibility(View.VISIBLE);
                holder.cb_available.setVisibility(View.GONE);
                holder.txt_order_status.setText(model.getStatusDescription());
            }*/
            //decQty = Integer.parseInt(model.getQuantity().toString());
            decQty = arrayList.get(position).getQuantity();
            holder.tv_product_desc.setText(model.getProductDescription());
            holder.tv_company_name.setText("Shopper : "+model.getShopper_company_name());
            holder.tv_qty.setText("Qty: "+model.getQuantity());
            holder.tv_oder_num.setText("Order #  "+model.getOrderNumber());
            decQty = model.getQuantity()-model.getReceivedQuantity();
            holder.tv_qty_new.setTag(decQty);
            holder.tv_qty_new.setText(String.valueOf(decQty));
            holder.tv_dis_qty.setText("Dispatched Qty: "+ model.getReceivedQuantity());
            holder.tv_total_amt.setText(context.getResources().getString(R.string.rs)+" "+model.getTotalAmount());
            holder.txt_order_status.setText(model.getStatusDescription());
            holder.iv_dec_qty.setOnClickListener(v -> {
                if(decQty>0)
                {

                    decQty = decQty-1;
                    holder.tv_qty_new.setTag(decQty);
                    this.onClick(holder.tv_qty_new);
                }
            });
            holder.iv_inc_qty.setOnClickListener(v -> {
                if(decQty<model.getQuantity())
                {

                    decQty = decQty+1;
                    holder.tv_qty_new.setTag(decQty);
                    this.onClick(holder.tv_qty_new);


                }
            });
            if(!model.getImageUrl().equals("") && model.getImageUrl()!=null)
            {
                Picasso.with(context).load(model.getImageUrl()).into(holder.iv_product_image);
            }
            else
                Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
            holder.cb_available.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(model.getChecked().equals("true"))
                {
                    //int disQty = Integer.parseInt(holder.tv_dis_qty.getText().toString());
                    CartDatabase.updateCheckStatusShopper(model.getDemandOrderId(), "false", String.valueOf(model.getQuantity()-model.getReceivedQuantity()));
                    //Toast.makeText(context, "False", Toast.LENGTH_SHORT).show();
                }
                else {
                    CartDatabase.updateCheckStatusShopper(model.getDemandOrderId(), "true", String.valueOf(model.getQuantity()-model.getReceivedQuantity()));
                    //Toast.makeText(context, "True", Toast.LENGTH_SHORT).show();
                }
            });





        } else {
            view = convertView;
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        String value = holder.tv_qty_new.getTag().toString();
        holder.tv_qty_new.setText(value);
        CartDatabase.init(context);
        CartDatabase.updateCheckStatusShopper(arrayList.get(pos).getDemandOrderId(), "false", String.valueOf(decQty));
        Toast.makeText(context, ""+value, Toast.LENGTH_SHORT).show();
    }


    public interface OnItemClicked {
        void onItemClick(View view, int position);
    }

    public interface EventListener{
        void onSuccess(boolean data);
    }

    private class ViewHolder {
        public ImageView iv_product_image;
        public ImageView iv_dec_qty;
        public ImageView iv_inc_qty;
        public TextView tv_qty_new;
        public TextView tv_dis_qty;
        public TextView tv_product_desc;
        public TextView tv_company_name;
        public TextView tv_total_amt;
        public TextView txt_order_status;
        public TextView tv_oder_num;
        public TextView tv_qty;
        public CheckBox cb_available;
        public LinearLayout ll_dispatch_qty;


    }




}