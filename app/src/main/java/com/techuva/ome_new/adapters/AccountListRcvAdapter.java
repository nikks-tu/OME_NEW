package com.techuva.ome_new.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.techuva.ome_new.MainActivity;
import com.techuva.ome_new.R;
import com.techuva.ome_new.activity.SupplierMainActivity;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.holders.AccountsNameRCVHolder;
import com.techuva.ome_new.object_models.AccountListResultObject;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;

public class AccountListRcvAdapter extends RecyclerView.Adapter<AccountsNameRCVHolder>
{
    private ArrayList<AccountListResultObject> arrayList;
    private Context context;
    private AccountsNameRCVHolder listHolder;
    private CompoundButton lastCheckedRB = null;
    private String UserName="";
    private int selectedPosition = -1;
    private OnItemClicked listener;
    private boolean isRoleSelected;
    private boolean onBind;
    private EventListener eventListener;

    public AccountListRcvAdapter(Context context, ArrayList<AccountListResultObject> arrayList, EventListener eventListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.eventListener = eventListener;
    }

    @Override
    public int getItemCount() {
       // return (null != arrayList ? arrayList.size() : 0);
        return arrayList.size();

    }

    @Override
    public void onBindViewHolder(AccountsNameRCVHolder holder, int position) {
        AccountListResultObject model = arrayList.get(position);
        //UserName = MApplication.getString(context, "UserName");
        holder.companyName.setText(model.getTypeDescription());
        holder.nameInitials.setText(firstTwo(model.getTypeDescription()));
        isRoleSelected = MApplication.getBoolean(context, Constants.IsRoleSelected);
        onBind = true;
         if(isRoleSelected)
         {

             UserName = MApplication.getString(context, Constants.UserType);
             if(UserName.equals(model.getTypeCode()))
             {
                 holder.rb_checked.setChecked(true);
             }
             else {
                 holder.rb_checked.setChecked(false);
             }
             holder.rb_checked.setOnCheckedChangeListener(ls);
             holder.rb_checked.setTag(position);
         }
         else {
             holder.rb_checked.setOnCheckedChangeListener(ls);
             holder.rb_checked.setTag(position);
             holder.rb_checked.setChecked(position == selectedPosition);
         }
    }



    private CompoundButton.OnCheckedChangeListener ls = ((buttonView, isChecked) -> {
        int tag = (int) buttonView.getTag();
        //Toast.makeText(context, "Changed", Toast.LENGTH_SHORT).show();
        if (lastCheckedRB == null) {
            lastCheckedRB = buttonView;
            itemCheckChanged(buttonView);

        } else if (tag != (int) lastCheckedRB.getTag()) {
            lastCheckedRB.setChecked(false);
            lastCheckedRB = buttonView;
            itemCheckChanged(buttonView);
        }

    });


    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }

    @Override
    public AccountsNameRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_account_list, viewGroup, false);
        listHolder = new AccountsNameRCVHolder(mainGroup);
        return listHolder;

    }


    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        if(!getSelectedItem().equals(""))
        {
            if(arrayList.get(selectedPosition).getTypeCode().equals("SUPPL"))
            {
               // Toast.makeText(context, "Changed", Toast.LENGTH_SHORT).show();

                MApplication.setBoolean(context, Constants.IsDefaultDeviceSaved, false);
                MApplication.setString(context, Constants.UserType, arrayList.get(Integer.parseInt(getSelectedItem())).getTypeCode());
                MApplication.setBoolean(context, Constants.IsRoleSelected, true);
                Intent intent = new Intent(context, SupplierMainActivity.class);
                context.startActivity(intent);
            }
            else
            {
               // Toast.makeText(context, "Changed !", Toast.LENGTH_SHORT).show();

                MApplication.setBoolean(context, Constants.IsDefaultDeviceSaved, false);
                MApplication.setString(context, Constants.UserType, arrayList.get(Integer.parseInt(getSelectedItem())).getTypeCode());
                MApplication.setBoolean(context, Constants.IsRoleSelected, true);
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

        }
        else
            Toast.makeText(context, "Please select account to proceed", Toast.LENGTH_SHORT).show();
        /*
        Intent intent = new Intent(context, Dashboard.class);
        context.startActivity(intent);*/
        if(!onBind) {
            notifyDataSetChanged();
        }
    }

    public interface OnItemClicked {
        void onItemClick(View view, int position);
    }

    public String getSelectedItem() {
        if ( selectedPosition!= -1) {
           // Toast.makeText(context, "Selected Item : " + arrayList.get(selectedPosition).getCompanyName(), Toast.LENGTH_SHORT).show();
            return String.valueOf(selectedPosition);
        }
        return "";
    }

    public interface EventListener {
        void onEvent(Boolean data);

        void onDelete(Boolean data);
    }


}