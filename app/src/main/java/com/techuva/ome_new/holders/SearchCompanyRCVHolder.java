package com.techuva.ome_new.holders;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.techuva.ome_new.R;


public class SearchCompanyRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView iv_product_image;
    public TextView tv_company_name;
    public TextView tv_state_desc;
    public TextView tv_address_name;
    public TextView tv_view_product;
    public CheckBox rb_favourite;
    public int mSelectedItem;
    public SearchCompanyRCVHolder(View view) {
        super(view);
        // Find all views ids

        this.iv_product_image =  view.findViewById(R.id.iv_product_image);
        this.tv_company_name =  view.findViewById(R.id.tv_company_name);
        this.tv_state_desc =  view.findViewById(R.id.tv_state_desc);
        this.tv_address_name =  view.findViewById(R.id.tv_address_name);
        this.tv_view_product =  view.findViewById(R.id.tv_view_product);
        this.rb_favourite = view.findViewById(R.id.rb_favourite);

        Typeface faceLight = Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Thin.ttf");
        this.tv_company_name.setTypeface(faceLight);
        Typeface facebold = Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        this.tv_company_name.setTypeface(facebold);
        this.tv_state_desc.setTypeface(facebold);
        this.tv_address_name.setTypeface(facebold);
        this.tv_view_product.setTypeface(facebold);
    }

    @Override
    public void onClick(View v) {

        mSelectedItem = getAdapterPosition();
    }


}