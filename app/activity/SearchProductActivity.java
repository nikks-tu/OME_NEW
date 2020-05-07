package com.example.ordermadeeasy.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.adapters.SearchProductAdapter;
import com.example.ordermadeeasy.api_interface.LoginDataInterface;
import com.example.ordermadeeasy.api_interface.SearchProductDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.LoginMainObject;
import com.example.ordermadeeasy.object_models.SearchProductMainObject;
import com.example.ordermadeeasy.object_models.SearchProductResultObject;
import com.example.ordermadeeasy.post_parameters.SearchProductPostParameters;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchProductActivity extends Fragment implements SearchProductAdapter.EventListener {
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    AppCompatActivity mActivity;
    Context searchContext;
    String userId, searchKey, companyId, pagePerCount, pageNumber;
    RelativeLayout rl_serverError, internetConnection;
    FrameLayout fl_main;
    TextView tv_proceed_to_checkout;
    SearchProductAdapter adapter;
    ArrayList<SearchProductResultObject> productList;
    TextView tv_no_products;
    EditText edt_search;
    RecyclerView rcv_product_search_list;
    int cartSize=0;
    String accessToken ="";
    LinearLayout ll_no_product_found;
    String authorityKey ="";
    String grantType = "password";
    String refreshToken = "";
    private String EmailId;
    private String Password;
    LinearLayoutManager mLayoutManager;
    int pageno  =1;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int totalrecordsinDB;
    FrameLayout fl_icon_cart;
    TextView tv_cartSize;
    Toolbar toolbar;
    LinearLayout ll_bottom_view, ll_cart;
    TextView tv_comp_heading, tv_company_name, tv_items_in_cart_txt, tv_items_in_cart, tv_total_cart_value_txt;
    TextView tv_total_cart_value, tv_cart_btn;
    String userType;


    public static SearchProductActivity newInstance(String searchKey)
    {
        Bundle bundle = new Bundle();
        bundle.putString("searchKey",searchKey);
        SearchProductActivity fragment = new SearchProductActivity();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View contentview =inflater.inflate(R.layout.activity_search_product, null, false);

        initialize(contentview);
        CartDatabase.init(getActivity());

        Bundle bundle = getArguments();
         searchKey =bundle.getString("searchKey");
        CartDatabase.init(searchContext);
        cartSize = CartDatabase.getCartlistCount();
        tv_items_in_cart.setText(String.valueOf(cartSize));
        tv_proceed_to_checkout.setOnClickListener(v -> {
           /* Intent intent = new Intent(searchContext, YourCartActivity.class);
            startActivity(intent);*/
            if(CartDatabase.getCartlistCount()>0)
            {
                YourCartActivity nextFrag= new YourCartActivity();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        //.addToBackStack(null)
                        .commit();
            }
            else
            {
                Toast.makeText(getActivity(),"Please add at least one product ",Toast.LENGTH_SHORT).show();
            }


        });

        tv_cart_btn.setOnClickListener(v -> {
            Fragment someFragment = new YourCartActivity();
            FragmentTransaction transaction;
            if(getFragmentManager() != null)
            {
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment , "Cart"); // give your fragment container id in first parameter
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        tv_cart_btn.setOnClickListener(v -> {
            Fragment someFragment = new YourCartActivity();
            FragmentTransaction transaction;
            if(getFragmentManager() != null)
            {
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment , "Cart");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        if(adapter!=null)
        {
            cartSize = CartDatabase.getCartlistCount();
            tv_items_in_cart.setText(String.valueOf(cartSize));
        }

        ll_no_product_found.setVisibility(View.VISIBLE);
        tv_no_products.setText(getResources().getString(R.string.search_for_products));
        rcv_product_search_list.setVisibility(View.GONE);
        productList = new ArrayList<>();
       /* if(!searchKey.equals(""))
        {
           if(userType.equals(Constants.Shopper))
           {
               showLoaderNew();
               productList = new ArrayList<>();
               serviceCallforSearchProducts(false);
               ll_bottom_view.setVisibility(View.GONE);
           }
        }
        else {
            //Toast.makeText(searchContext, "Call", Toast.LENGTH_SHORT).show();
            if(userType.equals("CONSU")){
                productList = new ArrayList<>();
                serviceCallforSearchProductsForConsumer(false);
                ll_bottom_view.setVisibility(View.VISIBLE);
                tv_company_name.setText(MApplication.getString(searchContext, Constants.SelectedCompany));
                tv_total_cart_value.setText("0");
            }
        }*/

        fl_icon_cart.setOnClickListener(v -> {
            onEvent(true);
            Fragment someFragment = new YourCartActivity();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, someFragment , "Cart"); // give your fragment container id in first parameter
           // transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
            transaction.commit();
        });

        return contentview;
    }

    private void initialize(View view) {
        searchContext = getActivity();
        toolbar =  getActivity().findViewById(R.id.toolbar);
        userId = MApplication.getString(searchContext, Constants.UserID);
        companyId = MApplication.getString(searchContext, Constants.CompanyID);
        searchKey = "";
        pagePerCount = "ALL";
        pageNumber ="1";
        rl_serverError = view.findViewById(R.id.rl_serverError);
        internetConnection = view.findViewById(R.id.internetConnection);
        fl_main = view.findViewById(R.id.fl_main);
        tv_items_in_cart = view.findViewById(R.id.tv_items_in_cart);
        tv_proceed_to_checkout = view.findViewById(R.id.tv_proceed_to_checkout);
        rcv_product_search_list = view.findViewById(R.id.rcv_product_search_list);
        tv_no_products = view.findViewById(R.id.tv_no_products);
        ll_no_product_found = view.findViewById(R.id.ll_no_product_found);
        productList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(getActivity(), Constants.AccessToken));
        accessToken = sb.toString();
        authorityKey = Constants.AuthorizationKey;
        grantType = Constants.GrantType;
        refreshToken = MApplication.getString(searchContext, Constants.RefreshToken);
        EmailId = MApplication.getString(getContext(), Constants.LoginID);
        Password = MApplication.getString(getContext(), Constants.Password);
        fl_icon_cart = toolbar.findViewById(R.id.fl_icon_cart);
        edt_search = getActivity().findViewById(R.id.edt_search);
        tv_cartSize = toolbar.findViewById(R.id.tv_cart_size);
        ll_bottom_view = view.findViewById(R.id.ll_bottom_view);
        ll_cart = view.findViewById(R.id.ll_cart);
        tv_comp_heading = view.findViewById(R.id.tv_comp_heading);
        tv_company_name = view.findViewById(R.id.tv_company_name);
        tv_items_in_cart_txt = view.findViewById(R.id.tv_items_in_cart_txt);
        tv_items_in_cart = view.findViewById(R.id.tv_items_in_cart);
        tv_total_cart_value_txt = view.findViewById(R.id.tv_total_cart_value_txt);
        tv_total_cart_value = view.findViewById(R.id.tv_total_cart_value);
        tv_cart_btn = view.findViewById(R.id.tv_cart_btn);
        userType = MApplication.getString(searchContext, Constants.UserType);

        if(userType.equalsIgnoreCase(Constants.Consumer))
        {
            if(MApplication.getBoolean(searchContext, Constants.IsFromFavourites))
            {
                edt_search.setHint("Search Products");
            }
            int tempCartSize = Integer.parseInt(MApplication.getString(searchContext, Constants.CartSize));
            if(tempCartSize>0)
            {
                int tempCompanyId = Integer.parseInt(MApplication.getString(searchContext, Constants.CompanyID));
                tv_no_products.setText("You already have products in cart with other company,/n To change the company please place order or clear cart!");
            }
        }


    }

    private void serviceCallforSearchProducts(final boolean more){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SearchProductDataInterface service = retrofit.create(SearchProductDataInterface .class);
        Call<SearchProductMainObject> call = service.getAllProductForShopperWithSession(Integer.parseInt(userId), accessToken,
                new SearchProductPostParameters(Integer.parseInt(userId), Integer.parseInt(companyId), searchKey,  "", String.valueOf(pageno)));
        call.enqueue(new Callback<SearchProductMainObject>() {
            @Override
            public void onResponse(Call<SearchProductMainObject> call, Response<SearchProductMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                 hideloader();
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if(response.body().getInfo()!=null)
                    {
                        if(response.body().getInfo().getErrorCode()==0)
                        {
                            ll_no_product_found.setVisibility(View.GONE);
                            rcv_product_search_list.setVisibility(View.VISIBLE);
                            int size = response.body().getResult().size();
                            totalrecordsinDB=response.body().getInfo().getTotalRecords()-1;
                            int tonumber =response.body().getInfo().getToRecords();
                            if (!more) {
                                productList = new ArrayList<>();
                            }
                            if(tonumber<totalrecordsinDB)
                            {
                                loading = true;
                            }
                            else
                            {
                                loading=false;
                            }

                            // Toast.makeText(getBaseContext(), "Data came" +size, Toast.LENGTH_SHORT).show();
                            for (int i =0; i< size; i++)
                            {
                                productList.add(response.body().getResult().get(i));
                            }

                            if (more) {
                                if (rcv_product_search_list.getAdapter() != null && rcv_product_search_list.getAdapter().getItemCount() < totalrecordsinDB) {
                                    loading = true;
                                } else {
                                    loading = false;
                                }
                            }

                            if(productList.size()>0) {
                                loadRecyclerview(productList, more);
                            }
                            else
                            {
                                if(!more)
                                {
                                    rcv_product_search_list.setAdapter(null);
                                    productList = new ArrayList<>();
                                }
                            }
                        }
                        else
                        {
                            hideloader();
                            rcv_product_search_list.setVisibility(View.GONE);
                          ll_no_product_found.setVisibility(View.VISIBLE);
                          tv_no_products.setVisibility(View.VISIBLE);
                          tv_no_products.setText(R.string.no_product_by_key);
                        }
                    }

                }else {
                    hideloader();
                    rcv_product_search_list.setVisibility(View.GONE);
                    ll_no_product_found.setVisibility(View.VISIBLE);
                   }

            }

            @Override
            public void onFailure(Call<SearchProductMainObject> call, Throwable t) {
                hideloader();
                Toast.makeText(searchContext, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }

    private void serviceCallforSearchProductsForConsumer(final boolean more){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SearchProductDataInterface service = retrofit.create(SearchProductDataInterface .class);
        Call<SearchProductMainObject> call = service.getAllProductForConsumer(Integer.parseInt(userId), accessToken,
                new SearchProductPostParameters(Integer.parseInt(userId), Integer.parseInt(companyId), searchKey,  "", String.valueOf(pageno)));
        call.enqueue(new Callback<SearchProductMainObject>() {
            @Override
            public void onResponse(Call<SearchProductMainObject> call, Response<SearchProductMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                 hideloader();
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if(response.body().getInfo()!=null)
                    {
                        if(response.body().getInfo().getErrorCode()==0)
                        {
                            ll_no_product_found.setVisibility(View.GONE);
                            rcv_product_search_list.setVisibility(View.VISIBLE);
                            int size = response.body().getResult().size();
                            totalrecordsinDB=response.body().getInfo().getTotalRecords()-1;
                            int tonumber =response.body().getInfo().getToRecords();

                            if (!more) {
                                productList = new ArrayList<>();
                            }

                            if(tonumber<totalrecordsinDB)
                            {
                                loading = true;
                            }
                            else
                            {
                                loading=false;
                            }

                            // Toast.makeText(getBaseContext(), "Data came" +size, Toast.LENGTH_SHORT).show();
                            for (int i =0; i< size; i++)
                            {
                                productList.add(response.body().getResult().get(i));
                            }

                            if (more) {
                                if (rcv_product_search_list.getAdapter() != null && rcv_product_search_list.getAdapter().getItemCount() < totalrecordsinDB) {
                                    loading = true;
                                } else {
                                    loading = false;
                                }
                            }

                            if(productList.size()>0) {
                                loadRecyclerview(productList, more);
                            }
                            else
                            {
                                if(!more)
                                {
                                    rcv_product_search_list.setAdapter(null);
                                    productList = new ArrayList<>();
                                }
                            }
                        }
                        else
                        {
                            hideloader();
                            rcv_product_search_list.setVisibility(View.GONE);
                          ll_no_product_found.setVisibility(View.VISIBLE);
                          tv_no_products.setVisibility(View.VISIBLE);
                          tv_no_products.setText(R.string.no_product_by_key);
                          // tv_no_products.setText(response.body().getInfo().getErrorCode());
                          //Toast.makeText(getActivity(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    hideloader();
                    rcv_product_search_list.setVisibility(View.GONE);
                    ll_no_product_found.setVisibility(View.VISIBLE);
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<SearchProductMainObject> call, Throwable t) {
                hideloader();
                rcv_product_search_list.setVisibility(View.GONE);
                ll_no_product_found.setVisibility(View.VISIBLE);
                //Toast.makeText(searchContext, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }


    private void loadRecyclerview(ArrayList<SearchProductResultObject> productlist, boolean more) {

        if(!more) {
            mLayoutManager = new LinearLayoutManager(searchContext);
            rcv_product_search_list.setLayoutManager(mLayoutManager);
            adapter = new SearchProductAdapter(getContext(), getActivity(), productlist, SearchProductActivity.this);
            rcv_product_search_list.addItemDecoration(new DividerItemDecoration(searchContext, LinearLayoutManager.VERTICAL));
            rcv_product_search_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else
        {
            if(adapter!=null) {
                adapter.notifyDataSetChanged();
            }
        }

    }

    private void setDataToList(ArrayList<SearchProductResultObject> productList) {
        adapter = new SearchProductAdapter(getContext(), getActivity(), this.productList, SearchProductActivity.this);
        rcv_product_search_list.setLayoutManager(new LinearLayoutManager(searchContext));
        rcv_product_search_list.addItemDecoration(new DividerItemDecoration(searchContext, LinearLayoutManager.VERTICAL));
        rcv_product_search_list.setAdapter(adapter);
    }

    @Override
    public void onEvent(Boolean data) {
        cartSize = CartDatabase.getCartlistCount();
        tv_items_in_cart.setText(String.valueOf(cartSize));
        if(cartSize>0)
        {
            tv_cartSize.setText(String.valueOf(cartSize));
        }
        else
        {
            tv_cartSize.setText("0");
        }
    }

    @Override
    public void startLoader(Boolean data) {
        showLoaderNew();
    }

    @Override
    public void stopLoader(Boolean data) {
        hideloader();
    }


    public void showLoaderNew() {
        mActivity.runOnUiThread(new SearchProductActivity.Runloader(getResources().getString(R.string.loading)));
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
                    dialog = new Dialog(searchContext,R.style.Theme_AppCompat_Light_DarkActionBar);
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
    private void serviceCall() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                //.baseUrl("http://182.18.177.27/TUUserManagement/api/user/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginDataInterface service = retrofit.create(LoginDataInterface.class);

        Call<LoginMainObject> call = service.loginCall(authorityKey, EmailId, Password, grantType);
        call.enqueue(new Callback<LoginMainObject>() {
            @Override
            public void onResponse(Call<LoginMainObject> call, Response<LoginMainObject> response) {
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if(response.body().getUserId()!=null)
                    {
                        //  Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                        MApplication.setBoolean(searchContext, Constants.IsLoggedIn, true);
                        MApplication.setString(searchContext, Constants.AccessToken, response.body().getAccessToken());
                        MApplication.setString(searchContext, Constants.RefreshToken, response.body().getRefreshToken());
                        MApplication.setString(searchContext, Constants.RetailerID, String.valueOf(response.body().getUserId()));
                    }
                    else {
                        // Toast.makeText(context, "Unable to login!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast toast = Toast.makeText(searchContext, R.string.something_went_wrong, Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                    toast.show();
                }

            }
            @Override
            public void onFailure(Call<LoginMainObject> call, Throwable t) {
                //Toast.makeText(context, "Unable to login!", Toast.LENGTH_SHORT).show();
                Toast.makeText(searchContext, R.string.error_connecting_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
       // serviceCall();
        productList = new ArrayList<>();
        if(!searchKey.equals(""))
        {
            if(userType.equals(Constants.Shopper)){
                showLoaderNew();
                productList = new ArrayList<>();
                serviceCallforSearchProducts(false);
                ll_bottom_view.setVisibility(View.GONE);
            }
            else if(userType.equals("CONSU")){
                productList = new ArrayList<>();
                serviceCallforSearchProductsForConsumer(false);
                ll_bottom_view.setVisibility(View.VISIBLE);
                MApplication.serviceCallforCartProducts(searchContext, userId, accessToken);
                tv_company_name.setText(MApplication.getString(searchContext, Constants.SelectedCompany));
                tv_items_in_cart.setText(MApplication.getString(searchContext, Constants.CartSize));
                tv_total_cart_value.setText(MApplication.getString(searchContext, Constants.TotalCartValue));
            }
        }
        else {
            //Toast.makeText(searchContext, "Call", Toast.LENGTH_SHORT).show();
            if(userType.equals("CONSU")){
                productList = new ArrayList<>();
                serviceCallforSearchProductsForConsumer(false);
                ll_bottom_view.setVisibility(View.VISIBLE);
                MApplication.serviceCallforCartProducts(searchContext, userId, accessToken);
                tv_company_name.setText(MApplication.getString(searchContext, Constants.SelectedCompany));
                tv_items_in_cart.setText(MApplication.getString(searchContext, Constants.CartSize));
                tv_total_cart_value.setText(MApplication.getString(searchContext, Constants.TotalCartValue));

            }
        }
        super.onResume();
    }
}
