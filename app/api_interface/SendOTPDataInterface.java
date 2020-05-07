package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.post_parameters.SendOTPPostParameters;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface SendOTPDataInterface {

    @POST(Constants.SendOTP)
    Call<JsonElement> getStringScalar(@Header("authUser") int headerValue, @Body SendOTPPostParameters postParameter);
}
