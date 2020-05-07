package com.techuva.ome_new.holders;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.techuva.ome_new.R;


public class ConsumerOrderRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_order_id;
    public TextView tv_company_name;
    public TextView tv_total_items;
    public TextView tv_order_date;
    public TextView tv_view_details;
    public int mSelectedItem;
    public ConsumerOrderRCVHolder(View view) {
        super(view);
        // Find all views ids
        this.tv_order_id =  view.findViewById(R.id.tv_order_id);
        this.tv_company_name =  view.findViewById(R.id.tv_company_name);
        this.tv_total_items =  view.findViewById(R.id.tv_total_items);
        this.tv_order_date =  view.findViewById(R.id.tv_order_date);
        this.tv_view_details =  view.findViewById(R.id.tv_view_details);

        Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        this.tv_order_id.setTypeface(facebold);
        this.tv_company_name.setTypeface(facebold);
        this.tv_total_items.setTypeface(facebold);
        this.tv_order_date.setTypeface(facebold);
        this.tv_view_details.setTypeface(facebold);
    }

    @Override
    public void onClick(View v) {

        mSelectedItem = getAdapterPosition();
    }

    private void setTypeface() {

    }
}