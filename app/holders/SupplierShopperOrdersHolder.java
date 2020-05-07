package com.example.ordermadeeasy.holders;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ordermadeeasy.R;

public class SupplierShopperOrdersHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_product_name;
    public TextView tv_product_desc;
    public TextView tv_company_name;
    public TextView tv_dealer_txt;
    public TextView tv_qty_txt;
    public TextView tv_qty;
    public TextView tv_order_num;
    public TextView tv_dis_qty;
    public TextView tv_total_amt;
    public Spinner lv_qty_list;
    public ImageView iv_product_image;
    public int mSelectedItem;
    public  TextView deler_select_tv;
    public  TextView txt_order_status;
    public LinearLayout ll_dispatch_qty;
    public CheckBox cb_available;

   public TextView tv_qty_new;
   public ImageView iv_inc_qty, iv_dec_qty;
    public SupplierShopperOrdersHolder(View view) {
        super(view);
        // Find all views ids

        this.tv_product_name =  view.findViewById(R.id.tv_product_name);
        this.tv_product_desc =  view.findViewById(R.id.tv_product_desc);
        this.tv_dealer_txt =  view.findViewById(R.id.tv_dealer_txt);
        this.tv_company_name =  view.findViewById(R.id.tv_company_name);
        this.tv_total_amt =  view.findViewById(R.id.tv_total_amt);
        this.tv_dis_qty =  view.findViewById(R.id.tv_dis_qty);
        this.txt_order_status =  view.findViewById(R.id.txt_order_status);
        this.tv_order_num =  view.findViewById(R.id.tv_order_num);
        this.cb_available =  view.findViewById(R.id.cb_available);

        this.lv_qty_list =  view.findViewById(R.id.lv_qty_list);
        this.iv_product_image =  view.findViewById(R.id.iv_product_image);
        this.tv_qty_txt =  view.findViewById(R.id.tv_qty_txt);
        this.deler_select_tv =view.findViewById(R.id.deler_select_tv);
        this.ll_dispatch_qty =view.findViewById(R.id.ll_dispatch_qty);

        this.tv_qty_new= view.findViewById(R.id.tv_qty_new);
        this.tv_qty= view.findViewById(R.id.tv_qty);
        this.iv_inc_qty = view.findViewById(R.id.iv_inc_qty);
        this.iv_dec_qty = view.findViewById(R.id.iv_dec_qty);

        Typeface faceLight = Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Thin.ttf");
        Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        //this.tv_product_name.setTypeface(facebold);
        this.tv_product_desc.setTypeface(facebold);
        this.tv_order_num.setTypeface(facebold);
    }

    @Override
    public void onClick(View v) {

        mSelectedItem = getAdapterPosition();
    }

    private void setTypeface() {

    }
}