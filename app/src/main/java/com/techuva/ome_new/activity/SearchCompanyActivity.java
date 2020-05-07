package com.techuva.ome_new.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.ome_new.R;
import com.techuva.ome_new.adapters.SearchCompanyAdapter;
import com.techuva.ome_new.api_interface.SearchProductDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.CompanyListObjectModel;
import com.techuva.ome_new.post_parameters.SearchProductPostParameters;
import com.techuva.ome_new.utilities.CartDatabase;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchCompanyActivity extends Fragment implements SearchCompanyAdapter.EventListener {
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    Activity mActivity;
    Context searchContext;
    String userId, searchKey, companyId, pagePerCount, pageNumber;
    RelativeLayout rl_serverError, internetConnection;
    FrameLayout fl_main;
    TextView tv_items_in_cart;
    TextView tv_proceed_to_checkout;
    SearchCompanyAdapter adapter;
    EditText edt_search;
    ArrayList<CompanyListObjectModel> companyList;
    TextView tv_no_products;
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
    String userType;


    public static SearchCompanyActivity newInstance(String searchKey)
    {
        Bundle bundle = new Bundle();
        bundle.putString("searchKey",searchKey);
        SearchCompanyActivity fragment = new SearchCompanyActivity();
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

        if(adapter!=null)
        {
            cartSize = CartDatabase.getCartlistCount();
            tv_items_in_cart.setText(String.valueOf(cartSize));
        }


        if(!searchKey.equals(""))
        {
            //showLoaderNew();
            serviceCallforSearchCompany(false);
        }


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
        edt_search = getActivity().findViewById(R.id.edt_search);
        rl_serverError = view.findViewById(R.id.rl_serverError);
        internetConnection = view.findViewById(R.id.internetConnection);
        fl_main = view.findViewById(R.id.fl_main);
        tv_items_in_cart = view.findViewById(R.id.tv_items_in_cart);
        tv_proceed_to_checkout = view.findViewById(R.id.tv_proceed_to_checkout);
        rcv_product_search_list = view.findViewById(R.id.rcv_product_search_list);
        tv_no_products = view.findViewById(R.id.tv_no_products);
        ll_no_product_found = view.findViewById(R.id.ll_no_product_found);
        companyList = new ArrayList<>();
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
        tv_cartSize = toolbar.findViewById(R.id.tv_cart_size);
        userType = MApplication.getString(searchContext, Constants.UserType);
        ll_no_product_found.setVisibility(View.VISIBLE);
        tv_no_products.setText(getResources().getString(R.string.search_for_company));
        rcv_product_search_list.setVisibility(View.GONE);
        if(userType.equalsIgnoreCase(Constants.Consumer))
        {
            int tempCartSize = Integer.parseInt(MApplication.getString(searchContext, Constants.CartSize));
            if(tempCartSize>0)
            {
                String comp = MApplication.getString(searchContext, Constants.SelectedCompany);
                Resources res = getResources();
                String text = String.format(res.getString(R.string.company_exists), comp);
                tv_no_products.setText(text);
            }
            else {
                edt_search.setHint("Search Company");
                MApplication.setString(searchContext, Constants.SelectedCompany, "");
            }
        }
    }


    private void serviceCallforSearchCompany(final boolean more){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SearchProductDataInterface service = retrofit.create(SearchProductDataInterface .class);
        Call<JsonElement> call = service.getCompanyList(Integer.parseInt(userId), accessToken,
                new SearchProductPostParameters(Integer.parseInt(userId), searchKey,  "", String.valueOf(pageno)));
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
                            rcv_product_search_list.setVisibility(View.VISIBLE);
                            ll_no_product_found.setVisibility(View.GONE);
                            for (int i=0; i<resultArray.size(); i++)
                            {
                                CompanyListObjectModel model = new CompanyListObjectModel();
                                JsonObject object = resultArray.get(i).getAsJsonObject();
                                model.setCompany_id(object.get("company_id").getAsInt());
                                model.setCompany_short_code(object.get("company_short_code").getAsString());
                                model.setCompany_name(object.get("company_name").getAsString());
                                model.setState_name(object.get("state_name").getAsString());
                                model.setCity_name(object.get("city_name").getAsString());
                                if(object.get("company_addr").isJsonNull())
                                {
                                    model.setCompany_addr("");
                                }else
                                {
                                    model.setCompany_addr(object.get("company_addr").getAsString());

                                }
                                if(object.get("favourite").getAsInt()==0){
                                    model.setFavourite(false);
                                }
                                else model.setFavourite(true);
                                companyList.add(model);
                            }
                            setDataToList(companyList);
                        }


                    }
                    else {
                        tv_no_products.setText("No company found with search key!");
                    }

                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                }else {
                    hideloader();
                    rcv_product_search_list.setVisibility(View.GONE);
                    ll_no_product_found.setVisibility(View.VISIBLE);
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideloader();
                Toast.makeText(searchContext, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }


    private void loadRecyclerview(ArrayList<CompanyListObjectModel> productlist, boolean more) {

        if(!more) {
            mLayoutManager = new LinearLayoutManager(searchContext);
            rcv_product_search_list.setLayoutManager(mLayoutManager);
            adapter = new SearchCompanyAdapter(getContext(), getActivity(), productlist, SearchCompanyActivity.this);
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

    private void setDataToList(ArrayList<CompanyListObjectModel> companyList) {
        adapter = new SearchCompanyAdapter(getContext(), getActivity(), companyList, SearchCompanyActivity.this);
        rcv_product_search_list.setLayoutManager(new LinearLayoutManager(searchContext));
        rcv_product_search_list.addItemDecoration(new DividerItemDecoration(searchContext, LinearLayoutManager.VERTICAL));
        rcv_product_search_list.setAdapter(adapter);
    }

    @Override
    public void onEvent(Boolean data) {

        edt_search.setText("");
       /* cartSize = CartDatabase.getCartlistCount();
        tv_items_in_cart.setText(String.valueOf(cartSize));
        if(cartSize>0)
        {
            tv_cartSize.setText(String.valueOf(cartSize));
        }
        else
        {
            tv_cartSize.setText("0");
        }*/
       edt_search.setHint("Search Product");

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
        mActivity.runOnUiThread(new SearchCompanyActivity.Runloader(getResources().getString(R.string.loading)));
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

    @Override
    public void onResume() {
       // serviceCall();
        super.onResume();
    }



}
