package com.techuva.ome_new.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techuva.ome_new.R;
import com.techuva.ome_new.adapters.AllOrdersAdapter;
import com.techuva.ome_new.adapters.CartListAdapter;
import com.techuva.ome_new.adapters.DealersAdapter;
import com.techuva.ome_new.adapters.OrderStatusAdapter;
import com.techuva.ome_new.adapters.SearchProductAdapter;
import com.techuva.ome_new.api_interface.GetDealersDataInterface;
import com.techuva.ome_new.api_interface.LoginDataInterface;
import com.techuva.ome_new.api_interface.OrderStatusDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.domain.PlaceOrderDO;
import com.techuva.ome_new.object_models.AllOrdersResultObject;
import com.techuva.ome_new.object_models.GetDealerMainObject;
import com.techuva.ome_new.object_models.GetDealerResultObject;
import com.techuva.ome_new.object_models.LoginMainObject;
import com.techuva.ome_new.object_models.OrderStatusMainObject;
import com.techuva.ome_new.object_models.OrderStatusResultObject;
import com.techuva.ome_new.post_parameters.GetAllOrdersPostParameters;
import com.techuva.ome_new.post_parameters.GetDealerPostParameters;
import com.techuva.ome_new.utilities.CartDatabase;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;
import java.util.EventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class HomeActivity extends Fragment implements CartListAdapter.EventListener, AllOrdersAdapter.EventListener, AllOrdersAdapter.OnItemClicked , SearchProductAdapter.EventListener {

    LinearLayout ll_main_layout;
    LinearLayout ll_cart_layout;
    RecyclerView rcv_cartlist;
    FrameLayout fl_add_to_cart;
    TextView tv_empty_cart;
    TextView tv_empty_order_list;
    TextView tv_more_items_cart;
    TextView tv_proceed_to_cart;
    TextView tv_cart_items_txt, tv_pending_order_txt;
    RecyclerView rcv_placedOrders;
    Context context;
    int cartSize;
    String retailerId = "";
    ArrayList<GetDealerResultObject> dealersList;
    ArrayList<PlaceOrderDO> cartList;
    CartListAdapter cartListAdapter;
    String pagePerCount = "ALL";
    String pageNumber = "";
    LinearLayout ll_cart_items, ll_order_list;
    Toast exitToast;
    public Dialog dialog;
    TextView tv_cartSize;
    private AnimationDrawable animationDrawable;
    AllOrdersAdapter allOrdersAdapter;
    OrderStatusAdapter orderStatusAdapter;
    DealersAdapter dealersAdapter;
    String demandOrderId = "";
    String statusId = "";
    String receivedQuantity = "";

    String authorityKey ="";
    String grantType = "password";
    String accessToken ="";
    String refreshToken = "";
    private String EmailId;
    private String Password;
    FrameLayout fl_icon_cart;
    Toolbar toolbar;
    private EventListener listener;

    ArrayList<OrderStatusResultObject> statusList;

    ArrayList<AllOrdersResultObject> orderList;
    Boolean doubleBackToExitPressedOnce = true;
    FragmentManager fragmentManager;

    public static HomeActivity newInstance() {

        Bundle args = new Bundle();
        HomeActivity fragment = new HomeActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        context = getActivity();
        initialize(contentView);
        CartDatabase.init(context);
        orderList = new ArrayList<>();
        cartSize = CartDatabase.getCartlistCount();
        if (cartSize == 0) {
            tv_proceed_to_cart.setVisibility(View.GONE);
            tv_more_items_cart.setVisibility(View.GONE);
            tv_empty_cart.setVisibility(View.VISIBLE);
            rcv_cartlist.setVisibility(View.GONE);
        } else if (cartSize <= 2) {
            tv_more_items_cart.setVisibility(View.GONE);
        } else {
            tv_more_items_cart.setVisibility(View.VISIBLE);
        }

        if (cartSize <= 1) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1.0f
            );
            ll_cart_items.setLayoutParams(param);
            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0,
                    2.0f
            );
            ll_order_list.setLayoutParams(param1);
        }

        if(orderList.size()<=0)
        {
            tv_empty_order_list.setVisibility(View.VISIBLE);
            rcv_placedOrders.setVisibility(View.GONE);
        }
        serviceCallforAllDealers();
        serviceCallforStatuses();
        //serviceCallforGettingAllOrders();
        tv_proceed_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(context, YourCartActivity.class);
                startActivity(intent);*/
                loadFragments(YourCartActivity.newInstance());
            }
        });
        fl_icon_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEvent(true);

                Fragment someFragment = new YourCartActivity();
                FragmentTransaction transaction = getHostFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment , "Cart"); // give your fragment container id in first parameter
                //transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

             /* if(Objects.requireNonNull(getActivity()).getSupportFragmentManager().getFragments().get(0).isMenuVisible())
              {
                  Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
              }
              else Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();*/
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return contentView;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!

        drawer.addView(contentView, 0);
        initialize();
        CartDatabase.init(context);
        cartSize = CartDatabase.getCartlistCount();
        if(cartSize==0)
        {
            tv_more_items_cart.setVisibility(View.GONE);
            tv_empty_cart.setVisibility(View.VISIBLE);
            rcv_cartlist.setVisibility(View.GONE);
        }
        serviceCallforAllDealers();
        serviceCallforGettingAllOrders();
        tv_proceed_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YourCartActivity.class);
                startActivity(intent);
            }
        });
    }*/

    private void initialize(View view) {

        toolbar =  getActivity().findViewById(R.id.toolbar);
        ll_main_layout = view.findViewById(R.id.ll_main_layout);
        ll_cart_layout = view.findViewById(R.id.ll_cart_layout);
        rcv_cartlist = view.findViewById(R.id.rcv_cartlist);
        tv_empty_cart = view.findViewById(R.id.tv_empty_cart);
        tv_empty_order_list = view.findViewById(R.id.tv_empty_order_list);
        fl_add_to_cart = view.findViewById(R.id.fl_add_to_cart);
        tv_more_items_cart = view.findViewById(R.id.tv_more_items_cart);
        tv_proceed_to_cart = view.findViewById(R.id.tv_proceed_to_cart);
        rcv_placedOrders = view.findViewById(R.id.rcv_placedOrders);
        ll_order_list = view.findViewById(R.id.ll_order_list);
        ll_cart_items = view.findViewById(R.id.ll_cart_items);
        tv_cart_items_txt = view.findViewById(R.id.tv_cart_items_txt);
        tv_pending_order_txt = view.findViewById(R.id.tv_pending_order_txt);
        retailerId = MApplication.getString(context, Constants.RetailerID);
        exitToast = Toast.makeText(getActivity(), "Press back again to exit Order Made Easy", Toast.LENGTH_SHORT);
        setTypeFace();
        authorityKey = Constants.AuthorizationKey;
        grantType = Constants.GrantType;
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(context, Constants.AccessToken));
        accessToken = sb.toString();
        //refreshToken = MApplication.getString(context, Constants.RefreshToken);
        EmailId = MApplication.getString(getContext(), Constants.LoginID);
        Password = MApplication.getString(getContext(), Constants.Password);
        fl_icon_cart = toolbar.findViewById(R.id.fl_icon_cart);
        tv_cartSize = toolbar.findViewById(R.id.tv_cart_size);
        serviceCall();

    }

    private void setTypeFace() {

        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Thin.ttf");
        Typeface typebold = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        tv_empty_cart.setTypeface(typeface);
        tv_empty_order_list.setTypeface(typeface);
        tv_more_items_cart.setTypeface(typeface);
        tv_proceed_to_cart.setTypeface(typebold);
        tv_cart_items_txt.setTypeface(typebold);
        tv_pending_order_txt.setTypeface(typebold);
        tv_empty_cart.setTypeface(typebold);
        tv_empty_order_list.setTypeface(typebold);
    }

    private void serviceCallforAllDealers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetDealersDataInterface service = retrofit.create(GetDealersDataInterface.class);

        //Call<GetDealerMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new GetDealerPostParameters(retailerId));
        Call<GetDealerMainObject> call = service.callWithSession(Integer.parseInt(retailerId), accessToken, new GetDealerPostParameters(retailerId));
        call.enqueue(new Callback<GetDealerMainObject>() {
            @Override
            public void onResponse(Call<GetDealerMainObject> call, Response<GetDealerMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                //hideloader();
                if (response.body() != null) {
                    //Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if (response.body().getInfo().getErrorCode() == 0) {
                        tv_empty_order_list.setVisibility(View.GONE);
                        rcv_placedOrders.setVisibility(View.VISIBLE);
                        int size = response.body().getResult().size();
                        dealersList = new ArrayList<>();
                        //  Toast.makeText(getBaseContext(), "Data came" +size, Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < size; i++) {
                            dealersList.add(response.body().getResult().get(i));
                        }

                        setAdapterToList(dealersList);
                    }
                    else{
                        showCustomDialog(getResources().getString(R.string.no_dealer_map_msg));
                    }
                }
                else{
                    showCustomDialog(getResources().getString(R.string.no_dealer_map_msg));
                }

            }

            @Override
            public void onFailure(Call<GetDealerMainObject> call, Throwable t) {
                //hideloader();
                // Toast.makeText(context, "Dealers not coming" , Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }

    private void setAdapterToList(ArrayList<GetDealerResultObject> dealersList) {

        if (cartSize <= 2) {
            cartList = CartDatabase.getTwoProductCart(cartSize);
        } else {
            cartList = CartDatabase.getTwoProductCart(2);
        }
        if (cartSize > 0) {

            if (cartSize > 2) {
                tv_more_items_cart.setVisibility(View.VISIBLE);
                int moreItems = cartSize - 2;
                tv_more_items_cart.setText("+" + moreItems + " items");
            }
            tv_empty_cart.setVisibility(View.GONE);
            rcv_cartlist.setVisibility(View.VISIBLE);
        }

        cartListAdapter = new CartListAdapter(getActivity(), context, dealersList, cartList, HomeActivity.this);
        rcv_cartlist.setLayoutManager(new LinearLayoutManager(context));
        rcv_cartlist.setAdapter(cartListAdapter);

    }

  /*  private void serviceCallforGettingAllOrders() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetAllOrdersDataInterface service = retrofit.create(GetAllOrdersDataInterface.class);

        //Call<AllOrdersMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new GetAllOrdersPostParameters(Integer.parseInt(retailerId), "", "1,3,4,6", pagePerCount, pageNumber));
        Call<AllOrdersMainObject> call = service.callWithSession(Integer.parseInt(retailerId), accessToken, new GetAllOrdersPostParameters(Integer.parseInt(retailerId), Integer.parseInt(retailerId), "", "1,3,4,6", pagePerCount, pageNumber));
        call.enqueue(new Callback<AllOrdersMainObject>() {
            @Override
            public void onResponse(Call<AllOrdersMainObject> call, Response<AllOrdersMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                // hideloader();
                if (response.body() != null) {
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if (response.body().getInfo().getErrorCode() == 0) {
                        tv_empty_order_list.setVisibility(View.GONE);
                        rcv_placedOrders.setVisibility(View.VISIBLE);
                        int size = response.body().getResult().size();
                        orderList = new ArrayList<>();
                        orderList.addAll(response.body().getResult());
                        //Toast.makeText(getBaseContext(), ""+response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                        allOrdersAdapter = new AllOrdersAdapter(getActivity(), context, statusList, orderList,HomeActivity.this, HomeActivity.this);
                        rcv_placedOrders.setLayoutManager(new LinearLayoutManager(context));
                        rcv_placedOrders.setAdapter(allOrdersAdapter);
                    }
                    else if(response.body().getInfo().getErrorCode()==1)
                    {
                        rcv_placedOrders.setVisibility(View.GONE);
                        tv_empty_order_list.setVisibility(View.VISIBLE);

                    }

                    else{
                        showCustomDialog(getResources().getString(R.string.relogin));
                    }


                }
                else{
                    showCustomDialog(getResources().getString(R.string.relogin));
                }

            }

            @Override
            public void onFailure(Call<AllOrdersMainObject> call, Throwable t) {
                // hideloader();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });

    }*/

    private void serviceCallforStatuses() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OrderStatusDataInterface service = retrofit.create(OrderStatusDataInterface.class);

        //Call<OrderStatusMainObject> call = service.getStringScalar(Integer.parseInt(retailerId));
        Call<OrderStatusMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), accessToken, new GetAllOrdersPostParameters(""));
        call.enqueue(new Callback<OrderStatusMainObject>() {
            @Override
            public void onResponse(Call<OrderStatusMainObject> call, Response<OrderStatusMainObject> response) {
                if (response.body() != null) {
                    if (response.body().getInfo().getErrorCode() == 0) {
                        int size = response.body().getResult().size();
                        statusList = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            statusList.add(response.body().getResult().get(i));
                        }

                    }

                    else{
                        //showCustomDialog();
                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<OrderStatusMainObject> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


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
                        MApplication.setBoolean(context, Constants.IsLoggedIn, true);
                        MApplication.setString(context, Constants.AccessToken, response.body().getAccessToken());
                        MApplication.setString(context, Constants.RefreshToken, response.body().getRefreshToken());
                        MApplication.setString(context, Constants.RetailerID, String.valueOf(response.body().getUserId()));
                    }
                    else {
                     // Toast.makeText(context, "Unable to login!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast toast = Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<LoginMainObject> call, Throwable t) {
               //Toast.makeText(context, "Unable to login!", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, R.string.error_connecting_error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onResume() {
        serviceCall();
        super.onResume();
    }

    @Override
    public void onEvent(Boolean data) {
        CartDatabase.init(context);
        cartSize = CartDatabase.getCartlistCount();
        //Toast.makeText(context, "Event", Toast.LENGTH_SHORT).show();
        if(cartSize>0)
        {
            tv_cartSize.setText(String.valueOf(cartSize));
        }
        else
        {
            tv_cartSize.setText("0");
        }
       if (cartSize == 0) {
            tv_more_items_cart.setVisibility(View.GONE);
            tv_proceed_to_cart.setVisibility(View.GONE);
        } else if (cartSize == 1) {
            tv_more_items_cart.setVisibility(View.GONE);
            }

        setAdapterToList(dealersList);

    }

    @Override
    public void startLoader(Boolean data) {

    }

    @Override
    public void stopLoader(Boolean data) {

    }

    @Override
    public void onDelete(Boolean data) {
        rcv_cartlist.setVisibility(View.GONE);
        tv_empty_cart.setVisibility(View.VISIBLE);
    }


    private void showCustomDialog(String alertText) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_no_dealers, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        TextView tv_alertText = dialogView.findViewById(R.id.tv_alertText);
        tv_alertText.setText(alertText);
        TextView button_yes = dialogView.findViewById(R.id.button_yes);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        button_yes.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        });
        //finally creating the alert dialog and displaying it

    }

    public  void loadFragments(Fragment fragment)
    {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
                .commit();
    }

    @Override
    public void onSuccess(boolean data) {
        if(data)
        {
         //  serviceCallforGettingAllOrders();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
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
}
