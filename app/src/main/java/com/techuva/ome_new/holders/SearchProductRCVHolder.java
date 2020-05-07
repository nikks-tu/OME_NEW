package com.techuva.ome_new.holders;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techuva.ome_new.R;


public class SearchProductRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView iv_product_image;
    public TextView tv_product_name;
    public TextView tv_product_desc;
    public TextView tv_dealers_name;
    public TextView tv_add_to_cart;
    public int mSelectedItem;
    public SearchProductRCVHolder(View view) {
        super(view);
        // Find all views ids

        this.iv_product_image =  view.findViewById(R.id.iv_product_image);
        this.tv_product_name =  view.findViewById(R.id.tv_product_name);
        this.tv_product_desc =  view.findViewById(R.id.tv_product_desc);
        this.tv_dealers_name =  view.findViewById(R.id.tv_dealers_name);
        this.tv_add_to_cart =  view.findViewById(R.id.tv_add_to_cart);

        Typeface faceLight = Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Thin.ttf");
        this.tv_product_name.setTypeface(faceLight);
        Typeface facebold = Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        this.tv_product_name.setTypeface(facebold);
        this.tv_product_desc.setTypeface(facebold);
        this.tv_dealers_name.setTypeface(facebold);
        this.tv_add_to_cart.setTypeface(facebold);
    }

    @Override
    public void onClick(View v) {

        mSelectedItem = getAdapterPosition();
    }


}