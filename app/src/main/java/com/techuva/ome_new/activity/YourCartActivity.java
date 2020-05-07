package com.techuva.ome_new.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.ome_new.R;
import com.techuva.ome_new.adapters.CartListAdapter;
import com.techuva.ome_new.adapters.PlaceOrderAdapter;
import com.techuva.ome_new.api_interface.PlaceOrderDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.domain.PlaceOrderDO;
import com.techuva.ome_new.object_models.GetDealerResultObject;
import com.techuva.ome_new.object_models.PlaceOrderResultObject;
import com.techuva.ome_new.post_parameters.GetCartConsumerPostParameter;
import com.techuva.ome_new.utilities.CartDatabase;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class YourCartActivity extends Fragment implements CartListAdapter.EventListener{
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    Context cartContext;
    String retailerId = "";
    String userId ="";
    EditText edt_search;
    ArrayList<GetDealerResultObject> dealersList;
    ArrayList<PlaceOrderDO> cartList;
    ArrayList<PlaceOrderDO> cartListforPlacingOrder;
    CartListAdapter cartListAdapter;
    RecyclerView rcv_cart_item_list;
    TextView tv_proceed_to_checkout;
    TextView tv_empty_cart;
    LinearLayout ll_empty_cart;
    ArrayList<PlaceOrderResultObject> placeOrderResultObjects;
    LinearLayout ll_order_placed;
    TextView tv_order_num;
    RecyclerView rcv_placed_order;
    LinearLayout cv_place_order_button;
    LinearLayout ll_cart_items;
    PlaceOrderAdapter placeOrderAdapter;
    int cartSize = 0;
    FrameLayout fl_icon_cart;
    TextView tv_cartSize;
    Toolbar toolbar;
    String accessToken ="";
    Activity mActivity;
    TextView  tv_items_in_cart, tv_items_in_cart_txt, tv_total_cart_value_txt, tv_total_cart_value;
    String userType;
    int pageNumber, pagePerCount;
    static int y;
    int listCount;
    int totalAmt=0;


    public static YourCartActivity newInstance() {
        
        Bundle args = new Bundle();
        YourCartActivity fragment = new YourCartActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.activity_your_cart, null, false);

        initialize(contentView);
        //serviceCallforAllDealers();
        CartDatabase.init(cartContext);
        cartSize = Integer.parseInt(MApplication.getString(cartContext, Constants.CartSize));
        tv_items_in_cart.setText(String.valueOf(cartSize));
        if(cartSize>0)
        {
            tv_empty_cart.setVisibility(View.GONE);
            ll_empty_cart.setVisibility(View.GONE);
            rcv_cart_item_list.setVisibility(View.VISIBLE);
            updateBottomView();
        }
        //showLoaderNew();
        if(userType.equals(Constants.Shopper)){
            showLoaderNew();
            serviceCallforShopperCartProducts(false);
        }
        else {
            showLoaderNew();
            serviceCallforCartProducts(false);
        }



        tv_proceed_to_checkout.setOnClickListener(v -> {


            if(cartSize>0){
                if(userType.equals(Constants.Consumer))
                {
                    //Toast.makeText(cartContext, "Consumer", Toast.LENGTH_SHORT).show();
                    getConsumerProductForOrder();
                }

                else
                {
                    showLoaderNew();
                    getProductsAndPlaceOrder();
                }
            }
            else {
                Toast.makeText(cartContext, "Please add some products to cart.", Toast.LENGTH_SHORT).show();
            }

        });

        fl_icon_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onEvent(true);
            }
        });

        rcv_cart_item_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(rcv_cart_item_list.SCROLL_STATE_DRAGGING==newState){
                    //fragProductLl.setVisibility(View.GONE);
                }
                if(rcv_cart_item_list.SCROLL_STATE_IDLE==newState){
                    // fragProductLl.setVisibility(View.VISIBLE);
                    if(y<=0){
                        //Toast.makeText(searchContext, "up up", Toast.LENGTH_SHORT).show();
                        rcv_cart_item_list.setVisibility(View.VISIBLE);
                        if(pageNumber<listCount)
                        {

                            pageNumber = pageNumber+1;

                            if(userType.equals(Constants.Shopper)){
                                showLoaderNew();
                                serviceCallforShopperCartProducts(true);
                            }
                            else {
                                showLoaderNew();
                                serviceCallforCartProducts(true);
                            }

                        }


                    }
                    else{
                        y=0;
                        /*Toast.makeText(searchContext, "up", Toast.LENGTH_SHORT).show();
                        rcv_product_search_list.setVisibility(View.GONE);*/
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                y=dy;
            }
        });

        return  contentView;
    }

    private void updateBottomView() {
        if (userType.equals(Constants.Consumer)){
            MApplication.serviceCallforCartProducts(cartContext, userId, accessToken);
            mActivity.runOnUiThread(() -> {
                new Handler().post(() -> {

                    tv_items_in_cart.setText(MApplication.getString(cartContext, Constants.CartSize));
                    tv_total_cart_value.setText(MApplication.getString(cartContext, Constants.TotalCartValue));
                });
            });
        }
        else {
            MApplication.serviceCallforShopperCartProducts(cartContext, userId, accessToken);
            mActivity.runOnUiThread(() -> {
                new Handler().post(() -> {

                    tv_items_in_cart.setText(MApplication.getString(cartContext, Constants.CartSize));
                    tv_total_cart_value.setText(MApplication.getString(cartContext, Constants.TotalCartValue));
                });
            });
        }
    }

    private void getConsumerProductForOrder() {
        JsonObject placeOrder = new JsonObject();

        JsonArray jsonArray = new JsonArray();
        if(cartSize >0)
        {
            for (int i=0; i<cartSize; i++)
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("order_id", cartList.get(i).getOrder_id());
                jsonObject.addProperty("ordered_by", userId );
                jsonObject.addProperty("quantity", cartList.get(i).getQuantity());
                jsonObject.addProperty("total_amount", cartList.get(i).getTotal_amount());
                jsonArray.add(jsonObject);
            }
            placeOrder.add("ordersList", jsonArray);
            serviceCallForConsumerPlaceOrder(placeOrder);
        }

    }

    private void getProductsAndPlaceOrder() {

        JsonArray jsonArray = new JsonArray();

        JsonObject placeOrder = new JsonObject();
            if(cartSize>0)
            {
               // JsonObject placeOrder = new JsonObject();
                for (int i=0; i<cartSize; i++)
                {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("order_id", cartList.get(i).getOrder_id());
                    jsonObject.addProperty("ordered_by", userId );
                    jsonObject.addProperty("quantity", cartList.get(i).getQuantity());
                    jsonObject.addProperty("total_amount", cartList.get(i).getTotal_amount());
                    jsonArray.add(jsonObject);
                }
                placeOrder.add("ordersList", jsonArray);
              //  serviceCallForConsumerPlaceOrder(placeOrder);
            }
            serviceCallForPlacingOrder(placeOrder);

    }

    private void serviceCallForPlacingOrder(JsonObject jsonArray) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);

        //Call<PlaceOrderMainObject> call = service.getStringScalar(1, jsonArray);
        Call<JsonElement> call = service.getStringScalar(Integer.parseInt(userId),accessToken, jsonArray);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                hideloader();
                if(response.body()!=null){

                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if(errorCode==0){
                        MApplication.setString(cartContext, Constants.CartSize, String.valueOf(0));
                        tv_cartSize.setText("0");
                       /* JsonArray result = jsonObject.get("result").getAsJsonArray();
                        for (int i=0; i<result.size(); i++){
                            JsonObject object1 = result.get(i).getAsJsonObject();
                            String po_id = object1.get("po_id").getAsString();
                            onEvent(true);
                            rcv_cart_item_list.setVisibility(View.GONE);
                            ll_order_placed.setVisibility(View.VISIBLE);
                            StringBuilder sb = new StringBuilder();
                            sb.append("ORDER # ");
                            sb.append(po_id);
                            String orderNum = sb.toString();
                            tv_order_num.setText(orderNum);
                        }*/
                       MApplication.setBoolean(cartContext, Constants.IsFromFavourites, false);
                       MApplication.serviceCallforShopperPendingOrders(cartContext, accessToken, userId, userType);
                       ll_order_placed.setVisibility(View.VISIBLE);
                       rcv_cart_item_list.setVisibility(View.GONE);
                       tv_order_num.setText(errorMsg);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MApplication.serviceCallforShopperPendingOrders(cartContext, accessToken, userId, userType);
                            }
                        }, 500);
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideloader();
                Toast.makeText(cartContext, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }

    private void serviceCallForConsumerPlaceOrder(JsonObject object) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);

        //Call<PlaceOrderMainObject> call = service.getStringScalar(1, jsonArray);
        Call<JsonElement> call = service.placeConsumerOrder(Integer.parseInt(userId),accessToken, object);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                hideloader();
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    if(errorCode==0){
                        JsonArray result = jsonObject.get("result").getAsJsonArray();
                       for (int i=0; i<result.size(); i++){
                           MApplication.setString(cartContext, Constants.CartSize, String.valueOf(0));
                           tv_cartSize.setText("0");
                           JsonObject object1 = result.get(i).getAsJsonObject();
                           String po_id = object1.get("po_id").getAsString();
                           onEvent(true);
                           rcv_cart_item_list.setVisibility(View.GONE);
                           ll_order_placed.setVisibility(View.VISIBLE);
                           StringBuilder sb = new StringBuilder();
                           sb.append("ORDER # ");
                           sb.append(po_id);
                           String orderNum = sb.toString();
                           tv_order_num.setText(orderNum);
                           edt_search.setHint("Search Company");
                           MApplication.setBoolean(cartContext, Constants.IsFromFavourites, false);
                           MApplication.serviceCallforConsumerPendingOrders(cartContext, accessToken, userId, userType);
                       }

                    }
                    else Toast.makeText(cartContext, "Something went wrong",Toast.LENGTH_SHORT).show();

                  /*  rcv_placed_order.setLayoutManager(new LinearLayoutManager(cartContext));
                    rcv_placed_order.setAdapter(placeOrderAdapter);*/
                }else {
                    hideloader();
                    // response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    // Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show()
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideloader();
                Toast.makeText(cartContext, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }

    private void initialize(View view) {
        cartContext = getActivity();
        cartList = new ArrayList<>();
        toolbar =  getActivity().findViewById(R.id.toolbar);
        edt_search = getActivity().findViewById(R.id.edt_search);
        rcv_cart_item_list = view.findViewById(R.id.rcv_cart_item_list);
        tv_empty_cart = view.findViewById(R.id.tv_empty_cart);
        tv_items_in_cart = view.findViewById(R.id.tv_items_in_cart);
        tv_items_in_cart_txt = view.findViewById(R.id.tv_items_in_cart_txt);
        tv_total_cart_value_txt = view.findViewById(R.id.tv_total_cart_value_txt);
        tv_total_cart_value = view.findViewById(R.id.tv_total_cart_value);
        tv_proceed_to_checkout = view.findViewById(R.id.tv_proceed_to_checkout);
        ll_order_placed= view.findViewById(R.id.ll_order_placed);
        ll_empty_cart= view.findViewById(R.id.ll_empty_cart);
        tv_order_num= view.findViewById(R.id.tv_order_num);
        rcv_placed_order = view.findViewById(R.id.rcv_placed_order);
        cv_place_order_button = view.findViewById(R.id.cv_place_order_button);
        ll_cart_items= view.findViewById(R.id.ll_cart_items);
        dealersList = new ArrayList<>();
        accessToken = "Bearer " + MApplication.getString(getActivity(), Constants.AccessToken);
        retailerId = MApplication.getString(cartContext, Constants.RetailerID);
        userId = MApplication.getString(cartContext, Constants.UserID);
        fl_icon_cart = toolbar.findViewById(R.id.fl_icon_cart);
        tv_cartSize = toolbar.findViewById(R.id.tv_cart_size);
        Typeface facebold= Typeface.createFromAsset(view.getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        tv_empty_cart.setTypeface(facebold);
        tv_order_num.setTypeface(facebold);
        userType = MApplication.getString(cartContext, Constants.UserType);
        pageNumber = 1;
        pagePerCount = 15;
    }

    private void serviceCallforCartProducts(boolean more){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);
        Call<JsonElement> call = service.getConsumerProductToCart(Integer.parseInt(userId),accessToken, new GetCartConsumerPostParameter(userId, String.valueOf(pageNumber),String.valueOf(pagePerCount),""));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
               if(response.body()!=null){
                   hideloader();
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if(errorCode==0){
                        ll_empty_cart.setVisibility(View.GONE);
                        ll_cart_items.setVisibility(View.VISIBLE);
                        rcv_cart_item_list.setVisibility(View.VISIBLE);
                        listCount = infoObject.get("ListCount").getAsInt();
                        cartSize = infoObject.get("TotalRecords").getAsInt();
                        MApplication.setString(cartContext, Constants.CartSize, String.valueOf(cartSize));
                        tv_cartSize.setText(String.valueOf(cartSize));
                        if(cartSize==0)
                        {
                            cv_place_order_button.setVisibility(View.GONE);
                            rcv_cart_item_list.setVisibility(View.GONE);
                            tv_empty_cart.setVisibility(View.VISIBLE);
                            ll_empty_cart.setVisibility(View.VISIBLE);
                            MApplication.setBoolean(cartContext, Constants.IsFromFavourites, false);
                        }
                        else {
                            cv_place_order_button.setVisibility(View.VISIBLE);
                        }
                        tv_items_in_cart.setText(String.valueOf(cartSize));
                        tv_cartSize.setText(String.valueOf(cartSize));
                        if(!more)
                        {
                            int totalAmt=0;
                            cartList = new ArrayList<>();
                        }
                        JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();

                        for (int i=0; i<jsonArray.size(); i++)
                        {
                            int price;
                            JsonObject object = jsonArray.get(i).getAsJsonObject();
                            PlaceOrderDO orderDO = new PlaceOrderDO();
                            orderDO.setCompany_id(String.valueOf(object.get("company_id").getAsInt()));
                            orderDO.setOrder_id(object.get("order_id").getAsInt());
                            orderDO.setProduct_id(object.get("product_id").getAsString());
                            orderDO.setProduct_description(object.get("display_name").getAsString());
                            if(!object.get("company_name").isJsonNull())
                            {
                                orderDO.setCompany_Name(object.get("company_name").getAsString());
                            }
                            if(!object.get("product_image").isJsonNull()){
                                orderDO.setImageUrl(object.get("product_image").getAsString());
                            }
                            orderDO.setQuantity(object.get("quantity").getAsInt());
                            orderDO.setPrice(object.get("price").getAsInt());
                            orderDO.setCategory(object.get("category_name").getAsString());
                            price = object.get("price").getAsInt()*object.get("quantity").getAsInt();
                            totalAmt = totalAmt+price;
                            orderDO.setTotal_amount(String.valueOf(price));
                            cartList.add(orderDO);
                            totalAmt = Integer.parseInt(MApplication.getString(cartContext, Constants.TotalCartValue));
                            tv_total_cart_value.setText(String.valueOf(totalAmt));
                        }

                    }
                    else {
                        MApplication.setString(cartContext, Constants.CartSize,"0");
                        edt_search.setHint("Search Company");
                        MApplication.setBoolean(cartContext, Constants.IsFromFavourites, false);
                        tv_items_in_cart.setText(MApplication.getString(cartContext, Constants.CartSize));
                        tv_cartSize.setText(MApplication.getString(cartContext, Constants.CartSize));
                        cv_place_order_button.setVisibility(View.GONE);
                        rcv_cart_item_list.setVisibility(View.GONE);
                        tv_empty_cart.setVisibility(View.VISIBLE);
                        ll_empty_cart.setVisibility(View.VISIBLE);
                    }

                    loadRecyclerview(cartList, more, userType);
                    /*cartListAdapter = new CartListAdapter(getActivity(), cartContext, dealersList, cartList, YourCartActivity.this);
                    rcv_cart_item_list.setLayoutManager(new LinearLayoutManager(cartContext));
                    rcv_cart_item_list.setAdapter(cartListAdapter);*/
                }else {
                   hideloader();
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideloader();
                Toast.makeText(cartContext, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }

    private void serviceCallforShopperCartProducts(boolean more){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);

        //Call<GetDealerMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new GetDealerPostParameters(retailerId));
        Call<JsonElement> call = service.getShopperProductToCart(Integer.parseInt(userId),accessToken, new GetCartConsumerPostParameter(userId,  String.valueOf(pageNumber),String.valueOf(pagePerCount),""));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
               hideloader();
                if(response.body()!=null){
                    hideloader();
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if(errorCode==0){
                        ll_empty_cart.setVisibility(View.GONE);
                        ll_cart_items.setVisibility(View.VISIBLE);
                        rcv_cart_item_list.setVisibility(View.VISIBLE);
                        listCount = infoObject.get("ListCount").getAsInt();
                        cartSize = infoObject.get("TotalRecords").getAsInt();
                        MApplication.setString(cartContext, Constants.CartSize, String.valueOf(cartSize));
                        tv_items_in_cart.setText(String.valueOf(cartSize));
                        if(!more)
                        {
                            int totalAmt=0;
                            cartList = new ArrayList<>();
                        }
                        tv_cartSize.setText(String.valueOf(cartSize));
                        if(cartSize==0)
                        {
                            cv_place_order_button.setVisibility(View.GONE);
                            rcv_cart_item_list.setVisibility(View.GONE);
                            tv_empty_cart.setVisibility(View.VISIBLE);
                            ll_empty_cart.setVisibility(View.VISIBLE);
                        }
                        else {
                            cv_place_order_button.setVisibility(View.VISIBLE);
                        }
                        tv_items_in_cart.setText(String.valueOf(cartSize));
                        JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();

                        for (int i=0; i<jsonArray.size(); i++)
                        {
                            int price;
                            JsonObject object = jsonArray.get(i).getAsJsonObject();
                            PlaceOrderDO orderDO = new PlaceOrderDO();
                            orderDO.setCompany_id("");
                            orderDO.setOrder_id(object.get("order_id").getAsInt());
                            orderDO.setProduct_description(object.get("display_name").getAsString());
                            if(object.has("product_image") && !object.get("product_image").isJsonNull()){
                                orderDO.setImageUrl(object.get("product_image").getAsString());
                            }if(!object.get("company_name").isJsonNull())
                            {
                                orderDO.setCompany_Name(object.get("company_name").getAsString());
                            }
                                orderDO.setQuantity(object.get("quantity").getAsInt());
                            orderDO.setPrice(object.get("price").getAsInt());
                            orderDO.setCategory(object.get("category_name").getAsString());
                            price = object.get("price").getAsInt()*object.get("quantity").getAsInt();
                            totalAmt = totalAmt+price;
                            orderDO.setTotal_amount(String.valueOf(price));
                            cartList.add(orderDO);
                        }
                        totalAmt = Integer.parseInt(MApplication.getString(cartContext, Constants.TotalCartValue));
                        tv_total_cart_value.setText(String.valueOf(totalAmt));
                        loadRecyclerview(cartList, more, userType);

                    }
                    else {
                        MApplication.setString(cartContext, Constants.CartSize,"0");
                        tv_items_in_cart.setText(MApplication.getString(cartContext, Constants.CartSize));
                        tv_cartSize.setText(MApplication.getString(cartContext, Constants.CartSize));
                        cv_place_order_button.setVisibility(View.GONE);
                        rcv_cart_item_list.setVisibility(View.GONE);
                        tv_empty_cart.setVisibility(View.VISIBLE);
                        ll_empty_cart.setVisibility(View.VISIBLE);
                    }

                }
                else {
                    hideloader();
                    Toast.makeText(cartContext, "" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
               hideloader();
                Toast.makeText(cartContext, "Error connecting server" , Toast.LENGTH_SHORT).show();
            }

        });


    }
/*
    private void setAdapterToList(ArrayList<GetDealerResultObject> dealersList) {

        if(userType.equals(Constants.Shopper)){
            serviceCallforShopperCartProducts();
        }
        else {
            serviceCallforCartProducts();
        }
    }*/


    @Override
    public void onEvent(Boolean data) {
        //cartSize = CartDatabase.getCartlistCount();
        pageNumber = 1;
        updateBottomView();

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            cartList= new ArrayList<>();
            if(userType.equals(Constants.Shopper))
            {
                serviceCallforShopperCartProducts(false);
            }
            else {
                serviceCallforCartProducts(false);
            }
        }, 600);

        //tv_items_in_cart.setText(String.valueOf(cartSize));
        //setAdapterToList(dealersList);
        /*  if(cartSize>0)
        {
            tv_cartSize.setText(String.valueOf(cartSize));
        }
        else
        {
            tv_cartSize.setText("0");
        }*/
    }
    @Override
    public void onDelete(Boolean data) {
        pageNumber = 1;
        updateBottomView();

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            cartList = new ArrayList<>();
            if(userType.equals(Constants.Shopper)){
                serviceCallforShopperCartProducts(false);
            }
            else {
                serviceCallforCartProducts(false);
            }
        }, 600);

    }


    public void showLoaderNew() {
        mActivity.runOnUiThread(new Runloader(getResources().getString(R.string.loading)));
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
                    dialog = new Dialog(cartContext,R.style.Theme_AppCompat_Light_DarkActionBar);
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



    private void loadRecyclerview(ArrayList<PlaceOrderDO> productlist, boolean more, String userType) {

        if(!more) {
            cartListAdapter = new CartListAdapter(getActivity(), cartContext, dealersList, productlist, YourCartActivity.this);
            rcv_cart_item_list.setLayoutManager(new LinearLayoutManager(cartContext));
            rcv_cart_item_list.setAdapter(cartListAdapter);
            cartListAdapter.notifyDataSetChanged();
        }
        else
        {
            if(cartListAdapter!=null) {
                cartListAdapter.notifyDataSetChanged();
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


}
