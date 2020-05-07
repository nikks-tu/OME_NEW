package com.example.ordermadeeasy.api_interface;


import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.LoginMainObject;
import com.example.ordermadeeasy.post_parameters.LoginPostParameters;
import com.google.gson.JsonElement;

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
   Call<JsonElement> refreshSession(@Header("authorization") String authorization, @Field("grant_type") String grant_type, @Field("refresh_token") String refresh_token );

    //@FormUrlEncoded
    @POST(Constants.ValidateOTPForConsumer)
    Call<JsonElement> loginCallConsumer( @Header("authorization") String authorization, @Body LoginPostParameters postParameter);


}
