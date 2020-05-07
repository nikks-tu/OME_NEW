package com.techuva.ome_new.holders;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.techuva.ome_new.R;


public class AllOrderRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_dealers_name;
    public TextView tv_order_date;
    public TextView tv_order_status;
    public TextView tv_product_qty;
    public int mSelectedItem;
    public ImageView iv_product_image;
    public TextView tv_product_name;
    public TextView txt_order_status;
    public TextView tv_product_desc;
    public Spinner spin_order_status;
    public LinearLayout ll_status;
    public AllOrderRCVHolder(View view) {
        super(view);
        // Find all views ids
        this.iv_product_image =  view.findViewById(R.id.iv_product_image);
        this.tv_product_name =  view.findViewById(R.id.tv_product_name);
        this.tv_dealers_name =  view.findViewById(R.id.tv_dealers_name);
        this.tv_product_desc =  view.findViewById(R.id.tv_product_desc);
        this.txt_order_status =  view.findViewById(R.id.txt_order_status);
        this.tv_order_date =  view.findViewById(R.id.tv_order_date);
        //this.spin_order_status =  view.findViewById(R.id.spin_order_status);
        this.tv_product_qty =  view.findViewById(R.id.tv_product_qty);

        Typeface faceLight = Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Thin.ttf");
        Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        this.tv_product_name.setTypeface(facebold);
        this.tv_product_desc.setTypeface(facebold);
        this.tv_dealers_name.setTypeface(facebold);
        this.tv_order_date.setTypeface(facebold);
        this.txt_order_status.setTypeface(facebold);
        this.tv_product_qty.setTypeface(facebold);
    }

    @Override
    public void onClick(View v) {

        mSelectedItem = getAdapterPosition();
    }

    private void setTypeface() {

    }
}