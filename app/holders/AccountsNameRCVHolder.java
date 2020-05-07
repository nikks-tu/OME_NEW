package com.example.ordermadeeasy.holders;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ordermadeeasy.R;


public class AccountsNameRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

public TextView companyName;
public TextView nameInitials;
public RadioButton rb_checked;
public int mSelectedItem;
public AccountsNameRCVHolder(View view) {
        super(view);
        // Find all views ids

        this.companyName =  view.findViewById(R.id.tv_user_names);
        this.nameInitials = view.findViewById(R.id.tv_name_initials);
        this.rb_checked = view.findViewById(R.id.rb_account_used);
        Typeface faceLight = Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Light.ttf");
        this.companyName.setTypeface(faceLight);
        this.nameInitials.setTypeface(faceLight);
        }

        @Override
        public void onClick(View v) {

        mSelectedItem = getAdapterPosition();
        }

        private void setTypeface() {

        }
}