package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.post_parameters.ConsumerRegisterPostParameter;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface CustomerRegisterDataInterface {

    @POST(Constants.ConsumerSignUp)
    Call<JsonElement> getStringScalar(@Header("authUser") int headerValue, @Body ConsumerRegisterPostParameter postParameter);
}
