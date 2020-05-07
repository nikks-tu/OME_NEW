package com.example.ordermadeeasy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import com.google.android.material.navigation.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.adapters.CartListAdapter;
import com.example.ordermadeeasy.adapters.SearchProductAdapter;
import com.example.ordermadeeasy.api_interface.LoginDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.LoginMainObject;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SupplierMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchProductAdapter.EventListener, CartListAdapter.EventListener {
    Context mainContext;
    Toolbar toolbar;
    protected DrawerLayout drawer;
    EditText edt_search;
    LinearLayout ll_cancel_search;
    ImageView iv_cancel_search;
    Toast exitToast;
    Boolean doubleBackToExitPressedOnce = false;
    FragmentManager fragmentManager;
    FrameLayout fl_icon_cart;
    TextView tv_cartSize;
    ImageView goto_homfragment,goto_search_frag;
    int cartSize;
    String authorityKey ="";
    String grantType = "password";
    String accessToken ="";
    private String EmailId;
    private String Password;
    String userType;
    NavigationView navigationView;
    String companyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_supplier);
        init();

        drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.tv_userName);
        String userName = MApplication.getString(mainContext, Constants.UserName);
        String firstLetterCapitalized =
                userName.substring(0, 1).toUpperCase(Locale.getDefault()) + userName.substring(1);
        navUsername.setText(firstLetterCapitalized);
        Typeface faceLight = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");

        if(MApplication.isNetConnected(mainContext))
        {
            loadFragments(SupplierDashboard.newInstance(), "Home");
        }
        else {
            loadFragments(NoInternetConnectionFragment.newInstance(), "NoInternet");
        }
    }


    private void init() {
        mainContext = SupplierMainActivity.this;
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.top_bar_green));
        edt_search = findViewById(R.id.edt_search);
        exitToast = Toast.makeText(getApplicationContext(), "Press back again to exit Order Made Easy", Toast.LENGTH_SHORT);
        goto_homfragment =findViewById(R.id.goto_homfragment);
        goto_search_frag =findViewById(R.id.goto_search_frag);
        fl_icon_cart =findViewById(R.id.fl_icon_cart);
        tv_cartSize =findViewById(R.id.tv_cart_size);
        ll_cancel_search =findViewById(R.id.ll_cancel_search);
        iv_cancel_search =findViewById(R.id.iv_cancel_search);
        authorityKey = Constants.AuthorizationKey;
        grantType = Constants.GrantType;
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(mainContext, Constants.AccessToken));
        accessToken = sb.toString();
        EmailId = MApplication.getString(mainContext, Constants.LoginID);
        Password = MApplication.getString(mainContext, Constants.Password);
        userType = MApplication.getString(mainContext, Constants.UserType);
        companyType = MApplication.getString(mainContext, Constants.CompanyType);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            fragmentManager.popBackStack();
        }
        if(isInHomeFragment() && !doubleBackToExitPressedOnce){
            // Do what ever you want
            exitToast.show();
            doubleBackToExitPressedOnce = true;
            new Handler().postDelayed(() -> {
                doubleBackToExitPressedOnce = false;
                //finish();
            }, 10000);
        }
        else if (isInHomeFragment() ) {
            //finish();
            finishAffinity();
        }
        else if (isInSearchProductFragment() ) {
            //edt_search.setText("");
            loadFragments(SupplierDashboard.newInstance(), "Home");
        }
        else if (isInSupplierConsumerOrders() ) {
            //edt_search.setText("");
            loadFragments(SupplierDashboard.newInstance(), "Home");
        }
        else if (isInSupplierConsumerOrders() ) {
            //edt_search.setText("");
            loadFragments(SupplierDashboard.newInstance(), "Home");
        }
        else if (isInSupplierConsumerOrdersUpdate() ) {
            //edt_search.setText("");
            loadFragments(SupplierConsumerOrdersActivity.newInstance(), "Home");
        }
        else if (isInSupplierConsumerOrdersHistory() ) {
            //edt_search.setText("");
            loadFragments(SupplierDashboard.newInstance(), "Home");
        }
        else if (isInSupplierConsumerOrdersHistoryUpdate() ) {
            //edt_search.setText("");
            loadFragments(SupplierConsumerOrdersHistoryActivity.newInstance(), "Home");
        }
        else if (isInSearchProductFragment() ) {
            //edt_search.setText("");
            loadFragments(SupplierDashboard.newInstance(), "Home");
        }
        else if (isInYourAccountFragment() ) {
            //edt_search.setText("");
            loadFragments(SupplierDashboard.newInstance(), "Home");
        }
        else if (isInCartFragment() ) {
            //edt_search.setText("");
            loadFragments(SupplierDashboard.newInstance(), "Home");
        }
        else if (isInYourOrdersFragment() ) {
            //edt_search.setText("");
            loadFragments(SupplierDashboard.newInstance(),"Home");
        }
        else if (isInProductDescFragment() ) {
            String searchKey = MApplication.getString(mainContext, Constants.SearchKey);
            edt_search.setText(searchKey);
            loadFragments(SearchProductActivity.newInstance(searchKey),"Search");
        }
        else if (isInNoInternetFragment() ) {
            edt_search.setText("");
            loadFragments(SupplierDashboard.newInstance(),"Home");
        }
        else{
            finishAffinity();
            //finish();
            // Do exit app or back press here
            super.onBackPressed();
        }
    }

    private boolean isInHomeFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
           // if (item.isVisible() && "HomeActivity".equals(item.getClass().getSimpleName())) {
            if (item.isVisible() && "SupplierDashboard".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isInSupplierConsumerOrders() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
           // if (item.isVisible() && "HomeActivity".equals(item.getClass().getSimpleName())) {
            if (item.isVisible() && "SupplierConsumerOrdersActivity".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }


    private boolean isInSupplierConsumerOrdersUpdate() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
           // if (item.isVisible() && "HomeActivity".equals(item.getClass().getSimpleName())) {
            if (item.isVisible() && "SupplierConsumerOrderUpdateActivity".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isInSupplierConsumerOrdersHistory() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
           // if (item.isVisible() && "HomeActivity".equals(item.getClass().getSimpleName())) {
            if (item.isVisible() && "SupplierConsumerOrdersHistoryActivity".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }


    private boolean isInSupplierConsumerOrdersHistoryUpdate() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
           // if (item.isVisible() && "HomeActivity".equals(item.getClass().getSimpleName())) {
            if (item.isVisible() && "SupplierConsumerOrderHistoryUpdateActivity".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCartFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "YourCartActivity".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }
    private boolean isInSearchProductFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "SearchProductActivity".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }
    private boolean isInYourAccountFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "YourAccountSupplierActivity".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }
    private boolean isInYourOrdersFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "YourOrdersActivity".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }
    private boolean isInProductDescFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "ProductDescFragment".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isInNoInternetFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "NoInternetConnectionFragment".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.main, menu);
        Menu nav_Menu = navigationView.getMenu();
        CartDatabase.init(mainContext);

        int roleListSize = CartDatabase.getUserRolesListCount();
        if(companyType.equals(Constants.CompanyBtoB))
        {
            nav_Menu.findItem(R.id.nav_consumer_orders).setVisible(false);
            nav_Menu.findItem(R.id.nav_consumer_orders_history).setVisible(false);
        }
        else if (companyType.equals(Constants.CompanyBtoC))
        {
            nav_Menu.findItem(R.id.nav_shopper_orders).setVisible(false);
            nav_Menu.findItem(R.id.nav_shopper_orders_history).setVisible(false);
        }
        else if (companyType.equals(Constants.CompanyTypeBoth))
        {
            nav_Menu.findItem(R.id.nav_consumer_orders).setVisible(true);
            nav_Menu.findItem(R.id.nav_consumer_orders_history).setVisible(true);
            nav_Menu.findItem(R.id.nav_shopper_orders).setVisible(true);
            nav_Menu.findItem(R.id.nav_shopper_orders_history).setVisible(true);
        }
        if(roleListSize==1)
        {
            nav_Menu.findItem(R.id.nav_switch_account).setVisible(false);
        }
        else {
            nav_Menu.findItem(R.id.nav_switch_account).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       int id = item.getItemId();

        if(MApplication.isNetConnected(mainContext))
        {
            if (id == R.id.nav_home) {
                // loadFragments(HomeActivity.newInstance(),"Home");
                loadFragments(SupplierDashboard.newInstance(),"Home");
            } else if (id == R.id.nav_consumer_orders) {
                //serviceCall();
                loadFragments(SupplierConsumerOrdersActivity.newInstance(), "Consumer Orders");
             } else if (id == R.id.nav_consumer_orders_history) {
                //serviceCall();
                loadFragments(SupplierConsumerOrdersHistoryActivity.newInstance(), "Consumer Orders");
             } else if (id == R.id.nav_shopper_orders) {
                //serviceCall();
                loadFragments(SupplierShopperOrdersActivity.newInstance(), "Shopper Orders");
             }else if (id == R.id.nav_shopper_orders_history) {
                //serviceCall();
                loadFragments(SupplierShopperOrdersHistoryActivity.newInstance(), "Shopper Orders");
             } else if (id == R.id.nav_your_account) {
                //serviceCall();
                loadFragments(YourAccountSupplierActivity.newInstance(), "Account");

            }else if (id == R.id.nav_change_password) {
                //serviceCall();
                loadFragments(ChangePasswordSupplier.newInstance(), "Change_password");
            }else if (id == R.id.nav_switch_account) {
                //serviceCall();
               Intent intent = new Intent(mainContext, UserAccountsActivity.class);
               startActivity(intent);

             } else if (id == R.id.nav_logout) {
                gotologout();
            }
        }
        else {
           loadFragments(NoInternetConnectionFragment.newInstance(),"NoInternet");
        }


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotologout() {
        CartDatabase.init(mainContext);
        CartDatabase.clearProductCart();
        CartDatabase.clearRoles();
        MApplication.setString(mainContext, Constants.AccessToken, "");
        MApplication.setString(mainContext, Constants.LoginID, "");
        MApplication.setString(mainContext, Constants.Password, "");
        MApplication.setString(mainContext, Constants.UserID, "");
        MApplication.setString(mainContext, Constants.RetailerID, "");
        MApplication.setString(mainContext, Constants.UserType, "");
        MApplication.setString(mainContext, Constants.UserName, "");
        MApplication.setBoolean(mainContext, Constants.IsLoggedIn, false);
        MApplication.setString(mainContext, Constants.PendingOrderCount, "");
        Intent intent = new Intent(mainContext, LoginActivity.class);
        startActivity(intent);
    }

    public  void loadFragments(Fragment fragment, String tag)
    {
        fragmentManager =getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment, tag).commit();
    }

    @Override
    public void onEvent(Boolean data) {
        CartDatabase.init(mainContext);
        cartSize = CartDatabase.getCartlistCount();
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
    public void onDelete(Boolean data) {
        CartDatabase.init(mainContext);
        cartSize = CartDatabase.getCartlistCount();
        // Toast.makeText(mainContext, "size" +cartSize, Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void stopLoader(Boolean data) {

    }

    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }

      private void serviceCallForCompanySearch() {
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
                    if(response.body().getUserId()!=null)
                    {
                        MApplication.setBoolean(mainContext, Constants.IsLoggedIn, true);
                        MApplication.setString(mainContext, Constants.AccessToken, response.body().getAccessToken());
                        MApplication.setString(mainContext, Constants.RefreshToken, response.body().getRefreshToken());
                        MApplication.setString(mainContext, Constants.RetailerID, String.valueOf(response.body().getUserId()));
                        //MApplication.setString(mainContext, Constants.RetailerCompanyID, String.valueOf(response.body().getCompanyId()));
                    }
                    else {
                        // Toast.makeText(context, "Unable to login!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast toast = Toast.makeText(mainContext, R.string.something_went_wrong, Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<LoginMainObject> call, Throwable t) {
                //Toast.makeText(context, "Unable to login!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(mainContext, R.string.error_connecting_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
       // Toast.makeText(mainContext, "Resume", Toast.LENGTH_SHORT).show();
        loadFragments(SupplierDashboard.newInstance(), "Home");
        super.onResume();
    }
}
