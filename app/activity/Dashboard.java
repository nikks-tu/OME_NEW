package com.example.ordermadeeasy.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.adapters.AllOrdersAdapter;
import com.example.ordermadeeasy.adapters.CartListAdapter;
import com.example.ordermadeeasy.adapters.GridFavCompanyAdapter;
import com.example.ordermadeeasy.adapters.SearchProductAdapter;
import com.example.ordermadeeasy.api_interface.CompanyFavouriteDataInterface;
import com.example.ordermadeeasy.api_interface.DashboardImagesDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.domain.PlaceOrderDO;
import com.example.ordermadeeasy.listener.EndlessScrollListener;
import com.example.ordermadeeasy.object_models.AdvertisementObject;
import com.example.ordermadeeasy.object_models.AllOrdersResultObject;
import com.example.ordermadeeasy.object_models.CompanyListObjectModel;
import com.example.ordermadeeasy.object_models.DashboardImgResutObject;
import com.example.ordermadeeasy.object_models.DealerProductImgObject;
import com.example.ordermadeeasy.object_models.GetDealerResultObject;
import com.example.ordermadeeasy.object_models.OrderStatusResultObject;
import com.example.ordermadeeasy.post_parameters.CompanyFavouritePostParameters;
import com.example.ordermadeeasy.post_parameters.GetDashboardImgPostParameters;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;
import com.example.ordermadeeasy.views.SimpleLayoutBanner;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Dashboard extends Fragment implements CartListAdapter.EventListener, AllOrdersAdapter.EventListener, AllOrdersAdapter.OnItemClicked , SearchProductAdapter.EventListener,
ProductDescFragment.EventListener, GridFavCompanyAdapter.EventListener{

    LinearLayout ll_main_layout, ll_cart_count;
    ViewPager vp_admin_images;
    GridView gv_fav_comp;
    Context context;
    int cartSize;
    EditText edt_search;
    int pendingOrderCount;
    String retailerId = "";
    ArrayList<GetDealerResultObject> dealersList;
    ArrayList<DashboardImgResutObject> imgResult;
    ArrayList<PlaceOrderDO> cartList;
    CartListAdapter cartListAdapter;
    GridFavCompanyAdapter favCompanyAdapter;
    String pagePerCount = "100";
    int pageNumber = 1;
    LinearLayout ll_cart_items, ll_order_list, ll_create_cart, ll_no_fav_comp, ll_ads_one;
    ImageView iv_add_fav_company;
    Toast exitToast;
    public Dialog dialog;
    AppCompatActivity mActivity;
    private AnimationDrawable animationDrawable;
    TextView tv_cartSize, tv_num_pending_orders, tv_cart_items, tv_cart_items_txt;
    String authorityKey ="";
    String grantType = "password";
    String refreshToken = "";
    String accessToken ="";
    private String EmailId;
    private String Password;
    FrameLayout fl_icon_cart;
    Toolbar toolbar;
    private EventListener listener;
    SimpleLayoutBanner banner_admin;
    FlycoPageIndicaor indicator_circle;
    SimpleLayoutBanner banner_admin_one;
    FlycoPageIndicaor indicator_circle_one;
    private EndlessScrollListener scrollListener;
    ArrayList<OrderStatusResultObject> statusList;
    int listCount = 0;
    ArrayList<AllOrdersResultObject> orderList;
    Boolean doubleBackToExitPressedOnce = true;
    ArrayList<DealerProductImgObject> dealerProductImgObjects;
    ArrayList<AdvertisementObject> advertisementList;
    ArrayList<AdvertisementObject> advertisementListSAdmin;
    FragmentManager fragmentManager;
    ImageView iv_product_image;
    String userType;
    String userId;
    ArrayList<CompanyListObjectModel> companyList;
    String companyId;
    int tempCartSize;
    LinearLayout ll_banner, ll_indicator, ll_order_count;

    public static Dashboard newInstance() {

        Bundle args = new Bundle();
        Dashboard fragment = new Dashboard();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_dashboard, null, false);
        context = getActivity();
        initialize(contentView);
        CartDatabase.init(context);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        orderList = new ArrayList<>();
        //cartSize = CartDatabase.getCartlistCount();
        serviceCallforImages();
        scrollListener = new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (pageNumber < listCount) {
                    pageNumber = pageNumber + 1;
                    loadNextDataFromApi(pageNumber, 100);
                } else {
                    pageNumber = 1;
                }
                return true;
            }
        };


        ll_order_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pendingOrderCount>0)
                {
                    if(userType.equals(Constants.Consumer))
                    {
                        loadFragments(ConsumerOrdersActivity.newInstance());
                    }
                    else {
                        loadFragments(YourOrdersActivity.newInstance());
                    }
                }
            }
        });

        fl_icon_cart.setOnClickListener(v -> {
           if(MApplication.isNetConnected(context))
           {
               if(isInHomeFragment())
               {
                   onEvent(true);
                   Fragment someFragment = new YourCartActivity();
                   FragmentTransaction transaction = getHostFragmentManager().beginTransaction();
                   transaction.replace(R.id.fragment_container, someFragment , "Cart"); // give your fragment container id in first parameter
                   transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                   transaction.commit();
               }
           }
        });
        ll_create_cart.setOnClickListener(v -> {
           if(userType.equals(Constants.Shopper))
           {
               loadFragments(SearchProductActivity.newInstance(""));
           }
           else if(userType.equals(Constants.Consumer))
           {
               loadFragments(SearchCompanyActivity.newInstance(""));
           }
        });

        iv_add_fav_company.setOnClickListener(v -> {
            if(userType.equals(Constants.Consumer))
            {
                loadFragments(SearchCompanyActivity.newInstance(""));
            }
        });


        gv_fav_comp.setOnItemClickListener((parent, view, position, id) -> {
            tempCartSize = Integer.parseInt(MApplication.getString(context, Constants.CartSize));
            if(tempCartSize>0)
            {
                int tempCompanyId = Integer.parseInt(MApplication.getString(context, Constants.CompanyID));
                if(tempCompanyId== favCompanyAdapter.getItem(position).getCompany_id())
                {
                    MApplication.setString(context, Constants.CompanyID, String.valueOf(favCompanyAdapter.getItem(position).getCompany_id()));
                    MApplication.setString(context, Constants.SelectedCompany,  favCompanyAdapter.getItem(position).getCompany_name());
                    loadFragments(SearchProductActivity.newInstance(""));
                }
                else {
                    Toast.makeText(context, "You cannot choose different company! Please clear cart.", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                MApplication.setBoolean(context, Constants.IsFromFavourites, true);
                MApplication.setString(context, Constants.CompanyID, String.valueOf(favCompanyAdapter.getItem(position).getCompany_id()));
                MApplication.setString(context, Constants.SelectedCompany,  favCompanyAdapter.getItem(position).getCompany_name());
                loadFragments(SearchProductActivity.newInstance(""));
            }
        });


        return contentView;
    }

    private void initialize(View view) {
        toolbar =  getActivity().findViewById(R.id.toolbar);
        ll_main_layout = view.findViewById(R.id.ll_main_layout);
        ll_order_list = view.findViewById(R.id.ll_order_list);
        ll_cart_items = view.findViewById(R.id.ll_cart_items);
        ll_create_cart = view.findViewById(R.id.ll_create_cart);
        ll_cart_count = view.findViewById(R.id.ll_cart_count);
        ll_ads_one = view.findViewById(R.id.ll_ads_one);
        ll_banner = view.findViewById(R.id.ll_banner);
        ll_indicator = view.findViewById(R.id.ll_indicator);
        ll_order_count = view.findViewById(R.id.ll_order_count);
        iv_add_fav_company = view.findViewById(R.id.iv_add_fav_company);
        retailerId = MApplication.getString(context, Constants.RetailerID);
        exitToast = Toast.makeText(getActivity(), "Press back again to exit Order Made Easy", Toast.LENGTH_SHORT);
        setTypeFace();
        authorityKey = Constants.AuthorizationKey;
        grantType = Constants.GrantType;
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(context, Constants.AccessToken));
        accessToken = sb.toString();
        refreshToken = MApplication.getString(context, Constants.RefreshToken);
        EmailId = MApplication.getString(getContext(), Constants.LoginID);
        Password = MApplication.getString(getContext(), Constants.Password);
        fl_icon_cart = toolbar.findViewById(R.id.fl_icon_cart);
        tv_num_pending_orders = view.findViewById(R.id.tv_num_pending_orders);
        tv_cart_items = view.findViewById(R.id.tv_cart_items);
        tv_cart_items_txt = view.findViewById(R.id.tv_cart_items_txt);
        tv_cartSize = toolbar.findViewById(R.id.tv_cart_size);
        vp_admin_images = view.findViewById(R.id.vp_admin_images);
        gv_fav_comp = view.findViewById(R.id.gv_fav_comp);
        ll_no_fav_comp = view.findViewById(R.id.ll_no_fav_comp);
        edt_search = getActivity().findViewById(R.id.edt_search);
        iv_product_image = view.findViewById(R.id.iv_product_image);
        banner_admin = view.findViewById(R.id.banner_admin);
        indicator_circle = view.findViewById(R.id.indicator_circle);
        banner_admin_one = view.findViewById(R.id.banner_admin_one);
        indicator_circle_one = view.findViewById(R.id.indicator_circle_one);
        indicator_circle.bringToFront();
        userType = MApplication.getString(context, Constants.UserType);
        userId = MApplication.getString(context, Constants.UserID);
        companyId = MApplication.getString(context, Constants.CompanyID);
        if(MApplication.getString(context, Constants.CartSize).equals(""))
        {
            cartSize=0;
        }
        else {
            cartSize = Integer.parseInt(MApplication.getString(context, Constants.CartSize));
        }
        advertisementList = new ArrayList<>();
        advertisementListSAdmin = new ArrayList<>();
        tv_cartSize.setText(String.valueOf(cartSize));
        if(cartSize>0)
        {
            if(cartSize==1)
            {
                tv_cart_items_txt.setText("Item in cart");
            }
            ll_create_cart.setVisibility(View.GONE);
            ll_cart_count.setVisibility(View.VISIBLE);
            tv_cart_items.setText(String.valueOf(cartSize));
        }
//Try
        new Handler().post(() -> {
            String count = MApplication.getString(context, Constants.PendingOrderCount);
            if(!count.equals(""))
            {
                pendingOrderCount = Integer.parseInt(MApplication.getString(context, Constants.PendingOrderCount));
                tv_num_pending_orders.setText(String.valueOf(pendingOrderCount));
            }
        });


        ll_cart_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragments(YourCartActivity.newInstance());
            }
        });

        if(userType.equals(Constants.Shopper))
        {
            ll_ads_one.setVisibility(View.VISIBLE);
            ll_no_fav_comp.setVisibility(View.GONE);
            MApplication.serviceCallforShopperCartProducts(context, userId, accessToken);
            MApplication.serviceCallforShopperPendingOrders(context, accessToken, userId, userType);
            if(MApplication.getString(context, Constants.CartSize).equals(""))
            {
                cartSize = 0;
            }
            else {
                cartSize = Integer.parseInt(MApplication.getString(context, Constants.CartSize));
            }
            tv_cartSize.setText(String.valueOf(cartSize));
        }
        else if(userType.equals(Constants.Consumer))
        {
            companyList = new ArrayList<>();
            serviceCallForFavouriteCompanyList();
            ll_ads_one.setVisibility(View.VISIBLE);
            ll_no_fav_comp.setVisibility(View.VISIBLE);
            MApplication.serviceCallforCartProducts(context, userId, accessToken);
            MApplication.serviceCallforConsumerPendingOrders(context, accessToken, userId, userType);
            if(MApplication.getString(context, Constants.CartSize).equals(""))
            {
                cartSize = 0;
                edt_search.setHint("Search Company");
                MApplication.setString(context, Constants.SelectedCompany, "");
            }
            else {
                cartSize = Integer.parseInt(MApplication.getString(context, Constants.CartSize));
            }
            tv_cartSize.setText(String.valueOf(cartSize));

        }
        new Handler().post(() -> {
            String count = MApplication.getString(context, Constants.PendingOrderCount);
            if(!count.equals(""))
            {
                pendingOrderCount = Integer.parseInt(MApplication.getString(context, Constants.PendingOrderCount));
                tv_num_pending_orders.setText(String.valueOf(pendingOrderCount));
            }
        });

        updateTextView();
    }

    private void setTypeFace() {

        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Thin.ttf");
        Typeface typebold = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
       /* tv_empty_cart.setTypeface(typeface);
        tv_empty_order_list.setTypeface(typeface);
        tv_more_items_cart.setTypeface(typeface);
        tv_proceed_to_cart.setTypeface(typebold);
        tv_cart_items_txt.setTypeface(typebold);
        tv_pending_order_txt.setTypeface(typebold);
        tv_empty_cart.setTypeface(typebold);
        tv_empty_order_list.setTypeface(typebold);*/
    }


    @Override
    public void onResume() {
       /* if(userType.equals(Constants.Shopper))
        {
            ll_ads_1.setVisibility(View.VISIBLE);
            ll_no_fav_comp.setVisibility(View.GONE);
            MApplication.serviceCallforShopperCartProducts(context, userId, accessToken);
            MApplication.serviceCallforShopperPendingOrders(context, accessToken, userId, userType);
            if(MApplication.getString(context, Constants.CartSize).equals(""))
            {
                cartSize = 0;
            }
            else {
                cartSize = Integer.parseInt(MApplication.getString(context, Constants.CartSize));
            }
            tv_cartSize.setText(String.valueOf(cartSize));

        }
        else if(userType.equals(Constants.Consumer))
        {
            serviceCallForFavouriteCompanyList();
            ll_ads_1.setVisibility(View.VISIBLE);
            ll_no_fav_comp.setVisibility(View.VISIBLE);
            MApplication.serviceCallforCartProducts(context, userId, accessToken);
            MApplication.serviceCallforConsumerPendingOrders(context, accessToken, userId, userType);
            if(MApplication.getString(context, Constants.CartSize).equals(""))
            {
                cartSize = 0;
            }
            else {
                cartSize = Integer.parseInt(MApplication.getString(context, Constants.CartSize));
            }
            tv_cartSize.setText(String.valueOf(cartSize));

        }
        updateTextView();*/
       if(cartSize==0){
           edt_search.setHint("Search Company");
           MApplication.setString(context, Constants.SelectedCompany, "");
       }
        super.onResume();
    }

    private void updateTextView() {

        mActivity.runOnUiThread(() -> {
            String count = MApplication.getString(context, Constants.PendingOrderCount);
            if(!count.equals(""))
            {
                pendingOrderCount = Integer.parseInt(MApplication.getString(context, Constants.PendingOrderCount));
                tv_num_pending_orders.setText(String.valueOf(pendingOrderCount));
            }
        });
    }

    @Override
    public void onEvent(Boolean data) {

        onResume();
    }

    @Override
    public void startLoader(Boolean data) {

    }

    @Override
    public void stopLoader(Boolean data) {

    }

    @Override
    public void onDelete(Boolean data) {
        Toast.makeText(context, "HI", Toast.LENGTH_SHORT).show();
        onResume();
    }


    private void showCustomDialog(String alertText) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_no_dealers, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        TextView tv_alertText = dialogView.findViewById(R.id.tv_alertText);
        tv_alertText.setText(alertText);
        TextView button_yes = dialogView.findViewById(R.id.button_yes);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        button_yes.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        });
        //finally creating the alert dialog and displaying it

    }

    public  void loadFragments(Fragment fragment)
    {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
                .commit();
    }

    @Override
    public void onSuccess(boolean data) {
        if(data)
        {
            //serviceCallforGettingAllOrders();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        mActivity = activity;
    }

    private boolean isInHomeFragment() {
        for (Fragment item : getActivity().getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "Dashboard".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    public FragmentManager getHostFragmentManager() {
        FragmentManager fm = getFragmentManager();
        if (fm == null && isAdded()) {
            fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        }
        return fm;
    }


    private void serviceCallforImages(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DashboardImagesDataInterface service = retrofit.create(DashboardImagesDataInterface.class);

        //Call<MyProfileMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new MyProfilePostParameters( retailerId, "", ""));
        Call<JsonElement> call = service.getAdvertisements(Integer.parseInt(userId), accessToken, new GetDashboardImgPostParameters( companyId, "ALL", String.valueOf(pageNumber), userType));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    if(errorCode==0)
                    {
                        JsonObject object = jsonObject.get("result").getAsJsonObject();
                        if(!object.isJsonNull())
                        {
                            AdvertisementObject advertisementObject;
                            JsonArray jsonArray = object.get("OTHER").getAsJsonArray();
                            if(jsonArray.size()>0)
                            {
                                for (int i=0; i<jsonArray.size(); i++)
                                {
                                    advertisementObject = new AdvertisementObject();
                                    JsonObject image = jsonArray.get(i).getAsJsonObject();
                                    advertisementObject.setAdvertisementId(image.get("advertisement_id").getAsInt());
                                    advertisementObject.setCompanyId(image.get("company_id").getAsInt());
                                    advertisementObject.setImageUrl(image.get("image_url").getAsString());
                                   if(image.has("image_name"))
                                    {
                                        advertisementObject.setImageName(image.get("image_name").getAsString());
                                    }
                                    advertisementObject.setImageDesc(image.get("image_desc").getAsString());
                                    advertisementObject.setTypeCode(image.get("type_code").getAsString());
                                    advertisementList.add(advertisementObject);
                                }
                            }
                            if(advertisementList.size()>0)
                            {
                                banner_admin.setSource(advertisementList).startScroll();
                                indicator_circle.setViewPager(banner_admin.getViewPager(), advertisementList.size());
                            }
                            else {
                                ll_indicator.setVisibility(View.GONE);
                                ll_banner.setVisibility(View.GONE);
                            }
                            JsonArray jsonArray1 = object.get("SADMIN").getAsJsonArray();
                            if(jsonArray1.size()>0)
                            {
                                for (int i=0; i<jsonArray1.size(); i++)
                                {
                                    advertisementObject = new AdvertisementObject();
                                    JsonObject image = jsonArray1.get(i).getAsJsonObject();
                                    advertisementObject.setAdvertisementId(image.get("advertisement_id").getAsInt());
                                    advertisementObject.setCompanyId(image.get("company_id").getAsInt());
                                    advertisementObject.setImageUrl(image.get("image_url").getAsString());
                                   if(image.has("image_name"))
                                    {
                                        advertisementObject.setImageName(image.get("image_name").getAsString());
                                    }
                                    advertisementObject.setImageDesc(image.get("image_desc").getAsString());
                                    advertisementObject.setTypeCode(image.get("type_code").getAsString());
                                    advertisementListSAdmin.add(advertisementObject);
                                }
                            }
                            if(advertisementListSAdmin.size()>0)
                            {
                                banner_admin_one.setSource(advertisementListSAdmin).startScroll();
                                indicator_circle_one.setViewPager(banner_admin_one.getViewPager(), advertisementListSAdmin.size());
                            }
                        }
                    }
                }else {
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();
                    hideloader();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideloader();
                //Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                loadFragments(NoInternetConnectionFragment.newInstance());
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }


    private void loadNextDataFromApi(int page, int delay) {
        pageNumber = page;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                showLoaderNew();
               // serviceCallforImages();
            }
        }, delay);
    }


    @Override
    public void onRefresh(Boolean data) {
        companyList = new ArrayList<>();
        serviceCallForFavouriteCompanyList();
    }

    public void showLoaderNew() {
        mActivity.runOnUiThread(new Dashboard.Runloader(getResources().getString(R.string.loading)));
    }
    class Runloader implements Runnable {
        private String strrMsg;

        public Runloader(String strMsg) {
            this.strrMsg = strMsg;
        }

        @SuppressWarnings("ResourceType")
        @Override
        public void run() {
            try {
                if (dialog == null)
                {
                    dialog = new Dialog(context,R.style.Theme_AppCompat_Light_DarkActionBar);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                }
                dialog.setContentView(R.layout.loading);
                dialog.setCancelable(false);

                if (dialog != null && dialog.isShowing())
                {
                    dialog.dismiss();
                    dialog=null;
                }
                dialog.show();

                ImageView imgeView = dialog
                        .findViewById(R.id.imgeView);
                TextView tvLoading = dialog
                        .findViewById(R.id.tvLoading);
                if (!strrMsg.equalsIgnoreCase(""))
                    tvLoading.setText(strrMsg);

                imgeView.setBackgroundResource(R.drawable.frame);

                animationDrawable = (AnimationDrawable) imgeView
                        .getBackground();
                imgeView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (animationDrawable != null)
                            animationDrawable.start();
                    }
                });
            } catch (Exception e)
            {

            }
        }
    }

    public void hideloader() {
        mActivity.runOnUiThread(() -> {
            try
            {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }); }

    private void serviceCallForFavouriteCompanyList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CompanyFavouriteDataInterface service = retrofit.create(CompanyFavouriteDataInterface.class);
        Call<JsonElement> call = service.getFavouriteCompanies(Integer.parseInt(userId), accessToken,
                new CompanyFavouritePostParameters(userId, "", String.valueOf(pageNumber)));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                hideloader();
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if(errorCode==0){
                        JsonArray resultArray = jsonObject.get("result").getAsJsonArray();
                        companyList = new ArrayList<>();
                        if(resultArray.size()>0)
                        {
                            gv_fav_comp.setVisibility(View.VISIBLE);
                            ll_no_fav_comp.setVisibility(View.GONE);
                            for (int i=0; i<resultArray.size(); i++)
                            {
                                CompanyListObjectModel model = new CompanyListObjectModel();
                                JsonObject object = resultArray.get(i).getAsJsonObject();
                                model.setCompany_id(object.get("company_id").getAsInt());
                                model.setCompany_short_code(object.get("company_short_code").getAsString());
                                model.setCompany_name(object.get("company_name").getAsString());
                                model.setState_name(object.get("state_name").getAsString());
                                model.setCompany_addr(object.get("company_addr").getAsString());
                                model.setCity_name(object.get("city_name").getAsString());
                                if(object.get("favourite").getAsInt()==0){
                                    model.setFavourite(false);
                                }
                                else model.setFavourite(true);
                                companyList.add(model);
                            }
                            setDataToList(companyList);
                        }
                        else {
                            companyList = new ArrayList<>();
                            gv_fav_comp.setVisibility(View.GONE);
                            ll_no_fav_comp.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        companyList = new ArrayList<>();
                        gv_fav_comp.setVisibility(View.GONE);
                        ll_no_fav_comp.setVisibility(View.VISIBLE);
                    }

                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                }else {
                    hideloader();
                    gv_fav_comp.setVisibility(View.GONE);
                    ll_no_fav_comp.setVisibility(View.VISIBLE);
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideloader();
                //Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                loadFragments(NoInternetConnectionFragment.newInstance());
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }

    private void setDataToList(ArrayList<CompanyListObjectModel> companyList) {
        favCompanyAdapter = new GridFavCompanyAdapter(context, R.layout.grid_fav_company, companyList, Dashboard.this);
        gv_fav_comp.setAdapter(favCompanyAdapter);
    }



}
