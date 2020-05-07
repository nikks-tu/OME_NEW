package com.techuva.ome_new.api_interface;


import com.google.gson.JsonElement;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.post_parameters.ConsumerRegisterPostParameter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface CustomerRegisterDataInterface {

    @POST(Constants.ConsumerSignUp)
    Call<JsonElement> getStringScalar(@Header("authUser") int headerValue, @Body ConsumerRegisterPostParameter postParameter);
}
