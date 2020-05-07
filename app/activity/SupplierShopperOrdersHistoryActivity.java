package com.example.ordermadeeasy.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.adapters.SupplierConsumerOrderUpdateAdapter;
import com.example.ordermadeeasy.adapters.SupplierShopperOrdersHistoryAdapter;
import com.example.ordermadeeasy.api_interface.GetAllOrdersDataInterface;
import com.example.ordermadeeasy.api_interface.SupplierOrdersDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.listener.EndlessScrollListener;
import com.example.ordermadeeasy.object_models.SupplierShopperOrderResultObject;
import com.example.ordermadeeasy.post_parameters.GetAllOrdersPostParameters;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SupplierShopperOrdersHistoryActivity extends Fragment implements SupplierShopperOrdersHistoryAdapter.EventListener{
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    Context context;
    String retailerId = "";
    SupplierShopperOrdersHistoryAdapter supplierShopperOrdersAdapter;
    String pagePerCount = "15";
    int  pageNumber= 1;
    String accessToken ="";
    Toolbar toolbar;
    TextView tv_cartSize;
    int listCount = 1;
    AppCompatActivity mActivity;
    private EndlessScrollListener scrollListener;
    String userType;
    String userId;
    ArrayList<SupplierShopperOrderResultObject> productList;
    String companyId;
    String searchKey="";
    TextView tv_po_id, tv_accept, tv_reject;
    ListView lv_orderList;
    LinearLayout ll_bottom, ll_noData;
    SupplierConsumerOrderUpdateAdapter adapter;
    String po_id;
    String status;
    FragmentManager fragmentManager;
    FrameLayout fl_main;
    ArrayList<SupplierShopperOrderResultObject> shopperOrderList;

    public static SupplierShopperOrdersHistoryActivity newInstance() {

        Bundle args = new Bundle();

        SupplierShopperOrdersHistoryActivity fragment = new SupplierShopperOrdersHistoryActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_supplier_order_history, null, false);

        initialize(view);
        //po_id = MApplication.getString(context, Constants.PoID);
        tv_po_id.setVisibility(View.GONE);
        lv_orderList.setOnScrollListener(scrollListener);
        CartDatabase.init(context);
        CartDatabase.clearShopperOrderList();
        shopperOrderList = new ArrayList<>();
        serviceCallForSupplierShopperOrders();
        tv_accept.setOnClickListener(v -> {

            productList = new ArrayList<>();
            productList  = CartDatabase.getAllShopperOrderProduct();
            if(productList.size()>0)
            {
                JsonObject jsonObject = new JsonObject();
                JsonArray jsonArray = new JsonArray();
                for (int i=0; i<productList.size(); i++)
                {
                    if (productList.get(i).getChecked().equals("true"))
                    {
                            JsonObject newObject = new JsonObject();
                            newObject.addProperty("order_id", productList.get(i).getDemandOrderId());
                            newObject.addProperty("quantity", productList.get(i).getQuantity());
                            newObject.addProperty("dispatched_qty", productList.get(i).getDispachedQty());
                            jsonArray.add(newObject);
                    }
                    jsonObject.add("ordersList", jsonArray);
                }
                serviceCallForUpdateMultiOrder(jsonObject);
            }

        });
        //Toast.makeText(context, ""+productList.size(), Toast.LENGTH_SHORT).show();
        tv_reject.setOnClickListener(v -> {
            productList = new ArrayList<>();
            productList  = CartDatabase.getAllShopperOrderProduct();
            if(productList.size()>0)
            {
                JsonObject jsonObject = new JsonObject();
                JsonArray jsonArray = new JsonArray();
                for (int i=0; i<productList.size(); i++)
                {
                    if (productList.get(i).getChecked().equals("true"))
                    {
                        if(productList.get(i).getStatusShortCode().equals("PR"))
                        {
                            Toast.makeText(context, "Partially Received orders cannot be cancelled!", Toast.LENGTH_SHORT).show();
                        }
                        else if(productList.get(i).getStatusShortCode().equals("PD"))
                        {
                            Toast.makeText(context, "Partially Dispatched orders cannot be cancelled!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            jsonArray.add(productList.get(i).getDemandOrderId());
                        }
                    }
                    jsonObject.add("order_ids", jsonArray);
                }
                if(jsonArray.size()>0)
                {
                    showLoaderNew();
                    serviceCallForRejectOrder(jsonObject);
                }
            }

        });
        return view;

    }
    private void initialize(View view) {
        context= getActivity();
        mActivity = getActivity();
        lv_orderList = view.findViewById(R.id.lv_orderList);
        ll_bottom = view.findViewById(R.id.ll_bottom);
        ll_noData = view.findViewById(R.id.ll_noData);
        tv_po_id = view.findViewById(R.id.tv_po_id);
        tv_accept = view.findViewById(R.id.tv_accept);
        tv_reject = view.findViewById(R.id.tv_reject);
        fl_main = view.findViewById(R.id.fl_main);
        toolbar =  Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        accessToken = "Bearer " + MApplication.getString(context, Constants.AccessToken);
        retailerId = MApplication.getString(context, Constants.RetailerID);
        userType = MApplication.getString(context, Constants.UserType);
        userId = MApplication.getString(context, Constants.UserID);
        productList = new ArrayList<>();
        companyId = MApplication.getString(context, Constants.CompanyID);
        tv_accept.setText(getResources().getString(R.string.confirm));
        tv_reject.setText(getResources().getString(R.string.cancel));
        ll_bottom.setVisibility(View.GONE);
    }


    private void serviceCallForRejectOrder(JsonObject jsonObject){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupplierOrdersDataInterface service = retrofit.create(SupplierOrdersDataInterface.class);
        Call<JsonElement> call = service.cancelShopperOrders(Integer.parseInt(userId), accessToken, jsonObject);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                hideLoader();
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if (errorCode==0)
                    {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        CartDatabase.init(context);
                        CartDatabase.clearShopperOrderList();
                        shopperOrderList = new ArrayList<>();
                        serviceCallForSupplierShopperOrders();
                    }
                    else  {
                        //loadFragments(SupplierConsumerOrdersActivity.newInstance());
                    }
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void serviceCallForUpdateMultiOrder(JsonObject object){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupplierOrdersDataInterface service = retrofit.create(SupplierOrdersDataInterface.class);
        Call<JsonElement> call = service.updateMultiShopperOrders(Integer.parseInt(userId), accessToken, object);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                hideLoader();
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if (errorCode==0)
                    {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        CartDatabase.init(context);
                        CartDatabase.clearShopperOrderList();
                        showLoaderNew();
                        shopperOrderList = new ArrayList<>();
                        serviceCallForSupplierShopperOrders();
                    }
                    else  {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        //loadFragments(SupplierConsumerOrdersActivity.newInstance());
                    }
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void showLoaderNew() {
        mActivity.runOnUiThread(new SupplierShopperOrdersHistoryActivity.Runloader(getResources().getString(R.string.loading)));
    }

    @Override
    public void onSuccess(boolean data) {
        shopperOrderList = CartDatabase.getAllShopperOrderProduct();
        //Toast.makeText(context, ""+shopperOrderList.size(), Toast.LENGTH_SHORT).show();
        lv_orderList.setVisibility(View.VISIBLE);
        //ll_bottom.setVisibility(View.VISIBLE);
        supplierShopperOrdersAdapter = new SupplierShopperOrdersHistoryAdapter(getActivity(), context, shopperOrderList, SupplierShopperOrdersHistoryActivity.this);
        lv_orderList.setAdapter(supplierShopperOrdersAdapter);
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
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                }
                dialog.setContentView(R.layout.loading);
                dialog.setCancelable(false);

                if (dialog != null && dialog.isShowing())
                {
                    dialog.dismiss();
                    dialog=null;
                }
                assert dialog != null;
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
                Log.println(1, "catch", ""+e);

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
        });
    }



    public  void loadFragments(Fragment fragment)
    {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
                .commit();
    }

    private void serviceCallForSupplierShopperOrders() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetAllOrdersDataInterface service = retrofit.create(GetAllOrdersDataInterface.class);
        Call<JsonElement> call = service.getSupplierShopperHistory(Integer.parseInt(userId), accessToken, new GetAllOrdersPostParameters(companyId, String.valueOf(pageNumber), pagePerCount, searchKey));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                hideLoader();
                if (response.body() != null) {
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    if(errorCode==0)
                    {
                        JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                        ll_noData.setVisibility(View.GONE);
                        fl_main.setVisibility(View.VISIBLE);
                        lv_orderList.setVisibility(View.VISIBLE);
                        shopperOrderList = new ArrayList<>();
                        for(int i=0; i<jsonArray.size(); i++)
                        {
                            SupplierShopperOrderResultObject resultObject = new SupplierShopperOrderResultObject();
                            JsonObject object = jsonArray.get(i).getAsJsonObject();
                            resultObject.setDemandOrderId(object.get("demand_order_id").getAsInt());
                            resultObject.setOrderNumber(object.get("order_number").getAsInt());
                            resultObject.setQuantity(Integer.valueOf(object.get("quantity").getAsString()));
                            resultObject.setTotalAmount(object.get("total_amount").getAsInt());
                            resultObject.setReceivedQuantity(object.get("received_quantity").getAsInt());
                            resultObject.setProductId(object.get("product_id").getAsInt());
                            if(!object.get("image_url").isJsonNull())
                            {
                                resultObject.setImageUrl(object.get("image_url").getAsString());
                            }
                            else {
                                resultObject.setImageUrl("");
                            }

                            resultObject.setProductDescription(object.get("display_name").getAsString());
                            resultObject.setStatusShortCode(object.get("status_short_code").getAsString());
                            resultObject.setStatusDescription(object.get("status_description").getAsString());
                            resultObject.setType(object.get("type").getAsString());
                            resultObject.setSubCategoryName(object.get("sub_category_name").getAsString());
                            resultObject.setCategoryName(object.get("category_name").getAsString());
                            resultObject.setCompanyName(object.get("company_name").getAsString());
                            /*resultObject.setCityName(object.get("city_name").getAsString());
                            resultObject.setStateName(object.get("state_name").getAsString());
                            resultObject.setAddress(object.get("address"));
                            resultObject.setPinCode(object.get("pin_code").getAsInt());
                            resultObject.setStatusId(object.get("status_id").getAsInt());*/
                            resultObject.setShopper_company_name(object.get("shopper_company_name").getAsString());
                            resultObject.setDispachedQty(0);
                            resultObject.setChecked("false");
                            shopperOrderList.add(resultObject);
                            //CartDatabase.addShopperProductToCart(resultObject);
                        }
                        //shopperOrderList = CartDatabase.getAllShopperOrderProduct();
                        lv_orderList.setVisibility(View.VISIBLE);
                        ll_noData.setVisibility(View.GONE);
                        supplierShopperOrdersAdapter = new SupplierShopperOrdersHistoryAdapter(getActivity(), context, shopperOrderList, SupplierShopperOrdersHistoryActivity.this);
                        lv_orderList.setAdapter(supplierShopperOrdersAdapter);

                    } else {
                        lv_orderList.setVisibility(View.GONE);
                        ll_noData.setVisibility(View.VISIBLE);
                        fl_main.setVisibility(View.GONE);
                    }
                } else {
                    shopperOrderList.clear();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
