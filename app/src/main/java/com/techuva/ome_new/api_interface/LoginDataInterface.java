package com.techuva.ome_new.api_interface;


import com.google.gson.JsonElement;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.LoginMainObject;
import com.techuva.ome_new.post_parameters.LoginPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface LoginDataInterface {

    @FormUrlEncoded
    @POST(Constants.LoginData)
   Call<JsonElement> loginCallNew(@Header("authorization") String authorization, @Field("username") String username, @Field("password") String password, @Field("grant_type") String grant_type);

    @FormUrlEncoded
    @POST(Constants.LoginData)
   Call<LoginMainObject> loginCall(@Header("authorization") String authorization, @Field("username") String username, @Field("password") String password, @Field("grant_type") String grant_type);

    @FormUrlEncoded
    @POST(Constants.LoginData)
   Call<JsonElement> refreshSession(@Header("authorization") String authorization, @Field("grant_type") String grant_type, @Field("refresh_token") String refresh_token);

    //@FormUrlEncoded
    @POST(Constants.ValidateOTPForConsumer)
    Call<JsonElement> loginCallConsumer(@Header("authorization") String authorization, @Body LoginPostParameters postParameter);


}
