package com.example.ordermadeeasy.holders;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ordermadeeasy.R;

public class PlaceOrderRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_product_name;
    public TextView tv_product_desc;
    public TextView tv_dealer_txt;
    public TextView tv_dealers_name;
    public TextView tv_qty;
    public TextView tv_qty_txt;
    public ImageView iv_product_image;
    public int mSelectedItem;
    public PlaceOrderRCVHolder(View view) {
        super(view);
        // Find all views ids

        this.tv_product_name =  view.findViewById(R.id.tv_product_name);
        this.tv_product_desc =  view.findViewById(R.id.tv_product_desc);
        this.tv_dealer_txt =  view.findViewById(R.id.tv_dealer_txt);
        this.tv_dealers_name =  view.findViewById(R.id.tv_dealers_name);
        this.tv_qty =  view.findViewById(R.id.tv_qty);
        this.tv_qty_txt =  view.findViewById(R.id.tv_qty_txt);
        this.iv_product_image = view.findViewById(R.id.iv_product_image);

        Typeface faceLight = Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        this.tv_product_name.setTypeface(faceLight);
        this.tv_product_desc.setTypeface(facebold);
        this.tv_dealers_name.setTypeface(faceLight);
        this.tv_dealer_txt.setTypeface(faceLight);
        this.tv_qty.setTypeface(faceLight);
        this.tv_qty_txt.setTypeface(faceLight);
    }

    @Override
    public void onClick(View v) {

        mSelectedItem = getAdapterPosition();
    }

    private void setTypeface() {

    }
}