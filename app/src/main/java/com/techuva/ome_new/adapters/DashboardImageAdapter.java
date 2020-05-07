package com.techuva.ome_new.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.techuva.ome_new.R;
import com.techuva.ome_new.object_models.DealerProductImgObject;

import java.util.ArrayList;

public class DashboardImageAdapter extends PagerAdapter {
    Context context;
    String images[];
    ArrayList<DealerProductImgObject> arrayList;
    LayoutInflater layoutInflater;


    public DashboardImageAdapter(Context context, ArrayList<DealerProductImgObject> arraylist) {
        this.context = context;
        this.arrayList = arraylist;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_admin_images, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_image_admin);

        DealerProductImgObject model = arrayList.get(position);
       // Toast.makeText(context, "" +arrayList.get(position).getImageUrl(), Toast.LENGTH_LONG).show();
        Picasso.with(context).setLoggingEnabled(true);
        if(!model.getImageUrl().equals(""))
        {
            Picasso.with(context).load(model.getImageUrl()).into(imageView);
        }
        else
        {
            Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(imageView);
        }
       // ((ViewPager) container).addView(itemView);
        container.addView(itemView, 0);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup viewPager, int position, Object object) {
        viewPager.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }
}