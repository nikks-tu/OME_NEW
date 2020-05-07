package com.techuva.ome_new.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.pageindicator.indicator.FlycoPageIndicaor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.ome_new.R;
import com.techuva.ome_new.adapters.GridAdapterDashboard;
import com.techuva.ome_new.adapters.SupplierAllOrdersCountRcvAdapter;
import com.techuva.ome_new.api_interface.DashboardImagesDataInterface;
import com.techuva.ome_new.api_interface.SupplierOrdersDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.AdvertisementObject;
import com.techuva.ome_new.object_models.AllOrdersCountResultObject;
import com.techuva.ome_new.object_models.SupplierAllOrdersCountObject;
import com.techuva.ome_new.post_parameters.GetAllOrdersPostParameters;
import com.techuva.ome_new.post_parameters.GetDashboardImgPostParameters;
import com.techuva.ome_new.utilities.CartDatabase;
import com.techuva.ome_new.views.MApplication;
import com.techuva.ome_new.views.SimpleLayoutBanner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class SupplierDashboard extends Fragment {

    LinearLayout ll_main_layout, ll_no_cons_order, ll_no_shopp_order;
    TextView tv_orders_heading, tv_cons_orders_heading, tv_no_orders_cons, tv_shopp_orders_heading, tv_no_orders_shopp;
    RecyclerView rcv_totalOrders;
    GridView rcv_consumerOrders, rcv_shopperOrders;
    CardView cv_cons_order, cv_shopp_order;
    String pagePerCount = "100";
    int pageNumber = 1;
    String refreshToken = "";
    String accessToken ="";
    Context context;
    Toolbar toolbar;
    Boolean doubleBackToExitPressedOnce = true;
    FragmentManager fragmentManager;
    ImageView iv_product_image;
    Toast exitToast;
    String userId;
    String companyId;
    String userType;
    ArrayList<SupplierAllOrdersCountObject> allOrdersList;
    SupplierAllOrdersCountObject allOrdersCountObject;
    SupplierAllOrdersCountRcvAdapter allOrdersCountRcvAdapter;
    ArrayList<AllOrdersCountResultObject> shopperCountList;
    ArrayList<AllOrdersCountResultObject> consumerCountList;
    AllOrdersCountResultObject resultObject;
    GridAdapterDashboard gridAdapterDashboard;
    SimpleLayoutBanner banner_admin1;
    ArrayList<AdvertisementObject> advertisementList;
    FlycoPageIndicaor indicator_circle1;
    String companyType;
    public static SupplierDashboard newInstance() {
        Bundle args = new Bundle();
        SupplierDashboard fragment = new SupplierDashboard();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_supplier_dashboard, null, false);
        context = getActivity();
        initialize(contentView);
        CartDatabase.init(context);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        serviceCallForAllOrdersSupplier();
        serviceCallForAllOrdersConsumer();
        serviceCallForAllOrdersShopper();
        return contentView;

    }

    private void initialize(View view) {
        toolbar =  getActivity().findViewById(R.id.toolbar);
        ll_main_layout = view.findViewById(R.id.ll_main_layout);
        ll_no_cons_order = view.findViewById(R.id.ll_no_cons_order);
        ll_no_shopp_order = view.findViewById(R.id.ll_no_shopp_order);
        tv_orders_heading = view.findViewById(R.id.tv_orders_heading);
        tv_cons_orders_heading = view.findViewById(R.id.tv_cons_orders_heading);
        tv_no_orders_cons = view.findViewById(R.id.tv_no_orders_cons);
        tv_shopp_orders_heading = view.findViewById(R.id.tv_shopp_orders_heading);
        tv_no_orders_shopp = view.findViewById(R.id.tv_no_orders_shopp);
        rcv_totalOrders = view.findViewById(R.id.rcv_totalOrders);
        rcv_consumerOrders = view.findViewById(R.id.rcv_consumerOrders);
        rcv_shopperOrders = view.findViewById(R.id.rcv_shopperOrders);
        banner_admin1 = view.findViewById(R.id.banner_admin1);
        indicator_circle1 = view.findViewById(R.id.indicator_circle1);
        cv_cons_order = view.findViewById(R.id.cv_cons_order);
        cv_shopp_order = view.findViewById(R.id.cv_shopp_order);
        exitToast = Toast.makeText(getActivity(), "Press back again to exit Order Made Easy", Toast.LENGTH_SHORT);
        setTypeFace();
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(context, Constants.AccessToken));
        accessToken = sb.toString();
        refreshToken = MApplication.getString(context, Constants.RefreshToken);
        iv_product_image = view.findViewById(R.id.iv_product_image);
        userId = MApplication.getString(context, Constants.UserID);
        companyType = MApplication.getString(context, Constants.CompanyType);
        if(companyType.equals(Constants.CompanyBtoB))
        {
            cv_cons_order.setVisibility(View.GONE);
        }
        else if(companyType.equals(Constants.CompanyBtoC))
        {
            cv_shopp_order.setVisibility(View.GONE);
        }


        companyId = MApplication.getString(context, Constants.CompanyID);
        userType = MApplication.getString(context, Constants.UserType);
        advertisementList = new ArrayList<>();
        serviceCallforImages();
    }

    private void setTypeFace() {

        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Thin.ttf");
        Typeface typebold = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        tv_orders_heading.setTypeface(typebold);
        tv_cons_orders_heading.setTypeface(typebold);
        tv_no_orders_cons.setTypeface(typebold);
        tv_shopp_orders_heading.setTypeface(typebold);
        tv_no_orders_shopp.setTypeface(typebold);

    }

    @Override
    public void onResume() {
        //serviceCall();
        super.onResume();
    }


    public  void loadFragments(Fragment fragment)
    {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
                .commit();
    }

    private boolean isInCartFragment() {
        for (Fragment item : getActivity().getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "YourCartActivity".equals(item.getClass().getSimpleName())) {
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

    private void serviceCallForAllOrdersSupplier(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupplierOrdersDataInterface service = retrofit.create(SupplierOrdersDataInterface.class);

        Call<JsonElement> call = service.getSupplierAllOrders(Integer.parseInt(userId), accessToken, new GetAllOrdersPostParameters( "SUPP", Integer.parseInt(userId)));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    if(errorCode==0){
                        JsonArray resultArray = jsonObject.get("result").getAsJsonArray();
                        allOrdersList = new ArrayList<>();
                        for (int i=0; i<resultArray.size(); i++)
                        {
                            JsonObject object = resultArray.get(i).getAsJsonObject();
                            allOrdersCountObject = new SupplierAllOrdersCountObject();
                            allOrdersCountObject.setTotal_Orders(object.get("Total_Orders").getAsInt());
                            allOrdersCountObject.setSTATUS(object.get("STATUS").getAsString());
                            allOrdersList.add(allOrdersCountObject);
                        }
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false);
                    allOrdersCountRcvAdapter = new SupplierAllOrdersCountRcvAdapter(context, allOrdersList);
                    rcv_totalOrders.setLayoutManager(linearLayoutManager);
                    rcv_totalOrders.setAdapter(allOrdersCountRcvAdapter);
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, "Data Error",Toast.LENGTH_SHORT).show();
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

    private void serviceCallForAllOrdersShopper(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupplierOrdersDataInterface service = retrofit.create(SupplierOrdersDataInterface.class);

        Call<JsonElement> call = service.getShopperAllOrders(Integer.parseInt(userId), accessToken, new GetAllOrdersPostParameters( Constants.Shopper, Integer.parseInt(userId)));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    if(errorCode==0){
                        JsonArray resultArray = jsonObject.get("result").getAsJsonArray();
                        shopperCountList = new ArrayList<>();
                        for (int i=0; i<resultArray.size(); i++)
                        {
                            JsonObject object = resultArray.get(i).getAsJsonObject();
                            resultObject = new AllOrdersCountResultObject();
                            resultObject.setTotalOrderByStatus(object.get("total_order_by_status").getAsString());
                            resultObject.setStatusGroupName(object.get("status_group_name").getAsString());
                            shopperCountList.add(resultObject);
                        }

                        if (resultArray.size()==1 || resultArray.size()==3 || resultArray.size()==5)
                        {
                            resultObject = new AllOrdersCountResultObject();
                            resultObject.setTotalOrderByStatus("");
                            resultObject.setStatusGroupName("");
                            shopperCountList.add(resultObject);
                        }
                    }
                    ll_no_shopp_order.setVisibility(View.GONE);
                    rcv_shopperOrders.setVisibility(View.VISIBLE);
                    gridAdapterDashboard = new GridAdapterDashboard(context, R.layout.grid_item_dashboard, shopperCountList);
                    rcv_shopperOrders.setAdapter(gridAdapterDashboard);
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, "Data Error",Toast.LENGTH_SHORT).show();
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

    private void serviceCallForAllOrdersConsumer(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupplierOrdersDataInterface service = retrofit.create(SupplierOrdersDataInterface.class);

        Call<JsonElement> call = service.getConsumerAllOrders(Integer.parseInt(userId), accessToken, new GetAllOrdersPostParameters( Constants.Consumer, Integer.parseInt(userId)));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    if(errorCode==0){
                        JsonArray resultArray = jsonObject.get("result").getAsJsonArray();
                        consumerCountList = new ArrayList<>();
                        for (int i=0; i<resultArray.size(); i++)
                        {
                            JsonObject object = resultArray.get(i).getAsJsonObject();
                            resultObject = new AllOrdersCountResultObject();
                            resultObject.setTotalOrderByStatus(object.get("total_order_by_status").getAsString());
                            resultObject.setStatusGroupName(object.get("status_group_name").getAsString());
                            consumerCountList.add(resultObject);
                        }
                        if (resultArray.size()==1 || resultArray.size()==3 || resultArray.size()==5)
                        {
                            resultObject = new AllOrdersCountResultObject();
                            resultObject.setTotalOrderByStatus("");
                            resultObject.setStatusGroupName("");
                            consumerCountList.add(resultObject);
                        }
                    }
                    ll_no_cons_order.setVisibility(View.GONE);
                    rcv_consumerOrders.setVisibility(View.VISIBLE);
                    gridAdapterDashboard = new GridAdapterDashboard(context, R.layout.grid_item_dashboard, consumerCountList);
                    rcv_consumerOrders.setAdapter(gridAdapterDashboard);
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, "Data Error",Toast.LENGTH_SHORT).show();
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
                                    advertisementList.add(advertisementObject);
                                }
                            }

                           if (advertisementList.size()>0)
                           {
                               banner_admin1.setSource(advertisementList).startScroll();
                               indicator_circle1.setViewPager(banner_admin1.getViewPager(), advertisementList.size());
                           }
                        }
                    }
                }else {
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();
                 //   hideloader();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
               // hideloader();
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }

}
