package com.example.ordermadeeasy.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.listener.ListenerforRestaurentBanner;
import com.example.ordermadeeasy.object_models.AdvertisementObject;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.squareup.picasso.Picasso;


public class SimpleLayoutBanner extends BaseBanner<AdvertisementObject, SimpleLayoutBanner> {

    public ListenerforRestaurentBanner indicator;

    Viewholder viewholder;


    public SimpleLayoutBanner(Context context) {
        this(context, null, 0);
    }


    public SimpleLayoutBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLayoutBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
    }

    @Override
    public View onCreateItemView(final int position) {
        final View opview = View.inflate(context, R.layout.banner_admin_img, null);
        final AdvertisementObject imgObject = list.get(position);
        viewholder = new Viewholder();

        viewholder.image_bg = (ImageView) opview.findViewById(R.id.image_bg);

        //ImageLoader.getInstance().displayImage(list.get(position).restaurantImageUrl, viewholder.image_bg, options);
        if(!imgObject.getImageUrl().equals(""))
        {
            Picasso.with(context).load(imgObject.getImageUrl()).into(viewholder.image_bg);
        }
        else
        {
            Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(viewholder.image_bg);
        }


        return opview;
    }

    public void SetOnBannerClickListener(ListenerforRestaurentBanner indicator) {
        this.indicator = indicator;
    }

//    private RoundCornerIndicaor indicator;

    @Override
    public View onCreateIndicator() {
//        indicator = new RoundCornerIndicaor(context);
//        indicator.setViewPager(vp, list.size());
//        return indicator;
        return null;
    }

    @Override
    public void setCurrentIndicator(int i) {
        //  indicator.setCurrentItem(i);
    }

    class Viewholder {
        TextView tv_restaurent_theme, tv_restaurent_name;
        Button btn_order;
        LinearLayout ll_restaurent;
        ImageView image_bg;
    }

}
