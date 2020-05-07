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
import com.example.ordermadeeasy.adapters.SupplierConsumerOrdersHistoryAdapter;
import com.example.ordermadeeasy.api_interface.GetAllOrdersDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.listener.EndlessScrollListener;
import com.example.ordermadeeasy.object_models.AllOrdersResultObject;
import com.example.ordermadeeasy.object_models.OrderStatusResultObject;
import com.example.ordermadeeasy.object_models.SupplierConsumerHistoryMainObject;
import com.example.ordermadeeasy.object_models.SupplierConsumerHistoryProductObject;
import com.example.ordermadeeasy.object_models.SupplierConsumerHistoryResultObject;
import com.example.ordermadeeasy.post_parameters.GetAllOrdersPostParameters;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;

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

public class SupplierConsumerOrdersHistoryActivity extends Fragment  {
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
    SupplierConsumerOrdersHistoryAdapter supplierConsumerOrdersAdapter;
    String pagePerCount = "15";
    int  pageNumber= 1;
    String accessToken ="";
    LinearLayout ll_dealers, ll_statuses;
    ImageView iv_dealer_dropdown, iv_status_dropdown;
    FrameLayout fl_search_company;
    Toolbar toolbar;
    TextView tv_cartSize;
    int listCount = 1;
    AppCompatActivity mActivity;
    private EndlessScrollListener scrollListener;
    String userType;
    String userId;
    ArrayList<SupplierConsumerHistoryResultObject> supplierConsumerOrdersList;
    String companyId;
    String searchKey="";
    EditText edt_search;
    ImageView iv_search;
    FragmentManager fragmentManager;

    public static SupplierConsumerOrdersHistoryActivity newInstance() {
        Bundle args = new Bundle();
        SupplierConsumerOrdersHistoryActivity fragment = new SupplierConsumerOrdersHistoryActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_your_orders, null, false);

        initialize(view);
        if(userType.equals(Constants.Supplier))
        {
            serviceCallForSupplierConsumerOrders();
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
        lv_orderList.setOnScrollListener(scrollListener);
        iv_search.setOnClickListener(v -> {
            showLoaderNew();
            searchKey = edt_search.getText().toString();
            pageNumber=1;
            supplierConsumerOrdersList = new ArrayList<>();
            serviceCallForSupplierConsumerOrders();
        });

        lv_orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CartDatabase.init(context);
                CartDatabase.clearConsumerOrderList();
                int listSize = supplierConsumerOrdersList.get(position).getProducts().size();
                for (int i=0; i<listSize; i++)
                {
                    SupplierConsumerHistoryProductObject object = new SupplierConsumerHistoryProductObject();
                    object.setOrderId(supplierConsumerOrdersList.get(position).getProducts().get(i).getOrderId());
                    object.setOrderedOn(supplierConsumerOrdersList.get(position).getProducts().get(i).getOrderedOn());
                    object.setQuantity(supplierConsumerOrdersList.get(position).getProducts().get(i).getQuantity());
                    object.setTotalAmount(supplierConsumerOrdersList.get(position).getProducts().get(i).getTotalAmount());
                    object.setProductId(supplierConsumerOrdersList.get(position).getProducts().get(i).getProductId());
                    object.setProductImage(supplierConsumerOrdersList.get(position).getProducts().get(i).getProductImage());
                    object.setProductDescription(supplierConsumerOrdersList.get(position).getProducts().get(i).getProductDescription());
                    object.setCategoryName(supplierConsumerOrdersList.get(position).getProducts().get(i).getCategoryName());
                    object.setSubCategoryName(supplierConsumerOrdersList.get(position).getProducts().get(i).getSubCategoryName());
                    object.setType(supplierConsumerOrdersList.get(position).getProducts().get(i).getType());
                    object.setOrderStatus(supplierConsumerOrdersList.get(position).getProducts().get(i).getOrderStatus());
                    object.setStatusShortCode(supplierConsumerOrdersList.get(position).getProducts().get(i).getStatusShortCode());
                    object.setMobileNo(supplierConsumerOrdersList.get(position).getProducts().get(i).getMobileNo());
                    CartDatabase.addProductToOrderHistoryList(object);
                    //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                }
                MApplication.setString(context, Constants.PoID, supplierConsumerOrdersList.get(position).getPoId());
                loadFragments(SupplierConsumerOrderHistoryUpdateActivity.newInstance());
            }
        });

        return view;

    }
    private void initialize(View view) {
        context= getActivity();
        mActivity = getActivity();
        toolbar =  Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        fl_search_company = view.findViewById(R.id.fl_search_company);
        edt_search = view.findViewById(R.id.edt_search);
        iv_search = view.findViewById(R.id.iv_search);
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
        accessToken = "Bearer " + MApplication.getString(context, Constants.AccessToken);;
        retailerId = MApplication.getString(context, Constants.RetailerID);
        orderList = new ArrayList<>();
        statusList = new ArrayList<>();
        userType = MApplication.getString(context, Constants.UserType);
        userId = MApplication.getString(context, Constants.UserID);
        supplierConsumerOrdersList = new ArrayList<>();
        companyId = MApplication.getString(context, Constants.CompanyID);
        searchKey = edt_search.getText().toString();
        ll_statuses.setVisibility(View.GONE);

    }
    private void loadNextDataFromApi(int page, int delay) {
        pageNumber = page;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                showLoaderNew();
                serviceCallForSupplierConsumerOrders();
            }
        }, delay);
    }


    private void serviceCallForSupplierConsumerOrders(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetAllOrdersDataInterface service = retrofit.create(GetAllOrdersDataInterface.class);
        Call<SupplierConsumerHistoryMainObject> call = service.getSupplierConsumerHistory(Integer.parseInt(userId), accessToken, new GetAllOrdersPostParameters(companyId, String.valueOf(pageNumber),  pagePerCount, searchKey));
        call.enqueue(new Callback<SupplierConsumerHistoryMainObject>() {
            @Override
            public void onResponse(Call<SupplierConsumerHistoryMainObject> call, Response<SupplierConsumerHistoryMainObject> response) {
                hideLoader();
                if(response.body()!=null){
                    if(response.body().getInfo().getErrorCode()==0)
                    {
                        lv_orderList.setVisibility(View.VISIBLE);
                        ll_noData.setVisibility(View.GONE);
                        listCount = response.body().getInfo().getListCount();
                        supplierConsumerOrdersList = response.body().getResult();
                        supplierConsumerOrdersAdapter = new SupplierConsumerOrdersHistoryAdapter(getActivity(), context, supplierConsumerOrdersList);
                        lv_orderList.setAdapter(supplierConsumerOrdersAdapter);
                    }
                    else {
                        lv_orderList.setVisibility(View.VISIBLE);
                        ll_noData.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    orderList.clear();
                }
            }

            @Override
            public void onFailure(Call<SupplierConsumerHistoryMainObject> call, Throwable t) {
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void showLoaderNew() {
        mActivity.runOnUiThread(new SupplierConsumerOrdersHistoryActivity.Runloader(getResources().getString(R.string.loading)));
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
}
