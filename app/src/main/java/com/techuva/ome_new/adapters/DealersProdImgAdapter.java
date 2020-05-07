package com.techuva.ome_new.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.techuva.ome_new.R;
import com.techuva.ome_new.holders.DashboardImageRCVHolder;
import com.techuva.ome_new.object_models.DealerProductImgObject;

import java.util.ArrayList;
import java.util.List;

public class DealersProdImgAdapter extends BaseAdapter
{
    // Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<DealerProductImgObject> arrayList;
    private Context context;
    private DashboardImageRCVHolder listHolder;
    private int selectedPosition = -1;
    List<Integer> list;
    public DealersProdImgAdapter(Context context, ArrayList<DealerProductImgObject> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

  /*  @Override
    public void onBindViewHolder(DashboardImageRCVHolder holder, int position) {
        DealerProductImgObject model = arrayList.get(position);

        //Toast.makeText(context, ""+ model.getImageUrl(),Toast.LENGTH_SHORT).show();
        Picasso.with(context).setLoggingEnabled(true);
        if(!model.getImageUrl().equals(""))
        {
            Picasso.with(context).load(model.getImageUrl()).into(holder.iv_product_image);
        }
        else
        {
            Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
        }
    }*/

 /*   @Override
    public DashboardImageRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_dashboard_images, viewGroup, false);
        listHolder = new DashboardImageRCVHolder(mainGroup);
        return listHolder;

    }*/


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;

        // Inflater for custom layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        DealerProductImgObject model = arrayList.get(position);

        //Toast.makeText(context, ""+ model.getImageUrl(),Toast.LENGTH_SHORT).show();

        if (view == null) {
            view = inflater.inflate(R.layout.item_dashboard_images, parent, false);

            holder = new ViewHolder();

            holder.iv_image = (ImageView) view.findViewById(R.id.iv_image);
            Picasso.with(context).setLoggingEnabled(true);
            if(!model.getImageUrl().equals(""))
            {
                Picasso.with(context).load(model.getImageUrl()).into(holder.iv_image);
            }
            else
            {
                Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_image);
            }

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    private class ViewHolder {
        public ImageView iv_image;
    }
}