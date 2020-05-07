package com.example.ordermadeeasy.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordermadeeasy.MainActivity;
import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.api_interface.LoginDataInterface;
import com.example.ordermadeeasy.api_interface.SendOTPDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.fcm.Utils;
import com.example.ordermadeeasy.object_models.AccountListResultObject;
import com.example.ordermadeeasy.post_parameters.LoginPostParameters;
import com.example.ordermadeeasy.post_parameters.SendOTPPostParameters;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;
import com.example.ordermadeeasy.views.MPreferences;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    LinearLayout ll_root_login, ll_button_login, ll_layout_forgot_password, ll_button_signup;
    LinearLayout ll_button_submit, ll_password, ll_layout_otp, ll_click_here;
    EditText edt_userName, edt_userPassword;
    ScrollView sv_login;
    Context loginContext;
    TextView tv_forgotPassword, tv_copyrights, tv_resend_otp;
    MPreferences preferences;
    Toast exitToast;
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    Boolean doubleBackToExitPressedOnce = true;
    private String EmailId;
    private String Password;
    int UserId;
    String authorityKey ="";
    String grantType = "";
    ArrayList<AccountListResultObject> rolesList;
    FrameLayout toolbar;
    TextView tv_first, tv_second;
    RadioGroup rg_login_role;
    RadioButton rb_shopper, rb_consumer;
    View view_divider;
    TextInputLayout input_otp;
    EditText edt_otp;
    String mobileNumForOTP;
    String otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitViews();
        exitToast = Toast.makeText(getApplicationContext(), "Press back again to exit Order Made Easy", Toast.LENGTH_SHORT);
        edt_userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ll_button_login.setEnabled(true);
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                ll_button_login.setEnabled(true);
                // TODO Auto-generated method stub
            }
        });


        rg_login_role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rb_consumer.isChecked())
                {   edt_userName.setHint("Mobile No.");
                    //Toast.makeText(loginContext, rb_consumer.getText().toString(), Toast.LENGTH_SHORT).show();
                    ll_layout_forgot_password.setVisibility(View.GONE);
                    edt_userName.setHint("Mobile No.");
                    view_divider.setVisibility(View.GONE);
                    ll_password.setVisibility(View.GONE);
                }
                else if (rb_shopper.isChecked())
                {
                    //Toast.makeText(loginContext, rb_shopper.getText().toString(), Toast.LENGTH_SHORT).show();
                    ll_layout_forgot_password.setVisibility(View.VISIBLE);
                    edt_userName.setHint("Mobile No./ Email-Id");
                    view_divider.setVisibility(View.VISIBLE);
                    ll_password.setVisibility(View.VISIBLE);
                }
            }
        });

        ll_button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginContext, ConsumerSignUpActivity.class);
                startActivity(intent);
            }
        });

        edt_userPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ll_button_login.setEnabled(true);
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                ll_button_login.setEnabled(true);
                // TODO Auto-generated method stub
            }
        });

        tv_forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(loginContext, ForgotPassword.class);
            startActivity(intent);
        });

        ll_button_submit.setOnClickListener(v -> {
            otp = edt_otp.getText().toString();
            if(!otp.equals("")){
                serviceCallForConsumerLogin();
            }
            else {
                Toast.makeText(loginContext, getString(R.string.otp_validate), Toast.LENGTH_SHORT).show();
            }
        });

        ll_click_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                serviceCallForSendingOTP();
            }
        });


        ll_button_login.setOnClickListener(v -> {
          /*  Intent intent = new Intent(loginContext, Dashboard.class);
            startActivity(intent);*/

            ll_button_login.setEnabled(false);
            getTextInputs();

            if(MApplication.isNetConnected(loginContext))
            {
              if(rb_consumer.isChecked()){
                  if(EmailId.length()==10 ||  Utils.isValidMobile(EmailId)){
                      sv_login.setVisibility(View.GONE);
                      ll_layout_otp.setVisibility(View.VISIBLE);
                      mobileNumForOTP = EmailId;
                      MApplication.setString(loginContext, Constants.TempMobile, mobileNumForOTP);
                      serviceCallForSendingOTP();
                  }
                  else {
                      Toast.makeText(loginContext, "Please enter valid mobile number.", Toast.LENGTH_SHORT).show();
                  }
              }
              else {
                  if(EmailId.length() > 0 || Password.length() > 0)
                  {
                      if(EmailId.length()>0)
                      {
                          if(Password.length()>0)
                          {
                              showLoaderNew();
                              serviceCall();
                          }
                          else
                          {
                              ll_button_login.setEnabled(true);
                              Toast toast = Toast.makeText(loginContext, "Please enter password" , Toast.LENGTH_LONG);
                              View view = toast.getView();
                              view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                              toast.show();
                          }
                      }
                      else
                      {
                          ll_button_login.setEnabled(true);
                          Toast toast = Toast.makeText(loginContext, R.string.enter_user_name, Toast.LENGTH_LONG);
                          View view = toast.getView();
                          view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                          toast.show();
                      }
                  }
                  else
                  {
                      ll_button_login.setEnabled(true);
                      Toast toast = Toast.makeText(loginContext, "Please enter mobile number and password", Toast.LENGTH_LONG);
                      View view = toast.getView();
                      view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                      toast.show();
                  }
              }
            }
            else
            {
                ll_button_login.setEnabled(true);
                Toast toast = Toast.makeText(loginContext, "No Internet Connection!", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                toast.show();
            }
        });
    }

    private void getTextInputs() {
        EmailId = edt_userName.getText().toString().trim();
        Password = edt_userPassword.getText().toString();
        MApplication.setString(loginContext, Constants.LoginID, EmailId);
        MApplication.setString(loginContext, Constants.Password, Password);
    }

    private void proceedToNextScreen(ArrayList<AccountListResultObject> rolesList) {
        MApplication.setBoolean(loginContext, Constants.IsLoggedIn, true);
        if(rolesList.size()==1){
           MApplication.setString(loginContext, Constants.UserType, rolesList.get(0).typeCode);
           String userType = MApplication.getString(loginContext, Constants.UserType);
           if(userType.equals(Constants.Supplier))
           {
               MApplication.setBoolean(loginContext, Constants.IsRoleSelected, true);
               Intent intent = new Intent(loginContext, SupplierMainActivity.class);
               startActivity(intent);
           }
           else {
               MApplication.setBoolean(loginContext, Constants.IsRoleSelected, true);
               Intent intent = new Intent(loginContext, MainActivity.class);
               startActivity(intent);
           }
       }
       else {
           Intent intent = new Intent(loginContext, UserAccountsActivity.class);
           startActivity(intent);
       }
    }


    private void serviceCall() {

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(Constants.LIVE_BASE_URL)
                .baseUrl(Constants.LIVE_BASE_URL_New)
                //.baseUrl("http://182.18.177.27/TUUserManagement/api/user/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginDataInterface service = retrofit.create(LoginDataInterface.class);

        Call<JsonElement> call = service.loginCallNew(authorityKey, EmailId, Password, grantType);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.body()!=null){
                    hideloader();
                    JsonObject responseObject = response.body().getAsJsonObject();
                    JsonObject infoObject = responseObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();

                    if(errorCode==0)
                    {
                        JsonObject resultObject = responseObject.get("result").getAsJsonObject();
                        String userRoles = resultObject.get("type").getAsString();
                        String companyType = resultObject.get("companyTypeCode").getAsString();
                        MApplication.setString(loginContext, Constants.CompanyType, companyType);
                        //List<String> rolesList = Arrays.asList(userRoles.split(","));
                        if(userRoles.equals(Constants.Admin))
                        {  Toast.makeText(loginContext, "Login not allowed, Please login to our website!", Toast.LENGTH_LONG).show();
                        }
                        else if(userRoles.equals(Constants.SuperAdmin)){
                            Toast.makeText(loginContext, "Login not allowed, Please login to our website!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            MApplication.setString(loginContext, Constants.AccessToken, resultObject.get("access_token").getAsString());
                            MApplication.setString(loginContext, Constants.RefreshToken, resultObject.get("refresh_token").getAsString());
                            MApplication.setString(loginContext, Constants.UserName, resultObject.get("userName").getAsString());
                            MApplication.setString(loginContext, Constants.UserID, resultObject.get("userId").getAsString());
                            MApplication.setString(loginContext, Constants.CompanyID, resultObject.get("companyId").getAsString());
                            MApplication.setString(loginContext, Constants.UserMailId, resultObject.get("email").getAsString());
                            MApplication.setString(loginContext, Constants.CompanyTypeCode, resultObject.get("companyTypeCode").getAsString());
                            JsonArray jsonArray = resultObject.get("rolesList").getAsJsonArray();
                            rolesList = new ArrayList<>();
                            for (int i=0; i<jsonArray.size(); i++){
                                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                                AccountListResultObject accountListResultObject = new AccountListResultObject();
                                String userRole = jsonObject.get("type_code").getAsString();
                                if(userRole.equals(Constants.OTC))
                                {
                                }
                                else if(userRole.equals(Constants.SuperAdmin))
                                {

                                }
                                else if( userRole.equals(Constants.Admin))
                                {
                                }
                                else {
                                    accountListResultObject.setTypeId(jsonObject.get("type_id").getAsInt());
                                    accountListResultObject.setTypeCode(jsonObject.get("type_code").getAsString());
                                    accountListResultObject.setTypeDescription(jsonObject.get("type_description").getAsString());
                                    rolesList.add(accountListResultObject);
                                    CartDatabase.init(loginContext);
                                    CartDatabase.addUserRoles(accountListResultObject);
                                }
                            }
                            proceedToNextScreen(rolesList);

                        }

                    }

                    else {

                        Toast toast = Toast.makeText(loginContext, errorMsg, Toast.LENGTH_LONG);
                        View view = toast.getView();
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                        toast.show();
                    }
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                }else {
                    hideloader();
                    ll_button_login.setEnabled(true);
                    Toast toast = Toast.makeText(loginContext, R.string.wrong_credentials, Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideloader();
                ll_button_login.setEnabled(true);
                Toast toast = Toast.makeText(loginContext, R.string.server_error, Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                toast.show();

            }
        });

    }


    private void InitViews() {
        loginContext = LoginActivity.this;
        toolbar = findViewById(R.id.toolbar);
        tv_first = findViewById(R.id.tv_first);
        tv_second = findViewById(R.id.tv_second);
        preferences = new MPreferences(loginContext);
        ll_root_login  = findViewById(R.id.ll_root_login);
        tv_forgotPassword  = findViewById(R.id.tv_forgotPassword);
        tv_copyrights  = findViewById(R.id.tv_copyrights);
        tv_resend_otp  = findViewById(R.id.tv_resend_otp);
        ll_button_login = findViewById(R.id.ll_button_login);
        ll_layout_forgot_password = findViewById(R.id.ll_layout_forgot_password);
        ll_button_signup = findViewById(R.id.ll_button_signup);
        ll_password = findViewById(R.id.ll_password);
        ll_layout_otp = findViewById(R.id.ll_layout_otp);
        ll_click_here = findViewById(R.id.ll_click_here);
        ll_button_submit = findViewById(R.id.ll_button_submit);
        sv_login = findViewById(R.id.sv_login);
        input_otp = findViewById(R.id.input_otp);
        edt_otp = findViewById(R.id.edt_otp);
        edt_userName = findViewById(R.id.edt_userName);
        edt_userPassword = findViewById(R.id.edt_userPassword);
        rg_login_role = findViewById(R.id.rg_login_role);
        rb_shopper = findViewById(R.id.rb_shopper);
        rb_consumer = findViewById(R.id.rb_consumer);
        view_divider = findViewById(R.id.view_divider);
        CartDatabase.init(loginContext);
        CartDatabase.clearProductCart();
        ll_button_login.setEnabled(true);
        tv_copyrights.setText(getString(R.string.powered_by_text));
        authorityKey = Constants.AuthorizationKey;
        grantType = Constants.GrantType;
        MApplication.setBoolean(loginContext, Constants.IsFirstDownload, true);
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf");
        tv_first.setTypeface(typeface);
        tv_second.setTypeface(typeface);
        tv_forgotPassword.setTypeface(typeface);
        tv_copyrights.setTypeface(typeface);
        rb_shopper.setChecked(true);
        ll_layout_forgot_password.setVisibility(View.VISIBLE);
        edt_userName.setHint("Mobile No./ Email-Id");
        MApplication.setBoolean(loginContext, Constants.IsRoleSelected, false);
        MApplication.setString(loginContext, Constants.PendingOrderCount, "");
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce){

            if(ll_layout_otp.getVisibility()==View.VISIBLE)
            {
                ll_layout_otp.setVisibility(View.GONE);
                sv_login.setVisibility(View.VISIBLE);
                ll_button_login.setEnabled(true);
            }
            else
            {exitToast.show();
                doubleBackToExitPressedOnce = false;}
        } else{
            finishAffinity();
            finish();
            // Do exit app or back press here
            super.onBackPressed();
        }
    }

    public void showLoaderNew() {
        runOnUiThread(new Runloader(getResources().getString(R.string.loading)));
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
                    dialog = new Dialog(loginContext,R.style.Theme_AppCompat_Light_DarkActionBar);
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
                imgeView.post(() -> {
                    if (animationDrawable != null)
                        animationDrawable.start();
                });
            } catch (Exception e)
            {

            }
        }
    }

    public void hideloader() {
        runOnUiThread(() -> {
            try
            {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }); }


    private void serviceCallForSendingOTP(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SendOTPDataInterface service = retrofit.create(SendOTPDataInterface.class);
        Call<JsonElement> call = service.getStringScalar(UserId, new SendOTPPostParameters(mobileNumForOTP));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    Toast.makeText(loginContext, errorMsg, Toast.LENGTH_SHORT).show();
                    if(errorCode==0)
                    {
                        JsonObject resultObject = jsonObject.get("result").getAsJsonObject();
                        mobileNumForOTP = resultObject.get("mobile").getAsString();
                    }

                }else {
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(loginContext, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }


    private void serviceCallForConsumerLogin() {

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(Constants.LIVE_BASE_URL)
                .baseUrl(Constants.LIVE_BASE_URL_New)
                //.baseUrl("http://182.18.177.27/TUUserManagement/api/user/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginDataInterface service = retrofit.create(LoginDataInterface.class);

        Call<JsonElement> call = service.loginCallConsumer(authorityKey, new LoginPostParameters(mobileNumForOTP, otp, grantType));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.body()!=null){

                    JsonObject responseObject = response.body().getAsJsonObject();
                    JsonObject infoObject = responseObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();

                    if(errorCode==0)
                    {
                        JsonObject resultObject = responseObject.get("result").getAsJsonObject();
                        String userRoles = resultObject.get("type").getAsString();
                        //List<String> rolesList = Arrays.asList(userRoles.split(","));
                        MApplication.setString(loginContext, Constants.AccessToken, resultObject.get("access_token").getAsString());
                        MApplication.setString(loginContext, Constants.RefreshToken, resultObject.get("refresh_token").getAsString());
                        MApplication.setString(loginContext, Constants.UserName, resultObject.get("userName").getAsString());
                        MApplication.setString(loginContext, Constants.UserID, resultObject.get("userId").getAsString());
                        MApplication.setString(loginContext, Constants.CompanyID, resultObject.get("companyId").getAsString());
                        MApplication.setBoolean(loginContext, Constants.IsLoggedIn, true);
                        MApplication.setString(loginContext, Constants.UserType, userRoles);
                        /* rolesList = new ArrayList<>();
                        for (int i=0; i<jsonArray.size(); i++){
                            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                            AccountListResultObject accountListResultObject = new AccountListResultObject();
                            accountListResultObject.setTypeId(jsonObject.get("type_id").getAsInt());
                            accountListResultObject.setTypeCode(jsonObject.get("type_code").getAsString());
                            accountListResultObject.setTypeDescription(jsonObject.get("type_description").getAsString());
                            rolesList.add(accountListResultObject);
                            CartDatabase.init(context);
                            CartDatabase.addUserRoles(accountListResultObject);
                        }*/
                        proceedToNextScreen();

                    }

                    else {

                        Toast toast = Toast.makeText(loginContext, errorMsg, Toast.LENGTH_LONG);
                        View view = toast.getView();
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                        toast.show();
                    }
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast toast = Toast.makeText(loginContext, R.string.wrong_credentials, Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast toast = Toast.makeText(loginContext, R.string.server_error, Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                toast.show();

            }
        });

    }

    private void proceedToNextScreen() {
        Intent intent = new Intent(loginContext, MainActivity.class);
        startActivity(intent);
    }


}
