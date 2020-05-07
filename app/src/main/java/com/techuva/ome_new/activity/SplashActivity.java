package com.techuva.ome_new.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.ome_new.BuildConfig;
import com.techuva.ome_new.MainActivity;
import com.techuva.ome_new.R;
import com.techuva.ome_new.api_interface.OrderStatusDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.post_parameters.VersionCheckInfoPostParameters;
import com.techuva.ome_new.views.MApplication;
import com.techuva.ome_new.views.MPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class
SplashActivity extends AppCompatActivity {

    Context context;
    MPreferences preferences;
    String userRole;
    String userId;
    String accessToken;
    int pendingOrderCount;
    int cartSize;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        if(MApplication.isNetConnected(context))
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    serviceCallForVersionCheck();

                }
            }, 2000);
        }
        else {
            showInternetDialog("Please check your internet and retry!");
        }

    }



    private void init() {
        context = SplashActivity.this;
        mActivity = SplashActivity.this;
        preferences = new MPreferences(context);
    }

    private void checkIfLoggedIn() {
        if(preferences.isLoggedIn())
        {
            userRole = MApplication.getString(context, Constants.UserType);
            if(userRole.equals(Constants.Supplier)){
                Intent i = new Intent(context, SupplierMainActivity.class);
                startActivity(i);
            }
            else {
                userId = MApplication.getString(context, Constants.UserID);
                accessToken = "Bearer "+MApplication.getString(context, Constants.AccessToken);
                //updatePendingOrderCount();
                Intent i = new Intent(context, MainActivity.class);
                //MApplication.setString(context, Constants.PendingOrderCount, "");
                MApplication.setBoolean(context, Constants.IsFromFavourites, false);
                startActivity(i);
            }

        }
        else
        {
            Intent i = new Intent(context, LoginActivity.class);
            startActivity(i);
        }
    }

    private void updatePendingOrderCount() {
        if(userRole.equals(Constants.Shopper))
        {
            MApplication.serviceCallforShopperCartProducts(context, String.valueOf(userId), accessToken);
            MApplication.serviceCallforShopperPendingOrders(context, accessToken, String.valueOf(userId), userRole);
            if(MApplication.getString(context, Constants.CartSize).equals(""))
            {
                cartSize = 0;
            }
            else {
                cartSize = Integer.parseInt(MApplication.getString(context, Constants.CartSize));
            }
            if(MApplication.getString(context, Constants.PendingOrderCount).equals(""))
            {
                pendingOrderCount = 0;
            }
            else {
                pendingOrderCount = Integer.parseInt(MApplication.getString(context, Constants.PendingOrderCount));
            }
        }
        else if(userRole.equals(Constants.Consumer))
        {
            MApplication.serviceCallforCartProducts(context, String.valueOf(userId), accessToken);
            MApplication.serviceCallforConsumerPendingOrders(context, accessToken, String.valueOf(userId), userRole);
            if(MApplication.getString(context, Constants.CartSize).equals("") || MApplication.getString(context, Constants.CartSize).equals("0"))
            {
                cartSize = 0;
            }
            else {
                cartSize = Integer.parseInt(MApplication.getString(context, Constants.CartSize));
            }
            if(MApplication.getString(context, Constants.PendingOrderCount).equals(""))
            {
                pendingOrderCount = 0;
            }
            else {
                pendingOrderCount = Integer.parseInt(MApplication.getString(context, Constants.PendingOrderCount));
            }
        }
    }


    private void serviceCallForVersionCheck() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OrderStatusDataInterface service = retrofit.create(OrderStatusDataInterface.class);
        String appVersion = BuildConfig.VERSION_NAME;
        Call<JsonElement> call = service.checkForVersionCheck(new VersionCheckInfoPostParameters(appVersion));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject info = jsonObject.get("info").getAsJsonObject();
                    int errorCode = info.get("ErrorCode").getAsInt();
                    {
                        if(errorCode==0)
                        {
                            JsonObject resultObject = jsonObject.get("result").getAsJsonObject();
                            boolean versionStatus = resultObject.get("AppVersionStatus").getAsBoolean();
                            if(versionStatus)
                            {
                                checkIfLoggedIn();
                            }
                            else {
                                String appLink = resultObject.get("AppLink").getAsString();
                                showCustomDialog(appLink);
                            }
                        }
                        else {
                        }
                    }
                } else {

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }


    private void showCustomDialog(String link) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = mActivity.findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        TextView tv_alertText = dialogView.findViewById(R.id.tv_alertText);
        TextView button_yes = dialogView.findViewById(R.id.button_yes);
        TextView button_no = dialogView.findViewById(R.id.button_no);
        tv_alertText.setText(context.getResources().getString(R.string.app_update));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        button_yes.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
            alertDialog.dismiss();
        });

        button_no.setOnClickListener(v -> {
           finish();
        });

        //finally creating the alert dialog and displaying it

    }


    private void showInternetDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    finish();
                });
        AlertDialog alert = builder.create();
        alert.show();

    }



}
