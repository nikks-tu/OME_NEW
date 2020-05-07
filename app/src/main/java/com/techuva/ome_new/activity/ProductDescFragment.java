package com.techuva.ome_new.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.techuva.ome_new.MainActivity;
import com.techuva.ome_new.R;
import com.techuva.ome_new.api_interface.AddProductToCartInterface;
import com.techuva.ome_new.api_interface.PlaceOrderDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.domain.PlaceOrderDO;
import com.techuva.ome_new.object_models.SearchProductResultObject;
import com.techuva.ome_new.post_parameters.GetCartConsumerPostParameter;
import com.techuva.ome_new.views.MApplication;

import java.util.EventListener;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ProductDescFragment extends Fragment implements EventListener {

    Toolbar toolbar;
    FrameLayout fl_icon_cart;
    ImageView iv_product_image, iv_dealers;
    TextView tv_product_description;
    Spinner spin_quantity;
    TextView tv_add_to_cart_prod;
    TextView tv_prod_category;
    TextView tv_prod_sub_category;
    TextView tv_dealer_txt;
    TextView tv_prod_type;
    TextView tv_alertText;
    TextView tv_prod_category_txt;
    TextView tv_prod_sub_category_txt;
    TextView tv_prod_type_txt;
    TextView tv_alert_head;
    TextView tv_desc_head;
    TextView tv_qty;
    TextView tv_dealer_head;
    TextView tv_prod_desc;
    TextView tv_prod_desc_txt;
    TextView tv_qty_new;
    TextView tv_prod_price_txt;
    TextView tv_prod_price;
    ImageView iv_inc_qty, iv_dec_qty;
    Context context;
    int dealerId = 0;
    String dealerName = "";
    String userID = "";
    String productId = "";
    String accessToken="";
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    Activity mActivity;
    LinearLayout ll_product_exist_alert;
    private Activity activity;
    TextView tv_cartSize;
    String companyID="";
    int productQuantity=0;
    String company_id="";
    String totalAmount="";
    int statusId;
    String userType;
    int consumerPrice;
    int shopperPrice;
    int cartSize;
    private EventListener listener;
    LinearLayout ll_bottom_view, ll_cart;
    TextView tv_comp_heading, tv_company_name, tv_items_in_cart_txt, tv_items_in_cart, tv_total_cart_value_txt;
    TextView tv_total_cart_value, tv_cart_btn;

    public ProductDescFragment() {
        // Required empty public constructor
    }


    public static ProductDescFragment newInstance() {
        ProductDescFragment fragment = new ProductDescFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.fragment_product_desc, null, false);
        initialize(contentView);
        activity = getActivity();
        Bundle bundle = getArguments();
        assert bundle != null;
        listener = this.listener;
        updateBottomView();
        SearchProductResultObject obj= (SearchProductResultObject) bundle.getSerializable("product");
        if(obj != null)
        {

            productId = String.valueOf(obj.getProductId());
            company_id = String.valueOf(obj.getCompanyId());
            if(obj.getConsumerPrice()!=null)
            {
                consumerPrice = obj.getConsumerPrice();
            }
            if(obj.getShopperPrice()!=null)
            {
                shopperPrice = obj.getShopperPrice();
            }
            tv_prod_category.setText(obj.getCategoryName());
            tv_prod_sub_category.setText(obj.getSubCategoryName());
            tv_prod_type.setText(obj.getType());
            tv_prod_desc.setText(obj.getProductDetails());
            tv_product_description.setText(obj.getProductDescription());
            if(userType.equals(Constants.Consumer))
            {
                tv_prod_price.setText(context.getResources().getString(R.string.rs)+" "+obj.getConsumerPrice());
            }
            else {
                tv_prod_price.setText(context.getResources().getString(R.string.rs)+" "+obj.getShopperPrice());
            }
            if(obj.getImageUrl()!=null)
            {
                if(!obj.getImageUrl().equals(""))
                {
                    Picasso.with(context).load(obj.getImageUrl()).into(iv_product_image);
                }
                else
                    Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(iv_product_image);
            }
            else
                Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(iv_product_image);

        }

        companyID = MApplication.getString(context, Constants.CompanyID);
        iv_dec_qty.setOnClickListener(v -> {
            productQuantity = Integer.parseInt(tv_qty_new.getText().toString());
            if(productQuantity>=1){
                productQuantity = productQuantity-1;
                Handler txtsettext = new Handler(Looper.getMainLooper());
                txtsettext.post(new Runnable() {
                    public void run() {
                        tv_qty_new.setText(String.valueOf(productQuantity));
                    }
                });
            }
        });

        iv_inc_qty.setOnClickListener(v -> {
            productQuantity = Integer.parseInt(tv_qty_new.getText().toString());
            if(productQuantity>=0){
                productQuantity = productQuantity+1;
                Handler txtsettext = new Handler(Looper.getMainLooper());
                txtsettext.post(new Runnable() {
                    public void run() {
                        tv_qty_new.setText(String.valueOf(productQuantity));
                    }
                });            }
        });

        tv_cart_btn.setOnClickListener(v -> {
            Fragment someFragment = new YourCartActivity();
            FragmentTransaction transaction;
            if(getFragmentManager() != null)
            {
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment , "Cart"); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        fl_icon_cart.setOnClickListener(v -> {
            Fragment someFragment = new YourCartActivity();
            FragmentTransaction transaction;
            if(getFragmentManager() != null)
            {
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment , "Cart"); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        tv_add_to_cart_prod.setOnClickListener(v -> {
            if(productQuantity>0){
                if(userType.equals("CONSU"))
                {
                    getProductDetails();
                }
                else if(userType.equals(Constants.Shopper)){
                    JsonArray jsonArray  = new JsonArray();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("product_id", productId);
                    jsonObject.addProperty("company_id", company_id);
                    jsonObject.addProperty("quantity", productQuantity);
                    totalAmount = String.valueOf(shopperPrice*productQuantity);
                    jsonObject.addProperty("total_amount", totalAmount);
                    jsonArray.add(jsonObject);
                    addProductToCartForShopper(jsonArray);
                }
            }
            else {
                Toast.makeText(context, "Please increase quantity!", Toast.LENGTH_SHORT).show();
            }
        });
        return  contentView;
    }

    private void RefreshView() {
        loadFragments(ProductDescFragment.newInstance(), "");
    }

    private void updateBottomView() {
        if (userType.equals(Constants.Consumer)){
            ll_bottom_view.setVisibility(View.VISIBLE);
            MApplication.serviceCallforCartProducts(context, userID, accessToken);
            tv_company_name.setText(MApplication.getString(context, Constants.SelectedCompany));
            tv_items_in_cart.setText(MApplication.getString(context, Constants.CartSize));
            tv_total_cart_value.setText(MApplication.getString(context, Constants.TotalCartValue));
        }
        else {
            ll_bottom_view.setVisibility(View.GONE);
        }
    }


    public interface EventListener {
        void onEvent(Boolean data);

        void startLoader(Boolean data);

        void stopLoader(Boolean data);
    }

    private void getProductDetails() {
        totalAmount = "";
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("company_id", company_id);
        jsonObject.addProperty("product_id", productId );
        jsonObject.addProperty("quantity", productQuantity);
        totalAmount = String.valueOf(consumerPrice*productQuantity);
        jsonObject.addProperty("total_amount", totalAmount);
        jsonArray.add(jsonObject);
        addProductForConsumer(jsonArray);
    }

    private void initialize(View contentView) {
        context = getActivity();
        mActivity = getActivity();
        toolbar =  Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        fl_icon_cart = toolbar.findViewById(R.id.fl_icon_cart);
        iv_product_image = contentView.findViewById(R.id.iv_product_image);
        tv_product_description = contentView.findViewById(R.id.tv_product_description);
        tv_dealer_txt = contentView.findViewById(R.id.deler_select_tv);
        spin_quantity = contentView.findViewById(R.id.spin_quantity);
        tv_add_to_cart_prod = contentView.findViewById(R.id.tv_add_to_cart_prod);
        tv_prod_sub_category = contentView.findViewById(R.id.tv_prod_sub_category);
        tv_prod_type = contentView.findViewById(R.id.tv_prod_type);
        tv_prod_category = contentView.findViewById(R.id.tv_prod_category);
        tv_alertText = contentView.findViewById(R.id.tv_alertText);
        ll_product_exist_alert= contentView.findViewById(R.id.ll_product_exist_alert);
        tv_prod_category_txt = contentView.findViewById(R.id.tv_prod_category_txt);
        tv_prod_sub_category_txt = contentView.findViewById(R.id.tv_prod_sub_category_txt);
        tv_prod_type_txt = contentView.findViewById(R.id.tv_prod_type_txt);
        tv_alert_head = contentView.findViewById(R.id.tv_alert_head);
        tv_desc_head = contentView.findViewById(R.id.tv_desc_head);
        tv_qty = contentView.findViewById(R.id.tv_qty);
        //company_id = MApplication.getString(context, Constants.CompanyID);
        accessToken = "Bearer " + MApplication.getString(getActivity(), Constants.AccessToken);
        userID = MApplication.getString(context, Constants.UserID);
        userType = MApplication.getString(context, Constants.UserType);
        iv_dealers = contentView.findViewById(R.id.iv_dealers);
        tv_dealer_head = contentView.findViewById(R.id.tv_dealer_head);
        tv_prod_desc = contentView.findViewById(R.id.tv_prod_desc);
        tv_prod_desc_txt = contentView.findViewById(R.id.tv_prod_desc_txt);
        tv_prod_price_txt = contentView.findViewById(R.id.tv_prod_price_txt);
        tv_prod_price = contentView.findViewById(R.id.tv_prod_price);
        tv_qty_new = contentView.findViewById(R.id.tv_qty_new);
        iv_inc_qty = contentView.findViewById(R.id.iv_inc_qty);
        iv_dec_qty = contentView.findViewById(R.id.iv_dec_qty);
        tv_cartSize = toolbar.findViewById(R.id.tv_cart_size);
        ll_bottom_view = contentView.findViewById(R.id.ll_bottom_view);
        ll_cart = contentView.findViewById(R.id.ll_cart);
        tv_comp_heading = contentView.findViewById(R.id.tv_comp_heading);
        tv_company_name = contentView.findViewById(R.id.tv_company_name);
        tv_items_in_cart_txt = contentView.findViewById(R.id.tv_items_in_cart_txt);
        tv_items_in_cart = contentView.findViewById(R.id.tv_items_in_cart);
        tv_total_cart_value_txt = contentView.findViewById(R.id.tv_total_cart_value_txt);
        tv_total_cart_value = contentView.findViewById(R.id.tv_total_cart_value);
        tv_cart_btn = contentView.findViewById(R.id.tv_cart_btn);
        Typeface faceBold= Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        tv_add_to_cart_prod.setTypeface(faceBold);
        tv_prod_sub_category.setTypeface(faceBold);
        tv_prod_type.setTypeface(faceBold);
        tv_prod_category.setTypeface(faceBold);
        tv_alertText.setTypeface(faceBold);
        tv_dealer_txt.setTypeface(faceBold);
        tv_prod_category_txt.setTypeface(faceBold);
        tv_prod_sub_category_txt.setTypeface(faceBold);
        tv_prod_type_txt.setTypeface(faceBold);
        tv_dealer_head.setTypeface(faceBold);
        tv_alert_head.setTypeface(faceBold);
        tv_desc_head.setTypeface(faceBold);
        tv_qty.setTypeface(faceBold);
        tv_prod_desc.setTypeface(faceBold);
        tv_prod_desc_txt.setTypeface(faceBold);
        tv_qty_new.setTypeface(faceBold);
        tv_comp_heading.setTypeface(faceBold);
        tv_company_name.setTypeface(faceBold);
        tv_items_in_cart_txt.setTypeface(faceBold);
        tv_items_in_cart.setTypeface(faceBold);
        tv_total_cart_value_txt.setTypeface(faceBold);
        tv_total_cart_value.setTypeface(faceBold);
        tv_cart_btn.setTypeface(faceBold);
        tv_prod_price.setTypeface(faceBold);
        tv_prod_price_txt.setTypeface(faceBold);
        tv_cart_btn.setTypeface(faceBold);
        productQuantity = Integer.parseInt(tv_qty_new.getText().toString());
    }


    private void addProductToCartForShopper( JsonArray jsonArray) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AddProductToCartInterface service = retrofit.create(AddProductToCartInterface.class);
        Call<JsonElement> call = service.addProductToShopperCart(Integer.parseInt(userID), accessToken,jsonArray);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if (errorCode==0)
                    {
                        serviceCallforShopperCartProducts();
                        updateBottomView();
                        Toast.makeText(context, errorMsg,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, errorMsg,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //tv_add_to_cart_prod.setEnabled(true);
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });

    }

    private void addProductForConsumer(JsonArray jsonArray) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);

        //Call<PlaceOrderMainObject> call = service.getStringScalar(1, jsonArray);
        Call<JsonElement> call = service.addConsumerProductToCart(Integer.parseInt(userID),accessToken, jsonArray);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if (errorCode==0){
                         //listener.onEvent(true);
                         serviceCallforCartProducts();
                         updateBottomView();
                         Toast.makeText(context, errorMsg,Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(context, errorMsg,Toast.LENGTH_SHORT).show();
                    }


                }else {
                    //hideloader();
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }




    public void showLoaderNew() {
        mActivity.runOnUiThread(new ProductDescFragment.Runloader(getResources().getString(R.string.loading)));
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
                imgeView.post(() -> {
                    if (animationDrawable != null)
                        animationDrawable.start();
                });
            } catch (Exception e)
            {

            }
        }
    }

    public void hideLoader() {
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


    private void updateInDatabase(int dealerID, SearchProductResultObject obj, String dealerName, String quntity ) {

       /* CartDatabase.init(context);
        PlaceOrderDO placeOrderDO = new PlaceOrderDO();
        placeOrderDO.dealerId = dealerID;
        //placeOrderDO.retailerId = Integer.parseInt(retailerId);
        placeOrderDO.retailerId = Integer.parseInt(companyID);
        placeOrderDO.productId = obj.getProductId();
        placeOrderDO.quantity = Integer.parseInt(quntity);
        placeOrderDO.statusId = 1;
        placeOrderDO.receivedQuantity = 0;
        placeOrderDO.productName = obj.getProductShortDesc();
        placeOrderDO.productDesc = obj.getProductDescription();
        StringBuilder sb1 = new StringBuilder();
        *//*for (int i = 0; i < obj.getProductData().size(); i++) {
            sb1.append(obj.getProductData().get(i).getDealerId());
            sb1.append(",");
        }*//*
        placeOrderDO.dealerNames = sb1.toString();
        placeOrderDO.dealersName = dealerName;
        placeOrderDO.imageUrl = obj.getImageUrl();
        CartDatabase.updateCartProductsForDealerId(placeOrderDO);
    */}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    public  void loadFragments(Fragment fragment, String tag)
    {
        FragmentTransaction ft =  ((MainActivity)activity).getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.fragment_container, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void serviceCallforShopperCartProducts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);

        //Call<GetDealerMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new GetDealerPostParameters(retailerId));
        Call<JsonElement> call = service.getShopperProductToCart(Integer.parseInt(userID),accessToken, new GetCartConsumerPostParameter(userID, "","",""));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                //hideloader();
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    //Toast.makeText(cartContext, errorMsg,Toast.LENGTH_SHORT).show();
                    if(errorCode==0){
                        cartSize = infoObject.get("TotalRecords").getAsInt();
                        MApplication.setString(context, Constants.CartSize, String.valueOf(cartSize));
                        tv_cartSize.setText(String.valueOf(cartSize));
                        JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                        int totalAmt=0;
                        for (int i=0; i<jsonArray.size(); i++)
                        {
                            int price=0;
                            JsonObject object = jsonArray.get(i).getAsJsonObject();
                            PlaceOrderDO orderDO = new PlaceOrderDO();
                            orderDO.setCompany_id(String.valueOf(object.get("company_id").getAsInt()));
                            orderDO.setOrder_id(object.get("order_id").getAsInt());
                            orderDO.setProduct_description(object.get("product_description").getAsString());
                            if(object.has("product_image") && !object.get("product_image").isJsonNull())
                            {
                                orderDO.setImageUrl(object.get("product_image").getAsString());
                            }
                            orderDO.setQuantity(object.get("quantity").getAsInt());
                            orderDO.setPrice(object.get("price").getAsInt());
                            orderDO.setCategory(object.get("category_name").getAsString());
                            price = object.get("price").getAsInt()*object.get("quantity").getAsInt();
                            totalAmt = totalAmt+price;
                            orderDO.setTotal_amount(String.valueOf(totalAmt));
                            //cartList.add(orderDO);
                        }
                        //tv_total_cart_value.setText(String.valueOf(totalAmt));
                    }
                }else {
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }
    private void serviceCallforCartProducts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);

        //Call<GetDealerMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new GetDealerPostParameters(retailerId));
        Call<JsonElement> call = service.getConsumerProductToCart(Integer.parseInt(userID),accessToken, new GetCartConsumerPostParameter(userID, "","",""));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    //Toast.makeText(cartContext, errorMsg,Toast.LENGTH_SHORT).show();
                    if(errorCode==0){

                        cartSize = infoObject.get("TotalRecords").getAsInt();
                        MApplication.setString(context, Constants.CartSize, String.valueOf(cartSize));
                        tv_cartSize.setText(String.valueOf(cartSize));
                        tv_items_in_cart.setText(String.valueOf(cartSize));
                       // cartList = new ArrayList<>();
                        JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                        int totalAmt=0;
                        for (int i=0; i<jsonArray.size(); i++)
                        {
                            int price=0;
                            JsonObject object = jsonArray.get(i).getAsJsonObject();
                            PlaceOrderDO orderDO = new PlaceOrderDO();
                            orderDO.setCompany_id(String.valueOf(object.get("company_id").getAsInt()));
                            orderDO.setOrder_id(object.get("order_id").getAsInt());
                            orderDO.setProduct_description(object.get("product_description").getAsString());
                            if(!object.get("product_image").isJsonNull()){
                                orderDO.setImageUrl(object.get("product_image").getAsString());
                            }
                            orderDO.setQuantity(object.get("quantity").getAsInt());
                            orderDO.setPrice(object.get("price").getAsInt());
                            orderDO.setCategory(object.get("category_name").getAsString());
                            price = object.get("price").getAsInt()*object.get("quantity").getAsInt();
                            totalAmt = totalAmt+price;
                            orderDO.setTotal_amount(String.valueOf(totalAmt));
                           // cartList.add(orderDO);
                        }
                        /*tv_cartSize.setText(String.valueOf(cartSize));*/
                        MApplication.setString(context, Constants.CartSize, String.valueOf(cartSize));
                        tv_total_cart_value.setText(String.valueOf(totalAmt));
                    }

                }else {
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }

}
