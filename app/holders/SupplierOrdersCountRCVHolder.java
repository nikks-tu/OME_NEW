package com.example.ordermadeeasy.holders;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ordermadeeasy.R;

public class SupplierOrdersCountRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

public LinearLayout ll_main;
public CardView cv_day_view;
public TextView tv_order_count;
public TextView tv_order_status;




public SupplierOrdersCountRCVHolder(View view) {
        super(view);
        this.ll_main =  view.findViewById(R.id.ll_main);
        this.cv_day_view =  view.findViewById(R.id.cv_day_view);
        this.tv_order_count =  view.findViewById(R.id.tv_order_count);
        this.tv_order_status =  view.findViewById(R.id.tv_order_status);

        }

        @Override
        public void onClick(View v) {
                int position = getAdapterPosition();
        }
}