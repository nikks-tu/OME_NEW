package com.techuva.ome_new.api_interface;

import com.google.gson.JsonElement;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.post_parameters.SendOTPPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface SendOTPDataInterface {

    @POST(Constants.SendOTP)
    Call<JsonElement> getStringScalar(@Header("authUser") int headerValue, @Body SendOTPPostParameters postParameter);
}
