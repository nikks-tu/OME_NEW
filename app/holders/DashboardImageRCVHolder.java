package com.example.ordermadeeasy.holders;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ordermadeeasy.R;

public class DashboardImageRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public ImageView iv_product_image;
    public int mSelectedItem;
    public DashboardImageRCVHolder(View view) {
        super(view);
        this.iv_product_image =  view.findViewById(R.id.iv_image);
    }

    @Override
    public void onClick(View v) {

        mSelectedItem = getAdapterPosition();
    }

    private void setTypeface() {

    }
}