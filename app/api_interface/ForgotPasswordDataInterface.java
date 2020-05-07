package com.example.ordermadeeasy.api_interface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.post_parameters.ForgotPassPostParameters;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ForgotPasswordDataInterface {

    @POST(Constants.ForgetPassword)
    Call<JsonElement> getStringScalar(@Body ForgotPassPostParameters postParameter);

}
