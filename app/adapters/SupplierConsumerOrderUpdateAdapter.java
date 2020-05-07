package com.example.ordermadeeasy.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.api_interface.UpdateOrderDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.domain.PlaceOrderDO;
import com.example.ordermadeeasy.holders.AllOrderRCVHolder;
import com.example.ordermadeeasy.object_models.OrderStatusResultObject;
import com.example.ordermadeeasy.object_models.SupplierConsumerOrderProduct;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SupplierConsumerOrderUpdateAdapter extends BaseAdapter
{
    private ArrayList<SupplierConsumerOrderProduct> arrayList;
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
    String userType;
    int userId;
    ArrayList<OrderStatusResultObject> statusList;
    ArrayList<OrderStatusResultObject> statusListFilter;

    public SupplierConsumerOrderUpdateAdapter(AppCompatActivity activity, Context context, ArrayList<OrderStatusResultObject> statusList, ArrayList<SupplierConsumerOrderProduct> arrayList, String userType) {
        this.activity = activity;
        this.context = context;
        this.statusList = statusList;
        this.arrayList = arrayList;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SupplierConsumerOrderUpdateAdapter.ViewHolder holder = null;
        View view = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.item_supplier_shopper_orders, parent, false);
        } else {
            view = convertView;

        }
        userId = Integer.parseInt(MApplication.getString(context, Constants.UserID));
        accessToken = "Bearer "+ MApplication.getString(context, Constants.AccessToken);
        holder = new SupplierConsumerOrderUpdateAdapter.ViewHolder();
        holder.iv_product_image =  view.findViewById(R.id.iv_product_image);
        holder.txt_order_status =  view.findViewById(R.id.txt_order_status);
        holder.tv_product_desc =  view.findViewById(R.id.tv_product_desc);
        holder.tv_company_name =  view.findViewById(R.id.tv_company_name);
        holder.tv_total_amt =  view.findViewById(R.id.tv_total_amt);
        holder.tv_dis_qty =  view.findViewById(R.id.tv_dis_qty);
        holder.tv_qty =  view.findViewById(R.id.tv_qty);
        holder.cb_available =  view.findViewById(R.id.cb_available);
        holder.ll_dispatch_qty =  view.findViewById(R.id.ll_dispatch_qty);
        holder.ll_dispatch_qty.setVisibility(View.GONE);
        holder.tv_dis_qty.setVisibility(View.GONE);
        holder.txt_order_status.setVisibility(View.VISIBLE);

        Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        holder.tv_product_desc.setTypeface(facebold);
        holder.tv_company_name.setTypeface(facebold);
        holder.tv_total_amt.setTypeface(facebold);
        holder.txt_order_status.setTypeface(facebold);
        placeOrderDO = new PlaceOrderDO();
        SupplierConsumerOrderProduct model = arrayList.get(position);
        holder.tv_company_name.setText(model.getCategoryName());
        holder.tv_product_desc.setText(model.getProductDescription());
        holder.tv_total_amt.setText("Price: "+model.getTotalAmount().toString());
        holder.tv_qty.setText("Qty: "+model.getQuantity());
        holder.txt_order_status.setText(model.getOrderStatus());
        setColoreToView(holder.txt_order_status,model.getStatusId());

       if(model.getProductImage()!=null)
       {
           if (!model.getProductImage().equals("")) {
               Picasso.with(context).load(model.getProductImage()).into(holder.iv_product_image);
           } else {
               Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
           }
       }else {
           Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
       }

        if (model.getStatusShortCode().equals("PEN"))
        {
            holder.cb_available.setVisibility(View.VISIBLE);
        }
        else holder.cb_available.setVisibility(View.GONE);

        ViewHolder finalHolder1 = holder;
        holder.cb_available.setOnCheckedChangeListener((buttonView, isChecked) -> {
            boolean isCheckedTrue = finalHolder1.cb_available.isChecked();
            CartDatabase.init(context);
            if(!isCheckedTrue)
            {
                CartDatabase.updateCheckStatus(model.getOrderId(), "false", Integer.parseInt(arrayList.get(position).getQuantity()));
               // Toast.makeText(context, "False", Toast.LENGTH_SHORT).show();
            }
            else {
                CartDatabase.updateCheckStatus(model.getOrderId(), "true", Integer.parseInt(arrayList.get(position).getQuantity()));
              //  Toast.makeText(context, "True", Toast.LENGTH_SHORT).show();
            }
          /*  if(model.getChecked().equals("true"))
            {
                CartDatabase.updateCheckStatus(model.getOrderId(), "false", Integer.parseInt(arrayList.get(position).getQuantity()));
               // Toast.makeText(context, "False", Toast.LENGTH_SHORT).show();
            }
            else {
                CartDatabase.updateCheckStatus(model.getOrderId(), "true", Integer.parseInt(arrayList.get(position).getQuantity()));
              //  Toast.makeText(context, "True", Toast.LENGTH_SHORT).show();
            }*/
        });

        holder.txt_order_status.setTextColor(context.getResources().getColor(R.color.white));
        holder.txt_order_status.setTag(model.getStatusId());
        ViewHolder finalHolder = holder;
        holder.txt_order_status.setOnClickListener(v -> {
            // pos = finalHolder.getAdapterPosition();
            pos = position;
            statusListFilter = new ArrayList<>();
          /*  if(finalHolder.txt_order_status.getTag().toString().equalsIgnoreCase("8"))
            {
                for (int i=0; i<statusList.size(); i++)
                {
                    if(statusList.get(i).getStatusId()==12)
                    {
                        statusListFilter.add(statusList.get(i));
                    }
                }
            }
            // if(holder.txt_order_status.getTag().toString().equalsIgnoreCase("6")||holder.txt_order_status.getTag().toString().equalsIgnoreCase("3"))
            if(finalHolder.txt_order_status.getTag().toString().equalsIgnoreCase("12"))
            {
                for (int i=0; i<statusList.size(); i++)
                {
                    if(statusList.get(i).getStatusId()== 11)
                    {
                        //partially dispatched will have partially received.

                        statusListFilter.add(statusList.get(i));
                    }
                }
            }
            if(finalHolder.txt_order_status.getTag().toString().equalsIgnoreCase("4"))
            {
                for (int i=0; i<statusList.size(); i++)
                {
                    if(statusList.get(i).getStatusId()==2)
                    {
                        statusListFilter.add(statusList.get(i));
                    }
                }
            }*/
            if(finalHolder.txt_order_status.getTag().toString().equalsIgnoreCase("16"))
            {
                for (int i=0; i<statusList.size(); i++)
                {
                    if(statusList.get(i).getStatusId()==12)
                    {
                        statusListFilter.add(statusList.get(i));
                    }
                }
            }
            if(statusListFilter.size()>0)
            {
                showPopupMenu(finalHolder.txt_order_status, statusListFilter,position);
                if(model.getStatusId()==15)
                {
                    finalHolder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
                if(model.getStatusId()==12)
                {
                    finalHolder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
                if(model.getStatusId()==2)
                {
                    finalHolder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
                if(model.getStatusId()==10)
                {
                    finalHolder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }

        });
        view.setTag(holder);
        return view;
    }


    public interface OnItemClicked {
        void onItemClick(View view, int position);
    }

    public interface EventListener{
        void onSuccess(boolean data);
    }

    private class ViewHolder {
        public ImageView iv_product_image;
        public TextView txt_order_status;
        public TextView tv_product_desc;
        public TextView tv_company_name;
        public TextView tv_total_amt;
        public TextView tv_dis_qty;
        public TextView tv_qty;

        public CheckBox cb_available;
        public LinearLayout ll_dispatch_qty;
    }


    private void showPopupMenu(final View view1, ArrayList<OrderStatusResultObject> list,int position)
    {
        final TextView b1 = (TextView) view1;
        PopupMenu menu = new PopupMenu(activity, view1);
        for(int y=0;y<list.size();y++)
        {
            menu.getMenu().add(list.get(y).getStatusDescription());
        }
        menu.show();
        menu.setOnMenuItemClickListener(item -> {

            if(b1.getText().toString().equalsIgnoreCase(item.getTitle().toString()))
            {
                Toast.makeText(activity,"You have selected the same status.",Toast.LENGTH_SHORT).show();
            }
            else
            {
                b1.setText(item.getTitle());
                int id=0;
                for (int i=0; i<statusList.size(); i++)
                {
                    if(b1.getText().toString().equalsIgnoreCase(statusList.get(i).getStatusDescription()) )
                    {
                        b1.setTag(statusList.get(i).getStatusId());
                        id=statusList.get(i).getStatusId();
                    }
                }
                statusList.get(position).setStatusId(id);
                setColoreToView(b1,id);
                 if(id==12)
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("po_id", MApplication.getString(context, Constants.PoID));
                jsonObject.addProperty("status", "DEL");
                serviceCallforUpdateOrder(jsonObject);
            }
                // serviceCallforAllUpdatingStatus(id);
                // eventListener.onSuccess(true);
            }
            return false;
        });
    }



    @SuppressLint("NewApi")
    public void setColoreToView (View view, int id)
    {
        TextView textView;
        textView = view.findViewById(R.id.txt_order_status);
        if(id== 8)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.pending_backgound));
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        else if(id==12)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.received_backgound));
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        else if(id==3)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.part_disp_backgound));
        }
        else if(id==11)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.dispatched_backgound));
        }
        else if(id==15)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.canceled_backgound));
        }
        else if(id==6)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.part_rece_backgound));
        }
    }



    private void serviceCallforUpdateOrder(JsonObject jsonObject) {

        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait..");
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UpdateOrderDataInterface service = retrofit.create(UpdateOrderDataInterface.class);
        Call<JsonElement> call = service.updateSuppllierConsumerOrder(userId ,accessToken, jsonObject);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    JsonObject json = response.body().getAsJsonObject();
                    JsonObject infoObject = json.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if (errorCode==0)
                    {
                        Toast.makeText(context,errorMsg,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context,errorMsg,Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
            }

        });
    }



}