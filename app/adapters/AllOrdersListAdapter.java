package com.example.ordermadeeasy.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.api_interface.SupplierOrdersDataInterface;
import com.example.ordermadeeasy.api_interface.UpdateOrderDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.domain.PlaceOrderDO;
import com.example.ordermadeeasy.holders.AllOrderRCVHolder;
import com.example.ordermadeeasy.object_models.OrderStatusResultObject;
import com.example.ordermadeeasy.object_models.ShopperOrdersResultObject;
import com.example.ordermadeeasy.post_parameters.UpdateOrderPostParameters;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AllOrdersListAdapter  extends BaseAdapter
{   // Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<ShopperOrdersResultObject> arrayList;
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
    AppCompatActivity activity;
    String demandOrderId, statusId, receivedQuantity;
    String accessToken ="";
    int pos;
    String userRole;
    String userID;
    private EventListener eventListener;

    public AllOrdersListAdapter(AppCompatActivity activity, Context context, ArrayList<OrderStatusResultObject> statusList, ArrayList<ShopperOrdersResultObject> arrayList, OnItemClicked listener, EventListener eventListener) {
        this.activity = activity;
        this.statusList = statusList;
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
        this.eventListener = eventListener;
    }

   /* @Override
    public int getItemCount() {
        // return (null != arrayList ? arrayList.size() : 0);
        return arrayList.size();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(AllOrderRCVHolder holder, int position) {
           }
*/
    @SuppressLint("NewApi")
    public void setColoreToView (View view, int id)
    {
        if(id== 1)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.pending_backgound));
        }
        else if(id==2)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.received_backgound));
        }
        else if(id==3)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.part_disp_backgound));
        }
        else if(id==4)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.dispatched_backgound));
        }
        else if(id==5)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.canceled_backgound));
        }
        else if(id==6)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.part_rece_backgound));
        }
    }

    private void showPopupMenu(final View view1, ArrayList<OrderStatusResultObject> list,int position)
    {
        final TextView b1 = (TextView) view1;
        PopupMenu menu = new PopupMenu(activity, view1);
        for(int y=0;y<list.size();y++)
        {
            if(list.get(y).getStatusDescription().equals(Constants.Cancelled))
            {
                menu.getMenu().add(Constants.Cancel);
            }
            else
            {
                menu.getMenu().add(list.get(y).getStatusDescription());
            }
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
                { if(b1.getText().toString().equals(Constants.Cancel))
                {
                    if(Constants.Cancelled.equalsIgnoreCase(statusList.get(i).getStatusDescription()))
                    {
                        b1.setTag(statusList.get(i).getStatusId());
                        id=statusList.get(i).getStatusId();
                    }
                }

                else if (b1.getText().toString().equalsIgnoreCase(statusList.get(i).getStatusDescription()))
                {
                    b1.setTag(statusList.get(i).getStatusId());
                    id=statusList.get(i).getStatusId();
                }
                }
                arrayList.get(position).setStatusId(id);
                setColoreToView(b1,id);
                if(id==5)
                {
                    JsonObject jsonObject = new JsonObject();
                    JsonArray jsonArray = new JsonArray();
                    jsonArray.add(arrayList.get(position).getDemandOrderId());
                    jsonObject.add("order_ids", jsonArray);
                    serviceCallForCancelOrder(jsonObject);
                }
                else {
                    serviceCallforAllUpdatingStatus(arrayList.get(position).getDemandOrderId());
                }
               // eventListener.onSuccess(true);
            }
            return false;
        });
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
       ViewHolder holder = null;
       View view = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.item_order_list_shoppers, parent, false);
        } else {
           view = convertView;

        }
        userRole = MApplication.getString(context, Constants.UserType);
        userID = MApplication.getString(context, Constants.UserID);
            holder = new AllOrdersListAdapter.ViewHolder();

            holder.iv_product_image =  view.findViewById(R.id.iv_product_image);
            holder.tv_product_name =  view.findViewById(R.id.tv_product_name);
            holder.tv_dealers_name =  view.findViewById(R.id.tv_dealers_name);
            holder.tv_product_desc =  view.findViewById(R.id.tv_product_desc);
            holder.txt_order_status =  view.findViewById(R.id.txt_order_status);
            holder.tv_order_date =  view.findViewById(R.id.tv_order_date);
            holder.tv_total_amt =  view.findViewById(R.id.tv_total_amt);
            holder.tv_disp =  view.findViewById(R.id.tv_disp);
            //this.spin_order_status =  view.findViewById(R.id.spin_order_status);
            holder.tv_product_qty =  view.findViewById(R.id.tv_product_qty);

            Typeface faceLight = Typeface.createFromAsset(view.getResources().getAssets(),
                    "fonts/Roboto-Thin.ttf");
            Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                    "fonts/Roboto-Regular.ttf");
            holder.tv_product_name.setTypeface(facebold);
            holder.tv_product_desc.setTypeface(facebold);
            holder.tv_dealers_name.setTypeface(facebold);
            holder.tv_order_date.setTypeface(facebold);
            holder.txt_order_status.setTypeface(facebold);
            holder.tv_product_qty.setTypeface(facebold);
            holder.tv_total_amt.setTypeface(facebold);
            holder.tv_disp.setTypeface(facebold);
            retailerId = MApplication.getString(context, Constants.UserID);
            //holder.setIsRecyclable(false);
            placeOrderDO = new PlaceOrderDO();
            ShopperOrdersResultObject model = arrayList.get(position);
            holder.tv_product_name.setText( model.getProductDescription());
            holder.tv_product_desc.setText("Supplier : "+model.getCompanyName());
           // holder.tv_dealers_name.setText(model.getDealerName());
            holder.txt_order_status.setVisibility(View.VISIBLE);
            holder.txt_order_status.setText(model.getStatusDescription());
            holder.tv_product_qty.setText(context.getResources().getString(R.string.qty)+" "+ model.getQuantity());
            holder.tv_disp.setText("Dispatched Qty: "+ model.getReceivedQuantity());
            holder.tv_dealers_name.setText("Order #"+model.getOrderNumber());
            holder.tv_total_amt.setText("Total Amount: "+model.getTotalAmount());
            demandOrderId = String.valueOf(model.getDemandOrderId());
            //statusId = String.valueOf(holder.spin_order_status.getSelectedItemId());
            receivedQuantity="";
            if(model.getProductImage()!=null)
            {
                if(!model.getProductImage().equals("") && model.getProductImage()!=null)
                {
                    Picasso.with(context).load(model.getProductImage()).into(holder.iv_product_image);
                }
                else
                    Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
            }
            else {
                Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
            }
            /*  */
                if(model.getStatusId()==5)
        {
            holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if(model.getStatusId()==6)
        {
            holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if(model.getStatusId()==2)
        {
            holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
                StringBuilder sb = new StringBuilder();
            sb.append("Bearer ");
            sb.append( MApplication.getString(context, Constants.AccessToken));
            accessToken = sb.toString();

            String date1 = model.getReceivedOn();
            if(date1!=null)
            {
                SimpleDateFormat sdfIn = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
                SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date date = null;
                try {
                    date = sdfIn.parse(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                statusListFilter = new ArrayList<>();
                holder.tv_order_date.setText(sdfOut.format(date));
            }

            //Picasso.with(activity).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
            holder.txt_order_status.setTextColor(context.getResources().getColor(R.color.white));
            holder.txt_order_status.setTag(model.getStatusId());

            setColoreToView(holder.txt_order_status,model.getStatusId());

            ViewHolder finalHolder = holder;
            holder.txt_order_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // pos = finalHolder.getAdapterPosition();
                    pos = position;
                    statusListFilter = new ArrayList<>();
                    if(finalHolder.txt_order_status.getTag().toString().equalsIgnoreCase("1"))
                    {
                        for (int i=0; i<statusList.size(); i++)
                        {
                            if(statusList.get(i).getStatusId()==5)
                            {
                                statusListFilter.add(statusList.get(i));
                            }
                        }
                    }
                    // if(holder.txt_order_status.getTag().toString().equalsIgnoreCase("6")||holder.txt_order_status.getTag().toString().equalsIgnoreCase("3"))
                    if(finalHolder.txt_order_status.getTag().toString().equalsIgnoreCase("3"))
                    {
                        for (int i=0; i<statusList.size(); i++)
                        {
                            if(statusList.get(i).getStatusId()== 6)
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
                    }
                    if(statusListFilter.size()>0)
                    {
                        showPopupMenu(finalHolder.txt_order_status, statusListFilter,position);
                        if(model.getStatusId()==5)
                        {
                            finalHolder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        }
                        if(model.getStatusId()==6)
                        {
                            finalHolder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        }
                        if(model.getStatusId()==2)
                        {
                            finalHolder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        }
                    }

                }
            });

            if(model.getStatusId()==5)
            {
                holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
            if(model.getStatusId()==6)
            {
                holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
            if(model.getStatusId()==2)
            {
                holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
            dealersList = new String();
            view.setTag(holder);



        return view;
    }


    public interface OnItemClicked {
        void onItemClick(View view, int position);
    }

    public interface EventListener{
        void onSuccess(boolean data);
    }

    private void serviceCallforAllUpdatingStatus(int demandOrderId ) {

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
        Call<JsonElement> call = service.updateShopperOrder(Integer.parseInt(retailerId),accessToken,  new UpdateOrderPostParameters(String.valueOf(demandOrderId), userRole));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                //hideloader();

                dialog.dismiss();
                if (response.body() != null) {
                    //Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject info = jsonObject.get("info").getAsJsonObject();
                    int errorCode = info.get("ErrorCode").getAsInt();
                    String errorMsg = info.get("ErrorMessage").getAsString();
                    if(errorCode==0)
                    {
                        MApplication.serviceCallforShopperPendingOrders(context, accessToken, userID, userRole);
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        eventListener.onSuccess(true);

                    }
                    else Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void serviceCallForCancelOrder(JsonObject jsonObject){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupplierOrdersDataInterface service = retrofit.create(SupplierOrdersDataInterface.class);
        Call<JsonElement> call = service.cancelShopperOrders(Integer.parseInt(retailerId), accessToken, jsonObject);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if (errorCode==0)
                    {
                        MApplication.serviceCallforShopperPendingOrders(context, accessToken, userID, userRole);
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        eventListener.onSuccess(true);
                    }
                    else  {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        //loadFragments(SupplierConsumerOrdersActivity.newInstance());
                    }
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
            }

        });

    }


    private class ViewHolder {
        public TextView tv_dealers_name;
        public TextView tv_order_date;
        public TextView tv_total_amt;
        public TextView tv_product_qty;
        public TextView tv_disp;
        public int mSelectedItem;
        public ImageView iv_product_image;
        public TextView tv_product_name;
        public TextView txt_order_status;
        public TextView tv_product_desc;
        public Spinner spin_order_status;
        public LinearLayout ll_status;
    }


}