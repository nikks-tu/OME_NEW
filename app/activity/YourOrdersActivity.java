package com.example.ordermadeeasy.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.adapters.AllOrdersAdapter;
import com.example.ordermadeeasy.adapters.AllOrdersListAdapter;
import com.example.ordermadeeasy.adapters.ConsumerOrdersAdapter;
import com.example.ordermadeeasy.adapters.DealersFilterAdapter;
import com.example.ordermadeeasy.adapters.OrderStatusFilterAdapter;
import com.example.ordermadeeasy.api_interface.GetAllOrdersDataInterface;
import com.example.ordermadeeasy.api_interface.OrderStatusDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.listener.EndlessScrollListener;
import com.example.ordermadeeasy.object_models.ConsumerAllOrderObject;
import com.example.ordermadeeasy.object_models.ConsumerAllOrderResultObject;
import com.example.ordermadeeasy.object_models.GetDealerResultObject;
import com.example.ordermadeeasy.object_models.OrderStatusMainObject;
import com.example.ordermadeeasy.object_models.OrderStatusResultObject;
import com.example.ordermadeeasy.object_models.ShopperOrdersResultObject;
import com.example.ordermadeeasy.post_parameters.GetAllOrdersPostParameters;
import com.example.ordermadeeasy.views.MApplication;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class YourOrdersActivity extends Fragment implements AllOrdersAdapter.EventListener, AllOrdersAdapter.OnItemClicked, AllOrdersListAdapter.EventListener, AllOrdersListAdapter.OnItemClicked, ConsumerOrdersAdapter.EventListener, ConsumerOrdersAdapter.OnItemClicked {
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    Context context;
    String retailerId = "";
    ArrayList<GetDealerResultObject> dealersList;
    ArrayList<OrderStatusResultObject> statusList;
    ArrayList<ShopperOrdersResultObject> orderList;
    Spinner lv_dealers_list;
    Spinner lv_status_list;
    RecyclerView rcv_orderList;
    ListView lv_orderList;
    LinearLayout ll_noData, ll_main;
    OrderStatusFilterAdapter orderStatusAdapter;
    DealersFilterAdapter dealersAdapter;
    AllOrdersAdapter allOrdersAdapter;
    AllOrdersListAdapter allOrdersListAdapter;
    ConsumerOrdersAdapter consumerOrdersAdapter;
    String statuses = "1";
    String pagePerCount = "15";
    int  pageNumber= 1;
    String dealerId="";
    String accessToken ="";
    LinearLayout ll_dealers, ll_statuses;
    ImageView iv_dealer_dropdown, iv_status_dropdown;
    FrameLayout fl_icon_cart;
    Toolbar toolbar;
    TextView tv_cartSize;
    int listCount = 1;
    AppCompatActivity mActivity;
    private EndlessScrollListener scrollListener;
    String userType;
    String userId;
    ArrayList<ConsumerAllOrderResultObject> consumerOrdersList;
    String statusFor;
    String searchKey="";

    public static YourOrdersActivity newInstance() {
        Bundle args = new Bundle();
        YourOrdersActivity fragment = new YourOrdersActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_your_orders, null, false);

        initialize(view);
        //serviceCallforAllDealers();
        if(userType.equals(Constants.Consumer))
        {
            statusFor ="B2C";
            serviceCallforStatuses();
            serviceCallforConsumerOrders();
        }
        else if(userType.equals(Constants.Shopper))
        {
            statusFor ="B2B";
            serviceCallforStatuses();
            //serviceCallforGettingAllOrders();
        }
        //serviceCallforGettingAllOrders();
        //orderList = new ArrayList<>();
        scrollListener = new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (pageNumber < listCount) {
                    pageNumber = pageNumber + 1;
                    loadNextDataFromApi(pageNumber, 300);
                }
               /* else if(pageNumber == listCount)
                {
                  //  pageNumber = listCount;
                    Toast.makeText(context, "Same" +pageNumber+ " "+listCount, Toast.LENGTH_SHORT).show();
                    loadNextDataFromApi(listCount, 100);
                }*/
                else {
                    //Toast.makeText(context, "Else" +pageNumber, Toast.LENGTH_SHORT).show();
                    //pageNumber = 1;
                }
                return true;
            }
        };

        lv_orderList.setOnScrollListener(scrollListener);

        lv_dealers_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              dealerId = String.valueOf(dealersAdapter.getItem(position).getDealerId());
              //serviceCallforGettingAllOrders();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fl_icon_cart.setOnClickListener(v -> {
            Fragment someFragment = new YourCartActivity();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, someFragment , "Cart"); // give your fragment container id in first parameter
            //transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
            transaction.commit();
        });

        lv_status_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //statuses = String.valueOf(orderStatusAdapter.getItem(position).getStatusId());
                showLoaderNew();
                pageNumber = 1;
                searchKey = orderStatusAdapter.getItem(position).getStatusShortCode();
                orderList.clear();
                loadNextDataFromApi(pageNumber, 100);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ll_dealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       lv_dealers_list.performClick();
            }
        });

        ll_statuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       lv_status_list.performClick();
            }
        });

        iv_dealer_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    lv_dealers_list.performClick();
            }
        });

        iv_status_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lv_status_list.performClick();
            }
        });



      /*  if(statusList.size()>0)
        {

        }
        else {
            Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();
        }*/
        return view;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_your_orders, null, false);
        drawer.addView(contentView, 0);
        initialize();
        serviceCallforAllDealers();
        serviceCallforStatuses();
        serviceCallforGettingAllOrders();
    }*/

    private void initialize(View view) {
        context= getActivity();
        toolbar =  getActivity().findViewById(R.id.toolbar);
        fl_icon_cart = toolbar.findViewById(R.id.fl_icon_cart);
        tv_cartSize = toolbar.findViewById(R.id.tv_cart_size);
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
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(context, Constants.AccessToken));
        accessToken = sb.toString();
        retailerId = MApplication.getString(context, Constants.RetailerID);
        orderList = new ArrayList<>();
        statusList = new ArrayList<>();
        userType = MApplication.getString(context, Constants.UserType);
        userId = MApplication.getString(context, Constants.UserID);
        consumerOrdersList = new ArrayList<>();
       /* allOrdersListAdapter = new AllOrdersListAdapter(getActivity(), context, statusList, orderList, YourOrdersActivity.this, YourOrdersActivity.this);
        lv_orderList.setAdapter(allOrdersListAdapter);*/
    }
    private void loadNextDataFromApi(int page, int delay) {
        pageNumber = page;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(userType.equals(Constants.Consumer))
                {
                    serviceCallforConsumerOrders();
                }
                else {
                    serviceCallforGettingAllOrders();
                }

            }
        }, delay);
    }

    private void serviceCallforStatuses(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OrderStatusDataInterface service = retrofit.create(OrderStatusDataInterface.class);

        //Call<OrderStatusMainObject> call = service.getStringScalar(Integer.parseInt(retailerId));
        Call<OrderStatusMainObject> call = service.getStringScalar(Integer.parseInt(userId), accessToken,new GetAllOrdersPostParameters(statusFor));
        call.enqueue(new Callback<OrderStatusMainObject>() {
            @Override
            public void onResponse(Call<OrderStatusMainObject> call, Response<OrderStatusMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                //hideloader();
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if(response.body().getInfo().getErrorCode()==0)
                    {
                        int size = response.body().getResult().size();
                        statusList = new ArrayList<>();
                        //  Toast.makeText(getBaseContext(), "Data came" +size, Toast.LENGTH_SHORT).show();
                        for (int i =0; i< size; i++)
                        {
                            if(response.body().getResult().get(i).getStatusId()!=7)
                            {
                                statusList.add(response.body().getResult().get(i));
                            }
                        }
                        orderStatusAdapter = new OrderStatusFilterAdapter(getActivity(), R.layout.item_order_status, R.id.tv_status_name, statusList);
                        lv_status_list.setAdapter(orderStatusAdapter);
                    }
                    else
                    {
                        //Toast.makeText(getBaseContext(), "Data not found",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<OrderStatusMainObject> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }

    private void serviceCallforGettingAllOrders(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetAllOrdersDataInterface service = retrofit.create(GetAllOrdersDataInterface.class);

        //Call<AllOrdersMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new GetAllOrdersPostParameters(Integer.parseInt(retailerId), dealerId, statuses, pagePerCount, pageNumber));
        Call<JsonElement> call = service.callWithSession(Integer.parseInt(userId), accessToken,new GetAllOrdersPostParameters(pagePerCount, String.valueOf(pageNumber), searchKey, userType, Integer.parseInt(userId)));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                hideloader();
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    listCount = infoObject.get("ListCount").getAsInt();

                    if(errorCode==0)
                    {
                        ll_noData.setVisibility(View.GONE);
                        lv_orderList.setVisibility(View.VISIBLE);
                        JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                        for (int i=0; i<jsonArray.size(); i++)
                        {
                            JsonObject jObject = jsonArray.get(i).getAsJsonObject();
                            ShopperOrdersResultObject ordersResultObject = new ShopperOrdersResultObject();
                            ordersResultObject.setDemandOrderId(jObject.get("demand_order_id").getAsInt());
                            ordersResultObject.setOrderNumber(jObject.get("order_number").getAsInt());
                            ordersResultObject.setCompanyId(jObject.get("company_id").getAsInt());
                            ordersResultObject.setProductId(jObject.get("product_id").getAsInt());
                            ordersResultObject.setQuantity(Integer.valueOf(jObject.get("quantity").getAsString()));
                            ordersResultObject.setTotalAmount(jObject.get("total_amount").getAsInt());
                            ordersResultObject.setReceivedQuantity(jObject.get("received_quantity").getAsInt());
                            if(!jObject.get("dispatched_date").isJsonNull())
                            { ordersResultObject.setDispatchedDate(jObject.get("dispatched_date").getAsString()); }
                            else { ordersResultObject.setDispatchedDate("");
                            }
                            ordersResultObject.setReceivedOn(jObject.get("received_on").getAsString());
                            ordersResultObject.setCompanyShortCode(jObject.get("company_short_code").getAsString());
                            ordersResultObject.setCompanyName(jObject.get("company_name").getAsString());
                            ordersResultObject.setCompanyAddr(jObject.get("company_addr").getAsString());
                            if(!jObject.get("product_image").isJsonNull())
                            {
                                ordersResultObject.setProductImage(jObject.get("product_image").getAsString());
                            }
                            else {
                                ordersResultObject.setProductImage("");
                            }
                            ordersResultObject.setProductShortDesc(jObject.get("product_short_desc").getAsString());
                            ordersResultObject.setProductDescription(jObject.get("display_name").getAsString());
                            ordersResultObject.setSubCategoryName(jObject.get("sub_category_name").getAsString());
                            ordersResultObject.setStatusId(jObject.get("status_id").getAsInt());
                            ordersResultObject.setStatusShortCode(jObject.get("status_short_code").getAsString());
                            ordersResultObject.setStatusDescription(jObject.get("status_description").getAsString());
                            orderList.add(ordersResultObject);
                        }
                        lastViewedPosition = listView.getFirstVisiblePosition();
                        View v = listView.getChildAt(0);
                        topOffset = (v == null) ? 0 : v.getTop();
                        allOrdersListAdapter = new AllOrdersListAdapter(getActivity(), context, statusList, orderList, YourOrdersActivity.this, YourOrdersActivity.this);
                        lv_orderList.setAdapter(allOrdersListAdapter);
                        lv_orderList.setSelectionFromTop(lastViewedPosition, topOffset);

                    }
                    else
                    {
                        orderList.clear();
                        //allOrdersListAdapter.notifyDataSetChanged();
                        lv_orderList.setVisibility(View.GONE);
                        ll_noData.setVisibility(View.VISIBLE);
                        //Toast.makeText(getBaseContext(), "Data not found",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    hideloader();
                    orderList.clear();
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
               hideloader();
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });

    }

    private void serviceCallforConsumerOrders(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetAllOrdersDataInterface service = retrofit.create(GetAllOrdersDataInterface.class);

        //Call<AllOrdersMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new GetAllOrdersPostParameters(Integer.parseInt(retailerId), dealerId, statuses, pagePerCount, pageNumber));
        Call<JsonElement> call = service.getAllConsumerOrders(Integer.parseInt(userId), accessToken,new GetAllOrdersPostParameters(pagePerCount, String.valueOf(pageNumber), ""));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                hideloader();
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                    listCount = infoObject.get("ListCount").getAsInt();
                    //Toast.makeText(context, errorMsg , Toast.LENGTH_SHORT).show();
                    if(errorCode==0)
                    {
                        ll_noData.setVisibility(View.GONE);
                        lv_orderList.setVisibility(View.VISIBLE);
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
                                productObject.setProductImage(productJson.get("product_image").getAsString());
                                productObject.setProductDescription(productJson.get("display_name").getAsString());
                                productObject.setCategoryName(productJson.get("category_name").getAsString());
                                productObject.setSubCategoryName(productJson.get("sub_category_name").getAsString());
                                productObject.setType(productJson.get("type").getAsString());
                                productObject.setOrderStatus(productJson.get("order_status").getAsString());
                                productObject.setStatusShortCode(productJson.get("status_short_code").getAsString());
                                productObject.setCompanyName(productJson.get("company_name").getAsString());
                                productsList.add(productObject);
                            }

                            resultObject.setProducts(productsList);
                            resultObject.setProductListSize(productsList.size());
                            consumerOrdersList.add(resultObject);
                        }
                        consumerOrdersAdapter = new ConsumerOrdersAdapter(getActivity(), context, consumerOrdersList, YourOrdersActivity.this, YourOrdersActivity.this, userType);
                        lv_orderList.setAdapter(consumerOrdersAdapter);
                    }
                    else {
                        ll_noData.setVisibility(View.VISIBLE);
                        lv_orderList.setVisibility(View.GONE);
                    }


                }
                else {
                    orderList.clear();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });

    }
    @Override
    public void onSuccess(boolean data) {
        if(data)
        {
            pageNumber = 1;
            orderList.clear();
            //serviceCallforGettingAllOrders();
            loadNextDataFromApi(pageNumber, 100);
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

    public void showLoaderNew() {
        mActivity.runOnUiThread(new YourOrdersActivity.Runloader(getResources().getString(R.string.loading)));
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
        });
    }
}
