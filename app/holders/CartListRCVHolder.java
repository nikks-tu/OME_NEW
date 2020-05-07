package com.example.ordermadeeasy.holders;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ordermadeeasy.R;

public class CartListRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_product_name;
    public TextView tv_product_desc;
    public TextView tv_add_to_cart;
    public TextView tv_dealer_txt;
    public TextView tv_qty_txt;
    public Spinner lv_dealers_list;
    public ImageView iv_dealers;
    public ImageView iv_qty;
    public ImageView iv_delete_product;
    public Spinner lv_qty_list;
    public ImageView iv_product_image;
    public int mSelectedItem;
    public  TextView deler_select_tv;

   public TextView tv_qty_new;
   public ImageView iv_inc_qty, iv_dec_qty;
    public CartListRCVHolder(View view) {
        super(view);
        // Find all views ids

        this.tv_product_name =  view.findViewById(R.id.tv_product_name);
        this.tv_product_desc =  view.findViewById(R.id.tv_product_desc);
        this.tv_dealer_txt =  view.findViewById(R.id.tv_dealer_txt);

        this.lv_dealers_list =  view.findViewById(R.id.lv_dealers_list);
        this.iv_dealers =  view.findViewById(R.id.iv_dealers);
        this.iv_qty =  view.findViewById(R.id.iv_qty);
        this.iv_delete_product =  view.findViewById(R.id.iv_delete_product);
        this.lv_qty_list =  view.findViewById(R.id.lv_qty_list);
        this.iv_product_image =  view.findViewById(R.id.iv_product_image);
        this.tv_qty_txt =  view.findViewById(R.id.tv_qty_txt);
        this.deler_select_tv =view.findViewById(R.id.deler_select_tv);

        this.tv_qty_new= view.findViewById(R.id.tv_qty_new);
        this.iv_inc_qty = view.findViewById(R.id.iv_inc_qty);
        this.iv_dec_qty = view.findViewById(R.id.iv_dec_qty);

        Typeface faceLight = Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Thin.ttf");
        Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        this.tv_product_name.setTypeface(facebold);
        this.tv_product_desc.setTypeface(facebold);
        this.tv_dealer_txt.setTypeface(facebold);
        this.tv_qty_txt.setTypeface(facebold);
    }

    @Override
    public void onClick(View v) {

        mSelectedItem = getAdapterPosition();
    }

    private void setTypeface() {

    }
}