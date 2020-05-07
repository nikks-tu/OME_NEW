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
import android.support.v4.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.adapters.AllOrdersAdapter;
import com.example.ordermadeeasy.adapters.ConsumerOrderUpdateAdapter;
import com.example.ordermadeeasy.adapters.ConsumerOrdersAdapter;
import com.example.ordermadeeasy.adapters.OrderStatusFilterAdapter;
import com.example.ordermadeeasy.api_interface.GetAllOrdersDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.listener.EndlessScrollListener;
import com.example.ordermadeeasy.object_models.AllOrdersResultObject;
import com.example.ordermadeeasy.object_models.ConsumerAllOrderObject;
import com.example.ordermadeeasy.object_models.ConsumerAllOrderResultObject;
import com.example.ordermadeeasy.object_models.OrderStatusResultObject;
import com.example.ordermadeeasy.object_models.SupplierConsumerOrderProduct;
import com.example.ordermadeeasy.post_parameters.GetAllOrdersPostParameters;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ConsumerOrdersActivity extends Fragment implements AllOrdersAdapter.EventListener, AllOrdersAdapter.OnItemClicked, ConsumerOrderUpdateAdapter.EventListener, ConsumerOrdersAdapter.EventListener, ConsumerOrdersAdapter.OnItemClicked {
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    Context context;
    String retailerId = "";
    ArrayList<OrderStatusResultObject> statusList;
    ArrayList<AllOrdersResultObject> orderList;
    Spinner lv_dealers_list;
    Spinner lv_status_list;
    RecyclerView rcv_orderList;
    ListView lv_orderList;
    LinearLayout ll_noData, ll_main;
    OrderStatusFilterAdapter orderStatusAdapter;
    ConsumerOrdersAdapter consumerOrdersAdapter;
    String statuses = "1";
    String pagePerCount = "15";
    int  pageNumber= 1;
    String dealerId="";
    String accessToken ="";
    LinearLayout ll_dealers, ll_statuses;
    ImageView iv_dealer_dropdown, iv_status_dropdown;
    FrameLayout fl_icon_cart, fl_search_company;
    Toolbar toolbar;
    TextView tv_cartSize;
    int listCount = 0;
    AppCompatActivity mActivity;
    private EndlessScrollListener scrollListener;
    String userType;
    String userId;
    FragmentManager fragmentManager;
    ArrayList<ConsumerAllOrderResultObject> consumerOrdersList;
    String searchKey="";
    EditText edt_search;
    ImageView iv_search;
    boolean isOrdersAvailable = false;
    boolean isFromSearch = false;

    public static ConsumerOrdersActivity newInstance() {

        Bundle args = new Bundle();

        ConsumerOrdersActivity fragment = new ConsumerOrdersActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_your_orders, null, false);

        initialize(view);
        if(userType.equals(Constants.Consumer))
        {
            isFromSearch = false;
            serviceCallforConsumerOrders();
        }
        scrollListener = new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (pageNumber < listCount) {
                    pageNumber = pageNumber + 1;
                   loadNextDataFromApi(pageNumber, 300);
                }
                return true;
            }
        };

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = edt_search.getText().toString();
                if(!searchKey.equals(""))
                {
                    isFromSearch = true;
                    consumerOrdersList = new ArrayList<>();
                    serviceCallforConsumerOrders();
                }
                else {
                    Toast.makeText(context, "Please enter something to search", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_orderList.setOnScrollListener(scrollListener);

        lv_orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CartDatabase.init(context);
                CartDatabase.clearConsumerOrderList();
                int listSize = consumerOrdersList.get(position).getProducts().size();
                for (int i=0; i<listSize; i++)
                {
                    SupplierConsumerOrderProduct object = new SupplierConsumerOrderProduct();
                    object.setOrderId(consumerOrdersList.get(position).getProducts().get(i).getOrderId());
                    object.setOrderedOn(consumerOrdersList.get(position).getProducts().get(i).getOrderedOn());
                    object.setQuantity(consumerOrdersList.get(position).getProducts().get(i).getQuantity());
                    object.setTotalAmount(consumerOrdersList.get(position).getProducts().get(i).getTotalAmount());
                    object.setProductId(consumerOrdersList.get(position).getProducts().get(i).getProductId());
                    object.setProductImage(consumerOrdersList.get(position).getProducts().get(i).getProductImage());
                    object.setProductDescription(consumerOrdersList.get(position).getProducts().get(i).getProductDescription());
                    object.setCategoryName(consumerOrdersList.get(position).getProducts().get(i).getCategoryName());
                    object.setSubCategoryName(consumerOrdersList.get(position).getProducts().get(i).getSubCategoryName());
                    object.setType(consumerOrdersList.get(position).getProducts().get(i).getType());
                    object.setOrderStatus(consumerOrdersList.get(position).getProducts().get(i).getOrderStatus());
                    object.setStatusShortCode(consumerOrdersList.get(position).getProducts().get(i).getStatusShortCode());
                    object.setOrderedBy(consumerOrdersList.get(position).getProducts().get(i).getOrderedBy());
                    object.setUserName(consumerOrdersList.get(position).getProducts().get(i).getUserName());
                    object.setMobileNo(consumerOrdersList.get(position).getProducts().get(i).getMobileNo());
                    object.setCityName(consumerOrdersList.get(position).getProducts().get(i).getCityName());
                    object.setStateName(consumerOrdersList.get(position).getProducts().get(i).getStateName());
                    object.setAddress(consumerOrdersList.get(position).getProducts().get(i).getAddress());
                    object.setPinCode(consumerOrdersList.get(position).getProducts().get(i).getPinCode());
                    object.setStatusId(consumerOrdersList.get(position).getProducts().get(i).getStatus_id());
                    object.setChecked("false");
                    CartDatabase.addProductToOrderList(object);
                }
                MApplication.setString(context, Constants.PoID, consumerOrdersList.get(position).getPoId());
                loadFragments(ConsumerOrderUpdateActivity.newInstance());
            }
        });
        fl_icon_cart.setOnClickListener(v -> {
            Fragment someFragment = new YourCartActivity();
            assert getFragmentManager() != null;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, someFragment , "Cart"); // give your fragment container id in first parameter
            transaction.commit();
        });

        lv_status_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statuses = String.valueOf(Objects.requireNonNull(orderStatusAdapter.getItem(position)).getStatusId());
                pageNumber = 1;
                orderList.clear();
                loadNextDataFromApi(pageNumber, 100);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ll_dealers.setOnClickListener(v -> lv_dealers_list.performClick());

        ll_statuses.setOnClickListener(v -> lv_status_list.performClick());

        iv_dealer_dropdown.setOnClickListener(v -> lv_dealers_list.performClick());

        iv_status_dropdown.setOnClickListener(v -> lv_status_list.performClick());
        return view;
    }


    public  void loadFragments(Fragment fragment)
    {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
                .commit();
    }
    private void initialize(View view) {
        context= getActivity();
        mActivity = getActivity();
        toolbar =  Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        fl_icon_cart = toolbar.findViewById(R.id.fl_icon_cart);
        tv_cartSize = toolbar.findViewById(R.id.tv_cart_size);
        fl_search_company = view.findViewById(R.id.fl_search_company);
        edt_search = view.findViewById(R.id.edt_search);
        iv_search = view.findViewById(R.id.iv_search);
        lv_dealers_list = view.findViewById(R.id.lv_dealers_list);
        lv_status_list = view.findViewById(R.id.lv_status_list);
        rcv_orderList = view.findViewById(R.id.rcv_orderList);
        lv_orderList = view.findViewById(R.id.lv_orderList);
        ll_noData = view.findViewById(R.id.ll_noData);
        ll_main = view.findViewById(R.id.ll_main);
        ll_dealers = view.findViewById(R.id.ll_dealers);
        ll_statuses = view.findViewById(R.id.ll_statuses);
        iv_dealer_dropdown = view.findViewById(R.id.iv_dealer_dropdown);
        iv_status_dropdown = view.findViewById(R.id.iv_status_dropdown);
        accessToken = "Bearer " + MApplication.getString(context, Constants.AccessToken);;
        retailerId = MApplication.getString(context, Constants.RetailerID);
        orderList = new ArrayList<>();
        statusList = new ArrayList<>();
        userType = MApplication.getString(context, Constants.UserType);
        userId = MApplication.getString(context, Constants.UserID);
        fl_search_company.setVisibility(View.VISIBLE);
        ll_statuses.setVisibility(View.GONE);
        consumerOrdersList = new ArrayList<>();
    }
    private void loadNextDataFromApi(int page, int delay) {
        pageNumber = page;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
               // showLoaderNew();
                    serviceCallforConsumerOrders();
            }
        }, delay);
    }

    private void serviceCallforConsumerOrders(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetAllOrdersDataInterface service = retrofit.create(GetAllOrdersDataInterface.class);
        Call<JsonElement> call = service.getAllConsumerOrders(Integer.parseInt(userId), accessToken,new GetAllOrdersPostParameters(pagePerCount, String.valueOf(pageNumber), searchKey));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
               // hideLoader();
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    listCount = infoObject.get("TotalRecords").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if(errorCode==0)
                    {
                        isOrdersAvailable = true;
                        JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                        ll_noData.setVisibility(View.GONE);
                        lv_orderList.setVisibility(View.VISIBLE);
                        ll_statuses.setVisibility(View.GONE);
                        fl_search_company.setVisibility(View.VISIBLE);
                        for (int i=0; i<jsonArray.size(); i++)
                        {
                            JsonObject object = jsonArray.get(i).getAsJsonObject();
                            ConsumerAllOrderResultObject resultObject = new ConsumerAllOrderResultObject();
                            resultObject.setCompanyName(object.get("company_name").getAsString());
                            resultObject.setOrderedOn(object.get("ordered_on").getAsString());
                            resultObject.setPoId(object.get("po_id").getAsString());
                            ArrayList<ConsumerAllOrderObject> productsList = new ArrayList<>();
                            JsonArray products = object.get("products").getAsJsonArray();
                            for (int k=0; k<products.size(); k++){
                                JsonObject productJson = products.get(k).getAsJsonObject();
                                ConsumerAllOrderObject productObject = new ConsumerAllOrderObject();
                                productObject.setOrderId(productJson.get("order_id").getAsInt());
                                productObject.setOrderedOn(productJson.get("ordered_on").getAsString());
                                productObject.setQuantity(productJson.get("quantity").getAsString());
                                productObject.setTotalAmount(productJson.get("total_amount").getAsInt());
                                productObject.setProductId(productJson.get("product_id").getAsInt());
                                if(!productJson.get("product_image").isJsonNull()){
                                    productObject.setProductImage(productJson.get("product_image").getAsString());
                                }
                                productObject.setProductDescription(productJson.get("product_description").getAsString());
                                productObject.setCategoryName(productJson.get("category_name").getAsString());
                                productObject.setSubCategoryName(productJson.get("sub_category_name").getAsString());
                                productObject.setType(productJson.get("type").getAsString());
                                productObject.setOrderStatus(productJson.get("order_status").getAsString());
                                productObject.setStatusShortCode(productJson.get("status_short_code").getAsString());
                                productObject.setCompanyName(productJson.get("company_name").getAsString());
                                productObject.setStatus_id(productJson.get("status_id").getAsInt());
                                productsList.add(productObject);
                            }

                            resultObject.setProducts(productsList);
                            resultObject.setProductListSize(productsList.size());
                            consumerOrdersList.add(resultObject);
                        }
                        consumerOrdersAdapter = new ConsumerOrdersAdapter(getActivity(), context, consumerOrdersList, ConsumerOrdersActivity.this, ConsumerOrdersActivity.this, userType);
                        lv_orderList.setAdapter(consumerOrdersAdapter);

                    }
                    else {

                        if(isFromSearch)
                        {
                            isFromSearch = false;
                            Toast.makeText(context, "No Order found with given search key!", Toast.LENGTH_LONG).show();
                            searchKey="";
                            serviceCallforConsumerOrders();
                        }
                        //Toast.makeText(context, "No Order found with given search key!", Toast.LENGTH_LONG).show();
                        else {
                            lv_orderList.setVisibility(View.GONE);
                            fl_search_company.setVisibility(View.GONE);
                            ll_noData.setVisibility(View.VISIBLE);
                        }
                       /* Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                        ll_noData.setVisibility(View.VISIBLE);
                        lv_orderList.setVisibility(View.GONE);
                        ll_statuses.setVisibility(View.GONE);*/
                    }
                        }
                else {
                    orderList.clear();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
            }

        });

    }
    @Override
    public void onSuccess(boolean data) {
        if(data)
        {
            pageNumber = 1;
            orderList.clear();
            loadNextDataFromApi(pageNumber, 100);
        }
    }

    public void showLoaderNew() {
        mActivity.runOnUiThread(new ConsumerOrdersActivity.Runloader(getResources().getString(R.string.loading)));
    }

    @Override
    public void onItemClick(View view, int position) {

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
}
