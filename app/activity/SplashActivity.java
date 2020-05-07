package com.example.ordermadeeasy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ordermadeeasy.MainActivity;
import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.views.MApplication;
import com.example.ordermadeeasy.views.MPreferences;

public class SplashActivity extends AppCompatActivity {

    Context context;
    MPreferences preferences;
    String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

           @Override

            public void run() {
                        checkIfLoggedIn();
            }

        }, 2000);

    }

    private void init() {
        context = SplashActivity.this;
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
                Intent i = new Intent(context, MainActivity.class);
                //MApplication.setString(context, Constants.PendingOrderCount, "");
                MApplication.setBoolean(context, Constants.IsFromFavourites, false);
                startActivity(i);
            }

        }
        else
        {
            Intent i = new Intent(context, LoginActivity.class);
            startActivity(i);}
         }

}
