package com.techuva.ome_new;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import com.google.android.material.navigation.NavigationView;
import androidx.multidex.MultiDex;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.techuva.ome_new.activity.ChangePassword;
import com.techuva.ome_new.activity.ConsumerOrdersActivity;
import com.techuva.ome_new.activity.Dashboard;
import com.techuva.ome_new.activity.LoginActivity;
import com.techuva.ome_new.activity.NoInternetConnectionFragment;
import com.techuva.ome_new.activity.SearchCompanyActivity;
import com.techuva.ome_new.activity.SearchProductActivity;
import com.techuva.ome_new.activity.UserAccountsActivity;
import com.techuva.ome_new.activity.YourAccountActivity;
import com.techuva.ome_new.activity.YourCartActivity;
import com.techuva.ome_new.activity.YourOrdersActivity;
import com.techuva.ome_new.adapters.CartListAdapter;
import com.techuva.ome_new.adapters.SearchProductAdapter;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.SearchProductResultObject;
import com.techuva.ome_new.utilities.CartDatabase;
import com.techuva.ome_new.views.MApplication;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SearchProductAdapter.EventListener, CartListAdapter.EventListener {
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
    String refreshToken = "";
    private String EmailId;
    private String Password;
    private SearchProductResultObject selectedProduct;
    String userType;
    NavigationView navigationView;
    int pendingOrderCount;
    int userId;
    TextView navUsername;
    TextView navVersionCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.tv_userName);
        navVersionCode = headerView.findViewById(R.id.last_logged_time);
        String userName = MApplication.getString(mainContext, Constants.UserName);
        String firstLetterCapitalized =
                userName.substring(0, 1).toUpperCase(Locale.getDefault()) + userName.substring(1);
        navUsername.setText(firstLetterCapitalized);
        navVersionCode.setText(BuildConfig.VERSION_NAME);
        Typeface faceLight = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        edt_search.setTypeface(faceLight);
        ll_cancel_search.setVisibility(View.GONE);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(mainContext, "Change", Toast.LENGTH_SHORT).show();
                if(edt_search.getText().toString().equals(""))
                {
                    ll_cancel_search.setVisibility(View.GONE);
                }
                else {
                    ll_cancel_search.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        edt_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
                return true;
            }
            return false;
        });

        ll_cancel_search.setOnClickListener(v -> edt_search.setText(""));

        if(MApplication.isNetConnected(mainContext))
        {
            loadFragments(Dashboard.newInstance(), "Home");
        }
        else {
            loadFragments(NoInternetConnectionFragment.newInstance(), "NoInternet");
        }
    }

    private void performSearch() {

        String searchKey = edt_search.getText().toString().trim();
        if(searchKey.length()<3)
        {
            Toast.makeText(mainContext, "Please enter atleast 3 letters to search", Toast.LENGTH_SHORT).show();
        }
        else if(searchKey.matches(""))
        {
            Toast.makeText(mainContext, "Please enter atleast 3 letters to search", Toast.LENGTH_SHORT).show();
        }
        else if(userType.equals(Constants.Consumer))
        {
            boolean isFromFavourites = MApplication.getBoolean(mainContext , Constants.IsFromFavourites);
            int tempCartSize = Integer.parseInt(MApplication.getString(mainContext, Constants.CartSize));
            if(tempCartSize>0)
            {
                int tempCompanyId = Integer.parseInt(MApplication.getString(mainContext, Constants.CompanyID));
                loadFragments(SearchProductActivity.newInstance(searchKey),"Search");
            }
            else if(isFromFavourites)
            {
                int tempCompanyId = Integer.parseInt(MApplication.getString(mainContext, Constants.CompanyID));
                edt_search.setHint("Search Product");
                loadFragments(SearchProductActivity.newInstance(searchKey),"Search");
            }
            else {
                MApplication.setString(mainContext, Constants.SearchKey, edt_search.getText().toString());
                loadFragments(SearchCompanyActivity.newInstance(edt_search.getText().toString()),"Search");
            }

             }
        else {
            MApplication.setString(mainContext, Constants.SearchKey, edt_search.getText().toString());
            loadFragments(SearchProductActivity.newInstance(edt_search.getText().toString()),"Search");
        }

    }

    private void init() {
        mainContext = MainActivity.this;
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
        goto_homfragment.setOnClickListener(this);
        goto_search_frag.setOnClickListener(this);
        fl_icon_cart.setOnClickListener(this);
        authorityKey = Constants.AuthorizationKey;
        grantType = Constants.GrantType;
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(mainContext, Constants.AccessToken));
        accessToken = sb.toString();
        EmailId = MApplication.getString(mainContext, Constants.LoginID);
        Password = MApplication.getString(mainContext, Constants.Password);
        userType = MApplication.getString(mainContext, Constants.UserType);
        userId = Integer.parseInt(MApplication.getString(mainContext, Constants.UserID));
        if(userType.equals(Constants.Consumer)){
               if(cartSize>0)
               {
                   edt_search.setHint("Search Product");
               }
               else {
                   MApplication.setBoolean(mainContext, Constants.IsFromFavourites, false);
                   edt_search.setHint("Search Company");
               }
               if(!MApplication.getBoolean(mainContext, Constants.IsFromFavourites))
               {
                   edt_search.setHint("Search Company");
               }
               else {
                   edt_search.setHint("Search Product");
               }
        }
        if(userType.equals(Constants.Shopper))
        {
            //Toast.makeText(mainContext, "main init", Toast.LENGTH_SHORT).show();
            MApplication.serviceCallforShopperCartProducts(mainContext, String.valueOf(userId), accessToken);
            MApplication.serviceCallforShopperPendingOrders(mainContext, accessToken, String.valueOf(userId), userType);
            if(MApplication.getString(mainContext, Constants.CartSize).equals(""))
            {
                cartSize = 0;
            }
            else {
                cartSize = Integer.parseInt(MApplication.getString(mainContext, Constants.CartSize));
            }
            Handler handler = new Handler();
            handler.postDelayed(() -> {

            }, 1000);
            handler = new Handler();
            handler.postDelayed(() -> {
                tv_cartSize.setText(String.valueOf(cartSize));
                if(MApplication.getString(mainContext, Constants.PendingOrderCount).equals(""))
                {
                    pendingOrderCount = 0;
                }
                else {
                    pendingOrderCount = Integer.parseInt(MApplication.getString(mainContext, Constants.PendingOrderCount));
                }
            }, 1000);

        }
        else if(userType.equals(Constants.Consumer))
        {
            MApplication.serviceCallforCartProducts(mainContext, String.valueOf(userId), accessToken);
            MApplication.serviceCallforConsumerPendingOrders(mainContext, accessToken, String.valueOf(userId), userType);
            if(MApplication.getString(mainContext, Constants.CartSize).equals("") || MApplication.getString(mainContext, Constants.CartSize).equals("0"))
            {
                cartSize = 0;
                edt_search.setHint("Search Company");
            }
            else {
                cartSize = Integer.parseInt(MApplication.getString(mainContext, Constants.CartSize));
                edt_search.setHint("Search Product");
            }
            Handler handler = new Handler();
            handler.postDelayed(() -> {

            }, 1000);
            handler = new Handler();
            handler.postDelayed(() -> {
                tv_cartSize.setText(String.valueOf(cartSize));
                if(MApplication.getString(mainContext, Constants.PendingOrderCount).equals(""))
                {
                    pendingOrderCount = 0;
                }
                else {
                    pendingOrderCount = Integer.parseInt(MApplication.getString(mainContext, Constants.PendingOrderCount));
                }
            }, 1000);
        }
        new Handler().post(() -> {
            String count = MApplication.getString(mainContext, Constants.PendingOrderCount);
            if(!count.equals(""))
            {
                //Toast.makeText(mainContext, "Main"+count, Toast.LENGTH_SHORT).show();
                pendingOrderCount = Integer.parseInt(MApplication.getString(mainContext, Constants.PendingOrderCount));
                //tv_num_pending_orders.setText(String.valueOf(pendingOrderCount));
            }
        });
        updateTextView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
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
            edt_search.setText("");
          if(userType.equals(Constants.Consumer))
          {
              if(cartSize>0)
              {
                edt_search.setHint("Search Product");
              }
              else {
                  MApplication.setBoolean(mainContext, Constants.IsFromFavourites, false);
                  edt_search.setHint("Search Company");
              }
          }
            loadFragments(Dashboard.newInstance(), "Home");
        }
        else if (isInSwitchAccount() ) {
            edt_search.setText("");
            loadFragments(Dashboard.newInstance(), "Home");
        }
        else if (isInSearchCompanyFragment() ) {
            edt_search.setText("");
            /*if(MApplication.getBoolean(mainContext, Constants.IsFromFavourites))
            {
                Toast.makeText(mainContext, "Yes", Toast.LENGTH_SHORT).show();
            }*/
            loadFragments(Dashboard.newInstance(), "Home");
        }
        else if (isInYourAccountFragment() ) {
            edt_search.setText("");
            loadFragments(Dashboard.newInstance(), "Home");
        }
        else if (isInCartFragment() ) {
            edt_search.setText("");
            loadFragments(Dashboard.newInstance(), "Home");
        }
        else if (isInYourOrdersFragment() ) {
            edt_search.setText("");
            loadFragments(Dashboard.newInstance(),"Home");
        }
        else if (isInConsumerYourOrdersFragment() ) {
            edt_search.setText("");
            loadFragments(Dashboard.newInstance(),"Home");
        }
        else if (isInConsumerOrderUpdateActivity() ) {
            edt_search.setText("");
            loadFragments(ConsumerOrdersActivity.newInstance(),"Home");
        }
        else if (isInProductDescFragment() ) {
            if(userType.equals(Constants.Consumer))
            {
                String searchKey = "";
                edt_search.setText(searchKey);
                loadFragments(SearchProductActivity.newInstance(searchKey),"Search");
            }
            else {
                String searchKey = edt_search.getText().toString();
                loadFragments(SearchProductActivity.newInstance(searchKey), "Search");
            }
        }
        else if (isInNoInternetFragment() ) {
            edt_search.setText("");
            loadFragments(Dashboard.newInstance(),"Home");
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
            if (item.isVisible() && "Dashboard".equals(item.getClass().getSimpleName())) {
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
    private boolean isInSearchCompanyFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "SearchCompanyActivity".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }
    private boolean isInYourAccountFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "YourAccountActivity".equals(item.getClass().getSimpleName())) {
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
    private boolean isInConsumerYourOrdersFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "ConsumerOrdersActivity".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }
    private boolean isInConsumerOrderUpdateActivity() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "ConsumerOrderUpdateActivity".equals(item.getClass().getSimpleName())) {
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
    private boolean isInSwitchAccount() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (item.isVisible() && "UserAccountsActivity".equals(item.getClass().getSimpleName())) {
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
        Menu nav_Menu = navigationView.getMenu();
        if(userType.equals(Constants.Consumer))
        {
            nav_Menu.findItem(R.id.nav_search_products).setTitle(mainContext.getResources().getString(R.string.search_company));
            nav_Menu.findItem(R.id.nav_switch_account).setVisible(false);
            nav_Menu.findItem(R.id.nav_change_password).setVisible(false);
        }
        else if(userType.equals(Constants.Shopper))
        {
            nav_Menu.findItem(R.id.nav_search_products).setTitle(mainContext.getResources().getString(R.string.search_products));
            CartDatabase.init(mainContext);
            int roleListSize = CartDatabase.getUserRolesListCount();
            if(roleListSize==1)
            {
                nav_Menu.findItem(R.id.nav_switch_account).setVisible(false);
            }
            else {
                nav_Menu.findItem(R.id.nav_switch_account).setVisible(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(MApplication.isNetConnected(mainContext))
        {

            if (id == R.id.nav_home) {
                edt_search.setText("");
                if(MApplication.getString(mainContext, Constants.CartSize).equals("0") && userType.equals(Constants.Consumer))
                {
                    edt_search.setHint("Search Company");
                    MApplication.setBoolean(mainContext, Constants.IsFromFavourites, false);
                }
                else {
                    edt_search.setHint("Search Product");
                }
                loadFragments(Dashboard.newInstance(),"Home");

            } else if (id == R.id.nav_search_products) {
               //serviceCall();
               if(userType.equals(Constants.Consumer))
               {
                   edt_search.setText("");
                   if(MApplication.getString(mainContext, Constants.CartSize).equals("0"))
                   {
                       edt_search.setHint("Search Company");
                       MApplication.setBoolean(mainContext, Constants.IsFromFavourites, false);
                   }
                   else {
                       edt_search.setHint("Search Product");
                   }

                   MApplication.setBoolean(mainContext, Constants.IsFromFavourites, false);
                   loadFragments(SearchCompanyActivity.newInstance(""), "Search");
               }
               else if(userType.equals(Constants.Shopper))
               {
                   edt_search.setText("");
                   edt_search.setHint("Search Product");
                   loadFragments(SearchProductActivity.newInstance(""), "Search");
               }
            } else if (id == R.id.nav_your_orders) {
                //serviceCall();
                if(userType.equals(Constants.Consumer))
                {
                    edt_search.setText("");
                    if(MApplication.getString(mainContext, Constants.CartSize).equals("0"))
                    {
                        edt_search.setHint("Search Company");
                        MApplication.setBoolean(mainContext, Constants.IsFromFavourites, false);
                    }
                    else {
                        edt_search.setHint("Search Product");
                    }
                    loadFragments(ConsumerOrdersActivity.newInstance(), "Orders");
                }
                else {
                    edt_search.setText("");
                    edt_search.setHint("Search Product");
                    loadFragments(YourOrdersActivity.newInstance(), "Orders");
                }
            } else if (id == R.id.nav_your_cart) {
                //serviceCall();
                if(userType.equals(Constants.Consumer))
                {
                    edt_search.setText("");
                    if(MApplication.getString(mainContext, Constants.CartSize).equals("0"))
                    {
                        edt_search.setHint("Search Company");
                        MApplication.setBoolean(mainContext, Constants.IsFromFavourites, false);
                    }
                    else {
                        edt_search.setHint("Search Product");
                    }

                }
                else {
                    edt_search.setText("");
                    edt_search.setHint("Search Product");
                }
                loadFragments(YourCartActivity.newInstance(), "Cart");
            } else if (id == R.id.nav_switch_account) {
                //serviceCall();
                //loadFragments(UserAccountsActivity.newInstance(), "Switch");
                Intent intent = new Intent(mainContext, UserAccountsActivity.class);
                startActivity(intent);

            }else if (id == R.id.nav_your_account) {
                if(userType.equals(Constants.Consumer))
                {
                    edt_search.setText("");
                    if(MApplication.getString(mainContext, Constants.CartSize).equals("0"))
                    {
                        edt_search.setHint("Search Company");
                        MApplication.setBoolean(mainContext, Constants.IsFromFavourites, false);
                    }
                    else {
                        edt_search.setHint("Search Product");
                    }

                }
                else {

                    edt_search.setText("");
                    edt_search.setHint("Search Product");
                }
                loadFragments(YourAccountActivity.newInstance(), "Account");

            }else if (id == R.id.nav_change_password) {
                if(userType.equals(Constants.Shopper))
                {
                    edt_search.setText("");
                    edt_search.setHint("Search Product");
                }
                loadFragments(ChangePassword.newInstance(), "Change_password");
            } else if (id == R.id.nav_logout) {
                gotologout();
            }
        }
        else {
            // No Internet Connection
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
        MApplication.setString(mainContext, Constants.CompanyID, "");
        MApplication.setString(mainContext, Constants.COMPANY_ID, "");
        Intent intent = new Intent(mainContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public  void loadFragments(Fragment fragment, String tag)
    {
        fragmentManager =getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment, tag).commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.goto_homfragment:
                navigationView.getMenu().getItem(0).setChecked(true);
                edt_search.setText("");
                if(MApplication.getString(mainContext, Constants.CartSize).equals("0") && userType.equals(Constants.Consumer))
                {
                    MApplication.setBoolean(mainContext, Constants.IsFromFavourites, false);
                    edt_search.setHint("Search Company");
                }
                else {
                    edt_search.setHint("Search Product");
                }

                loadFragments(Dashboard.newInstance(), "Home");
                break;
            case R.id.goto_search_frag:
                String searchKey = edt_search.getText().toString().trim();
                userType = MApplication.getString(mainContext, Constants.UserType);
                if(searchKey.equals(""))
                {
                    Toast.makeText(mainContext, "Please enter at least 3 letters to search", Toast.LENGTH_SHORT).show();
                }
                else if(searchKey.length()<3)
                {
                    Toast.makeText(mainContext, "Please enter at least 3 letters to search", Toast.LENGTH_SHORT).show();
                }
                else if(userType.equals(Constants.Consumer))
                {
                  /*  MApplication.setString(mainContext, Constants.SearchKey, edt_search.getText().toString());
                    loadFragments(SearchCompanyActivity.newInstance(edt_search.getText().toString()),"Search");
                    *//*
                  //try
                    int tempCartSize = Integer.parseInt(MApplication.getString(mainContext, Constants.CartSize));
                    if(tempCartSize>0)
                    {
                        int tempCompanyId = Integer.parseInt(MApplication.getString(mainContext, Constants.CompanyID));
                        loadFragments(SearchProductActivity.newInstance(searchKey),"Search");
                    }
                    else {
                        MApplication.setString(mainContext, Constants.SearchKey, edt_search.getText().toString());
                        loadFragments(SearchCompanyActivity.newInstance(edt_search.getText().toString()),"Search");
                    }*/
                    boolean isFromFavourites = MApplication.getBoolean(mainContext , Constants.IsFromFavourites);
                    int tempCartSize = Integer.parseInt(MApplication.getString(mainContext, Constants.CartSize));
                    if(tempCartSize>0)
                    {
                        int tempCompanyId = Integer.parseInt(MApplication.getString(mainContext, Constants.CompanyID));
                        loadFragments(SearchProductActivity.newInstance(searchKey),"Search");
                    }
                    else if(isFromFavourites)
                    {
                        int tempCompanyId = Integer.parseInt(MApplication.getString(mainContext, Constants.CompanyID));
                        edt_search.setHint("Search Product");
                        loadFragments(SearchProductActivity.newInstance(searchKey),"Search");
                    }
                    else {
                        MApplication.setString(mainContext, Constants.SearchKey, edt_search.getText().toString());
                        loadFragments(SearchCompanyActivity.newInstance(edt_search.getText().toString()),"Search");
                    }

                }
                else {
                    MApplication.setString(mainContext, Constants.SearchKey, edt_search.getText().toString());
                    loadFragments(SearchProductActivity.newInstance(edt_search.getText().toString()),"Search");
                }
                break;
        }
    }

    @Override
    public void onEvent(Boolean data) {
      /*  CartDatabase.init(mainContext);
        cartSize = CartDatabase.getCartlistCount();
        if(cartSize>0)
        {
            tv_cartSize.setText(String.valueOf(cartSize));
        }
        else
        {
            tv_cartSize.setText("0");
        }*/

    }

    @Override
    public void onDelete(Boolean data) {
      /*  CartDatabase.init(mainContext);
        cartSize = CartDatabase.getCartlistCount();
        // Toast.makeText(mainContext, "size" +cartSize, Toast.LENGTH_SHORT).show();
        if(cartSize>0)
        {
            tv_cartSize.setText(String.valueOf(cartSize));
        }
        else
        {
            tv_cartSize.setText("0");
        }*/
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

    public SearchProductResultObject getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(SearchProductResultObject selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    @Override
    protected void onResume() {
        if(MApplication.getString(mainContext, Constants.CartSize).equals("0") && userType.equals(Constants.Consumer))
        {
            MApplication.setBoolean(mainContext, Constants.IsFromFavourites, false);
            edt_search.setHint("Search Company");
        }
        else {
            edt_search.setHint("Search Product");
        }
        if(userType.equals(Constants.Shopper))
        {
            //Toast.makeText(mainContext, "main resume", Toast.LENGTH_SHORT).show();
            MApplication.serviceCallforShopperCartProducts(mainContext, String.valueOf(userId), accessToken);
            MApplication.serviceCallforShopperPendingOrders(mainContext, accessToken, String.valueOf(userId), userType);
            if(MApplication.getString(mainContext, Constants.CartSize).equals(""))
            {
                cartSize = 0;
            }
            else {
                cartSize = Integer.parseInt(MApplication.getString(mainContext, Constants.CartSize));
            }

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if(MApplication.getString(mainContext, Constants.PendingOrderCount).equals(""))
                {
                    pendingOrderCount = 0;
                }
                else {
                    pendingOrderCount = Integer.parseInt(MApplication.getString(mainContext, Constants.PendingOrderCount));
                }
            }, 1000);
        }
        else if(userType.equals(Constants.Consumer))
        {
            MApplication.serviceCallforCartProducts(mainContext, String.valueOf(userId), accessToken);
            MApplication.serviceCallforConsumerPendingOrders(mainContext, accessToken, String.valueOf(userId), userType);
            if(MApplication.getString(mainContext, Constants.CartSize).equals("") || MApplication.getString(mainContext, Constants.CartSize).equals("0"))
            {
                cartSize = 0;
                edt_search.setHint("Search Company");
            }
            else {
                cartSize = Integer.parseInt(MApplication.getString(mainContext, Constants.CartSize));
                edt_search.setHint("Search Product");
            }
            tv_cartSize.setText(String.valueOf(cartSize));

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if(MApplication.getString(mainContext, Constants.PendingOrderCount).equals(""))
                {
                    pendingOrderCount = 0;
                }
                else {
                    pendingOrderCount = Integer.parseInt(MApplication.getString(mainContext, Constants.PendingOrderCount));
                }
            }, 1000);
        }
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            String count1 = MApplication.getString(mainContext, Constants.PendingOrderCount);
            if(!count1.equals(""))
            {
                //Toast.makeText(mainContext, "resume"+count1, Toast.LENGTH_SHORT).show();
                pendingOrderCount = Integer.parseInt(MApplication.getString(mainContext, Constants.PendingOrderCount));
                //updateTextView();
            }
            else
            {
               // Toast.makeText(mainContext, "resume only", Toast.LENGTH_SHORT).show();
            }
        }, 600);

        new Handler().post(() -> {
            String count = MApplication.getString(mainContext, Constants.PendingOrderCount);
            if(!count.equals(""))
            {
                 pendingOrderCount = Integer.parseInt(MApplication.getString(mainContext, Constants.PendingOrderCount));
                //tv_num_pending_orders.setText(String.valueOf(pendingOrderCount));
            }
        });

        updateTextView();
        String userName = MApplication.getString(mainContext, Constants.UserName);
        String firstLetterCapitalized =
                userName.substring(0, 1).toUpperCase(Locale.getDefault()) + userName.substring(1);
        navUsername.setText(firstLetterCapitalized);

        super.onResume();
    }

    private void updateTextView() {

        AppCompatActivity mActivity = MainActivity.this;
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            tv_cartSize.setText(String.valueOf(cartSize));
            if(MApplication.getString(mainContext, Constants.PendingOrderCount).equals(""))
            {
                pendingOrderCount = 0;
            }
            else {
                pendingOrderCount = Integer.parseInt(MApplication.getString(mainContext, Constants.PendingOrderCount));
            }
        }, 600);
     /*   mActivity.runOnUiThread(() -> {
            String count = MApplication.getString(mainContext, Constants.PendingOrderCount);
            if(!count.equals(""))
            {
                pendingOrderCount = Integer.parseInt(MApplication.getString(mainContext, Constants.PendingOrderCount));
            }
        });*/
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
