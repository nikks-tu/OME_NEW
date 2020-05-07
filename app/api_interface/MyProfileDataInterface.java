package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.MyProfileMainObject;
import com.example.ordermadeeasy.object_models.PlaceOrderMainObject;
import com.example.ordermadeeasy.post_parameters.MyProfilePostParameters;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MyProfileDataInterface {

    @POST(Constants.GettingMyProfile)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<MyProfileMainObject> getStringScalar(@Header("authUser") int headerValue, @Body MyProfilePostParameters postParameter);

    @POST(Constants.GettingMyProfile)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<MyProfileMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body MyProfilePostParameters postParameter);

}
