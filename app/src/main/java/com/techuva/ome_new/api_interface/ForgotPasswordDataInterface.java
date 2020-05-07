package com.techuva.ome_new.api_interface;
import com.google.gson.JsonElement;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.post_parameters.ForgotPassPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ForgotPasswordDataInterface {

    @POST(Constants.ForgetPassword)
    Call<JsonElement> getStringScalar(@Body ForgotPassPostParameters postParameter);

}
