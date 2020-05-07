package com.techuva.ome_new.api_interface;


import com.google.gson.JsonElement;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.post_parameters.ChangePasswordPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ChangePasswordDataInterface {

    @POST(Constants.ChangePassword)
    Call<JsonElement> getStringScalar(@Header("authUser") int headerValue, @Body ChangePasswordPostParameters postParameter);

    @POST(Constants.ChangePassword)
    Call<JsonElement> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body ChangePasswordPostParameters postParameter);

}
