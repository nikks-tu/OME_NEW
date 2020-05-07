package com.techuva.ome_new.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.ome_new.R;
import com.techuva.ome_new.adapters.ConsumerOrderUpdateAdapter;
import com.techuva.ome_new.api_interface.OrderStatusDataInterface;
import com.techuva.ome_new.api_interface.SupplierOrdersDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.listener.EndlessScrollListener;
import com.techuva.ome_new.object_models.OrderStatusMainObject;
import com.techuva.ome_new.object_models.OrderStatusResultObject;
import com.techuva.ome_new.object_models.SupplierConsumerOrderProduct;
import com.techuva.ome_new.post_parameters.GetAllOrdersPostParameters;
import com.techuva.ome_new.post_parameters.SupplierConsumerOrderUpdatePostParameters;
import com.techuva.ome_new.utilities.CartDatabase;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ConsumerOrderUpdateActivity extends Fragment {
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    Context context;
    String retailerId = "";
    String pagePerCount = "15";
    int  pageNumber= 1;
    String accessToken ="";
    Toolbar toolbar;
    TextView tv_cartSize;
    int listCount = 1;
    Activity mActivity;
    private EndlessScrollListener scrollListener;
    String userType;
    String userId;
    ArrayList<SupplierConsumerOrderProduct> productList;
    String companyId;
    String searchKey="";
    TextView tv_po_id, tv_accept, tv_reject;
    ListView lv_orderList;
    LinearLayout ll_bottom;
    ConsumerOrderUpdateAdapter adapter;
    String po_id;
    String status;
    FrameLayout fl_icon_cart;
    FragmentManager fragmentManager;
    ArrayList<OrderStatusResultObject> statusList;
    String statusFor;

    public static ConsumerOrderUpdateActivity newInstance() {
        Bundle args = new Bundle();
        ConsumerOrderUpdateActivity fragment = new ConsumerOrderUpdateActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_supplier_consumer_orders_update, null, false);

        initialize(view);
        po_id = MApplication.getString(context, Constants.PoID);
        tv_po_id.setText(context.getResources().getString(R.string.order_no)+ po_id);
        lv_orderList.setOnScrollListener(scrollListener);
        CartDatabase.init(context);
        productList = new ArrayList<>();

        fl_icon_cart.setOnClickListener(v -> {
          /*  Fragment someFragment = new YourCartActivity();
            assert getFragmentManager() != null;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, someFragment , "Cart"); // give your fragment container id in first parameter
            transaction.commit();*/

            loadFragments(YourCartActivity.newInstance());
        });


        tv_accept.setOnClickListener(v -> {
            productList = new ArrayList<>();
            productList  = CartDatabase.getAllOrderProduct();
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            for(int i=0; i<productList.size(); i++)
            {
                JsonObject inObject = new JsonObject();
                inObject.addProperty("order_id", productList.get(i).getOrderId());
                if (productList.get(i).getChecked().equals("true")){
                    inObject.addProperty("status", Constants.Accept);
                }
                else {
                    inObject.addProperty("status", Constants.NotAvailable);
                }

                jsonArray.add(inObject);

               // Toast.makeText(context, productList.get(i).getChecked(),Toast.LENGTH_SHORT).show();
            }
            jsonObject.addProperty("po_id", po_id);
            jsonObject.addProperty("status", Constants.Accept);
            jsonObject.add("ordersList", jsonArray);

            serviceCallForUpdateMultiOrder(jsonObject);
        });

        //Toast.makeText(context, ""+productList.size(), Toast.LENGTH_SHORT).show();
        tv_reject.setOnClickListener(v -> {
            status= Constants.Reject;
            showLoaderNew();
            serviceCallForRejectOrder();
        });

        return view;

    }
    private void initialize(View view) {
        context= getActivity();
        mActivity = getActivity();
        lv_orderList = view.findViewById(R.id.lv_orderList);
        ll_bottom = view.findViewById(R.id.ll_bottom);
        tv_po_id = view.findViewById(R.id.tv_po_id);
        tv_accept = view.findViewById(R.id.tv_accept);
        tv_reject = view.findViewById(R.id.tv_reject);
        toolbar =  Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        fl_icon_cart = toolbar.findViewById(R.id.fl_icon_cart);
        accessToken = "Bearer " + MApplication.getString(context, Constants.AccessToken);
        retailerId = MApplication.getString(context, Constants.RetailerID);
        userType = MApplication.getString(context, Constants.UserType);
        userId = MApplication.getString(context, Constants.UserID);
        productList = new ArrayList<>();
        if(userType.equals(Constants.Consumer))
        {
            statusFor = "B2C";
        }
        else {
            statusFor = "B2B";
        }
        ll_bottom.setVisibility(View.GONE);
        statusList = new ArrayList<>();
        serviceCallforStatuses();
    }

    private void serviceCallforStatuses(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OrderStatusDataInterface service = retrofit.create(OrderStatusDataInterface.class);

        //Call<OrderStatusMainObject> call = service.getStringScalar(Integer.parseInt(retailerId));
        Call<OrderStatusMainObject> call = service.getStringScalar(Integer.parseInt(userId), accessToken, new GetAllOrdersPostParameters(statusFor));
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
                            statusList.add(response.body().getResult().get(i));
                        }

                            productList = CartDatabase.getAllConsumerOrderProduct();

                        adapter = new ConsumerOrderUpdateAdapter(getActivity(), context, statusList, productList, userType);
                        lv_orderList.setAdapter(adapter);
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



    private void serviceCallForRejectOrder(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupplierOrdersDataInterface service = retrofit.create(SupplierOrdersDataInterface.class);
        Call<JsonElement> call = service.updateConsumerOrders(Integer.parseInt(userId), accessToken, new SupplierConsumerOrderUpdatePostParameters(Integer.parseInt(po_id), status));
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
                        loadFragments(SupplierConsumerOrdersActivity.newInstance());
                    }
                    else  {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        loadFragments(SupplierConsumerOrdersActivity.newInstance());
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
        Call<JsonElement> call = service.updateMultiConsumerOrders(Integer.parseInt(userId), accessToken, object);
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
                        loadFragments(SupplierConsumerOrdersActivity.newInstance());
                    }
                    else  {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        loadFragments(SupplierConsumerOrdersActivity.newInstance());
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
        mActivity.runOnUiThread(new ConsumerOrderUpdateActivity.Runloader(getResources().getString(R.string.loading)));
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
