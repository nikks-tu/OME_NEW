package com.techuva.ome_new.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.techuva.ome_new.R;
import com.techuva.ome_new.object_models.AllOrdersCountResultObject;

import java.util.ArrayList;

public class GridAdapterDashboard extends ArrayAdapter<AllOrdersCountResultObject> {
    private final int resource;
    private Context mContext;
    ArrayList<AllOrdersCountResultObject> list;

    public GridAdapterDashboard(@NonNull Context context, int resource, @NonNull ArrayList<AllOrdersCountResultObject> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.mContext = context;
        this.list = objects;
    }

    // Constructor


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AllOrdersCountResultObject getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getView(int position, View convertView, ViewGroup parent) {


        View row = convertView;
        RecordHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new RecordHolder();
            holder.tv_channel_name = row.findViewById(R.id.tv_channel_name);
            holder.tv_channel_value = row.findViewById(R.id.tv_channel_value);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        AllOrdersCountResultObject item = list.get(position);

        Typeface faceLight = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Regular.ttf");
        holder.tv_channel_value.setText(String.valueOf(item.getTotalOrderByStatus()));
        holder.tv_channel_name.setTypeface(faceLight);
        holder.tv_channel_name.setText(item.getStatusGroupName());
        holder.tv_channel_value.setTypeface(faceLight);
        return row;
    }
    static class RecordHolder {
        TextView tv_channel_name;
        TextView tv_channel_value;
    }

}
