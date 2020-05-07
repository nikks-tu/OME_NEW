package com.example.ordermadeeasy.adapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.holders.SupplierShopperOrdersHolder;
import com.example.ordermadeeasy.object_models.GetDealerResultObject;
import com.example.ordermadeeasy.object_models.QuatityList;
import com.example.ordermadeeasy.object_models.SupplierShopperOrderResultObject;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SupplierShopperOrderNewAdapter extends RecyclerView.Adapter<SupplierShopperOrdersHolder> {
    // recycler view adapter
    private ArrayList<SupplierShopperOrderResultObject> arrayList;
    private ArrayList<SupplierShopperOrderResultObject> tempList;
    private ArrayList<GetDealerResultObject> dealersList;
    private ArrayList<GetDealerResultObject> dealersListHavingProduct;
    private Context context;
    private SupplierShopperOrdersHolder listHolder;
    private CompoundButton lastCheckedRB = null;
    private String UserName = "";
    private int selectedPosition = -1;
    private EventListener listener;
    private ArrayList<QuatityList> quantityList;
    private QuatityList quatityListDO;
    private QuantityAdapter quantityAdapter;
    List<Integer> list;
    DealersAdapter dealersAdapter;
    private AppCompatActivity activity;
    private boolean delerselection = false;
    private boolean qunityenable = false;
    int dealerID;
    private int productId = 0;
    String dealerName = "";
    private int orderId;
    private int companyId;
    int totalAmount;
    int price;
    String userID = "";
    String userType = "";
    String accessToken = "";
    int newQty;
    int pos;
    int decQty;
    private EventListener eventListener;

    public SupplierShopperOrderNewAdapter(AppCompatActivity activity, Context context, ArrayList<SupplierShopperOrderResultObject> arrayList, EventListener listener) {
        this.activity = activity;
        this.context = context;
        this.arrayList = arrayList;
        this.eventListener = listener;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    @Override
    public void onBindViewHolder(SupplierShopperOrdersHolder holder, int position) {
        accessToken = "Bearer "+ MApplication.getString(context, Constants.AccessToken);
        userType = MApplication.getString(context, Constants.UserType);

        SupplierShopperOrderResultObject model = arrayList.get(position);

        if(userType.equals(Constants.Supplier))
        {
            holder.txt_order_status.setVisibility(View.GONE);
        }
        else {
            holder.txt_order_status.setVisibility(View.VISIBLE);
        }
        Typeface facebold= Typeface.createFromAsset(context.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        holder.tv_qty_new.setTypeface(facebold);
        holder.tv_product_desc.setTypeface(facebold);
        holder.tv_company_name.setTypeface(facebold);
        holder.tv_total_amt.setTypeface(facebold);
        holder.tv_qty.setTypeface(facebold);
        holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        if(model.getStatusShortCode().equals(Constants.Dispatched))
        {
            holder.ll_dispatch_qty.setVisibility(View.GONE);
            holder.txt_order_status.setVisibility(View.VISIBLE);
            holder.cb_available.setVisibility(View.GONE);
            holder.txt_order_status.setText(model.getStatusDescription());
        }
        //decQty = Integer.parseInt(model.getQuantity().toString());
        //decQty = arrayList.get(position).getQuantity();
        holder.tv_product_desc.setText(model.getProductDescription());
        holder.tv_company_name.setText("Shopper : "+model.getShopper_company_name());
        holder.tv_qty.setText("Qty: "+model.getQuantity());
        holder.tv_order_num.setText("Order # "+model.getOrderNumber());
        //newQty = model.getQuantity();
        decQty = arrayList.get(position).getQuantity()-arrayList.get(position).getReceivedQuantity();
        holder.tv_qty_new.setTag(decQty);
        holder.tv_qty_new.setText(String.valueOf(decQty));
        holder.tv_dis_qty.setText("Dispatched Qty: "+ model.getReceivedQuantity());
        holder.tv_total_amt.setText(context.getResources().getString(R.string.rs)+" "+model.getTotalAmount());
        holder.iv_dec_qty.setTag(position);
        holder.iv_inc_qty.setTag(position);
       /* //newQty = Integer.parseInt(holder.tv_qty_new.getTag().toString());
        newQty = model.getQuantity()-model.getReceivedQuantity();*/
        holder.iv_dec_qty.setOnClickListener(v -> {
            int pos = Integer.parseInt(holder.iv_dec_qty.getTag().toString());
            decQty = arrayList.get(pos).getQuantity()-arrayList.get(pos).getReceivedQuantity();
            newQty = Integer.parseInt(holder.tv_qty_new.getText().toString());
            if(newQty>=2)
            {
                //Toast.makeText(context, ""+holder.iv_dec_qty.getTag().toString(), Toast.LENGTH_SHORT).show();
                //int tempQty = Integer.parseInt(holder.tv_qty_new.getText().toString());
                //decQty = arrayList.get(position).getQuantity()-arrayList.get(position).getReceivedQuantity();
                newQty = newQty-1;
                holder.tv_qty_new.setText(String.valueOf(newQty));
                CartDatabase.init(context);
                CartDatabase.updateCheckStatusShopper(arrayList.get(pos).getDemandOrderId(), "false", String.valueOf(newQty));
                /*tempList = new ArrayList<>();
                tempList = CartDatabase.getShopperOrderProductWithOrderId(arrayList.get(pos).demandOrderId);
                Toast.makeText(context, ""+tempList.get(0).getDispachedQty(), Toast.LENGTH_SHORT).show();
                holder.tv_qty_new.setText(""+tempList.get(0).getDispachedQty());*/
                //eventListener.onEvent(true);
            }
        });
        holder.iv_inc_qty.setOnClickListener(v -> {
            int pos = Integer.parseInt(holder.iv_dec_qty.getTag().toString());
            decQty = arrayList.get(pos).getQuantity()-arrayList.get(pos).getReceivedQuantity();
            newQty = Integer.parseInt(holder.tv_qty_new.getText().toString());
            if(newQty<decQty)
            {
                newQty = newQty+1;
                holder.tv_qty_new.setText(String.valueOf(newQty));
                CartDatabase.init(context);
                CartDatabase.updateCheckStatusShopper(arrayList.get(pos).getDemandOrderId(), "false", String.valueOf(newQty));
                /*tempList = new ArrayList<>();
                tempList = CartDatabase.getShopperOrderProductWithOrderId(arrayList.get(pos).demandOrderId);
                Toast.makeText(context, ""+tempList.get(0).getDispachedQty(), Toast.LENGTH_SHORT).show();
                holder.tv_qty_new.setText(""+tempList.get(0).getDispachedQty());*/
                //eventListener.onEvent(true);
            }
        });
        if(!model.getImageUrl().equals("") && model.getImageUrl()!=null)
        {
            Picasso.with(context).load(model.getImageUrl()).into(holder.iv_product_image);
        }
        else
            Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
        holder.cb_available.setOnCheckedChangeListener((buttonView, isChecked) -> {
            CartDatabase.init(context);
            boolean isCheckedTrue = holder.cb_available.isChecked();
            if(!isChecked)
            {
                CartDatabase.updateCheckStatusShopper(model.getDemandOrderId(), "false", holder.tv_qty_new.getText().toString());
                //Toast.makeText(context, "False", Toast.LENGTH_SHORT).show();
            }
            else {
                CartDatabase.updateCheckStatusShopper(model.getDemandOrderId(), "true",  holder.tv_qty_new.getText().toString());
                //Toast.makeText(context, "True", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public SupplierShopperOrdersHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_supplier_shopper_orders_new, viewGroup, false);
        listHolder = new SupplierShopperOrdersHolder(mainGroup);
        return listHolder;

    }


    public interface OnItemClicked {
        void onItemClick(View view, int position);
    }

    public String getSelectedItem() {
        if (selectedPosition != -1) {
            // Toast.makeText(context, "Selected Item : " + arrayList.get(selectedPosition).getCompanyName(), Toast.LENGTH_SHORT).show();
            return String.valueOf(selectedPosition);
        }
        return "";
    }


    public interface EventListener {
        void onEvent(Boolean data);

        void onDelete(Boolean data);
    }

    private int indexOf(final QuantityAdapter adapter, Object value) {
        for (int index = 0, count = adapter.getCount(); index < count; ++index) {
            if (adapter.getItem(index).equals(value)) {
                return index;
            }
        }
        return -1;
    }
}