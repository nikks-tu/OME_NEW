package com.techuva.ome_new.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.multidex.MultiDex;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.ome_new.MainActivity;
import com.techuva.ome_new.R;
import com.techuva.ome_new.adapters.CityListAdapter;
import com.techuva.ome_new.adapters.StateListAdapter;
import com.techuva.ome_new.api_interface.CityListDataInterface;
import com.techuva.ome_new.api_interface.CustomerRegisterDataInterface;
import com.techuva.ome_new.api_interface.LoginDataInterface;
import com.techuva.ome_new.api_interface.SendOTPDataInterface;
import com.techuva.ome_new.api_interface.StateListDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.fcm.Utils;
import com.techuva.ome_new.object_models.CityListMainObject;
import com.techuva.ome_new.object_models.CityNamesObject;
import com.techuva.ome_new.object_models.StateListMainObject;
import com.techuva.ome_new.object_models.StateListResultObject;
import com.techuva.ome_new.post_parameters.ConsumerRegisterPostParameter;
import com.techuva.ome_new.post_parameters.GetCityPostParameters;
import com.techuva.ome_new.post_parameters.GetStatesPostParameters;
import com.techuva.ome_new.post_parameters.LoginPostParameters;
import com.techuva.ome_new.post_parameters.SendOTPPostParameters;
import com.techuva.ome_new.utilities.ClickToSelectEditText;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ConsumerSignUpActivity extends AppCompatActivity {

    Context context;
    TextInputLayout input_first_name, input_middle_name, input_last_name, input_mobile, input_email;
    TextInputLayout input_state, input_city, input_pincode, input_address1, input_address2, input_otp;
    TextView tv_signup, tv_otp_heading, tv_resend_otp;
    FrameLayout fl_signup;
    EditText edt_first_name, edt_middle_name, edt_last_name, edt_mobile, edt_email, edt_pincode;
    EditText edt_address1, edt_address2, edt_otp;
    ClickToSelectEditText spin_state, spin_city;
    LinearLayout ll_layout_otp, ll_button_submit, ll_click_here, ll_sign_up;
    FrameLayout toolbar;
    TextView tv_first, tv_second;
    int UserId;
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    String accessToken;
    String searchKey ="";
    String  pagePerCount="ALL", pageNumber="";
    AppCompatActivity mActivity;
    ArrayList<StateListResultObject> stateList;
    ArrayList<CityNamesObject> cityList;
    StateListAdapter stateListAdapter;
    CityListAdapter cityListAdapter;
    int stateId=0;
    int cityId=0;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNo;
    private String address1;
    private String address2;
    private String pin;
    private String emailId;
    String mobileNumForOTP="";
    String  otp, grantType;
    String authorityKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_sign_up);
        init();
        serviceCallforGettingStateList();

        spin_state.setOnItemSelectedListener((item, selectedIndex) -> {
            if(stateList.size()>0)
            {
                stateId = stateList.get(selectedIndex).getStateId();
                showLoaderNew();
                cityList = new ArrayList<>();
                serviceCallforGettingCityList();
            }

        });

        spin_city.setOnClickListener(v -> {
            if(cityList.size()>0){

            }
            else {
                Toast.makeText(context, "Please select state first.", Toast.LENGTH_SHORT).show();
            }
        });
        spin_city.setOnItemSelectedListener((item, selectedIndex) -> {
            if(cityList.size()>0){
                cityId = cityList.get(selectedIndex).getCityId();
            }
            else {
                Toast.makeText(context, "Please select state first.", Toast.LENGTH_SHORT).show();
            }
        });

        ll_button_submit.setOnClickListener(v -> {
            otp = edt_otp.getText().toString();
            if(!otp.equals(""))
            {
                serviceCall();
            }
            else {
                Toast.makeText(context, "Please enter the OTP sent on your number.", Toast.LENGTH_SHORT).show();
            }

        });

        ll_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputData();

                if(!firstName.equals("") && !lastName.equals("") && !mobileNo.equals("") && !address1.equals("") && !pin.equals("") && !emailId.equals(""))
                {
                    if(!firstName.equals("")){
                        if(!lastName.equals("")){
                            if(!mobileNo.equals("")){
                                if(mobileNo.length() == 10 && Utils.isValidMobile(mobileNo)){
                                    if(pin.length() == 6){
                                        if(!emailId.equals("")){
                                            if (Utils.isValidEmail(emailId))
                                            {
                                                if(stateId!=0){
                                                    if(cityId!=0){
                                                        showLoaderNew();
                                                        serviceCallForConsumerRegistration();

                                                    }
                                                    else {
                                                        Toast.makeText(context, "Please select city.", Toast.LENGTH_SHORT ).show();
                                                    }
                                                }
                                                else {
                                                    Toast.makeText(context, "Please select state.", Toast.LENGTH_SHORT ).show();
                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(context, "Please enter valid Email Id", Toast.LENGTH_SHORT ).show();
                                            }
                                        }
                                        else {
                                            Toast.makeText(context, "Please enter email.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(context, getString(R.string.pincode_validation), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(context, getString(R.string.valid_mobile_check), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(context, getString(R.string.empty_mobile_check), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(context, getString(R.string.last_name_check), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(context, getString(R.string.first_name_check), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, getString(R.string.mandatory_field_check), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getInputData() {
        firstName = edt_first_name.getText().toString();
        middleName = edt_middle_name.getText().toString();
        lastName = edt_last_name.getText().toString();
        mobileNo = edt_mobile.getText().toString();
        address1 = edt_address1.getText().toString();
        address2 = edt_address2.getText().toString();
        pin = edt_pincode.getText().toString();
        emailId = edt_email.getText().toString();
    }

    private void init() {
        context = ConsumerSignUpActivity.this;
        mActivity = ConsumerSignUpActivity.this;
        toolbar = findViewById(R.id.toolbar);
        tv_first = findViewById(R.id.tv_first);
        tv_second = findViewById(R.id.tv_second);
        ll_layout_otp = findViewById(R.id.ll_layout_otp);
        ll_button_submit = findViewById(R.id.ll_button_submit);
        ll_click_here = findViewById(R.id.ll_click_here);
        ll_sign_up = findViewById(R.id.ll_sign_up);
        spin_state = findViewById(R.id.spin_state);
        spin_city = findViewById(R.id.spin_city);
        fl_signup = findViewById(R.id.fl_signup);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_middle_name = findViewById(R.id.edt_middle_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        edt_pincode = findViewById(R.id.edt_pincode);
        edt_address1 = findViewById(R.id.edt_address1);
        edt_address2 = findViewById(R.id.edt_address2);
        edt_otp = findViewById(R.id.edt_otp);
        tv_signup = findViewById(R.id.tv_signup);
        tv_otp_heading = findViewById(R.id.tv_otp_heading);
        tv_resend_otp = findViewById(R.id.tv_resend_otp);
        input_first_name =  findViewById(R.id.input_first_name);
        input_middle_name = findViewById(R.id.input_middle_name);
        input_last_name = findViewById(R.id.input_last_name);
        input_mobile = findViewById(R.id.input_mobile);
        input_email = findViewById(R.id.input_email);
        input_state = findViewById(R.id.input_state);
        input_city = findViewById(R.id.input_city);
        input_pincode = findViewById(R.id.input_pincode);
        input_address1 = findViewById(R.id.input_address1);
        input_address2 = findViewById(R.id.input_address2);
        input_otp = findViewById(R.id.input_otp);
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf");
        edt_first_name.setTypeface(typeface);
        edt_middle_name.setTypeface(typeface);
        edt_last_name.setTypeface(typeface);
        edt_mobile.setTypeface(typeface);
        edt_email.setTypeface(typeface);
        edt_pincode.setTypeface(typeface);
        edt_address1.setTypeface(typeface);
        edt_address2.setTypeface(typeface);
        edt_otp.setTypeface(typeface);
        tv_signup.setTypeface(typeface);
        tv_otp_heading.setTypeface(typeface);
        tv_resend_otp.setTypeface(typeface);
        tv_first.setTypeface(typeface);
        tv_second.setTypeface(typeface);
       // UserId = Integer.parseInt(MApplication.getString(context, Constants.UserID));
        UserId=1;
        accessToken = "Bearer "+ MApplication.getString(context, Constants.AccessToken);
        cityList = new ArrayList<>();
        grantType = "password";
        authorityKey = Constants.AuthorizationKey;

    }


    private void serviceCallforGettingCityList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CityListDataInterface service = retrofit.create(CityListDataInterface.class);

         Call<CityListMainObject> call = service.getStringScalar(UserId, new GetCityPostParameters(String.valueOf(stateId)));
         call.enqueue(new Callback<CityListMainObject>() {
            @Override
            public void onResponse(Call<CityListMainObject> call, Response<CityListMainObject> response) {
               hideloader();
                if(response.body()!=null){
                    if(response.body().getInfo().getErrorCode()==0)
                    {
                        int size = response.body().getResult().getValues().size();
                        for (int i =0; i< size; i++)
                        {
                            cityList.add(response.body().getResult().getValues().get(i));
                        }

                        if(mActivity!=null && cityList.size()>0)
                        {
                            spin_city.setItems(cityList);
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CityListMainObject> call, Throwable t) {
                hideloader();
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }


    private void serviceCallforGettingStateList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StateListDataInterface service = retrofit.create(StateListDataInterface.class);

        Call<StateListMainObject> call = service.getStringScalar(UserId, new GetStatesPostParameters(searchKey, pagePerCount, pageNumber));
        call.enqueue(new Callback<StateListMainObject>() {
            @Override
            public void onResponse(Call<StateListMainObject> call, Response<StateListMainObject> response) {
                if(response.body()!=null){
                     if(response.body().getInfo().getErrorCode()==0)
                    {
                        int size = response.body().getResult().size();
                        stateList = new ArrayList<>();
                        for (int i =0; i< size; i++)
                        {
                            stateList.add(response.body().getResult().get(i));
                        }

                        if(mActivity!=null)
                        {
                           spin_state.setItems(stateList);
                        }
                    }
                    else
                    {
                   }


                }else {
                    }
            }

            @Override
            public void onFailure(Call<StateListMainObject> call, Throwable t) {
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }

    private void serviceCallForConsumerRegistration(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CustomerRegisterDataInterface service = retrofit.create(CustomerRegisterDataInterface.class);
        Call<JsonElement> call = service.getStringScalar(UserId, 
                new ConsumerRegisterPostParameter(firstName, middleName, lastName, mobileNo, address1, address2,
                        cityId, stateId, pin, emailId));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                hideloader();
                if(response.body()!=null){
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if(errorCode==0)
                    {
                        JsonObject resultObject = jsonObject.get("result").getAsJsonObject();
                        mobileNumForOTP = resultObject.get("mobile").getAsString();
                       /* if(!mobileNumForOTP.equals(""))
                        {
                            //serviceCallForSendingOTP();
                        }*/
                        fl_signup.setVisibility(View.GONE);
                        ll_layout_otp.setVisibility(View.VISIBLE);
                    }else {
                        Toast.makeText(context, errorMsg , Toast.LENGTH_SHORT).show();
                    }

                }else {
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
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
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
                        MApplication.setString(context, Constants.AccessToken, resultObject.get("access_token").getAsString());
                        MApplication.setString(context, Constants.RefreshToken, resultObject.get("refresh_token").getAsString());
                        MApplication.setString(context, Constants.UserName, resultObject.get("userName").getAsString());
                        MApplication.setString(context, Constants.UserID, resultObject.get("userId").getAsString());
                        MApplication.setString(context, Constants.CompanyID, resultObject.get("companyId").getAsString());
                        MApplication.setString(context, Constants.UserMailId, resultObject.get("email").getAsString());
                        MApplication.setString(context, Constants.UserType,userRoles);
                        JsonArray jsonArray = resultObject.get("rolesList").getAsJsonArray();
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

                        Toast toast = Toast.makeText(context, errorMsg, Toast.LENGTH_LONG);
                        View view = toast.getView();
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                        toast.show();
                    }
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast toast = Toast.makeText(context, R.string.wrong_credentials, Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast toast = Toast.makeText(context, R.string.server_error, Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                toast.show();

            }
        });

    }

    private void proceedToNextScreen() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }



    public void showLoaderNew() {
        mActivity.runOnUiThread(new ConsumerSignUpActivity.Runloader(getResources().getString(R.string.loading)));
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
        }); }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
